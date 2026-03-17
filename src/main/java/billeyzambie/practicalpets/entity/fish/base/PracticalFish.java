package billeyzambie.practicalpets.entity.fish.base;

import billeyzambie.animationcontrollers.ACData;
import billeyzambie.animationcontrollers.BVCData;
import billeyzambie.animationcontrollers.SwimmingAnimationEntity;
import billeyzambie.practicalpets.entity.base.WeightedVariantEntity;
import billeyzambie.practicalpets.entity.base.practicalpet.IPracticalPet;
import billeyzambie.practicalpets.goal.FishOwnerHurtByTargetGoal;
import billeyzambie.practicalpets.goal.FishOwnerHurtTargetGoal;
import billeyzambie.practicalpets.goal.PetEquipmentWearerCosmeticRangedAttackGoal;
import billeyzambie.practicalpets.goal.PetEquipmentWearerMeleeAttackGoal;
import billeyzambie.practicalpets.items.PetCosmetic;
import billeyzambie.practicalpets.items.PiranhaLauncher;
import billeyzambie.practicalpets.misc.PPEntities;
import billeyzambie.practicalpets.misc.PPSounds;
import billeyzambie.practicalpets.util.PPUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Optional;

public abstract class PracticalFish extends TamableFish implements IPracticalPet, SwimmingAnimationEntity, WeightedVariantEntity {
    HashMap<String, ACData> ACData = new HashMap<>();

    @Override
    public HashMap<String, ACData> getACData() {
        return ACData;
    }

    HashMap<String, BVCData> BVCData = new HashMap<>();

    @Override
    public HashMap<String, BVCData> getBVCData() {
        return BVCData;
    }

    private static final EntityDataAccessor<ItemStack> HEAD_ITEM = SynchedEntityData.defineId(PracticalFish.class, EntityDataSerializers.ITEM_STACK);
    private static final EntityDataAccessor<ItemStack> NECK_ITEM = SynchedEntityData.defineId(PracticalFish.class, EntityDataSerializers.ITEM_STACK);
    private static final EntityDataAccessor<ItemStack> BACK_ITEM = SynchedEntityData.defineId(PracticalFish.class, EntityDataSerializers.ITEM_STACK);
    private static final EntityDataAccessor<ItemStack> BODY_ITEM = SynchedEntityData.defineId(PracticalFish.class, EntityDataSerializers.ITEM_STACK);


    @Override
    public ItemStack getPetHeadItem() {
        return this.entityData.get(HEAD_ITEM);
    }

    @Override
    public void setPetHeadItemRaw(ItemStack stack) {
        this.entityData.set(HEAD_ITEM, stack);
    }

    @Override
    public ItemStack getPetNeckItem() {
        return this.entityData.get(NECK_ITEM);
    }

    @Override
    public void setPetNeckItemRaw(ItemStack stack) {
        this.entityData.set(NECK_ITEM, stack);
    }

    @Override
    public ItemStack getPetBackItem() {
        return this.entityData.get(BACK_ITEM);
    }

    @Override
    public void setPetBackItemRaw(ItemStack stack) {
        this.entityData.set(BACK_ITEM, stack);
    }

    @Override
    public ItemStack getPetBodyItem() {
        return this.entityData.get(BODY_ITEM);
    }

    @Override
    public void setPetBodyItemRaw(ItemStack stack) {
        this.entityData.set(BODY_ITEM, stack);
    }

    private boolean anyEquipmentIsBrave = false;
    private float reachMutliplier = 1;
    private Optional<PetCosmetic.Slot> canShootFromSlot = Optional.empty();

    @Override
    public boolean anyEquipmentIsBrave() {
        return anyEquipmentIsBrave;
    }

    @Override
    public float getReachMutliplier() {
        return reachMutliplier;
    }

    @Override
    public Optional<PetCosmetic.Slot> canShootFromSlot() {
        return canShootFromSlot;
    }

    @Override
    public void setAnyEquipmentIsBrave(boolean value) {
        this.anyEquipmentIsBrave = value;
    }

    @Override
    public void setReachMultiplier(float value) {
        this.reachMutliplier = value;
    }

