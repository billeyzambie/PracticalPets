package billeyzambie.practicalpets.entity.base.practicalpet;

import billeyzambie.practicalpets.util.PPUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

public interface IPracticalPet extends PetEquipmentWearer, LevelablePet, GuardingOwnerFollowingPet {
    default void savePracticalPetData(CompoundTag tag) {
        this.savePetCosmetics(tag);
        this.savePetLevelingData(tag);
        this.saveGuardingPetData(tag);
    }
    default void loadPracticalPetData(CompoundTag tag) {
        this.loadPetCosmetics(tag);
        this.loadPetLevelingData(tag);
        this.loadGuardingPetData(tag);
    }

    default void defaultPracticalPetInteraction(Player player, CallbackInfoReturnable<InteractionResult> cir) {
        if (player.isSecondaryUseActive()) {
            PPUtil.openPetMenu(player, this);
        }
        else {
            this.incrementPetFollowMode();
            this.refreshDisplayFollowMode();
            player.displayClientMessage(Component.translatable("action.practicalpets." + this.getFollowMode().name, this.getDisplayName()), true);
        }
        cir.setReturnValue(InteractionResult.SUCCESS);
    }
}
