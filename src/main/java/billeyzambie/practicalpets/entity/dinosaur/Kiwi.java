package billeyzambie.practicalpets.entity.dinosaur;

import billeyzambie.practicalpets.entity.PracticalPet;
import billeyzambie.practicalpets.misc.PPSounds;
import billeyzambie.practicalpets.misc.PPTags;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.common.Tags;
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

    public enum ShearedState{SHEARABLE, INTERMEDIATE, SHEARED}

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.timeToBiteFloor = compoundTag.getInt("TimeToBiteFloor");
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("TimeToBiteFloor", timeToBiteFloor);
    }


}
