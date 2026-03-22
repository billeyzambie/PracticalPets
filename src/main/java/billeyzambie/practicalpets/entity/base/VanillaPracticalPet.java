package billeyzambie.practicalpets.entity.base;

import billeyzambie.practicalpets.entity.base.practicalpet.IPracticalPet;
import billeyzambie.practicalpets.util.PPUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

public interface VanillaPracticalPet extends IPracticalPet {
    boolean practicalPets$shouldFollowOwner();
    void practicalPets$setShouldFollowOwner(boolean value);

    @Override
    default void savePracticalPetData(CompoundTag tag) {
        IPracticalPet.super.savePracticalPetData(tag);
        tag.putBoolean("PracticalPetShouldFollow", this.practicalPets$shouldFollowOwner());
    }

    @Override
    default void loadPracticalPetData(CompoundTag tag) {
        IPracticalPet.super.loadPracticalPetData(tag);
        if (tag.contains("PracticalPetShouldFollow"))
            this.practicalPets$setShouldFollowOwner(tag.getBoolean("PracticalPetShouldFollow"));
    }

    default void practicalPets$incrementFollowMode() {
        switch (this.getFollowMode()) {
            case FOLLOWING -> setFollowMode(this.petCanStartGuarding() ? FollowMode.GUARDING : FollowMode.WANDERING);
            case GUARDING -> setFollowMode(FollowMode.WANDERING);
            case WANDERING -> setFollowMode(FollowMode.SITTING);
            case SITTING -> setFollowMode(FollowMode.FOLLOWING);
        }
        this.refreshDisplayFollowMode();
    }

    default void practicalsPets$vanillaPetInteract(Player player, CallbackInfoReturnable<InteractionResult> cir) {
        if (player.isSecondaryUseActive()) {
            PPUtil.openPetMenu(player, this);
        }
        else {
            this.practicalPets$incrementFollowMode();
            this.refreshDisplayFollowMode();
            player.displayClientMessage(Component.translatable("action.practicalpets." + this.getFollowMode().name, this.getDisplayName()), true);
        }
        cir.setReturnValue(InteractionResult.SUCCESS);
    }
}
