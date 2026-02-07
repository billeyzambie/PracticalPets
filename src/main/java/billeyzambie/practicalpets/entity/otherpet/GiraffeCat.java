package billeyzambie.practicalpets.entity.otherpet;

import billeyzambie.practicalpets.entity.PracticalPet;
import billeyzambie.practicalpets.entity.StayStillGoalMob;
import billeyzambie.practicalpets.goal.OcelotAttackIfShouldGoal;
import billeyzambie.practicalpets.goal.StayStillGoal;
import billeyzambie.practicalpets.misc.PPEntities;
import billeyzambie.practicalpets.misc.PPSerializers;
import billeyzambie.practicalpets.misc.PPSounds;
import billeyzambie.practicalpets.util.PPUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.animal.CatVariant;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

public class GiraffeCat extends PracticalPet implements StayStillGoalMob {
    public GiraffeCat(EntityType<? extends TamableAnimal> entityType, Level level) {
        super(entityType, level);
    }

    private static final HashMap<Integer, Integer> VARIANT_SPAWN_WEIGHTS = new HashMap<>() {{
        put(0, 33);
        put(1, 33);
        put(2, 33);
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
        return 16;
    }

    @Override
    public int getLevel1AttackDamage() {
        return 3;
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
    public boolean shouldDefendSelf() {
        return true;
    }

    @Override
    public boolean shouldDefendOwner(@NotNull LivingEntity target) {
        return true;
    }

    @Override
    protected boolean shouldRegisterSpreadingAnger() {
        return true;
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
        if (height < 2) {
            height = 2;
            if (this.isLadder())
                this.stopLadder();
        }
        if (height > 64) {
            height = 64;
        }
        int prevHeight = this.getLadderHeight();
        if (height > prevHeight)
            this.playSound(PPSounds.GROW2.get());
        else if (height < prevHeight)
            this.playSound(PPSounds.SHRINK2.get());
        this.entityData.set(LADDER_HEIGHT, height);
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
    protected SoundEvent getHurtSound(@NotNull DamageSource p_28160_) {
        return SoundEvents.CAT_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.CAT_DEATH;
    }

    @Override
    public float getVoicePitch() {
        return super.getVoicePitch() * 0.8f;
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
            } else if (partner instanceof Cat cat) {
                baby.setCatHybrid(true);
                baby.setVariant(CAT_VARIANT_TO_INT.getOrDefault(cat.getVariant(), 10));
                baby.setSnoutless(this.isSnoutless());
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

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new StayStillGoal(this));
    }

    @Override
    public boolean shouldStayStill() {
        return !this.noCurrentAbility();
    }

    public void toggleLadder() {
        if (this.noCurrentAbility())
            this.becomeLadder();
        else if (this.isLadder())
            this.stopLadder();
    }

    public void becomeLadder() {
        if (!this.noCurrentAbility() || !(this.getOwner() instanceof Player owner))
            return;

        this.setCurrentAbility(CurrentAbility.LADDER);
        this.setLadderHeight(2);
        this.playSound(PPSounds.GROW.get());
        this.setFollowMode(FollowMode.SITTING);

        float roundedOwnerYRot = Math.round(owner.getYRot() / 45) * 45;
        this.setYRot(roundedOwnerYRot);
        this.teleportTo(
                Math.floor(this.position().x) + 0.5,
                this.position().y,
                Math.floor(this.position().z) + 0.5
        );

        BlockPos blockInFront = PPUtil.getBlockPosInFront(this);
        BlockPos blockAbove = this.blockPosition().above();

        if (PPUtil.isSolid(this, blockAbove.above()) || PPUtil.isSolid(this, blockAbove)) {
            MinecraftServer server = this.getServer();
            assert server != null : "GiraffeCat::becomeLadder called on client";
            server.execute(this::stopLadder);
            return;
        }

        //If the giraffe cat has a solid block in front of it, try to predict how tall the player wants it to be
        if (!PPUtil.isSolid(this, blockInFront))
            return;

        blockAbove = blockAbove.above();

        for (int ladderHeight = 2; ladderHeight <= 64; ladderHeight++) {
            blockInFront = blockInFront.above();
            blockAbove = blockAbove.above();
            if (
                    (
                            !PPUtil.isSolid(this, blockInFront)
                                    && !PPUtil.isSolid(this, blockInFront.above())
                    )
                            || PPUtil.isSolid(this, blockAbove)
                            || ladderHeight == 64
            ) {
                this.setLadderHeight(ladderHeight);
                return;
            }
        }
    }

    public void stopLadder() {
        this.setCurrentAbility(CurrentAbility.NONE);
        this.setLadderHeight(2);
        this.playSound(PPSounds.SHRINK.get());
        this.setFollowMode(FollowMode.FOLLOWING);
    }

    @Override
    public @NotNull InteractionResult mobInteract(Player player, @NotNull InteractionHand hand) {
        if (this.isLadder()) {
            if (!this.level().isClientSide()) {
                if (player.isSecondaryUseActive()) {
                    this.setLadderHeight(this.getLadderHeight() - 1);
                }
                else {
                    this.setLadderHeight(this.getLadderHeight() + 1);
                }
            }
            return InteractionResult.SUCCESS;
        }
        return super.mobInteract(player, hand);
    }


    public static final float SITTING_NECK_TOP = 15 / 16f;
    public static final float SITTING_NECK_BOTTOM = 3 / 16f;
    public static final float NECK_HEIGHT = 12 / 16f;

}
