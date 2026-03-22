package billeyzambie.practicalpets.mixin.entity.minecraft;

import billeyzambie.practicalpets.entity.base.VanillaPracticalPet;
import billeyzambie.practicalpets.util.PPUtil;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Wolf.class)
public abstract class WolfMixin extends Mob implements VanillaPracticalPet {

    @Override
    public float getPetHeadSizeX() {
        return 6;
    }

    @Override
    public float getPetHeadSizeY() {
        return 5;
    }

    @Override
    public float getPetHeadSizeZ() {
        return 4;
    }

    private WolfMixin(EntityType<? extends Mob> p_21368_, Level p_21369_) {
        super(p_21368_, p_21369_);
    }

    @Override
    public double getLevel10MaxHealth() {
        return 120;
    }

    @Override
    public double getLevel10AttackDamage() {
        return 21;
    }

    @Inject(
            method = {"mobInteract(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/InteractionHand;)Lnet/minecraft/world/InteractionResult;"},
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/animal/Wolf;setOrderedToSit(Z)V"
            ),
            cancellable = true
    )
    private void onInteract(Player player, InteractionHand p_28154_, CallbackInfoReturnable<InteractionResult> cir) {
        this.practicalsPets$vanillaPetInteract(player, cir);
    }

    @Override
    public boolean isGuardingPetAbleToAttack(@Nullable LivingEntity target) {
        return true;
    }

    @Override
    public void onGetFirstTamePetBowtie(float bowtieHue) {
        Wolf self = (Wolf)(Object)(this);
        self.setCollarColor(PPUtil.dyeColorClosestToHue(bowtieHue));
    }
}
