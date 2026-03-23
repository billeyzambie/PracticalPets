package billeyzambie.practicalpets.mixin.entity.minecraft;

import billeyzambie.practicalpets.compat.DomesticationInnovationHelper;
import billeyzambie.practicalpets.entity.base.VanillaPracticalPet;
import billeyzambie.practicalpets.items.PetCosmetic;
import billeyzambie.practicalpets.util.PPUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.animal.Parrot;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(value = {
        Cat.class,
        Wolf.class,
        Parrot.class
}, priority = 999)
public abstract class VanillaPetMixin extends TamableAnimal implements VanillaPracticalPet {

    private VanillaPetMixin(EntityType<? extends TamableAnimal> p_21803_, Level p_21804_) {
        super(p_21803_, p_21804_);
    }

    @Inject(
            method = "addAdditionalSaveData",
            at = @At("TAIL")
    )
    private void onSaveNbt(CompoundTag tag, CallbackInfo ci) {
        this.savePracticalPetData(tag);
    }

    @Inject(
            method = "readAdditionalSaveData",
            at = @At("TAIL")
    )
    private void onLoadNbt(CompoundTag tag, CallbackInfo ci) {
        this.loadPracticalPetData(tag);
    }

    @Inject(
            method = "defineSynchedData",
            at = @At("TAIL")
    )
    private void onDefineSynched(CallbackInfo ci) {
        this.entityData.define(HEAD_ITEM, ItemStack.EMPTY);
        this.entityData.define(NECK_ITEM, ItemStack.EMPTY);
        this.entityData.define(BACK_ITEM, ItemStack.EMPTY);
        this.entityData.define(BODY_ITEM, ItemStack.EMPTY);
        this.entityData.define(PET_LEVEL, 1);
        this.entityData.define(PET_XP, 0f);
        this.entityData.define(DISPLAY_FOLLOW_MODE, 0);
    }

    @Unique
    private static final EntityDataAccessor<ItemStack> HEAD_ITEM = SynchedEntityData.defineId(VanillaPetMixin.class, EntityDataSerializers.ITEM_STACK);
    @Unique
    private static final EntityDataAccessor<ItemStack> NECK_ITEM = SynchedEntityData.defineId(VanillaPetMixin.class, EntityDataSerializers.ITEM_STACK);
    @Unique
    private static final EntityDataAccessor<ItemStack> BACK_ITEM = SynchedEntityData.defineId(VanillaPetMixin.class, EntityDataSerializers.ITEM_STACK);
    @Unique
    private static final EntityDataAccessor<ItemStack> BODY_ITEM = SynchedEntityData.defineId(VanillaPetMixin.class, EntityDataSerializers.ITEM_STACK);
    @Unique
    private static final EntityDataAccessor<Integer> DISPLAY_FOLLOW_MODE = SynchedEntityData.defineId(VanillaPetMixin.class, EntityDataSerializers.INT);
    @Unique
    private static final EntityDataAccessor<Integer> PET_LEVEL = SynchedEntityData.defineId(VanillaPetMixin.class, EntityDataSerializers.INT);
    @Unique
    private static final EntityDataAccessor<Float> PET_XP = SynchedEntityData.defineId(VanillaPetMixin.class, EntityDataSerializers.FLOAT);


    @Override
    public ItemStack getPetHeadItem() {
        return this.entityData.get(HEAD_ITEM);
    }

    @Override
    public void setPetHeadItemRaw(ItemStack stack) {
        this.entityData.set(HEAD_ITEM, stack);
        refreshPetEquipmentCache();
    }

    @Override
    public ItemStack getPetNeckItem() {
        return this.entityData.get(NECK_ITEM);
    }

    @Override
    public void setPetNeckItemRaw(ItemStack stack) {
        this.entityData.set(NECK_ITEM, stack);
        refreshPetEquipmentCache();
    }

    @Override
    public ItemStack getPetBackItem() {
        return this.entityData.get(BACK_ITEM);
    }

    @Override
    public void setPetBackItemRaw(ItemStack stack) {
        this.entityData.set(BACK_ITEM, stack);
        refreshPetEquipmentCache();
    }

    @Override
    public ItemStack getPetBodyItem() {
        return this.entityData.get(BODY_ITEM);
    }

    @Override
    public void setPetBodyItemRaw(ItemStack stack) {
        this.entityData.set(BODY_ITEM, stack);
        refreshPetEquipmentCache();
    }

    @Unique
    private boolean practicalPets$anyEquipmentIsBrave = false;
    @Unique
    private float practicalPets$reachMultiplier = 1;
    @Unique
    private float practicalPets$guardPowerMultiplier = 1;
    @Unique
    private Optional<PetCosmetic.Slot> practicalPets$canShootFromSlot = Optional.empty();

