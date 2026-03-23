package billeyzambie.practicalpets.client.model.entity.base;

import com.mojang.blaze3d.vertex.PoseStack;

public interface PetEquipmentWearerVanillaModel extends PetEquipmentWearerVanillaLikeModel {

    PetEquipmentOffsets getPetEquipmentOffsets();

    @Override
    default void moveToPetHat(PoseStack poseStack) {
        PetEquipmentWearerVanillaLikeModel.super.moveToPetHat(poseStack);
        getPetEquipmentOffsets().applyHat(poseStack);
    }

    @Override
    default void moveToPetBowtie(PoseStack poseStack) {
        PetEquipmentWearerVanillaLikeModel.super.moveToPetBowtie(poseStack);
        getPetEquipmentOffsets().applyBowtie(poseStack);
    }

    @Override
    default void moveToPetBackpack(PoseStack poseStack) {
        PetEquipmentWearerVanillaLikeModel.super.moveToPetBackpack(poseStack);
        getPetEquipmentOffsets().applyBackpack(poseStack);
    }
}
