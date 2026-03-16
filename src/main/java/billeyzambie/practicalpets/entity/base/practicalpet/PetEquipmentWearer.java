package billeyzambie.practicalpets.entity.base.practicalpet;

import billeyzambie.practicalpets.entity.base.MobInterface;
import billeyzambie.practicalpets.items.PetBowtie;
import billeyzambie.practicalpets.items.PetCosmetic;
import billeyzambie.practicalpets.petequipment.PetCosmetics;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShearsItem;
import org.jetbrains.annotations.NotNull;

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

    //Used for scaling certain hats
    float headSizeX();
    float headSizeY();
    float headSizeZ();
    default boolean isModelYAxisInverted() {
        return false;
    }

    Component getDeathMessage();

    boolean isTame();
    boolean isOwnedBy(LivingEntity entity);
    int petLevel();

    default InteractionResult petEquipmentWearerEquip(Player player, InteractionHand hand) {
        if (this.isTame() && this.isOwnedBy(player)) {
            ItemStack stack = player.getItemInHand(hand);

            Optional<PetCosmetic> cosmeticOptional = PetCosmetics.getCosmeticForItem(stack);
            if (cosmeticOptional.isPresent()) {
                if (player.level().isClientSide())
                    return InteractionResult.SUCCESS;

                PetCosmetic cosmetic = cosmeticOptional.orElseThrow();

                if (cosmetic.canBePutOn(stack, this) && !player.isSecondaryUseActive()) {
                    PetCosmetic.Slot slot = cosmetic.slot(stack, this);
                    ItemStack currentCosmetic = this.getEquippedItem(slot);
                    if (currentCosmetic.isEmpty()) {
                        this.setEquippedItem(stack.copy(), slot);
                        if (!player.getAbilities().instabuild) {
                            stack.shrink(1);
                        }
                        return InteractionResult.CONSUME;
                    } else {
                        this.spawnAtLocation(currentCosmetic);
                        this.setEquippedItem(ItemStack.EMPTY, slot);
                        return InteractionResult.SUCCESS;
                    }
                }
            }

        }
        return InteractionResult.PASS;
    }

    default InteractionResult petEquipmentWearerShear(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        Item item = itemstack.getItem();

        if (item instanceof ShearsItem && this.isOwnedBy(player)) {

            boolean atLeastOneCosmetic = false;
            for (PetCosmetic.Slot slot : PetCosmetic.Slot.values()) {
                ItemStack stack = this.getEquippedItem(slot);
                if (stack != ItemStack.EMPTY) {
                    atLeastOneCosmetic = true;
                    break;
                }
            }

            if (atLeastOneCosmetic) {
                if (this.level().isClientSide) {
                    this.level().playSound(player, this.getX(), this.getY(), this.getZ(), SoundEvents.SHEEP_SHEAR, SoundSource.NEUTRAL, 1.0F, this.getRandom().nextFloat() * 0.4F + 0.8F);
                    return InteractionResult.SUCCESS;
                } else {
                    itemstack.hurtAndBreak(1, player, lambdaPlayer -> {
                        lambdaPlayer.broadcastBreakEvent(hand);
                    });
                    this.dropAllEquipment(true);
                    return InteractionResult.CONSUME;
                }
            }

        }

        return InteractionResult.PASS;
    }

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

    MutableComponent NEWLINE = Component.literal("\n");

    default void dropAllEquipment(boolean deleteCurrentEquipment) {
        for (PetCosmetic.Slot slot : PetCosmetic.Slot.values()) {
            ItemStack stack = this.getEquippedItem(slot).copy();
            Item item = stack.getItem();
            if (!this.isAlive() && item instanceof PetBowtie bowtieItem) {
                if (this.hasCustomName()) bowtieItem.putDeadPetInfo(stack, Component.translatable(
                        "tooltip.practicalpets.dead_pet_bowtie_named",
                        this.getDisplayName(),
                        this.mobInterfaceGetTypeName(),
                        NEWLINE,
                        this.getDisplayName(),
                        Component.translatable("ui.practicalpets.pet_level",
                                Component.literal(String.valueOf(this.petLevel())).withStyle(ChatFormatting.BLUE)
                        ).withStyle(ChatFormatting.LIGHT_PURPLE),
                        NEWLINE,
                        this.getDeathMessage()
                ).withStyle(ChatFormatting.GOLD));
                else bowtieItem.putDeadPetInfo(stack, Component.translatable(
                        "tooltip.practicalpets.dead_pet_bowtie",
                        this.getDisplayName(),
                        NEWLINE,
                        this.getDisplayName(),
                        Component.translatable("ui.practicalpets.pet_level",
                                Component.literal(String.valueOf(this.petLevel())).withStyle(ChatFormatting.BLUE)
                        ).withStyle(ChatFormatting.LIGHT_PURPLE),
                        NEWLINE,
                        this.getDeathMessage()

                ).withStyle(ChatFormatting.GOLD));
            }
            this.spawnAtLocation(stack);
            if (deleteCurrentEquipment)
                this.setEquippedItem(ItemStack.EMPTY, slot);
        }
    }

    default boolean canPerformCosmeticRangedAttack() {
        return this.canShootFromSlot().isPresent();
    }

    @Override
    default void performRangedAttack(@NotNull LivingEntity target, float distanceFactor) {
        if (this.canPerformCosmeticRangedAttack())
            this.performCosmeticRangedAttack(canShootFromSlot().orElseThrow(), target, distanceFactor);
    }

    default void performCosmeticRangedAttack(PetCosmetic.Slot slot, @NotNull LivingEntity target, float distanceFactor) {
        ItemStack equippedStack = this.getEquippedItem(slot);
        PetCosmetics.getCosmeticForItem(equippedStack).ifPresent(
                cosmetic ->
                        cosmetic.performRangedAttack(equippedStack, this, target, distanceFactor)
        );
    }

    /**
     * Remember to also define the things in defineSynchedData
     */
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
        throw new IllegalStateException("Missing case for getting pet equipment in slot " + slot);
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
