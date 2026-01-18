package billeyzambie.practicalpets.items;

import billeyzambie.practicalpets.entity.PracticalPet;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public interface PetCosmetic {
    enum Slot { HEAD, NECK, BACK, BODY }
    Slot slot();
    //Aquatic pets will have cosmetics too, it's just that none of them exist at the moment
    boolean canBePutOn(PracticalPet pet);
    boolean causesBravery(ItemStack stack);
    default float damageMultiplier() {
        return 1;
    }
    default float petXPMultiplier() {
        return 1;
    }
    default SoundEvent getEquipSound() {
        return SoundEvents.ARMOR_EQUIP_LEATHER;
    }
    default boolean canPerformRangedAttack(ItemStack stack) {
        return false;
    }
    default void performRangedAttack(ItemStack stack, PracticalPet shooter, LivingEntity target, float distanceFactor) {

    }
}