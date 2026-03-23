package billeyzambie.practicalpets.mixin.vanilla.model;

import billeyzambie.practicalpets.client.model.entity.base.PetEquipmentOffsets;
import billeyzambie.practicalpets.client.model.entity.base.PetEquipmentWearerAgeableListModel;
import net.minecraft.client.model.CatModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(CatModel.class)
public abstract class CatModelMixin<T extends Cat> extends EntityModel<T> implements PetEquipmentWearerAgeableListModel {

    @Inject(
            method = "<init>",
            at = @At("TAIL")
    )
    private void onConstructor(ModelPart root, CallbackInfo ci) {
        ModelPart body = root.getChild("body");
        ModelPart head = root.getChild("head");
        pathToPetBowtie = List.of(body);
        pathToPetHat = List.of(head);
        pathToPetBackpack = List.of(body);
    }

    @Unique
    private List<ModelPart> pathToPetBowtie;
    @Unique
    private List<ModelPart> pathToPetHat;
    @Unique
    private List<ModelPart> pathToPetBackpack;

    @Override
    public List<ModelPart> pathToPetBowtie() {
        return pathToPetBowtie;
    }

    @Override
    public List<ModelPart> pathToPetHat() {
        return pathToPetHat;
    }

    @Override
    public List<ModelPart> pathToPetBackpack() {
        return pathToPetBackpack;
    }

    @Unique
    private static final PetEquipmentOffsets PET_EQUIPMENT_OFFSETS = new PetEquipmentOffsets(
            new Vec3(0, -2, -0.5),
            new Vec3(0, 7, 3),
            new Vec3(0, 2, 11.5f),
            PetEquipmentOffsets.NO_ROTATION,
            PetEquipmentOffsets.MINUS_NINETY_DEGREES_X,
            PetEquipmentOffsets.MINUS_NINETY_DEGREES_X
    );

    @Override
    public PetEquipmentOffsets getPetEquipmentOffsets() {
        return PET_EQUIPMENT_OFFSETS;
    }

}
