package billeyzambie.practicalpets.mixin.vanilla;

import billeyzambie.practicalpets.entity.base.practicalpet.PetEquipmentWearer;
import billeyzambie.practicalpets.items.AnniversaryPetHat;
import billeyzambie.practicalpets.misc.PPItems;
import billeyzambie.practicalpets.misc.PPSounds;
import billeyzambie.practicalpets.misc.PracticalPets;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;
import java.time.LocalDate;

@Mixin(TamableAnimal.class)
public abstract class TamableAnimalMixin extends Animal {
    private TamableAnimalMixin(EntityType<? extends Animal> p_27557_, Level p_27558_) {
        super(p_27557_, p_27558_);
    }

    @Inject(
            method = "tame",
            at = @At("HEAD")
    )
    private void onTame(Player player, CallbackInfo ci) {
        if (this.level().isClientSide()) {
            return;
        }

        if (this instanceof PetEquipmentWearer wearer) {
            wearer.onPetEquipmentWearerFirstTame(player);
        }

    }
}
