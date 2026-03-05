package billeyzambie.practicalpets.client.model.entity.fish;

import billeyzambie.animationcontrollers.PracticalPetModel;
import billeyzambie.animationcontrollers.SwimmingAnimationEntity;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

public abstract class SwimmingEntityModel<T extends Entity & SwimmingAnimationEntity> extends PracticalPetModel<T> {

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        float prevLimbSwing = entity.getPrevLimbSwing();
        entity.setPrevLimbSwing(limbSwing);
        float swimSwingDelta = entity.isInWater() ? Math.max(limbSwing - prevLimbSwing, entity.getMinSwimSwingDelta()) : 0.17f;
        entity.addToSwimSwing(swimSwingDelta);

        entity.setSwimSwingAmount(Math.max(limbSwingAmount, entity.getMinSwimSwingAmount()));
    }

    @Override
    protected void applyHeadRotation(float pNetHeadYaw, float pHeadPitch, @NotNull T entity) {
        boolean inWater = entity.isInWater();
        ModelPart body = this.body();
        if (inWater) {
            body.xRot += pHeadPitch * Mth.DEG_TO_RAD / 2;
            pHeadPitch /= 2;
        }
        ModelPart head = this.head();
        if (head != null) {
            head.yRot += pNetHeadYaw * Mth.DEG_TO_RAD;
            head.xRot += pHeadPitch * Mth.DEG_TO_RAD;
        }
    }

    protected abstract @NotNull ModelPart body();
}
