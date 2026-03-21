package billeyzambie.practicalpets.client.model.entity.base;

import billeyzambie.practicalpets.util.PPUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

public interface PetEquipmentWearerVanillaModel extends PetEquipmentWearerVanillaLikeModel {
    /** @return in model space units (pixels) */
    Vec3 getPetHatPosition();
    /** @return in model space units (pixels) */
    Vec3 getPetBowtiePosition();
    /** @return in model space units (pixels) */
    Vec3 getPetBackpackPosition();

    Vector3f MINUS_NINETY_DEGREES_X = new Vector3f(-90, 0, 0);
    Vector3f NO_ROTATION = new Vector3f(0, 0, 0);

    /** @return in degrees */
    default Vector3f getPetHatRotation() {
        return NO_ROTATION;
    }
    /** @return in degrees */
    default Vector3f getPetBowtieRotation() {
        return NO_ROTATION;
    }
    /** @return in degrees */
    default Vector3f getPetBackpackRotation() {
        return NO_ROTATION;
    }

    @Override
    default void moveToHat(PoseStack poseStack) {
        PetEquipmentWearerVanillaLikeModel.super.moveToHat(poseStack);
        Vec3 offSet = getPetHatPosition();
        poseStack.translate(offSet.x / 16f, offSet.y / 16f, offSet.z / 16f);
        Vector3f rotation = getPetHatRotation();
        PPUtil.rotateDegrees(poseStack, rotation);
    }

    @Override
    default void moveToBowtie(PoseStack poseStack) {
        PetEquipmentWearerVanillaLikeModel.super.moveToBowtie(poseStack);
        Vec3 offSet = getPetBowtiePosition();
        poseStack.translate(offSet.x / 16f, offSet.y / 16f, offSet.z / 16f);
        Vector3f rotation = getPetBowtieRotation();
        PPUtil.rotateDegrees(poseStack, rotation);
    }

    @Override
    default void moveToBackpack(PoseStack poseStack) {
        PetEquipmentWearerVanillaLikeModel.super.moveToBackpack(poseStack);
        Vec3 offSet = getPetBackpackPosition();
        poseStack.translate(offSet.x / 16f, offSet.y / 16f, offSet.z / 16f);
        Vector3f rotation = getPetBackpackRotation();
        PPUtil.rotateDegrees(poseStack, rotation);
    }
}
