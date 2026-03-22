package billeyzambie.practicalpets.mixin.entity.minecraft;

import billeyzambie.practicalpets.entity.base.VanillaWanderablePet;
import billeyzambie.practicalpets.entity.base.practicalpet.IPracticalPet;
import billeyzambie.practicalpets.entity.base.practicalpet.LevelablePet;
import billeyzambie.practicalpets.entity.base.practicalpet.PetEquipmentWearer;
import billeyzambie.practicalpets.goal.DefendSelfIfShouldGoal;
import billeyzambie.practicalpets.goal.OwnerHurtByTargetIfShouldGoal;
import billeyzambie.practicalpets.goal.OwnerHurtTargetIfShouldGoal;
import billeyzambie.practicalpets.items.PetCosmetic;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(Cat.class)
public class CatMixin extends TamableAnimal implements IPracticalPet, VanillaWanderablePet {

    @Shadow @javax.annotation.Nullable private TemptGoal temptGoal;

    private CatMixin(EntityType<? extends TamableAnimal> p_21803_, Level p_21804_) {
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
    }

    @Inject(
            method = "registerGoals",
            at = @At("TAIL")
    )
    private void onRegisterGoals(CallbackInfo ci) {
        this.targetSelector.addGoal(0, new DefendSelfIfShouldGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtByTargetIfShouldGoal(this));
        this.targetSelector.addGoal(4, new OwnerHurtTargetIfShouldGoal(this));
    }

    @Unique
    private static final EntityDataAccessor<ItemStack> HEAD_ITEM = SynchedEntityData.defineId(CatMixin.class, EntityDataSerializers.ITEM_STACK);
    @Unique
    private static final EntityDataAccessor<ItemStack> NECK_ITEM = SynchedEntityData.defineId(CatMixin.class, EntityDataSerializers.ITEM_STACK);
    @Unique
    private static final EntityDataAccessor<ItemStack> BACK_ITEM = SynchedEntityData.defineId(CatMixin.class, EntityDataSerializers.ITEM_STACK);
    @Unique
    private static final EntityDataAccessor<ItemStack> BODY_ITEM = SynchedEntityData.defineId(CatMixin.class, EntityDataSerializers.ITEM_STACK);

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

    @Override
    public float getPetHeadSizeX() {
        return 5;
    }

    @Override
    public float getPetHeadSizeY() {
        return 4;
    }

    @Override
    public float getPetHeadSizeZ() {
        return 5;
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
    public double getLevel10MaxHealth() {
        return 0;
    }

    @Override
    public double getLevel10AttackDamage() {
        return 0;
    }

    @Override
    public int getPetLevel() {
        return 1;
    }

    /**
     * Should also call {@link LevelablePet#refreshPetLevelAttributeMultipliers()}
     *
     * @param value
     */
    @Override
    public void setPetLevelRaw(int value) {

    }

    /**
     * Remember to also register the synched entity data and call
     * the {@link LevelablePet#loadPetLevelingData(CompoundTag)}
     * and {@link LevelablePet#savePetLevelingData(CompoundTag)}
     * methods in the compound tag
     */
    @Override
    public float getPetXP() {
        return 0;
    }

    @Override
    public void setPetXPRaw(float value) {

    }

    @Override @Unique
    public @Nullable AgeableMob getBreedOffspring(ServerLevel p_146743_, AgeableMob p_146744_) {
        return null;
    }

    @Override
    public boolean petIsCurrentlyFollowingOwner() {
        return false;
    }

    @Override
    public FollowMode getFollowMode() {
        return null;
    }

    @Override
    public void setFollowMode(FollowMode value) {

    }

    /**
     * should use a synched entity data accessor
     */
    @Override
    public int getDisplayFollowModeId() {
        return 0;
    }

    /**
     * remember to define it in synched data
     *
     * @param value
     */
    @Override
    public void setDisplayFollowModeId(int value) {

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

    /**
     * @param target null if just checking whether the pet can guard against things in general
     * @return {@code true} for pets that always defend their owner,
     * otherwise should return {@link PetEquipmentWearer#petShouldDefendOwner(LivingEntity)}
     */
    @Override
    public boolean isGuardingPetAbleToAttack(@Nullable LivingEntity target) {
        return this.petShouldDefendOwner(target);
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