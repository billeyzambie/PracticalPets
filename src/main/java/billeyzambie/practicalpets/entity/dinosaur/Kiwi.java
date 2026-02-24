package billeyzambie.practicalpets.entity.dinosaur;

import billeyzambie.practicalpets.entity.PracticalPet;
import billeyzambie.practicalpets.misc.*;
import billeyzambie.practicalpets.util.PPUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.eventbus.api.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

public class Kiwi extends PracticalPet {
    public Kiwi(EntityType<? extends TamableAnimal> entityType, Level level) {
        super(entityType, level);
    }

    private static final HashMap<Integer, Integer> VARIANT_SPAWN_WEIGHTS = new HashMap<>() {{
        put(0, 29);
        put(1, 29);
        put(2, 29);
        put(3, 29);
        put(4, 29);
        put(5, 29);
        put(6, 2);
        put(7, 1);
    }};

    @Override
    public HashMap<Integer, Integer> variantSpawnWeights() {
        return VARIANT_SPAWN_WEIGHTS;
    }

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
        return 80;
    }

    @Override
    public int getLevel10AttackDamage() {
        return 16;
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
        return 4;
    }

    @Override
    public boolean isTameItem(ItemStack itemStack) {
        return itemStack.is(Items.SPIDER_EYE) || itemStack.is(Items.FERMENTED_SPIDER_EYE);
    }

    @Override
    public boolean isFoodThatDoesntTame(ItemStack itemStack) {
        return itemStack.is(Tags.Items.SEEDS) || itemStack.is(PPTags.Items.FRUITS);
    }

    @Override
    protected @Nullable SoundEvent getAmbientSound() {
        return PPSounds.KIWI_AMBIENT.get();
    }

    @Override
    protected @Nullable SoundEvent getHurtSound(@NotNull DamageSource damageSource) {
        return PPSounds.KIWI_HURT.get();
    }

    @Override
    protected @Nullable SoundEvent getDeathSound() {
        return PPSounds.KIWI_DEATH.get();
    }

    @Override
    protected float getSoundVolume() {
        return 0.5f;
    }

    public static boolean kiwiCanSpawn(EntityType<? extends Kiwi> p_218105_, LevelAccessor p_218106_, MobSpawnType p_218107_, BlockPos p_218108_, RandomSource p_218109_) {
        return p_218106_.getBlockState(p_218108_.below()).is(BlockTags.ANIMALS_SPAWNABLE_ON) && isBrightEnoughToSpawn(p_218106_, p_218108_);
    }

    protected static boolean isBrightEnoughToSpawn(BlockAndTintGetter p_186210_, @NotNull BlockPos p_186211_) {
        return p_186210_.getRawBrightness(p_186211_, 0) < 10;
    }

    @Override
    public void aiStep() {
        super.aiStep();

        //Allow the kiwi to have an attack animation
        this.updateSwingTime();
    }

    @Override
    protected void customServerAiStep() {
        super.customServerAiStep();

        if (--this.timeToBiteFloor <= 0 && this.getNavigation().isDone()) {
            if (!this.isInSittingPose() && this.onGround())
                this.sendRandomIdle1Packet();
            this.timeToBiteFloor = this.pickRandomBiteFloorTime();
        }
    }

    private int timeToBiteFloor = this.pickRandomBiteFloorTime();

    private int pickRandomBiteFloorTime() {
        return this.random.nextInt(600, 1200);
    }

    public enum ShearedState {SHEARABLE, INTERMEDIATE, SHEARED}

    private static final EntityDataAccessor<ShearedState> SHEARED_STATE = SynchedEntityData.defineId(
            Kiwi.class,
            PPSerializers.KIWI_SHEARED_STATE.get()
    );

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(SHEARED_STATE, ShearedState.SHEARABLE);
    }

    public ShearedState getShearedState() {
        return this.entityData.get(SHEARED_STATE);
    }

    public void setShearedState(ShearedState shearedState) {
        this.entityData.set(SHEARED_STATE, shearedState);
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.timeToBiteFloor = compoundTag.getInt("TimeToBiteFloor");
        this.setShearedState(ShearedState.values()[compoundTag.getInt("ShearedState")]);
        this.shearTime = compoundTag.getInt("ShearTime");
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("TimeToBiteFloor", timeToBiteFloor);
        compoundTag.putInt("ShearedState", this.getShearedState().ordinal());
        compoundTag.putInt("ShearTime", shearTime);
    }

    @Override
    public @NotNull InteractionResult mobInteract(Player player, @NotNull InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        Item item = stack.getItem();
        if (this.getShearedState() == ShearedState.SHEARABLE) {
            if (item instanceof ShearsItem) {
                if (this.level().isClientSide()) {
                    this.level().playSound(player, this.getX(), this.getY(), this.getZ(), SoundEvents.SHEEP_SHEAR, this.getSoundSource(), 1.0F, this.random.nextFloat() * 0.4F + 0.8F);
                    return InteractionResult.SUCCESS;
                } else {
                    stack.hurtAndBreak(1, player, lambdaPlayer ->
                            lambdaPlayer.broadcastBreakEvent(hand)
                    );
                    this.setShearedState(ShearedState.SHEARED);
                    this.setRandomShearTime();
                    //Just in case a mod makes shears enchantable with fortune I guess
                    int featherCount = this.random.nextIntBetweenInclusive(1, 2)
                            + stack.getEnchantmentLevel(Enchantments.BLOCK_FORTUNE);
                    this.spawnAtLocation(new ItemStack(PPItems.KIWI_FEATHERS.get(), featherCount));
                    if (player == this.getOwner())
                        PPAdvancementTriggers.USED_PET_ABILITY.trigger((ServerPlayer) player, this, 0);
                    return InteractionResult.CONSUME;
                }
            }
        } else if (item instanceof BoneMealItem) {
            if (this.level().isClientSide()) {
                PPUtil.playBoneMealEffectsAt(this, 15);
                return InteractionResult.SUCCESS;
            } else {
                stack.shrink(1);
                this.setNextShearedState();
                return InteractionResult.CONSUME;
            }
        }
        return super.mobInteract(player, hand);
    }

    private void setRandomShearTime() {
        float levelProgress1to10 = (this.petLevel() - 1) / 9f;
        int i = Math.max(10,
                (int) Mth.lerp(levelProgress1to10, 120 * 20, 30)
        );
        this.shearTime = this.random.nextInt(i, i * 2);
    }

    private int shearTime;

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide()) {
            ShearedState shearedState = this.getShearedState();
            if (shearedState != ShearedState.SHEARABLE && this.shearTime-- <= 0) {
                this.setNextShearedState();
            }
        }
    }

    private void setNextShearedState() {
        switch (this.getShearedState()) {
            case SHEARED -> this.setShearedState(ShearedState.INTERMEDIATE);
            case INTERMEDIATE -> this.setShearedState(ShearedState.SHEARABLE);
        }
        this.setRandomShearTime();
    }

    //Make them poison immune so that they don't get poisoned by being healed with spider eyes
    @Override
    public boolean canBeAffected(MobEffectInstance p_33809_) {
        if (p_33809_.getEffect() == MobEffects.POISON) {
            MobEffectEvent.Applicable event = new MobEffectEvent.Applicable(this, p_33809_);
            MinecraftForge.EVENT_BUS.post(event);
            return event.getResult() == Event.Result.ALLOW;
        }
        return super.canBeAffected(p_33809_);
    }
}
