package billeyzambie.practicalpets.items;

import billeyzambie.practicalpets.entity.LandPracticalPet;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public interface PetCosmetic {
    enum Slot { HEAD, NECK, BODY }
    Slot slot();
    //Aquatic pets will have cosmetics too, it's just that none of them exist at the moment
    boolean canBePutOn(LandPracticalPet pet);
    boolean causesBravery();
    default float damageMultiplier() {
        return 1;
    }
    default float petXPMultiplier() {
        return 1;
    }
    default SoundEvent getEquipSound() {
        return SoundEvents.ARMOR_EQUIP_LEATHER;
    }
}