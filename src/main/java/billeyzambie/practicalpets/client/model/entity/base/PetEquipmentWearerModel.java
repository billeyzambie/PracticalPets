package billeyzambie.practicalpets.client.model.entity.base;

import com.mojang.blaze3d.vertex.PoseStack;

public interface PetEquipmentWearerModel {
    void moveToPetBowtie(PoseStack poseStack);
    void moveToPetHat(PoseStack poseStack);
    void moveToPetBackpack(PoseStack poseStack);
}
