package billeyzambie.animationcontrollers;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

public interface SwimmingAnimationEntity extends ACEntity {
    float getMinSwimSwingDelta();
    float getMinSwimSwingAmount();
    float getPrevLimbSwing();
    void setPrevLimbSwing(float value);
    float getSwimSwing();
    void addToSwimSwing(float value);
    float getSwimSwingAmount();
    void setSwimSwingAmount(float value);
    float getSwimXRot(float partialTicks);
    void setSwimXRot(float value);
    default void tickSwimAnim() {
        Entity self = (Entity) this;
        Vec3 velocity = self.getDeltaMovement();
        float swimXRot = (float) Mth.atan2(velocity.y, velocity.horizontalDistance());
        setSwimXRot(Mth.lerp(0.2f, this.getSwimXRot(1), swimXRot));

        if (self.onGround())
            this.setOnAirTime(0);
        else
            this.setOnAirTime(this.getOnAirTime() + 1);
    }

    float getOnAirTime();
    void setOnAirTime(float value);
}
