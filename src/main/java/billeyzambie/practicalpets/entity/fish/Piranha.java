package billeyzambie.practicalpets.entity.fish;

import billeyzambie.practicalpets.client.model.entity.fish.PiranhaModel;
import billeyzambie.practicalpets.misc.PPItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

public class Piranha extends PracticalFish {
    public Piranha(EntityType<? extends Piranha> type, Level level) {
        super(type, level);
    }

    private static final HashMap<Integer, Integer> VARIANT_SPAWN_WEIGHTS = new HashMap<>() {{
        put(0, 1);
        put(1, 1);
    }};

    @Override
    public HashMap<Integer, Integer> variantSpawnWeights() {
        return VARIANT_SPAWN_WEIGHTS;
    }

    @Override
    public @NotNull ItemStack getBucketItemStack() {
        return new ItemStack(PPItems.PIRANHA_BUCKET.get());
    }

    @Override
    public float headSizeX() {
        return 2;
    }

    @Override
    public float headSizeY() {
        return 2;
    }

    @Override
    public float headSizeZ() {
        return 2;
    }

    @Override
    public float getMinSwimSwingDelta() {
        return PiranhaModel.MIN_SWIM_SWING_DELTA;
    }

    @Override
    public float getMinSwimSwingAmount() {
        return PiranhaModel.MIN_SWIM_SWING_AMOUNT;
    }

    private static final EntityDataAccessor<Integer> BELLY_COLOR = SynchedEntityData.defineId(Piranha.class, EntityDataSerializers.INT);

    public static final int DEFAULT_BELLY_COLOR = 8268561;

    public int getBellyColor() {
        return this.entityData.get(BELLY_COLOR);
    }

    public void setBellyColor(int color) {
        this.entityData.set(BELLY_COLOR, color);
    }

    @Override
    protected float getDeathSoundPitch() {
        return 0.7f;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(BELLY_COLOR, DEFAULT_BELLY_COLOR);
    }

    @Override
    public void loadCustomData(@NotNull CompoundTag tag) {
        super.loadCustomData(tag);
        if (tag.contains("BellyColor"))
            this.setBellyColor(tag.getInt("BellyColor"));
    }

    @Override
    public void saveCustomData(CompoundTag tag) {
        super.saveCustomData(tag);
        tag.putInt("BellyColor", getBellyColor());
    }

    @Override
    public @Nullable SpawnGroupData finalizeSpawn(ServerLevelAccessor p_27528_, DifficultyInstance p_27529_, MobSpawnType p_27530_, @Nullable SpawnGroupData p_27531_, @Nullable CompoundTag p_27532_) {
        if (this.getRandom().nextInt(50) == 0){
            this.setBellyColor(Mth.hsvToRgb(
                    this.getRandom().nextFloat(),
                    0.86f,
                    0.49f
            ));
        }

        return super.finalizeSpawn(p_27528_, p_27529_, p_27530_, p_27531_, p_27532_);
    }

    @Override
    protected boolean shouldRegisterAlertOthers() {
        return true;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(
                this, LivingEntity.class, 60 * 20, true, true,
                target -> (
                        !(target instanceof Piranha)
                        && this.hasFollowers() && this.schoolSize * 5 >= target.getHealth()
                )
        ));
    }
}
