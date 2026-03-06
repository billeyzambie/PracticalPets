package billeyzambie.practicalpets.entity.fish;

import billeyzambie.animationcontrollers.ACData;
import billeyzambie.animationcontrollers.BVCData;
import billeyzambie.animationcontrollers.SwimmingAnimationEntity;
import billeyzambie.practicalpets.entity.WeightedVariantEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

public abstract class PracticalFish extends TamableSchoolingFish implements SwimmingAnimationEntity, WeightedVariantEntity {
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

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.loadVariant(tag);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        this.saveVariant(tag);
    }

    @Override
    public @Nullable SpawnGroupData finalizeSpawn(ServerLevelAccessor p_27528_, DifficultyInstance p_27529_, MobSpawnType p_27530_, @Nullable SpawnGroupData p_27531_, @Nullable CompoundTag p_27532_) {
        this.setVariant(this.pickRandomWeightedVariant());
        return super.finalizeSpawn(p_27528_, p_27529_, p_27530_, p_27531_, p_27532_);
    }
}