package billeyzambie.practicalpets.mixin.model.minecraft;

import billeyzambie.practicalpets.client.model.entity.base.PetEquipmentWearerVanillaModel;
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
public abstract class CatModelMixin<T extends Cat> extends EntityModel<T> implements PetEquipmentWearerVanillaModel {

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
    private static final Vec3 HAT_POSITION = new Vec3(0, -2, -0.5f);

    @Unique
    private static final Vec3 BOWTIE_POSITION = new Vec3(0, -2, -8);

    @Override
    public Vec3 getPetHatPosition() {
        return HAT_POSITION;
    }

    @Override
    public Vec3 getPetBowtiePosition() {
        return BOWTIE_POSITION;
    }

    @Override
    public Vec3 getPetBackpackPosition() {
        return null;
    }
}
