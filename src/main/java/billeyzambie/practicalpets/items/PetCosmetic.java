package billeyzambie.practicalpets.items;

import billeyzambie.practicalpets.entity.PracticalPet;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public interface PetCosmetic {
    enum Slot { HEAD, NECK, BACK, BODY }
    Slot slot();
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
    /** Returns false if the damage should be canceled */
    default boolean onPetHurt(ItemStack stack, PracticalPet pet, DamageSource source, float amount) {
        return true;
    }
}