package billeyzambie.practicalpets.entity.dinosaur;

import billeyzambie.practicalpets.DelayedTaskManager;
import billeyzambie.practicalpets.ModEntities;
import billeyzambie.practicalpets.ModItems;
import billeyzambie.practicalpets.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.level.LevelAccessor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

public class BananaDuck extends AbstractDuck {
    private static final EntityDataAccessor<Integer> TOTAL_BANANAS_MADE = SynchedEntityData.defineId(BananaDuck.class, EntityDataSerializers.INT);

    @Override
    public int getLevel1MaxHealth() {
        return 6;
    }

    @Override
    public int getLevel1AttackDamage() {
        return 2;
    }

    @Override
    public int getLevel10MaxHealth() {
        return 100;
    }

    @Override
    public int getLevel10AttackDamage() {
        return 20;
    }

    public BananaDuck(EntityType<? extends TamableAnimal> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
    }

    public static boolean canSpawn(EntityType<BananaDuck> entityType, LevelAccessor level, MobSpawnType spawnType, BlockPos position, RandomSource random) {
        return Animal.checkAnimalSpawnRules(entityType, level, spawnType, position, random);
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
    public void addAdditionalSaveData(CompoundTag compoundTag) {
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

    public void incrementTotalBananasMade() {
        this.setTotalBananasMade(getTotalBananasMade() + 1);
    }

    public final AnimationState makingBananaAnimationState = new AnimationState();

    @Override
    public @NotNull InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (itemstack.is(Items.APPLE) && this.isTame() && !this.isBaby()) {
            makingBananaAnimationState.start(this.tickCount);
            if (!this.level().isClientSide) {
                this.usePlayerItem(player, hand, itemstack);
                this.incrementTotalBananasMade();
                if (this.getTotalBananasMade() % 5 == 0 && !player.getAbilities().instabuild) {
                    player.giveExperienceLevels(-1);
                    player.playSound(SoundEvents.EXPERIENCE_ORB_PICKUP, 1, 0.9f);
                }
                DelayedTaskManager.schedule(() -> {
                    if (this.isAlive()) {
                        this.level().playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.PLAYER_BURP, this.getSoundSource(), 1.0F, this.random.nextFloat() * 0.4F + 0.8F);
                        this.spawnAtLocation(new ItemStack(ModItems.POULTRY_BANANA.get()));
                    }
                }, 26);
            }

            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }
        return super.mobInteract(player, hand);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return ModSounds.BANANA_DUCK_AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource damageSource) {
        return ModSounds.DUCK_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.BANANA_DUCK_DEATH.get();
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob partner) {
        BananaDuck baby = ModEntities.BANANA_DUCK.get().create(level);
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
            this.getDeltaMovement().add(0, 0.1, 0);
        }
    }
}
