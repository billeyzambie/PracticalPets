package billeyzambie.practicalpets.client.model.entity.base;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.AgeableListModel;

public interface PetEquipmentWearerAgeableListModel extends PetEquipmentWearerVanillaModel {
    @Override
    default void moveToPetHat(PoseStack poseStack) {
        AgeableListModel<?> self = (AgeableListModel<?>)(this);
        if (self.young) {
            if (self.scaleHead) {
                float f = 1.5F / self.babyHeadScale;
                poseStack.scale(f, f, f);
            }
            poseStack.translate(0.0F, self.babyYHeadOffset / 16.0F, self.babyZHeadOffset / 16.0F);
        }
        PetEquipmentWearerVanillaModel.super.moveToPetHat(poseStack);
    }

    @Override
    default void moveToPetBowtie(PoseStack poseStack) {
        AgeableListModel<?> self = (AgeableListModel<?>)(this);
        if (self.young) {
            float f1 = 1.0F / self.babyBodyScale;
            poseStack.scale(f1, f1, f1);
            poseStack.translate(0.0F, self.bodyYOffset / 16.0F, 0.0F);
        }
        PetEquipmentWearerVanillaModel.super.moveToPetBowtie(poseStack);
    }

    @Override
    default void moveToPetBackpack(PoseStack poseStack) {
        AgeableListModel<?> self = (AgeableListModel<?>)(this);
        if (self.young) {
            float f1 = 1.0F / self.babyBodyScale;
            poseStack.scale(f1, f1, f1);
            poseStack.translate(0.0F, self.bodyYOffset / 16.0F, 0.0F);
        }
        PetEquipmentWearerVanillaModel.super.moveToPetBackpack(poseStack);
    }
}
