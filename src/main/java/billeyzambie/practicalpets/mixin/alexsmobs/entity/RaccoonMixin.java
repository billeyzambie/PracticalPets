package billeyzambie.practicalpets.mixin.alexsmobs.entity;

import billeyzambie.practicalpets.entity.base.practicalpet.IPracticalPet;
import com.github.alexthe666.alexsmobs.entity.EntityRaccoon;
import com.github.alexthe666.alexsmobs.entity.IFollower;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.UUID;

@Mixin(EntityRaccoon.class)
public abstract class RaccoonMixin extends TamableAnimal implements IFollower, IPracticalPet {

    private RaccoonMixin(EntityType<? extends TamableAnimal> p_21803_, Level p_21804_) {
        super(p_21803_, p_21804_);
    }

    @Override
    public float getPetHeadSizeX() {
        return 9;
    }

    @Override
    public float getPetHeadSizeY() {
        return 7;
    }

    @Override
    public float getPetHeadSizeZ() {
        return 5;
    }

    @Override
    public double getLevel10MaxHealth() {
        return 100;
    }

    @Override
    public double getLevel10AttackDamage() {
        return 16;
    }

    @Override
    public boolean isGuardingPetAbleToAttack(@Nullable LivingEntity target) {
        return true;
    }

    @Shadow(remap = false) @Nullable private UUID eggThrowerUUID;

    @Inject(
            remap = false,
            method = "postWashItem",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/github/alexthe666/alexsmobs/entity/EntityRaccoon;setTame(Z)V"
            )
    )
    private void onPostWash(ItemStack stack, CallbackInfo ci) {
        if (this.eggThrowerUUID != null) {
            Player player = this.level().getPlayerByUUID(this.eggThrowerUUID);
            this.onPetEquipmentWearerFirstTame(player);
        }
    }

    @Inject(
            remap = false,
            method = "mobInteract",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/github/alexthe666/alexsmobs/entity/EntityRaccoon;setCommand(I)V"
            ),
            cancellable = true
    )
    private void onInteract(
            Player player,
            InteractionHand hand,
            CallbackInfoReturnable<InteractionResult> cir
    ) {
        this.defaultPracticalPetInteraction(player, cir);
    }

    @Redirect(
            remap = false,
            method = "mobInteract",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/player/Player;isShiftKeyDown()Z"
            )
    )
    private boolean redirectPlayerSneaking(
            Player instance
    ) {
        return false;
    }


}
