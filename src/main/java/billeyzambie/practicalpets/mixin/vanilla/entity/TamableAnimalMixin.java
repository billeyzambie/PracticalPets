package billeyzambie.practicalpets.mixin.vanilla.entity;

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

    @Unique
    private static final LocalDate FOUNDERS_HAT_END_DATE = LocalDate.of(2025, 8, 1);
    @Unique
    private static final String FOUNDERS_HATS_CLAIMED_TAG_ID = PracticalPets.MODID + ":founders_hats_claimed";

    @Inject(
            method = "tame",
            at = @At("HEAD")
    )
    private void onTame(Player player, CallbackInfo ci) {
        if (this.level().isClientSide()) {
            return;
        }

        if (!(this instanceof PetEquipmentWearer))
            return;

        var self = (TamableAnimal & PetEquipmentWearer)(Object)(this);

        if (!self.isTame() && self.getPetNeckItem().isEmpty()) {
            ItemStack bowtie = new ItemStack(PPItems.PET_BOWTIE.get());

            float hue = self.getRandom().nextFloat();
            int rgb = Color.HSBtoRGB(hue, 1, 1);

            CompoundTag tag = new CompoundTag();
            CompoundTag display = new CompoundTag();
            display.putInt("color", rgb);
            tag.put("display", display);
            bowtie.setTag(tag);

            self.setPetNeckItem(bowtie);

            self.onGetFirstTamePetBowtie(hue);
        }

        CompoundTag persistentData = player.getPersistentData();
        CompoundTag persistedData = persistentData.getCompound(Player.PERSISTED_NBT_TAG);
        int foundersHatsClaimed = persistedData.getInt(FOUNDERS_HATS_CLAIMED_TAG_ID);
        if (
                self.getPetHeadItem().isEmpty()
                        //&& LocalDate.now().isBefore(FOUNDERS_HAT_END_DATE)
                        && foundersHatsClaimed < 5
        ) {
            ItemStack foundersHat = new ItemStack(PPItems.ANNIVERSARY_PET_HAT_0.get());
            AnniversaryPetHat.putPlayerName(foundersHat, player.getName().getString());
            self.setPetHeadItem(foundersHat);
            persistedData.putInt(FOUNDERS_HATS_CLAIMED_TAG_ID, foundersHatsClaimed + 1);
            persistentData.put(Player.PERSISTED_NBT_TAG, persistedData);
            player.sendSystemMessage(net.minecraft.network.chat.Component.translatable("ui.practicalpets.chat.got_founders_hat", foundersHat.getDisplayName(), foundersHatsClaimed + 1));
            player.sendSystemMessage(Component.translatable("ui.practicalpets.info.item.anniversary_pet_hat_0", foundersHat.getDisplayName()));
            player.playSound(PPSounds.PET_LEVEL_UP.get());
        }
    }
}
