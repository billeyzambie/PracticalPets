package billeyzambie.practicalpets.items;

import billeyzambie.practicalpets.entity.base.practicalpet.PetCosmeticMob;
import billeyzambie.practicalpets.entity.base.practicalpet.PracticalPet;
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
    Slot slot(ItemStack stack, PetCosmeticMob pet);
    boolean canBePutOn(ItemStack stack, PetCosmeticMob pet);
    boolean causesBravery(ItemStack stack, PetCosmeticMob pet);
    default float damageMultiplier(ItemStack stack, PetCosmeticMob pet) {
        return 1;
    }
    default float petXPMultiplier(ItemStack stack, PetCosmeticMob pet) {
        return 1;
    }
    default SoundEvent getEquipSound(ItemStack stack, PetCosmeticMob pet) {
        return SoundEvents.ARMOR_EQUIP_LEATHER;
    }
    default boolean canPerformRangedAttack(ItemStack stack, PetCosmeticMob pet) {
        return false;
    }
    default void performRangedAttack(ItemStack stack, PetCosmeticMob shooter, LivingEntity target, float distanceFactor) {

    }
    /** @return false if the damage should be canceled */
    default boolean onPetHurt(ItemStack stack, PetCosmeticMob pet, DamageSource source, float amount) {
        return true;
    }
    default float reachMultiplier(ItemStack stack, PetCosmeticMob pet) {
        return 1;
    }

    default void onPetSuccessfullyHurt(ItemStack stack, PetCosmeticMob pet, DamageSource source, float amount) {
    }

    default void onPetSuccessfullyHit(ItemStack stack, PetCosmeticMob pet, Entity target) {
    }
}