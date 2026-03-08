package billeyzambie.practicalpets.entity.fish.base;

import billeyzambie.animationcontrollers.ACData;
import billeyzambie.animationcontrollers.BVCData;
import billeyzambie.animationcontrollers.SwimmingAnimationEntity;
import billeyzambie.practicalpets.entity.PracticalPet;
import billeyzambie.practicalpets.entity.WeightedVariantEntity;
import billeyzambie.practicalpets.misc.PPSounds;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.HashMap;

public abstract class PracticalFish extends TamableFish implements SwimmingAnimationEntity, WeightedVariantEntity {
    HashMap<String, ACData> ACData = new HashMap<>();

    @Override
    public HashMap<String, ACData> getACData() {
        return ACData;
    }

    HashMap<String, BVCData> BVCData = new HashMap<>();

    @Override
    public HashMap<String, BVCData> getBVCData() {
        return BVCData;
    }

    private float prevLimbSwing = 0;

    @Override
    public float getPrevLimbSwing() {
        return prevLimbSwing;
    }

    @Override
    public void setPrevLimbSwing(float value) {
        this.prevLimbSwing = value;
    }

    private float swimSwing = 0;

    @Override
    public void addToSwimSwing(float value) {
        swimSwing += value;
    }

    @Override
    public float getSwimSwing() {
        return swimSwing;
    }

    private float swimSwingAmount = 0;

    @Override
    public float getSwimSwingAmount() {
        return swimSwingAmount;
    }

    @Override
    public void setSwimSwingAmount(float value) {
        this.swimSwingAmount = value;
    }

    private float swimXRot = 0;
    private float swimXRotO = 0;

    @Override
    public float getSwimXRot(float partialTicks) {
        return Mth.lerp(partialTicks, swimXRotO, swimXRot);
    }

    @Override
    public void setSwimXRot(float value) {
        this.swimXRot = value;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide()) {
            this.swimXRotO = this.swimXRot;
            this.tickSwimAnim();
        }
    }

    public PracticalFish(EntityType<? extends PracticalFish> p_27523_, Level p_27524_) {
        super(p_27523_, p_27524_);
    }

    @Override
    protected @NotNull SoundEvent getFlopSound() {
        return SoundEvents.COD_FLOP;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.COD_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource p_28281_) {
        return SoundEvents.COD_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return PPSounds.FISH_DEATH.get();
    }

    @Override
    public final float getVoicePitch() {
        return this.isAlive()
                ? this.getCustomVoicePitch()
                : this.getDeathSoundPitch() - 0.1f + 0.2f * this.getRandom().nextFloat();
    }

    abstract protected float getDeathSoundPitch();

    private float getCustomVoicePitch() {
        return super.getVoicePitch();
    }

    private static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(PracticalFish.class, EntityDataSerializers.INT);

    @Override
    public int getVariant() {
        return this.entityData.get(VARIANT);
    }

    @Override
    public void setVariant(int variant) {
        this.entityData.set(VARIANT, variant);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(VARIANT, 0);
    }

    protected void loadCustomData(CompoundTag tag) {
        this.loadVariant(tag);
    }

    protected void saveCustomData(CompoundTag tag) {
        this.saveVariant(tag);
    }

    @Override
    public final void readAdditionalSaveData(@NotNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.loadCustomData(tag);
    }

    @Override
    public final void addAdditionalSaveData(@NotNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        this.saveCustomData(tag);
    }

    @Override
    public final void loadFromBucketTag(@NotNull CompoundTag tag) {
        super.loadFromBucketTag(tag);
        this.loadCustomData(tag);
    }

    @Override
    public final void saveToBucketTag(@NotNull ItemStack stack) {
        super.saveToBucketTag(stack);
        this.saveCustomData(stack.getOrCreateTag());
    }

    @Override
    public @Nullable SpawnGroupData finalizeSpawn(ServerLevelAccessor p_27528_, DifficultyInstance p_27529_, MobSpawnType p_27530_, @Nullable SpawnGroupData p_27531_, @Nullable CompoundTag p_27532_) {
        this.setVariant(this.pickRandomWeightedVariant());
        return super.finalizeSpawn(p_27528_, p_27529_, p_27530_, p_27531_, p_27532_);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.25D, true));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.25D));
        this.goalSelector.addGoal(4, new AvoidEntityGoal<>(this, Player.class, 8.0F, 1.25D, 1.1D, EntitySelector.NO_SPECTATORS::test));
        this.goalSelector.addGoal(6, new FishSwimGoal(this));
        this.goalSelector.addGoal(8, new FollowFlockLeaderGoal(this));
        HurtByTargetGoal hurtByTargetGoal = new HurtByTargetGoal(this);
        if (shouldRegisterAlertOthers())
            hurtByTargetGoal = hurtByTargetGoal.setAlertOthers();
        this.targetSelector.addGoal(1, hurtByTargetGoal);
        this.targetSelector.addGoal(5, new CopyFlockLeaderTargetGoal(this));
    }

    protected boolean shouldRegisterAlertOthers() {
        return false;
    }

}