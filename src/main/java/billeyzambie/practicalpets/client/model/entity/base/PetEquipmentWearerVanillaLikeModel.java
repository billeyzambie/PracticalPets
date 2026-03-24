package billeyzambie.practicalpets.client.model.entity.base;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.ModelPart;

import java.util.List;

public interface PetEquipmentWearerVanillaLikeModel extends PetEquipmentWearerModel {
    List<ModelPart> pathToPetBowtie();
    List<ModelPart> pathToPetHat();
    List<ModelPart> pathToPetBackpack();

    @Override
    default void moveToPetBowtie(PoseStack poseStack) {
        for (ModelPart part : pathToPetBowtie()) {
            part.translateAndRotate(poseStack);
        }
    }
    @Override
    default void moveToPetHat(PoseStack poseStack) {
        for (ModelPart part : pathToPetHat()) {
            part.translateAndRotate(poseStack);
        }
    }
    @Override
    default void moveToPetBackpack(PoseStack poseStack) {
        for (ModelPart part : pathToPetBackpack()) {
            part.translateAndRotate(poseStack);
        }
    }
}
