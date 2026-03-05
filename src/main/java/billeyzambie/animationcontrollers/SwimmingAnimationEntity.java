package billeyzambie.animationcontrollers;

public interface SwimmingAnimationEntity extends ACEntity {
    float getMinSwimSwingDelta();
    float getMinSwimSwingAmount();
    float getPrevLimbSwing();
    void setPrevLimbSwing(float value);
    float getSwimSwing();
    void addToSwimSwing(float value);
    float getSwimSwingAmount();
    void setSwimSwingAmount(float value);
}
