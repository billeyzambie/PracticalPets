package billeyzambie.practicalpets.client.model.entity.base;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

public interface PetEquipmentWearerVanillaModel extends PetEquipmentWearerVanillaLikeModel {

    PetEquipmentOffsets getPetEquipmentOffsets();

    @Override
    default void moveToHat(PoseStack poseStack) {
        PetEquipmentWearerVanillaLikeModel.super.moveToHat(poseStack);
        getPetEquipmentOffsets().applyHat(poseStack);
    }

    @Override
    default void moveToBowtie(PoseStack poseStack) {
        PetEquipmentWearerVanillaLikeModel.super.moveToBowtie(poseStack);
        getPetEquipmentOffsets().applyBowtie(poseStack);
    }

    @Override
    default void moveToBackpack(PoseStack poseStack) {
        PetEquipmentWearerVanillaLikeModel.super.moveToBackpack(poseStack);
        getPetEquipmentOffsets().applyBackpack(poseStack);
    }
}