    @Override
    public void setCanShootFromSlot(Optional<PetCosmetic.Slot> value) {
        this.canShootFromSlot = value;
    }

    @Override
    public void remove(RemovalReason reason) {
        if (reason == RemovalReason.DISCARDED)
            this.dropAllPetEquipment(true);
        super.remove(reason);
    }

    @Override
    protected void dropEquipment() {
        super.dropEquipment();
        this.dropAllPetEquipment(false);
    }

    private Component deathMessage = Component.empty();

    @Override
    public Component getDeathMessage() {
        return deathMessage;
    }

    @Override
    public boolean isModelYAxisInverted() {
        return true;
    }

    @Override
    public void die(DamageSource p_21809_) {
        this.deathMessage = this.getCombatTracker().getDeathMessage();
        super.die(p_21809_);
    }

    @Override
    public int petLevel() {
        return 1;
    }

    @Override
    public boolean petIsCurrentlyFollowingOwner() {
        return false;
    }

    private float prevLimbSwing = 0;

    @Override
    public float getPrevLimbSwing() {
        return prevLimbSwing;
    }

    @Override
    public void setPrevLimbSwing(float value) {
        this.prevLimbSwing = value;
    }

    private float swimSwing = 0;

    @Override
    public void addToSwimSwing(float value) {
        swimSwing += value;
    }

    @Override
    public float getSwimSwing() {
        return swimSwing;
    }

    private float swimSwingAmount = 0;

    @Override
    public float getSwimSwingAmount() {
        return swimSwingAmount;
    }

    @Override
    public void setSwimSwingAmount(float value) {
        this.swimSwingAmount = value;
    }

    private float swimXRot = 0;
    private float swimXRotO = 0;

    @Override
    public float getSwimXRot(float partialTicks) {
        return Mth.lerp(partialTicks, swimXRotO, swimXRot);
    }

    @Override
    public void setSwimXRot(float value) {
        this.swimXRot = value;
    }

    private float onAirTime = 0;

    @Override
    public float getOnAirTime() {
        return this.onAirTime;
    }

