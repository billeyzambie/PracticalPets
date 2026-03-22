package billeyzambie.practicalpets.mixin.goal.minecraft;

import billeyzambie.practicalpets.entity.base.VanillaPracticalPet;
import billeyzambie.practicalpets.entity.base.practicalpet.PetEquipmentWearer;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FollowOwnerGoal.class)
public abstract class FollowOwnerGoalMixin extends Goal {
    @Final
    @Shadow
    private TamableAnimal tamable;

    @Inject(
            method = "canUse",
            at = @At("HEAD"),
            cancellable = true
    )
    private void onCanUse(CallbackInfoReturnable<Boolean> cir) {
        if (tamable instanceof PetEquipmentWearer && tamable.getTarget() != null && tamable.getTarget().isAlive())
            cir.setReturnValue(false);
        else if (tamable instanceof VanillaPracticalPet pet && !pet.practicalPets$shouldFollowOwner())
            cir.setReturnValue(false);
    }
}
