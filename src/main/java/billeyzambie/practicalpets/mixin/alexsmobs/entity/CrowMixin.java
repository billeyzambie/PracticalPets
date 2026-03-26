package billeyzambie.practicalpets.mixin.alexsmobs.entity;

import billeyzambie.practicalpets.entity.base.practicalpet.IPracticalPet;
import com.github.alexthe666.alexsmobs.entity.EntityCrow;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.UUID;

@Mixin(EntityCrow.class)
public abstract class CrowMixin extends TamableAnimal implements IPracticalPet {

    private CrowMixin(EntityType<? extends TamableAnimal> p_21803_, Level p_21804_) {
        super(p_21803_, p_21804_);
    }

    @Override
    public float getPetHeadSizeX() {
        return 3;
    }

    @Override
    public float getPetHeadSizeY() {
        return 3;
    }

    @Override
    public float getPetHeadSizeZ() {
        return 3;
    }

    @Override
    public double getLevel10MaxHealth() {
        return 60;
    }

    @Override
    public double getLevel10AttackDamage() {
        return 6;
    }

    @Override
    public boolean isGuardingPetAbleToAttack(@Nullable LivingEntity target) {
        return true;
    }

    @Override
    public boolean allowLoweringTopHatsAPixel() {
        return false;
    }

    @Override
    public boolean petCanUseSpecialFollowMode() {
        return true;
    }

    @Override
    public boolean petIsCurrentlyFollowingOwner() {
        return !this.isSitting() && this.getCommand() == 1;
    }

    @Override
    public String getSpecialPetFollowModeInteractString() {
        return "entity.alexsmobs.crow.command_3";
    }

    @Override
    public String getSpecialPetFollowModeJadeString() {
        return "ui.practicalpets.gathering_items";
    }

    @Shadow(remap = false) public abstract boolean isSitting();

    @Shadow(remap = false) public abstract void setCommand(int command);

    @Shadow(remap = false) public abstract int getCommand();

    @Shadow(remap = false) @Nullable private UUID seedThrowerID;

    @Override
    public FollowMode getFollowMode() {
        if (this.petIsCurrentlyGuarding())
            return FollowMode.GUARDING;
        if (this.getCommand() == 3)
            return FollowMode.SPECIAL;
        if (this.isSitting())
            return FollowMode.SITTING;
        if (this.getCommand() == 1)
            return FollowMode.FOLLOWING;
        return FollowMode.WANDERING;
    }

    @Override
    public void setFollowMode(FollowMode value) {
        this.setOrderedToSit(value == FollowMode.SITTING);
        if (value == FollowMode.GUARDING)
            this.petStartGuarding();
        else {
            this.petStopGuarding();
            if (value == FollowMode.SPECIAL) {
                this.setCommand(3);
            }
            else {
                int command = (value.ordinal() + 1) % 3;
                this.setCommand(command);
            }
        }
    }

    @Inject(
            remap = false,
            method = "mobInteract",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/github/alexthe666/alexsmobs/entity/EntityCrow;setCommand(I)V"
            ),
            cancellable = true
    )
    private void onInteract(
            Player player,
            InteractionHand hand,
            CallbackInfoReturnable<InteractionResult> cir
    ) {
        this.defaultPracticalPetMixinInteraction(player, cir);
    }


    @Inject(
            remap = false,
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/github/alexthe666/alexsmobs/entity/EntityCrow;setTame(Z)V"
            ),
            cancellable = true
    )
    private void onTame(CallbackInfo ci) {
        if (this.seedThrowerID != null) {
            Player player = this.level().getPlayerByUUID(this.seedThrowerID);
            this.onPetEquipmentWearerFirstTame(player);
        }
    }
}
