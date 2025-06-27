package billeyzambie.practicalpets.entity.dinosaur;

import billeyzambie.practicalpets.entity.PracticalPet;
import billeyzambie.practicalpets.network.DuckIdleFlapPacket;
import billeyzambie.practicalpets.network.ModNetworking;
import billeyzambie.practicalpets.network.QuackAnimPacket;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public abstract class AbstractDuck extends PracticalPet {

    public AbstractDuck(EntityType<? extends TamableAnimal> entityType, Level level) {
        super(entityType, level);
    }

    public static final int MIN_TIME_TO_FLAP = 30 * 20;
    public static final int MAX_TIME_TO_FLAP = 100 * 20;
    public static final int IDLE_FLAP_ANIMATION_DURATION = 30;

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setIdleFlapTime(compoundTag.getInt("IdleFlapTime"));
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("IdleFlapTime", this.getIdleFlapTime());
    }

    int idleFlapTime = getIdleFlapTimeResetValue();

    public int getIdleFlapTime() {
        return idleFlapTime;
    }

    public void resetIdleFlapTime() {
        this.setIdleFlapTime(
                getIdleFlapTimeResetValue()
        );
    }

    private int getIdleFlapTimeResetValue() {
        return this.getRandom().nextIntBetweenInclusive(-MAX_TIME_TO_FLAP, -MIN_TIME_TO_FLAP);
    }

    public void setIdleFlapTime(int value) {
        this.idleFlapTime = value;
    }

    public final AnimationState quackAnimationState = new AnimationState();

    @Override
    public boolean isTameItem(ItemStack itemStack) {
        return itemStack.is(Items.BREAD);
    }

    @Override
    public void playAmbientSound() {
        super.playAmbientSound();
        if (!this.level().isClientSide && this.getTarget() == null) {
            ModNetworking.CHANNEL.send(
                    PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> this),
                    new QuackAnimPacket(this.getId())
            );
        }
    }

    @Override
    public void playHurtSound(@NotNull DamageSource damageSource) {
        super.playHurtSound(damageSource);
        if (!this.level().isClientSide && this.getTarget() == null) {
            ModNetworking.CHANNEL.send(
                    PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> this),
                    new QuackAnimPacket(this.getId())
            );
        }
    }

    @Override
    protected boolean shouldRegisterSpreadingAnger() {
        return true;
    }

    @Override
    public boolean shouldDefendSelf() {
        return true;
    }

    private static final UUID IDLE_FLAP_SLOWDOWN_UUID = UUID.fromString("a5b76512-260b-42c3-88ce-167c04996aa0");
    private static final AttributeModifier IDLE_FLAP_SLOWDOWN_MODIFIER =
            new AttributeModifier(IDLE_FLAP_SLOWDOWN_UUID, "IdleFlapSlowdown", -0.75, AttributeModifier.Operation.MULTIPLY_TOTAL);

    @Override
    public void aiStep() {
        super.aiStep();

        if (!this.level().isClientSide()) {
            boolean wasIdleFlapping = this.isIdleFlapping();
            int flapTime = this.getIdleFlapTime();
            if (flapTime > IDLE_FLAP_ANIMATION_DURATION) {
                this.resetIdleFlapTime();
            }
            else {
                int nextFlapTime = this.getIdleFlapTime() + 1;
                //pause the idle flap timer while sitting
                if (!this.isInSittingPose() || wasIdleFlapping || nextFlapTime < 0) {
                    this.setIdleFlapTime(nextFlapTime);
                }
            }
            if (this.isIdleFlapping() || wasIdleFlapping)
                ModNetworking.CHANNEL.send(
                        PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> this),
                        new DuckIdleFlapPacket(this.getId(), this.getIdleFlapTime())
                );
        }

        Vec3 vec3 = this.getDeltaMovement();
        if (!this.onGround() && vec3.y < 0.0D) {
            this.setDeltaMovement(vec3.multiply(1.0D, 0.6D, 1.0D));
        }

        AttributeInstance movementSpeed = this.getAttribute(Attributes.MOVEMENT_SPEED);
        assert movementSpeed != null : "AbstractDuck movement speed was null";
        if (this.isIdleFlapping()) {
            if (!movementSpeed.hasModifier(IDLE_FLAP_SLOWDOWN_MODIFIER)) {
                movementSpeed.addTransientModifier(IDLE_FLAP_SLOWDOWN_MODIFIER);
            }
        }
        else if (movementSpeed.hasModifier(IDLE_FLAP_SLOWDOWN_MODIFIER)) {
            movementSpeed.removeModifier(IDLE_FLAP_SLOWDOWN_MODIFIER);
        }
    }


    //isFlapping already exists in LivingEntity so I had to name it duckIsFlapping
    public boolean duckIsFlapping() {
        return (!this.onGround() && !this.isInWater()) || this.isIdleFlapping();
    }

    public boolean isIdleFlapping() {
        return this.getIdleFlapTime() > 0;
    }
}