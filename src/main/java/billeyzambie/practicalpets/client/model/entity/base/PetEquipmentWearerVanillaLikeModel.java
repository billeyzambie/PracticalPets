package billeyzambie.practicalpets.client.model.entity.base;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.ModelPart;

import java.util.List;

public interface PetEquipmentWearerVanillaLikeModel extends PetEquipmentWearerModel {
    List<ModelPart> pathToPetBowtie();
    List<ModelPart> pathToPetHat();
    List<ModelPart> pathToPetBackpack();

    @Override
    default void moveToBowtie(PoseStack poseStack) {
        pathToPetBowtie().forEach(part -> part.translateAndRotate(poseStack));
    }
    @Override
    default void moveToHat(PoseStack poseStack) {
        pathToPetHat().forEach(part -> part.translateAndRotate(poseStack));
    }
    @Override
    default void moveToBackpack(PoseStack poseStack) {
        pathToPetBackpack().forEach(part -> part.translateAndRotate(poseStack));
    }
}
