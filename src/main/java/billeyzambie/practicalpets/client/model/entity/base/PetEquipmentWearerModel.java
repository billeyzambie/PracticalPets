package billeyzambie.practicalpets.client.model.entity.base;

import com.mojang.blaze3d.vertex.PoseStack;

public interface PetEquipmentWearerModel {
    void moveToBowtie(PoseStack poseStack);
    void moveToHat(PoseStack poseStack);
    void moveToBackpack(PoseStack poseStack);
}
