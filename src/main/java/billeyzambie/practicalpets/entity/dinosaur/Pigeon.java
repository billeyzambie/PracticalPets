package billeyzambie.practicalpets.entity.dinosaur;

import billeyzambie.practicalpets.entity.PracticalPet;
import billeyzambie.practicalpets.misc.PPEntities;
import billeyzambie.practicalpets.misc.PPSounds;
import billeyzambie.practicalpets.util.PPUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.Tags;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

public class Pigeon extends PracticalPet {
    public Pigeon(EntityType<? extends TamableAnimal> entityType, Level level) {
        super(entityType, level);
    }

    private static final HashMap<Integer, Integer> VARIANT_SPAWN_WEIGHTS = new HashMap<>() {{
        put(0, 48);
        put(1, 4);
        put(2, 24);
        put(3, 8);
        put(4, 8);
        put(5, 8);
        put(6, 1);
    }};

    @Override
    public HashMap<Integer, Integer> variantSpawnWeights() {
        return VARIANT_SPAWN_WEIGHTS;
    }

    @Override
    protected @NotNull ResourceLocation getDefaultLootTable() {
        return PPUtil.CHICKEN_LOOT;
    }

    @Override
    public int getLevel1MaxHealth() {
        return 4;
    }

    @Override
    public int getLevel1AttackDamage() {
        return 1;
    }

    @Override
    public int getLevel10MaxHealth() {
        return 60;
    }

    @Override
    public int getLevel10AttackDamage() {
        return 12;
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
    public boolean isTameItem(ItemStack itemStack) {
        return itemStack.is(Tags.Items.SEEDS)
                || itemStack.is(Tags.Items.CROPS_CARROT)
                || itemStack.is(Items.MELON_SLICE)
                || itemStack.is(Items.GLISTERING_MELON_SLICE)
                || super.isTameItem(itemStack);
    }

    @Override
    public HealOverride healOverride(ItemStack itemStack) {
        //Seeds and glistering melon aren't edible so a healOverride must be defined
        if (itemStack.is(Tags.Items.SEEDS))
            return HealOverride.defineNutrition(2);
        if (itemStack.is(Items.GLISTERING_MELON_SLICE))
            return HealOverride.defineNutrition(6);
        //Bread isn't very good for pigeons
        if (itemStack.is(Items.BREAD))
            return HealOverride.override(1);
        return super.healOverride(itemStack);
    }

    public final AnimationState biteFloorAnimationState = new AnimationState();

    @Override
    protected SoundEvent getAmbientSound() {
        return PPSounds.PIGEON_AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource damageSource) {
        return PPSounds.PIGEON_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return PPSounds.PIGEON_HURT.get();
    }

    @Override
    public float getVoicePitch() {
        float voicePitch = super.getVoicePitch();
        //make the death sound deeper than the hit sound
        if (!this.isAlive())
            voicePitch *= 0.6f;
        return voicePitch;
    }

    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob partner) {
        Pigeon baby = PPEntities.PIGEON.get().create(level);

        if (baby != null) {
            if (this.isTame()) {
                baby.setOwnerUUID(this.getOwnerUUID());
                baby.setTame(true);
            }

            if (partner instanceof Pigeon pigeonPartner) {
                if (this.random.nextBoolean())
                    baby.setVariant(this.getVariant());
                else
                    baby.setVariant(pigeonPartner.getVariant());
            }
        }

        return baby;
    }

    //copied from vanilla chicken
    public float flap;
    public float flapSpeed;
    public float oFlapSpeed;
    public float oFlap;
    public float flapping = 1.0F;

    private int pickRandomBiteFloorTime() {
        return this.random.nextInt(2400, 4800);
    }
    private int timeToBiteFloor = this.pickRandomBiteFloorTime();

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

    @Override
    public void aiStep() {
        super.aiStep();
        Vec3 vec3 = this.getDeltaMovement();
        if (!this.onGround() && vec3.y < 0.0D) {
            this.setDeltaMovement(vec3.multiply(1.0D, 0.6D, 1.0D));
        }

        //Copied from vanilla chicken
        this.oFlap = this.flap;
        this.oFlapSpeed = this.flapSpeed;
        this.flapSpeed += (!this.onGround() ? 4.0F : -1.0F) * 0.3F;
        this.flapSpeed = Mth.clamp(this.flapSpeed, 0.0F, 1.0F);
        if (!this.onGround() && this.flapping < 1.0F) {
            this.flapping = 1.0F;
        }
        this.flapping *= 0.9F;
        this.flap += this.flapping * 2.0F;

        if (!this.level().isClientSide() && --this.timeToBiteFloor <= 0 && this.onGround() && !this.isInSittingPose()) {
            this.timeToBiteFloor = this.pickRandomBiteFloorTime();
            this.biteFloorAnimationState.start(this.tickCount);
        }
    }
}
