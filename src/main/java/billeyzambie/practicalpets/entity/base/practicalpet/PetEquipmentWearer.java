package billeyzambie.practicalpets.entity.base.practicalpet;

import billeyzambie.practicalpets.entity.base.MobInterface;
import billeyzambie.practicalpets.items.PetCosmetic;
import billeyzambie.practicalpets.petequipment.PetCosmetics;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.item.ItemStack;

import java.util.Optional;
import java.util.function.BiFunction;

public interface PetEquipmentWearer extends RangedAttackMob, MobInterface {
    void setPetHeadItemRaw(ItemStack stack);
    void setPetNeckItemRaw(ItemStack stack);
    void setPetBackItemRaw(ItemStack stack);
    void setPetBodyItemRaw(ItemStack stack);

    ItemStack getPetHeadItem();
    ItemStack getPetNeckItem();
    ItemStack getPetBackItem();
    ItemStack getPetBodyItem();

    boolean anyEquipmentIsBrave();
    float getReachMutliplier();
    Optional<PetCosmetic.Slot> canShootFromSlot();

    void setAnyEquipmentIsBrave(boolean value);
    void setReachMultiplier(float value);
    void setCanShootFromSlot(Optional<PetCosmetic.Slot> value);

    default Mob asMob() {
        return (Mob)this;
    }

    boolean isTame();

    default boolean petCosmeticDamageEntity(Entity target, float amount) {
        return target.hurt(asMob().damageSources().mobAttack(asMob()), amount);
    }

    default boolean petCosmeticsWrappedHurt(
            DamageSource source,
            float amount,
            BiFunction<DamageSource, Float, Boolean> wrappedHurt
    ) {

        for (PetCosmetic.Slot slot : PetCosmetic.Slot.values()) {
            ItemStack cosmeticStack = this.getEquippedItem(slot);
            Optional<PetCosmetic> cosmeticOptional = PetCosmetics.getCosmeticForItem(cosmeticStack);
            if (cosmeticOptional.isPresent()) {
                var cosmetic = cosmeticOptional.orElseThrow();
                amount *= cosmetic.damageMultiplier(cosmeticStack, this);
            }
        }
        for (PetCosmetic.Slot slot : PetCosmetic.Slot.values()) {
            ItemStack cosmeticStack = this.getEquippedItem(slot);
            Optional<PetCosmetic> cosmeticOptional = PetCosmetics.getCosmeticForItem(cosmeticStack);
            if (cosmeticOptional.isPresent()) {
                var cosmetic = cosmeticOptional.orElseThrow();
                if (!cosmetic.onPetHurt(cosmeticStack, this, source, amount))
                    return false;
            }
        }
        boolean result = wrappedHurt.apply(source, amount);
        float finalAmount = amount;
        if (result) {
            for (PetCosmetic.Slot slot : PetCosmetic.Slot.values()) {
                ItemStack cosmeticStack = this.getEquippedItem(slot);
                PetCosmetics.getCosmeticForItem(cosmeticStack).ifPresent(
                        cosmetic -> cosmetic.onPetSuccessfullyHurt(cosmeticStack, this, source, finalAmount)
                );
            }
        }
        return result;
    }

    default void refreshPetEquipmentCache() {
        boolean hasBraveEquipment = false;
        Optional<PetCosmetic.Slot> rangedSlot = Optional.empty();
        float reachMutliplier = 1;
        for (PetCosmetic.Slot slot : PetCosmetic.Slot.values()) {
            ItemStack cosmeticStack = this.getEquippedItem(slot);
            Optional<PetCosmetic> cosmeticOptional = PetCosmetics.getCosmeticForItem(cosmeticStack);
            if (
                    cosmeticOptional.isPresent()
            ) {
                PetCosmetic cosmetic = cosmeticOptional.orElseThrow();
                if (cosmetic.causesBravery(cosmeticStack, this)) {
                    hasBraveEquipment = true;
                }
                if (rangedSlot.isEmpty() && cosmetic.canPerformRangedAttack(cosmeticStack, this)) {
                    rangedSlot = Optional.of(slot);
                }
                reachMutliplier *= cosmetic.reachMultiplier(cosmeticStack, this);
            }
        }
        this.setAnyEquipmentIsBrave(hasBraveEquipment);
        this.setCanShootFromSlot(rangedSlot);
        this.setReachMultiplier(reachMutliplier);
    }

