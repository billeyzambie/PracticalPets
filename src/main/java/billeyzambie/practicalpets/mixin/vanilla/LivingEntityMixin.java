package billeyzambie.practicalpets.mixin.vanilla;

import billeyzambie.practicalpets.entity.base.practicalpet.PetEquipmentWearer;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @Inject(
            method = "dropEquipment",
            at = @At("TAIL")
    )
    private void onDropEquipment(CallbackInfo ci) {
        if (this instanceof PetEquipmentWearer wearer) {
            wearer.dropAllPetEquipment(false);
        }
    }
}
