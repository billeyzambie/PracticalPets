package billeyzambie.practicalpets.client.model.entity.base;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.ModelPart;

import java.util.List;

public interface PetEquipmentWearerVanillaLikeModel extends PetEquipmentWearerModel {
    List<ModelPart> pathToBowtie();
    List<ModelPart> pathToHat();
    List<ModelPart> pathToBackpack();

    @Override
    default void moveToBowtie(PoseStack poseStack) {
        pathToBowtie().forEach(part -> part.translateAndRotate(poseStack));
    }
    @Override
    default void moveToHat(PoseStack poseStack) {
        pathToHat().forEach(part -> part.translateAndRotate(poseStack));
    }
    @Override
    default void moveToBackpack(PoseStack poseStack) {
        pathToBackpack().forEach(part -> part.translateAndRotate(poseStack));
    }
}
