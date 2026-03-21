package billeyzambie.practicalpets.client.model.entity.base;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.world.phys.Vec3;

public interface PetEquipmentWearerVanillaModel extends PetEquipmentWearerVanillaLikeModel {
    /** @return in model space units (pixels) */
    Vec3 getPetHatPosition();
    /** @return in model space units (pixels) */
    Vec3 getPetBowtiePosition();
    /** @return in model space units (pixels) */
    Vec3 getPetBackpackPosition();

    @Override
    default void moveToHat(PoseStack poseStack) {
        PetEquipmentWearerVanillaLikeModel.super.moveToHat(poseStack);
        Vec3 hatPosition = getPetHatPosition();
        poseStack.translate(hatPosition.x / 16f, hatPosition.y / 16f, hatPosition.z / 16f);
    }

    @Override
    default void moveToBowtie(PoseStack poseStack) {
        PetEquipmentWearerVanillaLikeModel.super.moveToBowtie(poseStack);
        Vec3 bowtiePosition = getPetBowtiePosition();
        poseStack.translate(bowtiePosition.x / 16f, bowtiePosition.y / 16f, bowtiePosition.z / 16f);
    }

    @Override
    default void moveToBackpack(PoseStack poseStack) {
        PetEquipmentWearerVanillaLikeModel.super.moveToBackpack(poseStack);
        Vec3 backpackPosition = getPetBackpackPosition();
        poseStack.translate(backpackPosition.x / 16f, backpackPosition.y / 16f, backpackPosition.z / 16f);
    }
}