    //Remember to also define the things in defineSynchedData

    default void loadPetCosmetics(CompoundTag compoundTag) {
        this.setPetHeadItem(ItemStack.of(compoundTag.getCompound("PPetHeadItem")));
        this.setPetNeckItem(ItemStack.of(compoundTag.getCompound("PPetNeckItem")));
        this.setPetBackItem(ItemStack.of(compoundTag.getCompound("PPetBackItem")));
        this.setPetBodyItem(ItemStack.of(compoundTag.getCompound("PPetBodyItem")));
    }

    default void savePetCosmetics(CompoundTag compoundTag) {
        compoundTag.put("PPetHeadItem", this.getPetHeadItem().save(new CompoundTag()));
        compoundTag.put("PPetNeckItem", this.getPetNeckItem().save(new CompoundTag()));
        compoundTag.put("PPetBackItem", this.getPetBackItem().save(new CompoundTag()));
        compoundTag.put("PPetBodyItem", this.getPetBodyItem().save(new CompoundTag()));
    }

    default void setPetHeadItem(ItemStack itemStack) {
        if (itemStack.isEmpty()) {
            ItemStack currentStack = this.getPetHeadItem();
            PetCosmetics.getCosmeticForItem(currentStack).ifPresent(
                    cosmetic -> playSound(cosmetic.getEquipSound(currentStack, this))
            );
        }
        setPetHeadItemRaw(itemStack);
        refreshPetEquipmentCache();
        PetCosmetics.getCosmeticForItem(itemStack).ifPresent(
                cosmetic -> this.playSound(cosmetic.getEquipSound(itemStack, this))
        );
    }

    default void setPetNeckItem(ItemStack itemStack) {
        if (itemStack.isEmpty()) {
            ItemStack currentStack = this.getPetNeckItem();
            PetCosmetics.getCosmeticForItem(currentStack).ifPresent(
                    cosmetic -> playSound(cosmetic.getEquipSound(currentStack, this))
            );
        }
        setPetNeckItemRaw(itemStack);
        refreshPetEquipmentCache();
        PetCosmetics.getCosmeticForItem(itemStack).ifPresent(
                cosmetic -> this.playSound(cosmetic.getEquipSound(itemStack, this))
        );
    }

    default void setPetBackItem(ItemStack itemStack) {
        if (itemStack.isEmpty()) {
            ItemStack currentStack = this.getPetBackItem();
            PetCosmetics.getCosmeticForItem(currentStack).ifPresent(
                    cosmetic -> playSound(cosmetic.getEquipSound(currentStack, this))
            );
        }
        setPetBackItemRaw(itemStack);
        refreshPetEquipmentCache();
        PetCosmetics.getCosmeticForItem(itemStack).ifPresent(
                cosmetic -> this.playSound(cosmetic.getEquipSound(itemStack, this))
        );
    }

    default void setPetBodyItem(ItemStack itemStack) {
        if (itemStack.isEmpty()) {
            ItemStack currentStack = this.getPetBodyItem();
            PetCosmetics.getCosmeticForItem(currentStack).ifPresent(
                    cosmetic -> playSound(cosmetic.getEquipSound(currentStack, this))
            );
        }
        setPetBodyItemRaw(itemStack);
        refreshPetEquipmentCache();
        PetCosmetics.getCosmeticForItem(itemStack).ifPresent(
                cosmetic -> this.playSound(cosmetic.getEquipSound(itemStack, this))
        );
    }

    default ItemStack getEquippedItem(PetCosmetic.Slot slot) {
        switch (slot) {
            case HEAD -> {
                return getPetHeadItem();
            }
            case NECK -> {
                return getPetNeckItem();
            }
            case BACK -> {
                return getPetBackItem();
            }
            case BODY -> {
                return getPetBodyItem();
            }
        }
        throw new AssertionError("Missing case for getting pet equipment in slot " + slot);
    }

    default void setEquippedItem(ItemStack itemStack, PetCosmetic.Slot slot) {
        switch (slot) {
            case HEAD -> setPetHeadItem(itemStack);
            case NECK -> setPetNeckItem(itemStack);
            case BACK -> setPetBackItem(itemStack);
            case BODY -> setPetBodyItem(itemStack);
        }
    }
}
