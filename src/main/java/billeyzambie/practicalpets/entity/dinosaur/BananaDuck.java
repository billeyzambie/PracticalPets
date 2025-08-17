package billeyzambie.practicalpets.entity.dinosaur;

import billeyzambie.practicalpets.misc.PPTags;
import billeyzambie.practicalpets.util.DelayedTaskManager;
import billeyzambie.practicalpets.misc.PPEntities;
import billeyzambie.practicalpets.misc.PPItems;
import billeyzambie.practicalpets.misc.PPSounds;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

public class BananaDuck extends AbstractDuck {
    private static final EntityDataAccessor<Integer> TOTAL_BANANAS_MADE = SynchedEntityData.defineId(BananaDuck.class, EntityDataSerializers.INT);

    public BananaDuck(EntityType<? extends TamableAnimal> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(TOTAL_BANANAS_MADE, 0);

    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setTotalBananasMade(compoundTag.getInt("TotalBananasMade"));
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("TotalBananasMade", this.getTotalBananasMade());
    }

    @Override
    public HashMap<Integer, Integer> variantSpawnWeights() {
        return null;
    }

    public int getTotalBananasMade() {
        return this.entityData.get(TOTAL_BANANAS_MADE);
    }

    public void setTotalBananasMade(int value) {
        this.entityData.set(TOTAL_BANANAS_MADE, value);
    }

    public final AnimationState makingBananaAnimationState = new AnimationState();

    public TagKey<Item> getCanTurnToBananaTag() {
        int level = this.petLevel();
        if (level >= 10) {
            return PPTags.Items.LEVEL_10_TO_POULTRY_BANANA;
        }
        else if (level >= 7) {
            return PPTags.Items.LEVEL_7_TO_POULTRY_BANANA;
        }
        else if (level >= 5) {
            return PPTags.Items.LEVEL_5_TO_POULTRY_BANANA;
        }
        else {
            return PPTags.Items.LEVEL_1_TO_POULTRY_BANANA;
        }
    }

    @Override
    public @NotNull InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);

        if (itemstack.is(this.getCanTurnToBananaTag()) && this.isTame() && !this.isBaby()) {
            this.makingBananaAnimationState.start(this.tickCount);
            if (!this.level().isClientSide) {
                this.usePlayerItem(player, hand, itemstack);
                this.setTotalBananasMade(this.getTotalBananasMade() + 1);
                if (this.getTotalBananasMade() % 5 == 0 && !player.getAbilities().instabuild) {
                    if (player.experienceLevel > 0) {
                        player.giveExperienceLevels(-1);
                        player.playSound(SoundEvents.EXPERIENCE_ORB_PICKUP, 1, 0.9f);
                    }
                    else {
                        player.addEffect(new MobEffectInstance(MobEffects.WITHER, 1, 3));
                    }
                }
                DelayedTaskManager.schedule(() -> {
                    if (this.isAlive()) {
                        this.level().playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.PLAYER_BURP, this.getSoundSource(), 1.0F, this.random.nextFloat() * 0.4F + 0.8F);
                        this.spawnAtLocation(new ItemStack(PPItems.POULTRY_BANANA.get()));
                    }
                }, 26);
            }

            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }
        return super.mobInteract(player, hand);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return PPSounds.BANANA_DUCK_AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource damageSource) {
        return PPSounds.DUCK_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return PPSounds.BANANA_DUCK_DEATH.get();
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob partner) {
        BananaDuck baby = PPEntities.BANANA_DUCK.get().create(level);
        if (baby != null) {
            if (this.isTame()) {
                baby.setOwnerUUID(this.getOwnerUUID());
                baby.setTame(true);
            }
        }

        return baby;
    }

    @Override
    public float headSizeX() {
        return 3;
    }

    @Override
    public float headSizeY() {
        return 3;
    }

    @Override
    public float headSizeZ() {
        return 3;
    }

    @Override
    protected float getStandingEyeHeight(@NotNull Pose pose, @NotNull EntityDimensions dimensions) {
        return dimensions.height * 14.5f / 15;
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (this.isIdleFlapping()) {
            Vec3 motion = this.getDeltaMovement();
            this.setDeltaMovement(motion.x, 0.05, motion.z);
            this.hasImpulse = true;
        }
    }
}
