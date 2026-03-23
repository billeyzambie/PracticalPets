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
        pathToPetBowtie().forEach(part -> part.translateAndRotate(poseStack));
    }
    @Override
    default void moveToPetHat(PoseStack poseStack) {
        pathToPetHat().forEach(part -> part.translateAndRotate(poseStack));
    }
    @Override
    default void moveToPetBackpack(PoseStack poseStack) {
        pathToPetBackpack().forEach(part -> part.translateAndRotate(poseStack));
    }
}
