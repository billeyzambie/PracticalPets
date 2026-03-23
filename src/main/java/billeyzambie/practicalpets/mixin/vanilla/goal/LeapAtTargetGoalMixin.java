package billeyzambie.practicalpets.mixin.vanilla.goal;

import billeyzambie.practicalpets.entity.base.practicalpet.PetEquipmentWearer;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LeapAtTargetGoal;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LeapAtTargetGoal.class)
public abstract class LeapAtTargetGoalMixin extends Goal {
    @Final
    @Shadow
    private Mob mob;

    @Inject(
            method = "canUse",
            at = @At("HEAD"),
            cancellable = true
    )
    private void onCanUse(CallbackInfoReturnable<Boolean> cir) {
        if (mob instanceof PetEquipmentWearer wearer && wearer.canPerformCosmeticRangedAttack())
            cir.setReturnValue(false);
    }
}
