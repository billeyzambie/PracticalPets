package billeyzambie.practicalpets.mixin.minecraft.model;

import billeyzambie.practicalpets.client.model.entity.base.PetEquipmentOffsets;
import billeyzambie.practicalpets.client.model.entity.base.PetEquipmentWearerVanillaModel;
import billeyzambie.practicalpets.entity.base.practicalpet.PetEquipmentWearer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.ParrotModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.animal.Parrot;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(ParrotModel.class)
public abstract class ParrotModelMixin<T extends Parrot> extends EntityModel<T> implements PetEquipmentWearerVanillaModel {

    @Inject(
            method = "<init>",
            at = @At("TAIL")
    )
    private void onConstructor(ModelPart root, CallbackInfo ci) {
        ModelPart body = root.getChild("body");
        ModelPart head = root.getChild("head");
        pathToPetBowtie = List.of(head);
        pathToPetHat = List.of(head);
        pathToPetBackpack = List.of(body);
    }

    @Final
    @Shadow
    private ModelPart feather;

    @Inject(
            method = "prepareMobModel(Lnet/minecraft/world/entity/animal/Parrot;FFF)V",
            at = @At("TAIL")
    )
    private void onPrepareModel(Parrot parrot, float p_103213_, float p_103214_, float p_103215_, CallbackInfo ci) {
        if (parrot instanceof PetEquipmentWearer wearer && !wearer.getPetHeadItem().isEmpty())
            this.feather.visible = false;
    }

    @Inject(
            method = "prepareMobModel(Lnet/minecraft/world/entity/animal/Parrot;FFF)V",
            at = @At("HEAD")
    )
    private void onPrepareModelHead(Parrot parrot, float p_103213_, float p_103214_, float p_103215_, CallbackInfo ci) {
        this.feather.visible = true;
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
            new Vec3(0, -2.5, -1),
            new Vec3(0, 1, -1),
            new Vec3(0, -1.5, 3.5),
            PetEquipmentOffsets.NO_ROTATION,
            PetEquipmentOffsets.NO_ROTATION,
            PetEquipmentOffsets.MINUS_NINETY_DEGREES_X
    );

    @Override
    public PetEquipmentOffsets getPetEquipmentOffsets() {
        return PET_EQUIPMENT_OFFSETS;
    }

}
