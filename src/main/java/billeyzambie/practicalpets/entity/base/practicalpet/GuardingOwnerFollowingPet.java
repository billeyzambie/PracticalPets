package billeyzambie.practicalpets.entity.base.practicalpet;

import billeyzambie.practicalpets.entity.base.MobInterface;
import billeyzambie.practicalpets.misc.PPTags;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

public interface GuardingOwnerFollowingPet extends MobInterface {
    boolean petIsCurrentlyFollowingOwner();
    enum FollowMode {
        FOLLOWING("following"),
        SITTING("sitting"),
        WANDERING("wandering"),
        GUARDING("guarding");

        public final String name;
        public final String jadeTranslationString;

        FollowMode(String name) {
            this.name = name;
            this.jadeTranslationString = "ui.practicalpets." + name;
        }
    }

    FollowMode getFollowMode();
    void setFollowMode(FollowMode value);

    boolean isOrderedToSit();
    /** Should be a field, doesn't need to be synched to the client */
    @Nullable Vec3 getPetGuardCenter();
    void setPetGuardCenter(@Nullable Vec3 value);

    /** Should be a field, doesn't need to be synched to the client */
    int getPetGuardStartTime();
    void setPetGuardStartTime(int value);

    default boolean petCanStartGuarding() {
        return isGuardingPetAbleToAttack(null);
    }
    /** @param target null if just checking whether the pet can guard against things in general
     * @return {@code true} for pets that always defend their owner,
     * otherwise should return {@link PetEquipmentWearer#petShouldDefendOwner(LivingEntity)}
     * */
    boolean isGuardingPetAbleToAttack(@Nullable LivingEntity target);

    default void saveGuardingPetData(CompoundTag compoundTag) {
        Vec3 pos = getPetGuardCenter();
        if (pos == null)
            return;
        compoundTag.putDouble("PPetGuardCenterX", pos.x());
        compoundTag.putDouble("PPetGuardCenterY", pos.y());
        compoundTag.putDouble("PPetGuardCenterZ", pos.z());
    }

    default void loadGuardingPetData(CompoundTag compoundTag) {
        if (compoundTag.contains("PPetGuardCenterX", Tag.TAG_DOUBLE)) {
            this.setPetGuardCenter(new Vec3(
                    compoundTag.getDouble("PPetGuardCenterX"),
                    compoundTag.getDouble("PPetGuardCenterY"),
                    compoundTag.getDouble("PPetGuardCenterZ")
            ));
        }
    }

    default void petStartGuarding() {
        setPetGuardCenter(this.position());
        setPetGuardStartTime(this.asMob().tickCount);

        if (!this.petIsCurrentlyGuarding())
            throw new IllegalStateException("Failed to make pet start guarding. Pet entered guard mode without unsitting and unfollowing first");
    }
    default void petStopGuarding() {
        setPetGuardCenter(null);
    }
    default boolean petIsCurrentlyGuarding() {
        return getPetGuardCenter() != null && !isOrderedToSit() && !petIsCurrentlyFollowingOwner();
    }
    default int getPetGuardRadius() {
        return 16;
    }
    default boolean shouldGuardingPetAttack(LivingEntity target) {
        Vec3 petGuardCenter = getPetGuardCenter();
        if (petGuardCenter == null || !(target instanceof Enemy) || !isGuardingPetAbleToAttack(target))
            return false;

        double distSqr = petGuardCenter.distanceToSqr(target.position());
        int radius = getPetGuardRadius();
        if (distSqr > radius * radius)
            return false;

        double selfDamage = this.getAttribute(Attributes.ATTACK_DAMAGE).getValue();
        double targetDamage = target.getAttribute(Attributes.ATTACK_DAMAGE).getValue();
        //not how armor works but maybe a decent approximation
        double targetHealth = target.getHealth()
                + target.getAttribute(Attributes.ARMOR).getValue()
                + target.getAttribute(Attributes.ARMOR_TOUGHNESS).getValue();
        double selfHealth = this.getHealth()
                + this.getAttribute(Attributes.ARMOR).getValue()
                + this.getAttribute(Attributes.ARMOR_TOUGHNESS).getValue();
        if (
                target.getType().is(PPTags.EntityTypes.EXPLOSIVE_ENEMIES)
                        && selfDamage < targetHealth
        ) {
            return false;
        }

        //if (targetDamage < 5 && targetHealth < 25)
        //    return true;

        double selfPower = selfHealth * selfDamage;
        double targetPower = targetHealth * Math.max(targetDamage, 2);

        return selfPower * 1.5 > targetPower;
    }

    class GuardTargetGoal<T extends Mob & GuardingOwnerFollowingPet> extends NearestAttackableTargetGoal<LivingEntity> {
        public final T pet;
        public GuardTargetGoal(T pet) {
            super(pet, LivingEntity.class, 20, false, true, pet::shouldGuardingPetAttack);
            this.pet = pet;
        }

        private boolean canGuard() {
            return pet.petIsCurrentlyGuarding() && pet.getPetGuardStartTime() + 60 < pet.tickCount;
        }

