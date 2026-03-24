package billeyzambie.practicalpets.compat;

import billeyzambie.practicalpets.client.model.entity.base.PetEquipmentOffsets;
import billeyzambie.practicalpets.client.model.entity.base.PetEquipmentWearerModel;
import com.github.alexthe666.citadel.client.model.AdvancedModelBox;
import com.mojang.blaze3d.vertex.PoseStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public interface PetEquipmentWearerCitadelModel extends PetEquipmentWearerModel {

    List<AdvancedModelBox> pathToPetBowtie();
    List<AdvancedModelBox> pathToPetHat();
    List<AdvancedModelBox> pathToPetBackpack();

    PetEquipmentOffsets getPetEquipmentOffsets();

    default List<AdvancedModelBox> ppets$MakeListTo(AdvancedModelBox part) {
        List<AdvancedModelBox> result = new ArrayList<>();
        AdvancedModelBox currentPart = part;
        while (currentPart.getParent() != null) {
            result.add(currentPart);
            currentPart = currentPart.getParent();
        }
        Collections.reverse(result);
        return result;
    }

    @Override
    default void moveToPetBowtie(PoseStack poseStack) {
        poseStack.translate(0, 24 / 16f, 0);
        for (AdvancedModelBox part : pathToPetBowtie()) {
            part.translateAndRotate(poseStack);
        }
        getPetEquipmentOffsets().applyBowtie(poseStack);
    }
    @Override
    default void moveToPetHat(PoseStack poseStack) {
        poseStack.translate(0, 24 / 16f, 0);
        for (AdvancedModelBox part : pathToPetHat()) {
            part.translateAndRotate(poseStack);
        }
        getPetEquipmentOffsets().applyHat(poseStack);
    }
    @Override
    default void moveToPetBackpack(PoseStack poseStack) {
        poseStack.translate(0, 24 / 16f, 0);
        for (AdvancedModelBox part : pathToPetBackpack()) {
            part.translateAndRotate(poseStack);
        }
        getPetEquipmentOffsets().applyBackpack(poseStack);
    }
}
