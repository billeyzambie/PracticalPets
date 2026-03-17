package billeyzambie.practicalpets.items;

import billeyzambie.practicalpets.entity.base.practicalpet.PetEquipmentWearer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

/**
 * Never do {@code item instanceof PetCosmetic}, use {@code PetEquipment.getCosmeticForItem(item)} instead
 */
public interface PetCosmetic {
    enum Slot { HEAD, NECK, BACK, BODY }
    Slot slot(ItemStack stack, PetEquipmentWearer wearer);
    boolean canBePutOn(ItemStack stack, PetEquipmentWearer wearer);
    boolean causesBravery(ItemStack stack, PetEquipmentWearer wearer);
    default float damageMultiplier(ItemStack stack, PetEquipmentWearer wearer) {
        return 1;
    }
    default float petXPMultiplier(ItemStack stack, PetEquipmentWearer wearer) {
        return 1;
    }
    default SoundEvent getEquipSound(ItemStack stack, PetEquipmentWearer wearer) {
        return SoundEvents.ARMOR_EQUIP_LEATHER;
    }
    default boolean canPerformRangedAttack(ItemStack stack, PetEquipmentWearer wearer) {
        return false;
    }
    default void performRangedAttack(ItemStack stack, PetEquipmentWearer wearer, LivingEntity target, float distanceFactor) {

    }
    /** @return true if the damage should be canceled */
    default boolean onPetHurt(ItemStack stack, PetEquipmentWearer wearer, DamageSource source, float amount) {
        return false;
    }
    default float reachMultiplier(ItemStack stack, PetEquipmentWearer wearer) {
        return 1;
    }

    default void onPetHit(ItemStack stack, PetEquipmentWearer wearer, Entity target) {
    }
}