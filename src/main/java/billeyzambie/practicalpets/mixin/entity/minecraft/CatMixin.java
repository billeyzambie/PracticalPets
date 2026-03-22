package billeyzambie.practicalpets.mixin.entity.minecraft;

import billeyzambie.practicalpets.entity.base.VanillaPracticalPet;
import billeyzambie.practicalpets.goal.DefendSelfIfShouldGoal;
import billeyzambie.practicalpets.goal.OwnerHurtByTargetIfShouldGoal;
import billeyzambie.practicalpets.goal.OwnerHurtTargetIfShouldGoal;
import billeyzambie.practicalpets.goal.PanicIfShouldGoal;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Cat.class)
public abstract class CatMixin extends Mob implements VanillaPracticalPet {
    @Override
    public float getPetHeadSizeX() {
        return 5;
    }

    @Override
    public float getPetHeadSizeY() {
        return 4;
    }

    @Override
    public float getPetHeadSizeZ() {
        return 5;
    }

    private CatMixin(EntityType<? extends Mob> p_21368_, Level p_21369_) {
        super(p_21368_, p_21369_);
    }

    @Override
    public double getLevel10MaxHealth() {
        return 100;
    }

    @Override
    public double getLevel10AttackDamage() {
        return 16;
    }

    @Inject(
            method = {"mobInteract(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/InteractionHand;)Lnet/minecraft/world/InteractionResult;"},
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/animal/Cat;setOrderedToSit(Z)V"
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
        this.goalSelector.addGoal(1, new PanicIfShouldGoal(this, 1.5d));

        this.targetSelector.addGoal(0, new DefendSelfIfShouldGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtByTargetIfShouldGoal(this));
        this.targetSelector.addGoal(4, new OwnerHurtTargetIfShouldGoal(this));
    }

    @Override
    public boolean isGuardingPetAbleToAttack(@Nullable LivingEntity target) {
        return this.petShouldDefendOwner(target);
    }
}