    @Override
    public boolean anyEquipmentIsBrave() {
        return practicalPets$anyEquipmentIsBrave;
    }

    @Override
    public float getPetReachMultiplier() {
        return practicalPets$reachMultiplier;
    }

    @Override
    public float getGuardPowerMultiplier() {
        return practicalPets$guardPowerMultiplier;
    }

    @Override
    public Optional<PetCosmetic.Slot> petCanShootFromSlot() {
        return practicalPets$canShootFromSlot;
    }

    @Override
    public void setAnyEquipmentIsBrave(boolean value) {
        this.practicalPets$anyEquipmentIsBrave = value;
    }

    @Override
    public void setPetReachMultiplier(float value) {
        this.practicalPets$reachMultiplier = value;
    }

    @Override
    public void setGuardPowerMultiplier(float guardPowerMultiplier) {
        this.practicalPets$guardPowerMultiplier = guardPowerMultiplier;
    }

    @Override
    public void setPetCanShootFromSlot(Optional<PetCosmetic.Slot> value) {
        this.practicalPets$canShootFromSlot = value;
    }

    @Override
    public void performRangedAttack(@NotNull LivingEntity target, float distanceFactor) {
        if (this.canPerformCosmeticRangedAttack())
            this.performCosmeticRangedAttack(petCanShootFromSlot().orElseThrow(), target, distanceFactor);
    }

    @Unique
    private Component practicalPets$deathMessage;

    @Override
    public Component getPetDeathMessage() {
        return practicalPets$deathMessage;
    }

    @Override
    public void setPetDeathMessage(Component value) {
        this.practicalPets$deathMessage = value;
    }

    @Override
    public int getPetLevel() {
        return this.entityData.get(PET_LEVEL);
    }

    @Override
    public void setPetLevelRaw(int petLevel) {
        this.entityData.set(PET_LEVEL, petLevel);
        this.refreshPetLevelAttributeMultipliers();
    }

    @Override
    public float getPetXP() {
        return this.entityData.get(PET_XP);
    }

    @Override
    public void setPetXPRaw(float petXP) {
        this.entityData.set(PET_XP, petXP);
    }

    @Override
    public boolean petIsCurrentlyFollowingOwner() {
        return !this.isOrderedToSit() && this.practicalPets$shouldFollowOwner();
    }

    @Override
    public FollowMode getFollowMode() {
        if (this.petIsCurrentlyGuarding())
            return FollowMode.GUARDING;
        if (PPUtil.isDIInstalled())
            return DomesticationInnovationHelper.getFollowModeForVanillaPet(this);
        if (this.isOrderedToSit())
            return FollowMode.SITTING;
        if (this.practicalPets$shouldFollowOwner())
            return FollowMode.FOLLOWING;
        return FollowMode.WANDERING;
    }

    @Override
    public void setFollowMode(FollowMode value) {
        this.setOrderedToSit(value == FollowMode.SITTING);
        this.practicalPets$setShouldFollowOwner(value == FollowMode.FOLLOWING || value == FollowMode.SITTING);
        if (value == FollowMode.GUARDING)
            this.petStartGuarding();
        else
            this.petStopGuarding();
        if (PPUtil.isDIInstalled()) {
            DomesticationInnovationHelper.setPetCommandFromFollowMode(this, value);
        }
    }

    /**
     * should use a synched entity data accessor
     */
    @Override
    public int getDisplayFollowModeId() {
        return this.entityData.get(DISPLAY_FOLLOW_MODE);
    }

    /**
     * remember to define it in synched data
     *
     * @param value
     */
    @Override
    public void setDisplayFollowModeId(int value) {
        this.entityData.set(DISPLAY_FOLLOW_MODE, value);
    }

    @Unique
    private @Nullable Vec3 practicalPets$guardCenter;

    /**
     * Should be a field, doesn't need to be synched to the client
     */
    @Override
    public @Nullable Vec3 getPetGuardCenter() {
        return practicalPets$guardCenter;
    }

    @Override
    public void setPetGuardCenter(@Nullable Vec3 value) {
        this.practicalPets$guardCenter = value;
    }

    private int practicalPets$guardTime;

    /**
     * Should be a field, doesn't need to be synched to the client
     */
    @Override
    public int getPetGuardStartTime() {
        return practicalPets$guardTime;
    }

    @Override
    public void setPetGuardStartTime(int value) {
        this.practicalPets$guardTime = value;
    }

    @Unique
    private boolean practicalPets$shouldFollowOwner;

    @Override
    public boolean practicalPets$shouldFollowOwner() {
        return practicalPets$shouldFollowOwner;
    }

    @Override
    public void practicalPets$setShouldFollowOwner(boolean value) {
        this.practicalPets$shouldFollowOwner = value;
    }

}