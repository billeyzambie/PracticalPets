package billeyzambie.practicalpets.items;

import billeyzambie.practicalpets.entity.PracticalPet;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;

public interface PetCosmetic {
    enum Slot { HEAD, NECK, BACK, BODY }
    Slot slot();
    //Aquatic pets will have cosmetics too, it's just that none of them exist at the moment
    boolean canBePutOn(PracticalPet pet);
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