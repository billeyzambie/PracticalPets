package billeyzambie.practicalpets.client.model.entity.fish;

import billeyzambie.animationcontrollers.PracticalPetModel;
import billeyzambie.animationcontrollers.SwimmingAnimationEntity;
import billeyzambie.practicalpets.misc.PPAnimationControllers;
import net.minecraft.client.Minecraft;
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
        float minSwimSwingDelta = entity.getMinSwimSwingDelta() * 60f / Minecraft.getInstance().getFps();
        float swimSwingDelta = entity.isInWater() ? Math.max(limbSwing - prevLimbSwing, minSwimSwingDelta) : 0.17f;
        entity.addToSwimSwing(swimSwingDelta);

        entity.setSwimSwingAmount(Math.max(limbSwingAmount, entity.getMinSwimSwingAmount()));

        float moveBlend = Mth.clamp(limbSwingAmount * 4, 0, 1);
        float ridingBlend = PPAnimationControllers.RIDING_BLEND.calculate(this, entity, limbSwing, limbSwingAmount, ageInTicks, 0, netHeadYaw, headPitch, 1);
        float xRotBlend = Math.max(moveBlend, ridingBlend);

        float partialTicks = ageInTicks % 1f;

        ModelPart body = this.body();
        body.zRot = netHeadYaw * Mth.DEG_TO_RAD / 2;
        body.xRot = entity.getSwimXRot(partialTicks) * xRotBlend;

        float airOrWaterBlend = PPAnimationControllers.ON_AIR_OR_WATER_BLEND.calculate(this, entity, limbSwing, limbSwingAmount, ageInTicks, 0, netHeadYaw, headPitch, 1);
        body.xRot *= airOrWaterBlend;
        body.zRot *= airOrWaterBlend;
    }

    protected abstract @NotNull ModelPart body();
}
