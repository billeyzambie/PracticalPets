package billeyzambie.practicalpets.mixin.alexsmobs.goal;

import billeyzambie.practicalpets.entity.base.VanillaPracticalPet;
import billeyzambie.practicalpets.entity.base.practicalpet.IPracticalPet;
import billeyzambie.practicalpets.entity.base.practicalpet.PetEquipmentWearer;
import com.github.alexthe666.alexsmobs.entity.EntityCrow;
import com.github.alexthe666.alexsmobs.entity.ai.CrowAIMelee;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CrowAIMelee.class)
public class CrowAIMeleeGoalMixin {
    @Shadow(remap = false) @Final
    private EntityCrow crow;

    @Inject(
            method = "canUse",
            at = @At("HEAD"),
            cancellable = true
    )
    private void onCanUse(CallbackInfoReturnable<Boolean> cir) {
        if (crow instanceof PetEquipmentWearer pet && pet.canPerformCosmeticRangedAttack())
            cir.setReturnValue(false);
    }
}
