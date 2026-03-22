package billeyzambie.practicalpets.mixin.entity.minecraft;

import billeyzambie.practicalpets.entity.base.VanillaPracticalPet;
import billeyzambie.practicalpets.goal.*;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.animal.Parrot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Parrot.class)
public abstract class ParrotMixin extends PathfinderMob implements VanillaPracticalPet {

    private ParrotMixin(EntityType<? extends PathfinderMob> p_21368_, Level p_21369_) {
        super(p_21368_, p_21369_);
    }

    @Override
    public double getLevel10MaxHealth() {
        return 60;
    }

    @Override
    public double getLevel10AttackDamage() {
        return 12;
    }

    @Inject(
            method = {"mobInteract(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/InteractionHand;)Lnet/minecraft/world/InteractionResult;"},
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/animal/Parrot;setOrderedToSit(Z)V"
            ),
            cancellable = true
    )
    private void onInteract(Player player, InteractionHand p_28154_, CallbackInfoReturnable<InteractionResult> cir) {
        this.practicalsPets$vanillaPetInteract(player, cir);
    }

    @Inject(
            method = "registerGoals",
            at = @At("TAIL")
    )
    private void onRegisterGoals(CallbackInfo ci) {
        this.removeAllGoals(goal -> goal instanceof PanicGoal);
        this.goalSelector.addGoal(0, new FlyPanicGoal(this, 1.25d));

        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.25d, true));

        this.targetSelector.addGoal(0, new DefendSelfIfShouldGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtByTargetIfShouldGoal(this));
        this.targetSelector.addGoal(4, new OwnerHurtTargetIfShouldGoal(this));
    }

    @Override
    public boolean isGuardingPetAbleToAttack(@Nullable LivingEntity target) {
        return this.petShouldDefendOwner(target);
    }
}
