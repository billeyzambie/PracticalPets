package billeyzambie.practicalpets.client.model.entity.base;

import billeyzambie.practicalpets.util.PPUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

/** offsets in model space units (pixels) and rotations in degrees */
public record PetEquipmentOffsets(
        Vec3 hatOffset,
        Vec3 bowtieOffset,
        Vec3 backpackOffset,
        Vector3f hatRotation,
        Vector3f bowtieRotation,
        Vector3f backpackRotation
) {
    public static final Vector3f MINUS_NINETY_DEGREES_X = new Vector3f(-90, 0, 0);
    public static final Vector3f NO_ROTATION = new Vector3f(0, 0, 0);
    public void applyHat(PoseStack poseStack) {
        poseStack.translate(hatOffset.x / 16f, hatOffset.y / 16f, hatOffset.z / 16f);
        PPUtil.rotateDegrees(poseStack, hatRotation);
    }
    public void applyBowtie(PoseStack poseStack) {
        poseStack.translate(bowtieOffset.x / 16f, bowtieOffset.y / 16f, bowtieOffset.z / 16f);
        PPUtil.rotateDegrees(poseStack, bowtieRotation);
    }
    public void applyBackpack(PoseStack poseStack) {
        poseStack.translate(backpackOffset.x / 16f, backpackOffset.y / 16f, backpackOffset.z / 16f);
        PPUtil.rotateDegrees(poseStack, backpackRotation);
    }
}
