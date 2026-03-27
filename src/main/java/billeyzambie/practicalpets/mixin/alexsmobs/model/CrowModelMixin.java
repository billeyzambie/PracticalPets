package billeyzambie.practicalpets.mixin.alexsmobs.model;

import billeyzambie.practicalpets.client.model.entity.base.PetEquipmentOffsets;
import billeyzambie.practicalpets.compat.PetEquipmentWearerCitadelModel;
import com.github.alexthe666.alexsmobs.client.model.ModelCrow;
import com.github.alexthe666.alexsmobs.entity.EntityCrow;
import com.github.alexthe666.citadel.client.model.AdvancedEntityModel;
import com.github.alexthe666.citadel.client.model.AdvancedModelBox;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(ModelCrow.class)
public abstract class CrowModelMixin extends AdvancedEntityModel<EntityCrow> implements PetEquipmentWearerCitadelModel {

    @Final
    @Shadow(remap = false) public AdvancedModelBox head;

    @Final
    @Shadow(remap = false) public AdvancedModelBox body;

    @Inject(
            method = "<init>",
            at = @At("TAIL")
    )
    private void onConstructor(CallbackInfo ci) {
        this.pathToPetHat = ppets$MakeListTo(this.head);
        List<AdvancedModelBox> bodyList = ppets$MakeListTo(this.body);
        this.pathToPetBowtie = bodyList;
        this.pathToPetBackpack = bodyList;
    }

    @Unique
    private List<AdvancedModelBox> pathToPetBowtie;
    @Unique
    private List<AdvancedModelBox> pathToPetHat;
    @Unique
    private List<AdvancedModelBox> pathToPetBackpack;

    @Override
    public List<AdvancedModelBox> pathToPetBowtie() {
        return pathToPetBowtie;
    }

    @Override
    public List<AdvancedModelBox> pathToPetHat() {
        return pathToPetHat;
    }

    @Override
    public List<AdvancedModelBox> pathToPetBackpack() {
        return pathToPetBackpack;
    }

    @Unique
    private static final PetEquipmentOffsets PET_EQUIPMENT_OFFSETS = new PetEquipmentOffsets(
            new Vec3(0, -2.5, 0),
            new Vec3(0, -5, 0),
            new Vec3(0, -3, -1.5),
            PetEquipmentOffsets.NO_ROTATION,
            PetEquipmentOffsets.NO_ROTATION,
            PetEquipmentOffsets.MINUS_NINETY_DEGREES_X
    );

    @Override
    public PetEquipmentOffsets getPetEquipmentOffsets() {
        return PET_EQUIPMENT_OFFSETS;
    }
}