        @Override
        public boolean canUse() {
            return this.canGuard() && super.canUse();
        }

        @Override
        public boolean canContinueToUse() {
            return this.canGuard() && super.canContinueToUse();
        }
    }

    class GoToRestrictionGoal<T extends Mob & GuardingOwnerFollowingPet> extends Goal {
        private final T pet;
        private final LevelReader level;
        private static final double SPEED_MODIFIER = 1.25;
        private int timeToRecalcPath;
        private float oldWaterCost;

        public GoToRestrictionGoal(T pet) {
            this.pet = pet;
            this.level = this.pet.level();
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
            if (!(this.pet.getNavigation() instanceof GroundPathNavigation) && !(this.pet.getNavigation() instanceof FlyingPathNavigation)) {
                throw new IllegalArgumentException("Unsupported mob type for GoToRestrictionGoal");
            }
        }

        private double getStartDistance() {
            return this.pet.getPetGuardRadius();
        }

        private double getStopDistance() {
            return Mth.clamp(this.pet.getPetGuardRadius(), 2, 4);
        }

        private double getTeleportDistance() {
            return this.pet.getPetGuardRadius() + 8d;
        }

        private Vec3 getRestrictionCenter() {
            return this.pet.getPetGuardCenter();
        }

        private boolean commonCantUse() {
            return this.pet.getTarget() != null || !this.pet.petIsCurrentlyGuarding();
        }

        @Override
        public boolean canUse() {
            if (this.commonCantUse())
                return false;
            if (this.unableToMove()) {
                return false;
            } else return this.pet.distanceToSqr(this.getRestrictionCenter()) >= this.getStartDistance() * this.getStartDistance();
        }

        @Override
        public boolean canContinueToUse() {
            if (this.commonCantUse())
                return false;
            if (this.pet.getNavigation().isDone()) {
                return false;
            } else if (this.unableToMove()) {
                return false;
            } else {
                return !(this.pet.distanceToSqr(this.getRestrictionCenter()) <= this.getStopDistance() * this.getStopDistance());
            }
        }

        private boolean unableToMove() {
            return this.pet.isOrderedToSit() || this.pet.isPassenger() || this.pet.isLeashed();
        }

        @Override
        public void start() {
            this.timeToRecalcPath = 0;
            this.oldWaterCost = this.pet.getPathfindingMalus(BlockPathTypes.WATER);
            this.pet.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
        }

        @Override
        public void stop() {
            this.pet.getNavigation().stop();
            this.pet.setPathfindingMalus(BlockPathTypes.WATER, this.oldWaterCost);
        }

        @Override
        public void tick() {
            Vec3 targetPosition = this.getRestrictionCenter();
            this.pet.getLookControl().setLookAt(targetPosition);
            if (--this.timeToRecalcPath <= 0) {
                this.timeToRecalcPath = this.adjustedTickDelay(10);
                if (this.pet.distanceToSqr(targetPosition) >= this.getTeleportDistance() * this.getTeleportDistance()) {
                    this.teleportToRestrictionCenter();
                } else {
                    this.pet.getNavigation().moveTo(targetPosition.x, targetPosition.y, targetPosition.z, SPEED_MODIFIER);
                }

            }
        }

        private void teleportToRestrictionCenter() {
            Vec3 targetPos = this.getRestrictionCenter();

            for(int i = 0; i < 10; ++i) {
                int j = this.randomIntInclusive(-3, 3);
                int k = this.randomIntInclusive(-1, 1);
                int l = this.randomIntInclusive(-3, 3);
                boolean flag = this.maybeTeleportTo((int) (targetPos.x + j), (int) (targetPos.y + k), (int) (targetPos.z + l));
                if (flag) {
                    return;
                }
            }

        }

        private boolean maybeTeleportTo(int p_25304_, int p_25305_, int p_25306_) {
            if (Math.abs((double)p_25304_ - this.getRestrictionCenter().x()) < 2.0D && Math.abs((double)p_25306_ - this.getRestrictionCenter().z()) < 2.0D) {
                return false;
            } else if (!this.canTeleportTo(new BlockPos(p_25304_, p_25305_, p_25306_))) {
                return false;
            } else {
                this.pet.moveTo((double)p_25304_ + 0.5D, p_25305_, (double)p_25306_ + 0.5D, this.pet.getYRot(), this.pet.getXRot());
                this.pet.getNavigation().stop();
                return true;
            }
        }

        private boolean canTeleportTo(BlockPos p_25308_) {
            BlockPathTypes blockpathtypes = WalkNodeEvaluator.getBlockPathTypeStatic(this.level, p_25308_.mutable());
            if (blockpathtypes != BlockPathTypes.WALKABLE) {
                return false;
            } else {
                BlockPos blockpos = p_25308_.subtract(this.pet.blockPosition());
                return this.level.noCollision(this.pet, this.pet.getBoundingBox().move(blockpos));
            }
        }

        private int randomIntInclusive(int p_25301_, int p_25302_) {
            return this.pet.getRandom().nextInt(p_25302_ - p_25301_ + 1) + p_25301_;
        }
    }
}
