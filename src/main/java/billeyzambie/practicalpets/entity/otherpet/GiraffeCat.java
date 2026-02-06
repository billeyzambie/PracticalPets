package billeyzambie.practicalpets.entity.otherpet;

import billeyzambie.practicalpets.entity.PracticalPet;
import billeyzambie.practicalpets.goal.OcelotAttackIfShouldGoal;
import billeyzambie.practicalpets.misc.PPEntities;
import billeyzambie.practicalpets.misc.PPSerializers;
import billeyzambie.practicalpets.util.PPUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.animal.CatVariant;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

public class GiraffeCat extends PracticalPet {
    public GiraffeCat(EntityType<? extends TamableAnimal> entityType, Level level) {
        super(entityType, level);
    }

    private static final HashMap<Integer, Integer> VARIANT_SPAWN_WEIGHTS = new HashMap<>() {{
        put(0, 50);
        put(1, 50);
        put(2, 50);
        put(3, 2);
        put(4, 3);
    }};

    @Override
    public HashMap<Integer, Integer> variantSpawnWeights() {
        return VARIANT_SPAWN_WEIGHTS;
    }

    @Override
    public @NotNull SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor level, @NotNull DifficultyInstance difficulty, @NotNull MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag tag) {
        SpawnGroupData result = super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData, tag);
        this.setSnoutless(this.random.nextInt(100) == 0);
        return result;
    }

    @Override
    public int getLevel1MaxHealth() {
        return 20;
    }

    @Override
    public int getLevel1AttackDamage() {
        return 4;
    }

    @Override
    public int getLevel10MaxHealth() {
        return 120;
    }

    @Override
    public int getLevel10AttackDamage() {
        return 21;
    }

    @Override
    public float headSizeX() {
        return 5;
    }

    @Override
    public float headSizeY() {
        return 4;
    }

    @Override
    public float headSizeZ() {
        return 5;
    }

    @Override
    public boolean isTameItem(ItemStack itemStack) {
        return super.isTameItem(itemStack)
                || itemStack.is(ItemTags.LEAVES)
                || itemStack.is(ItemTags.FISHES);
    }

    //They can eat any kind of meat but only fish can tame them
    @Override
    public boolean isFoodThatDoesntTame(ItemStack itemStack) {
        if (super.isFoodThatDoesntTame(itemStack)) return true;
        Item item = itemStack.getItem();
        FoodProperties foodProperties = item.getFoodProperties(itemStack, this);
        return foodProperties != null && foodProperties.isMeat();
    }

    @Override
    protected float getStandingEyeHeight(@NotNull Pose p_21131_, @NotNull EntityDimensions p_21132_) {
        float eyeHeightInPixels = shouldBendOver() ? 24.5f : 14.5f;
        return eyeHeightInPixels / 16f * this.getScale();
    }

    @Override
    protected @NotNull Goal createMeleeAttackGoal() {
        return new OcelotAttackIfShouldGoal(this);
    }

    private static final EntityDataAccessor<Boolean> SOLID_BLOCK_ABOVE = SynchedEntityData.defineId(GiraffeCat.class, EntityDataSerializers.BOOLEAN);

    private static final EntityDataAccessor<Boolean> IS_CAT_HYBRID = SynchedEntityData.defineId(GiraffeCat.class, EntityDataSerializers.BOOLEAN);

    private static final EntityDataAccessor<Boolean> IS_SNOUTLESS = SynchedEntityData.defineId(GiraffeCat.class, EntityDataSerializers.BOOLEAN);

    public enum CurrentAbility {NONE, YEETING, DIGGING, LADDER}
    private static final EntityDataAccessor<CurrentAbility> CURRENT_ABILITY = SynchedEntityData.defineId(GiraffeCat.class, PPSerializers.GIRAFFE_CAT_ABILITY.get());

    private static final EntityDataAccessor<Integer> LADDER_HEIGHT = SynchedEntityData.defineId(GiraffeCat.class, EntityDataSerializers.INT);

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(SOLID_BLOCK_ABOVE, false);
        this.entityData.define(IS_CAT_HYBRID, false);
        this.entityData.define(IS_SNOUTLESS, false);
        this.entityData.define(CURRENT_ABILITY, CurrentAbility.NONE);
        this.entityData.define(LADDER_HEIGHT, 2);
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setCatHybrid(compoundTag.getBoolean("IsCatHybrid"));
        this.setSnoutless(compoundTag.getBoolean("IsSnoutless"));
        this.setCurrentAbility(CurrentAbility.values()[compoundTag.getInt("CurrentAbility")]);
        this.setLadderHeight(compoundTag.getInt("LadderHeight"));
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putBoolean("IsCatHybrid", this.isCatHybrid());
        compoundTag.putBoolean("IsSnoutless", this.isSnoutless());
        compoundTag.putInt("CurrentAbility", this.getCurrentAbility().ordinal());
        compoundTag.putInt("LadderHeight", this.getLadderHeight());
    }

    public boolean isCatHybrid() {
        return this.entityData.get(IS_CAT_HYBRID);
    }
    public void setCatHybrid(boolean value) {
        this.entityData.set(IS_CAT_HYBRID, value);
    }

    public boolean isSnoutless() {
        return this.entityData.get(IS_SNOUTLESS);
    }
    public void setSnoutless(boolean value) {
        this.entityData.set(IS_SNOUTLESS, value);
    }

    public boolean hasSolidBlockAbove() {
        return this.entityData.get(SOLID_BLOCK_ABOVE);
    }
    public void setSolidBlockAbove(boolean value) {
        this.entityData.set(SOLID_BLOCK_ABOVE, value);
    }
    public boolean shouldBendOver() {
        return this.hasSolidBlockAbove() || this.isCrouching();
    }

    public CurrentAbility getCurrentAbility() {
        return this.entityData.get(CURRENT_ABILITY);
    }
    public void setCurrentAbility(CurrentAbility value) {
        this.entityData.set(CURRENT_ABILITY, value);
    }
    public boolean noCurrentAbility() {
        return this.getCurrentAbility() == CurrentAbility.NONE;
    }
    public boolean isYeeting() {
        return this.getCurrentAbility() == CurrentAbility.YEETING;
    }
    public boolean isDigging() {
        return this.getCurrentAbility() == CurrentAbility.DIGGING;
    }
    public boolean isLadder() {
        return this.getCurrentAbility() == CurrentAbility.LADDER;
    }

    public int getLadderHeight() {
        return this.entityData.get(LADDER_HEIGHT);
    }
    public void setLadderHeight(int height) {
        this.entityData.set(LADDER_HEIGHT, Mth.clamp(height, 2, 64));
    }

    private static final Vec3 NECK_BOTTOM_RIGHT = new Vec3(2, 13, -6);
    private static final Vec3 NECK_TOP_LEFT = new Vec3(-2, 26, -10);
    private static final Vec3 SIT_NECK_OFFSET = new Vec3(0, -7, 0);

    private static final boolean SPAWN_DEBUG_PARTICLES = false;

    @Override
    public void customServerAiStep() {
        super.customServerAiStep();
        if (this.getMoveControl().hasWanted()) {
            double d0 = this.getMoveControl().getSpeedModifier();
            if (d0 <= 0.6D) {
                this.setPose(Pose.CROUCHING);
                this.setSprinting(false);
            } else if (d0 >= 1.33D) {
                this.setPose(Pose.STANDING);
                this.setSprinting(true);
            } else {
                this.setPose(Pose.STANDING);
                this.setSprinting(false);
            }
        } else {
            this.setPose(Pose.STANDING);
            this.setSprinting(false);
        }

        if (this.tickCount % 2 == 1) {
            this.tickSolidBlockAbove();
        }
    }

    private void tickSolidBlockAbove() {
        Vec3 neckBottomRightInModel = NECK_BOTTOM_RIGHT;
        Vec3 neckTopLeftInModel = NECK_TOP_LEFT;

        if (this.isInSittingPose()) {
            neckBottomRightInModel = neckBottomRightInModel.add(SIT_NECK_OFFSET);
            neckTopLeftInModel = neckTopLeftInModel.add(SIT_NECK_OFFSET);
        }

        Vec3 neckBottomRight = PPUtil.modelToWorldPosition(this, neckBottomRightInModel);
        Vec3 neckTopLeft = PPUtil.modelToWorldPosition(this, neckTopLeftInModel);
        AABB neckBox = new AABB(neckBottomRight, neckTopLeft);

        if (SPAWN_DEBUG_PARTICLES && this.level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(
                    ParticleTypes.FLAME,
                    neckBottomRight.x, neckBottomRight.y, neckBottomRight.z,
                    1,
                    0, 0, 0,
                    0
            );
            serverLevel.sendParticles(
                    ParticleTypes.FLAME,
                    neckTopLeft.x, neckTopLeft.y, neckTopLeft.z,
                    1,
                    0, 0, 0,
                    0
            );
        }

        BlockPos blockPosInFront = PPUtil.getBlockPosInFront(this);
        boolean sturdyBlockFaceInFront = this.level().getBlockState(blockPosInFront)
                .isFaceSturdy(this.level(), blockPosInFront, this.getDirection().getOpposite());
        this.setSolidBlockAbove(!sturdyBlockFaceInFront && !this.level().noCollision(this, neckBox));
    }

    @Override
    protected SoundEvent getAmbientSound() {
        if (this.isTame()) {
            if (this.isInLove()) {
                return SoundEvents.CAT_PURR;
            } else {
                return this.random.nextInt(4) == 0 ? SoundEvents.CAT_PURREOW : SoundEvents.CAT_AMBIENT;
            }
        } else {
            return SoundEvents.CAT_STRAY_AMBIENT;
        }
    }

    @Override
    public int getAmbientSoundInterval() {
        return 120;
    }

    public void hiss() {
        this.playSound(SoundEvents.CAT_HISS, this.getSoundVolume(), this.getVoicePitch());
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource p_28160_) {
        return SoundEvents.CAT_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.CAT_DEATH;
    }

    @Override
    public float getVoicePitch() {
        return super.getVoicePitch() * 0.9f;
    }

    @Override
    public boolean canMate(@NotNull Animal animal) {
        if (animal == this) {
            return false;
        } else if (!(animal instanceof GiraffeCat) && !(animal instanceof Cat)) {
            return false;
        } else {
            return this.isInLove() && animal.isInLove();
        }
    }

    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob partner) {
        GiraffeCat baby = PPEntities.GIRAFFE_CAT.get().create(level);

        if (baby != null) {
            if (this.isTame()) {
                baby.setOwnerUUID(this.getOwnerUUID());
                baby.setTame(true);
            }

            if (partner instanceof GiraffeCat giraffeCat) {
                if (this.random.nextBoolean()) {
                    baby.setVariant(this.getVariant());
                    baby.setCatHybrid(this.isCatHybrid());
                } else {
                    baby.setVariant(giraffeCat.getVariant());
                    baby.setCatHybrid(giraffeCat.isCatHybrid());
                }

                if (this.random.nextBoolean()) {
                    baby.setSnoutless(this.isSnoutless());
                } else {
                    baby.setSnoutless(giraffeCat.isSnoutless());
                }
            }

            else if (partner instanceof Cat cat) {
                baby.setCatHybrid(true);
                baby.setVariant(CAT_VARIANT_TO_INT.getOrDefault(cat.getVariant(), 10));
            }
        }

        return baby;
    }

    public static final HashMap<CatVariant, Integer> CAT_VARIANT_TO_INT = new HashMap<>() {{
        int i = 0;
        put(BuiltInRegistries.CAT_VARIANT.getOrThrow(CatVariant.WHITE), i++);
        put(BuiltInRegistries.CAT_VARIANT.getOrThrow(CatVariant.BLACK), i++);
        put(BuiltInRegistries.CAT_VARIANT.getOrThrow(CatVariant.RED), i++);
        put(BuiltInRegistries.CAT_VARIANT.getOrThrow(CatVariant.SIAMESE), i++);
        put(BuiltInRegistries.CAT_VARIANT.getOrThrow(CatVariant.BRITISH_SHORTHAIR), i++);
        put(BuiltInRegistries.CAT_VARIANT.getOrThrow(CatVariant.CALICO), i++);
        put(BuiltInRegistries.CAT_VARIANT.getOrThrow(CatVariant.PERSIAN), i++);
        put(BuiltInRegistries.CAT_VARIANT.getOrThrow(CatVariant.RAGDOLL), i++);
        put(BuiltInRegistries.CAT_VARIANT.getOrThrow(CatVariant.TABBY), i++);
        put(BuiltInRegistries.CAT_VARIANT.getOrThrow(CatVariant.ALL_BLACK), i++);
        put(BuiltInRegistries.CAT_VARIANT.getOrThrow(CatVariant.JELLIE), i++);
    }};
}