    @Override
    public void setOnAirTime(float value) {
        this.onAirTime = value;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide()) {
            this.swimXRotO = this.swimXRot;
            this.tickSwimAnim();
        }
    }

    public PracticalFish(EntityType<? extends PracticalFish> p_27523_, Level p_27524_) {
        super(p_27523_, p_27524_);
    }

    @Override
    protected @NotNull SoundEvent getFlopSound() {
        return SoundEvents.COD_FLOP;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.COD_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource p_28281_) {
        return SoundEvents.COD_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return PPSounds.FISH_DEATH.get();
    }

    @Override
    public final float getVoicePitch() {
        if (this.isAlive())
            return this.getCustomVoicePitch();
        else {
            float result = this.getDeathSoundPitch() - 0.1f + 0.2f * this.getRandom().nextFloat();
            if (this.isBaby())
                result *= 1.5f;
            return result;
        }
    }

    abstract protected float getDeathSoundPitch();

    private float getCustomVoicePitch() {
        return super.getVoicePitch();
    }

    private static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(PracticalFish.class, EntityDataSerializers.INT);

    protected boolean isLaunched = false;

    public boolean isLaunched() {
        return isLaunched;
    }

    protected int launchTime = 0;

    public int getLaunchTime() {
        return launchTime;
    }

    @Override
    public int getVariant() {
        return this.entityData.get(VARIANT);
    }

    @Override
    public void setVariant(int variant) {
        this.entityData.set(VARIANT, variant);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(VARIANT, 0);
        this.entityData.define(HEAD_ITEM, ItemStack.EMPTY);
        this.entityData.define(NECK_ITEM, ItemStack.EMPTY);
        this.entityData.define(BACK_ITEM, ItemStack.EMPTY);
        this.entityData.define(BODY_ITEM, ItemStack.EMPTY);
    }

    @Override
    protected void loadBucketAndWorldSharedData(CompoundTag tag) {
        super.loadBucketAndWorldSharedData(tag);
        this.loadVariant(tag);
    }

    @Override
    protected void saveBucketAndWorldSharedData(CompoundTag tag) {
        super.saveBucketAndWorldSharedData(tag);
        this.saveVariant(tag);
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.isLaunched = tag.getBoolean("Launched");
        this.launchTime = tag.getInt("LaunchTime");
        this.loadPetCosmetics(tag);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putBoolean("Launched", this.isLaunched);
        tag.putInt("LaunchTime", this.launchTime);
        this.savePetCosmetics(tag);
    }

    public static final int MAX_LAUNCHED_LIFESPAN = 30 * 20;

    @Override
    protected void customServerAiStep() {
        super.customServerAiStep();
        if (this.isLaunched && ++this.launchTime >= MAX_LAUNCHED_LIFESPAN) {
            this.discard();
        }
    }

    public final CompoundTag makePiranhaLauncherTag() {
        CompoundTag result = new CompoundTag();
        this.addPiranhaLauncherData(result);

        if (this.getHealth() != this.getMaxHealth())
            result.putFloat("Health", this.getHealth());

        int age = this.getAge();
        if (age != 0)
            result.putInt("Age", age);

        Component component = this.getCustomName();
        if (component != null) {
            result.putString("CustomName", Component.Serializer.toJson(component));
        }

        String typeId = this.getEncodeId();
        if (typeId != null && !typeId.equals(PPEntities.PIRANHA.getId().toString())) {
            result.putString("id", typeId);
        }
        return result;
    }

    protected abstract void addPiranhaLauncherData(CompoundTag tag);

    public static PracticalFish createFromPiranhaLauncherTag(
            ItemStack piranhaLauncher,
            CompoundTag fishTag,
            Level level,
            @Nullable LivingEntity thrower
    ) {
        if (!fishTag.contains("id", Tag.TAG_STRING)) {
            fishTag.putString("id", PPEntities.PIRANHA.getId().toString());
        }
        PracticalFish fish = (PracticalFish) EntityType.create(fishTag, level).orElseThrow();

        Player owner = null;
        if (thrower == null) {
            CompoundTag launcherTag = piranhaLauncher.getTag();
            if (launcherTag != null && launcherTag.hasUUID("LastOwnerUUID")) {
                fish.setTame(true);
                fish.setOwnerUUID(launcherTag.getUUID("LastOwnerUUID"));
            }
        } else if (thrower instanceof Player player) {
            owner = player;
        } else if (thrower instanceof OwnableEntity pet && pet.getOwner() instanceof Player petOwner) {
            owner = petOwner;
        }
        if (owner != null) {
            fish.setTame(true);
            fish.setOwnerUUID(owner.getUUID());
        }

        fish.isLaunched = true;
        return fish;
    }

    @Override
    public @Nullable SpawnGroupData finalizeSpawn(ServerLevelAccessor p_27528_, DifficultyInstance p_27529_, MobSpawnType p_27530_, @Nullable SpawnGroupData p_27531_, @Nullable CompoundTag p_27532_) {
        this.setVariant(this.pickRandomWeightedVariant());
        return super.finalizeSpawn(p_27528_, p_27529_, p_27530_, p_27531_, p_27532_);
    }

    private AvoidEntityGoal<Player> avoidPlayersGoal;

    @Override
    protected void reassessTameGoals() {
        if (this.avoidPlayersGoal == null) {
            this.avoidPlayersGoal = new AvoidEntityGoal<>(this, Player.class, 8.0F, 1.25D, 1.1D, EntitySelector.NO_SPECTATORS::test);
        }

        this.goalSelector.removeGoal(this.avoidPlayersGoal);
        if (!this.isTame()) {
            this.goalSelector.addGoal(4, this.avoidPlayersGoal);
        }

    }

    @Override
    public boolean wantsToAttack(@NotNull LivingEntity target, @NotNull LivingEntity owner) {
        if (target instanceof OwnableEntity pet) {
            return !owner.getUUID().equals(pet.getOwnerUUID());
        } else
            return !(target instanceof Player) || !(owner instanceof Player) || ((Player) owner).canHarmPlayer((Player) target);
    }

    public boolean shouldDefendOwner(Entity target) {
        return this.isLaunched;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(2, new PetEquipmentWearerCosmeticRangedAttackGoal(this, 1.25d));
        this.goalSelector.addGoal(2, new PetEquipmentWearerMeleeAttackGoal(this, 1.25d, true));
        this.goalSelector.addGoal(3, new BreedGoal(this, 1.25d));
        this.goalSelector.addGoal(6, new FishSwimGoal(this));
        this.goalSelector.addGoal(8, new FollowFlockLeaderGoal(this));
        HurtByTargetGoal hurtByTargetGoal = new HurtByTargetGoal(this);
        if (shouldRegisterAlertOthers())
            hurtByTargetGoal = hurtByTargetGoal.setAlertOthers();
        this.targetSelector.addGoal(1, hurtByTargetGoal);
        this.targetSelector.addGoal(2, new FishOwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(3, new FishOwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(
                this, LivingEntity.class,  40, true, true,
                target -> {
                    if (this.isLaunched && target instanceof Mob mob && mob.getTarget() != null) {
                        return mob.getTarget().getUUID().equals(this.getOwnerUUID());
                    }
                    return false;
                }
        ));
        this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(
                this, LivingEntity.class,  20, true, true,
                target -> this.isLaunched && !(
                        target instanceof OwnableEntity ownableEntity
                                && getOwnerUUID() != null
                )
        ));
        this.targetSelector.addGoal(10, new CopyFlockLeaderTargetGoal(this));
    }

    public boolean sharesOwnerWith(Entity target) {
        return target instanceof OwnableEntity ownableEntity && PPUtil.petsShareOwner(this, ownableEntity);
    }

    protected boolean shouldRegisterAlertOthers() {
        return false;
    }

    @Override
    protected void usePlayerItem(@NotNull Player player, @NotNull InteractionHand hand, @NotNull ItemStack itemStack) {
        super.usePlayerItem(player, hand, itemStack);

        if (this.isFood(itemStack)) {
            this.playSound(SoundEvents.GENERIC_EAT, 1.0F, 1.0F);
        }
    }

    @Override
    public boolean canInteractEventPutPetEquipment(Player player, InteractionHand hand) {
        //Call this::petEquipmentWearerEquip manually instead after the piranha launcher interaction
        return false;
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        boolean clientSide = this.level().isClientSide();

        if (
                player.onGround() && stack.getItem() instanceof PiranhaLauncher piranhaLauncher
                    && !(this.getVehicle() instanceof Projectile) && !player.isSecondaryUseActive()
        ) {
            if (piranhaLauncher.tryInsertFish(stack, this, player)) {
                return InteractionResult.sidedSuccess(clientSide);
            }
        }

        InteractionResult petEquipmentWearerEquip = this.petEquipmentWearerEquip(player, hand);
        if (petEquipmentWearerEquip != InteractionResult.PASS)
            return petEquipmentWearerEquip;

        if (this.isFood(stack)) {
            if (this.getHealth() < this.getMaxHealth()) {
                if (!clientSide) {
                    FoodProperties foodProperties = stack.getFoodProperties(this);
                    float healAmount = foodProperties == null ? 2
                            : foodProperties.getNutrition();
                    this.heal(healAmount);
                    this.usePlayerItem(player, hand, stack);
                }
                return InteractionResult.sidedSuccess(clientSide);
            }
            if (!this.isTame()) {
                if (!clientSide) {
                    this.usePlayerItem(player, hand, stack);
                    if (this.random.nextInt(3) == 0) {
                        this.tame(player);
                        //Tame heart particle
                        this.level().broadcastEntityEvent(this, (byte) 7);
                    } else {
                        //Fail particle
                        this.level().broadcastEntityEvent(this, (byte) 6);
                    }
                }
                return InteractionResult.sidedSuccess(clientSide);
            }
        }
        return super.mobInteract(player, hand);
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        Entity entity = source.getEntity();
        if (entity instanceof Player player) {
            ItemStack mainHandStack = player.getMainHandItem();
            if (
                    mainHandStack.getItem() instanceof PiranhaLauncher launcherItem
                    && launcherItem.tryInsertFish(mainHandStack, this, player)
            ) {
                return false;
            }
        }
        if (entity instanceof LivingEntity living && this.isOwnedBy(living) && !living.isShiftKeyDown())
            return false;
        return super.hurt(source, amount);
    }
}