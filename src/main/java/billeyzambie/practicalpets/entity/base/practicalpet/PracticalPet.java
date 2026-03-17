package billeyzambie.practicalpets.entity.base.practicalpet;

import billeyzambie.animationcontrollers.ACData;
import billeyzambie.animationcontrollers.ACEntity;
import billeyzambie.animationcontrollers.BVCData;
import billeyzambie.practicalpets.entity.base.WeightedVariantEntity;
import billeyzambie.practicalpets.items.*;
import billeyzambie.practicalpets.misc.*;
import billeyzambie.practicalpets.network.RandomIdle1AnimPacket;
import billeyzambie.practicalpets.ui.PracticalPetMenu;
import billeyzambie.practicalpets.goal.*;
import billeyzambie.practicalpets.util.PPUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.time.LocalDate;
import java.util.*;
import java.util.List;

public abstract class PracticalPet extends TamableAnimal implements IPracticalPet, ACEntity, NeutralMob, WeightedVariantEntity {

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

    public enum FollowMode {
        FOLLOWING,
        SITTING,
        WANDERING;

        @Override
        public String toString() {
            return super.toString().toLowerCase(Locale.ROOT);
        }
    }

    @Override
    public boolean isModelYAxisInverted() {
        return true;
    }

    private static final EntityDataAccessor<Boolean> SHOULD_FOLLOW_OWNER = SynchedEntityData.defineId(PracticalPet.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> HAS_TARGET = SynchedEntityData.defineId(PracticalPet.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> PET_LEVEL = SynchedEntityData.defineId(PracticalPet.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Float> PET_XP = SynchedEntityData.defineId(PracticalPet.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<ItemStack> HEAD_ITEM = SynchedEntityData.defineId(PracticalPet.class, EntityDataSerializers.ITEM_STACK);
    private static final EntityDataAccessor<ItemStack> NECK_ITEM = SynchedEntityData.defineId(PracticalPet.class, EntityDataSerializers.ITEM_STACK);
    private static final EntityDataAccessor<ItemStack> BACK_ITEM = SynchedEntityData.defineId(PracticalPet.class, EntityDataSerializers.ITEM_STACK);
    private static final EntityDataAccessor<ItemStack> BODY_ITEM = SynchedEntityData.defineId(PracticalPet.class, EntityDataSerializers.ITEM_STACK);
    private static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(PracticalPet.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> IS_RAINBOW = SynchedEntityData.defineId(PracticalPet.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> IS_INTERESTED = SynchedEntityData.defineId(PracticalPet.class, EntityDataSerializers.BOOLEAN);

    @Override
    public boolean petIsCurrentlyFollowingOwner() {
        return !this.isOrderedToSit() && this.shouldFollowOwner();
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(HAS_TARGET, false);
        this.entityData.define(SHOULD_FOLLOW_OWNER, true);
        this.entityData.define(PET_LEVEL, 1);
        this.entityData.define(PET_XP, 0f);
        this.entityData.define(HEAD_ITEM, ItemStack.EMPTY);
        this.entityData.define(NECK_ITEM, ItemStack.EMPTY);
        this.entityData.define(BACK_ITEM, ItemStack.EMPTY);
        this.entityData.define(BODY_ITEM, ItemStack.EMPTY);
        this.entityData.define(VARIANT, 0);
        this.entityData.define(DATA_REMAINING_ANGER_TIME, 0);
        this.entityData.define(IS_RAINBOW, false);
        this.entityData.define(IS_INTERESTED, false);
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setShouldFollowOwner(compoundTag.getBoolean("ShouldFollowOwner"));

        if (compoundTag.contains("PetLevel", Tag.TAG_INT)) {
            this.setPetLevelRaw(compoundTag.getInt("PetLevel"));
            this.setPetXPRaw(compoundTag.getFloat("PetXP"));
        }
        else {
            this.loadPetLevelingData(compoundTag);
        }

        if (compoundTag.contains("NeckItem", CompoundTag.TAG_COMPOUND)) {
            this.setPetHeadItem(ItemStack.of(compoundTag.getCompound("HeadItem")));
            this.setPetNeckItem(ItemStack.of(compoundTag.getCompound("NeckItem")));
            this.setPetBackItem(ItemStack.of(compoundTag.getCompound("BackItem")));
            this.setPetBodyItem(ItemStack.of(compoundTag.getCompound("BodyItem")));
        }
        else {
            this.loadPetCosmetics(compoundTag);
        }

        this.loadVariant(compoundTag);
        this.setIsRainbow(compoundTag.getBoolean("IsRainbow"));
        this.readPersistentAngerSaveData(this.level(), compoundTag);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putBoolean("ShouldFollowOwner", this.shouldFollowOwner());
        this.savePetLevelingData(compoundTag);
        this.savePetCosmetics(compoundTag);
        this.saveVariant(compoundTag);
        compoundTag.putBoolean("IsRainbow", this.isRainbow());
        this.addPersistentAngerSaveData(compoundTag);
    }

    @Override
    public @NotNull SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor level, @NotNull DifficultyInstance difficulty, @NotNull MobSpawnType spawnType,
                                                 @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag tag) {
        this.setVariant(this.pickRandomWeightedVariant());

        this.setPetLevelRaw(1);

        if (this.random.nextInt(300) == 0)
            this.setIsRainbow(true);

        return super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData, tag);
    }

    public final AnimationState randomIdle1AnimationState = new AnimationState();

    protected void sendRandomIdle1Packet() {
        PPNetworking.CHANNEL.send(
                PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> this),
                new RandomIdle1AnimPacket(this.getId())
        );
    }

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
    public void performRangedAttack(@NotNull LivingEntity target, float distanceFactor) {
        if (this.canPerformCosmeticRangedAttack())
            this.performCosmeticRangedAttack(canShootFromSlot().orElseThrow(), target, distanceFactor);
    }

    public PracticalPet(EntityType<? extends TamableAnimal> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public final boolean isFood( ItemStack itemStack) {
        return isTameItem(itemStack) || isFoodThatDoesntTame(itemStack);
    }

    public boolean shouldTempt(@NotNull ItemStack itemStack) {
        return isTame() ? isFood(itemStack) : isTameItem(itemStack);
    }

    public boolean isTameItem(ItemStack itemStack) {
        return false;
    }

    public boolean isFoodThatDoesntTame(ItemStack itemStack) {
        return itemStack.is(PPItems.POULTRY_BANANA.get());
    }

    private enum HealOverrideType {
        DEFINE_NUTRITION,
        OVERRIDE,
        ADD,
        MULTIPLY
    }

    public record HealOverride(HealOverrideType type, int value) {

        public static HealOverride defineNutrition(int value) {
            return new HealOverride(HealOverrideType.DEFINE_NUTRITION, value);
        }

        public static HealOverride override(int value) {
            return new HealOverride(HealOverrideType.OVERRIDE, value);
        }

        public static HealOverride add(int value) {
            return new HealOverride(HealOverrideType.ADD, value);
        }

        public static HealOverride multiply(int value) {
            return new HealOverride(HealOverrideType.MULTIPLY, value);
        }
    }

    /**
     * Overrides must end with {@code return super.healOverride(itemStack)}
     */
    public HealOverride healOverride(ItemStack itemStack) {
        if (itemStack.is(Tags.Items.CROPS))
            return HealOverride.defineNutrition(3);
        if (itemStack.is(Tags.Items.SEEDS))
            return HealOverride.defineNutrition(2);
        if (itemStack.is(ItemTags.LEAVES))
            return HealOverride.defineNutrition(2);
        if (itemStack.is(Items.FERMENTED_SPIDER_EYE))
            return HealOverride.defineNutrition(3);
        return null;
    }

    public final void healFromEatingItem(ItemStack itemStack) {
        FoodProperties foodProperties = itemStack.getFoodProperties(this);
        HealOverride healOverride = healOverride(itemStack);

        float healAmount;

        if (foodProperties == null) {
            if (healOverride == null || healOverride.type() != HealOverrideType.DEFINE_NUTRITION) {
                Util.logAndPauseIfInIde("Item " + itemStack.getItem() + " isn't edible but no heal override was defined for it for " + this.getClass());
                return;
            }
            healAmount = healOverride.value();
            switch (itemStack.getRarity()) {
                case UNCOMMON -> healAmount *= 2f;
                case RARE -> healAmount *= 3f;
                case EPIC -> healAmount *= 4f;
                default -> {
                }
            }
        } else {
            healAmount = foodProperties.getNutrition();
            if (healOverride != null) {
                switch (healOverride.type()) {
                    case OVERRIDE -> healAmount = healOverride.value();
                    case ADD -> healAmount += healOverride.value();
                    case MULTIPLY -> healAmount *= healOverride.value();
                }
            }
            itemStack.finishUsingItem(this.level(), this);
        }

        this.heal(healAmount);
    }

    private Goal strollGoal = null;

    @Override
    protected void registerGoals() {
        if (shouldRegisterFloatGoal())
            this.goalSelector.addGoal(0, new FloatGoal(this));
        Goal panicGoal = this.createPanicGoal();
        if (panicGoal != null)
            this.goalSelector.addGoal(10, panicGoal);
        this.goalSelector.addGoal(20, new SitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(50, this.createMeleeAttackGoal());
        this.goalSelector.addGoal(55, new PPBegGoal(this));
        this.goalSelector.addGoal(60, new FollowOwnerWanderableGoal(this, this.createFollowOwnerSpeed(), 7.0F, 5.0F, false));
        this.goalSelector.addGoal(70, new PredicateTemptGoal(this, this.createFollowOwnerSpeed(), PracticalPet::shouldTempt, false));

        Goal followParentGoal = createFollowParentGoal();
        if (followParentGoal != null)
            this.goalSelector.addGoal(80, followParentGoal);

        this.goalSelector.addGoal(90, new BreedGoal(this, 0.8D, TamableAnimal.class));

        this.refreshStrollGoal();

        this.goalSelector.addGoal(120, new LookAtPlayerGoal(this, Player.class, 10.0F));
        this.goalSelector.addGoal(120, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(20, new OwnerHurtByTargetIfShouldGoal(this));
        this.targetSelector.addGoal(10,
                this.shouldRegisterSpreadingAnger()
                        ? new DefendSelfIfShouldGoal(this).setAlertOthers()
                        : new DefendSelfIfShouldGoal(this)
        );
        this.targetSelector.addGoal(30, new OwnerHurtTargetIfShouldGoal(this));
        if (this.shouldRegisterSpreadingAnger())
            this.targetSelector.addGoal(40, new ResetUniversalAngerTargetGoal<>(this, true));
    }

    protected @NotNull Goal createMeleeAttackGoal() {
        return new MeleeAttackGoal(this, this.createMeleeAttackSpeedMultiplier(), false);
    }

    protected @Nullable Goal createPanicGoal() {
        return new PanicIfShouldGoal(this, this.createPanicSpeedMultiplier());
    }

    protected double createPanicSpeedMultiplier() {
        return 1.3D;
    }

    protected void refreshStrollGoal() {
        if (this.strollGoal != null) {
            this.goalSelector.removeGoal(this.strollGoal);
        }
        Goal strollGoal = this.createStrollGoal();
        if (strollGoal != null) {
            this.strollGoal = strollGoal;
            this.goalSelector.addGoal(100, strollGoal);
        }
    }

    protected double createFollowOwnerSpeed() {
        return 1.1D;
    }

    protected double createMeleeAttackSpeedMultiplier() {
        return 1.25;
    }

    @Override
    public double createWearerCosmeticRangedSpeedModifier() {
        return createMeleeAttackSpeedMultiplier();
    }

    @Nullable
    protected Goal createFollowParentGoal() {
        return new FollowParentGoal(this, 1.1D);
    }

    @Nullable
    protected Goal createStrollGoal() {
        return new WaterAvoidingRandomStrollGoal(this, 1, 1.0000001E-5F);
    }

    protected boolean shouldRegisterSpreadingAnger() {
        return false;
    }

    protected boolean shouldRegisterFloatGoal() {
        return true;
    }

    public boolean shouldDropHeldItemToOwner() {
        return false;
    }

    public final AnimationState squishHatAnimationState = new AnimationState();

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (source.getEntity() instanceof LivingEntity living && this.isOwnedBy(living) && !living.isShiftKeyDown())
            return false;
        boolean result = super.hurt(source, amount);
        if (result && this.isTame())
            this.setFollowMode(PracticalPet.FollowMode.FOLLOWING);
        return result;
    }

    @Override
    public boolean doHurtTarget(@NotNull Entity entity) {
        boolean result = super.doHurtTarget(entity);
        if (result)
            this.doHurtEffect(entity);
        return result;
    }

    public void doHurtEffect(@NotNull Entity entity) {
        if (entity instanceof Mob mob) {
            float amount = 1;
            if (!mob.isAlive())
                amount += mob.getMaxHealth() / 20;
            addPetXP(amount);
        }
    }

    public boolean shouldntFollowParent() {
        return this.isInSittingPose();
    }

    public boolean shouldFollowOwner() {
        return this.entityData.get(SHOULD_FOLLOW_OWNER);
    }

    public void setShouldFollowOwner(boolean following) {
        this.entityData.set(SHOULD_FOLLOW_OWNER, following);
    }

    @Override
    public int getVariant() {
        return this.entityData.get(VARIANT);
    }

    @Override
    public void setVariant(int variant) {
        this.entityData.set(VARIANT, variant);
    }

    public boolean isRainbow() {
        return this.entityData.get(IS_RAINBOW);
    }

    public void setIsRainbow(boolean value) {
        this.entityData.set(IS_RAINBOW, value);
    }

    public boolean isInterested() {
        return this.entityData.get(IS_INTERESTED);
    }

    public void setIsInterested(boolean value) {
        this.entityData.set(IS_INTERESTED, value);
    }

    @Override
    public int petLevel() {
        return this.entityData.get(PET_LEVEL);
    }

    @Override
    public void setPetLevelRaw(int petLevel) {
        this.entityData.set(PET_LEVEL, petLevel);
        this.refreshPetLevelAttributeMultipliers();
    }

    @Override
    public float petXP() {
        return this.entityData.get(PET_XP);
    }

    @Override
    public void setPetXPRaw(float petXP) {
        this.entityData.set(PET_XP, petXP);
    }

    public FollowMode followMode() {
        if (this.isOrderedToSit())
            return FollowMode.SITTING;
        if (this.shouldFollowOwner())
            return FollowMode.FOLLOWING;
        return FollowMode.WANDERING;
    }

    public void setFollowMode(FollowMode followMode) {
        this.setOrderedToSit(followMode == FollowMode.SITTING);
        this.setShouldFollowOwner(followMode != FollowMode.WANDERING);
    }

    public void incrementFollowMode() {
        switch (this.followMode()) {
            case FOLLOWING -> setFollowMode(FollowMode.WANDERING);
            case WANDERING -> setFollowMode(FollowMode.SITTING);
            case SITTING -> setFollowMode(FollowMode.FOLLOWING);
        }
    }

    public boolean canSitStand() {
        return true;
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
    public void die(DamageSource p_21809_) {
        this.deathMessage = this.getCombatTracker().getDeathMessage();
        super.die(p_21809_);
    }

    @Override
    public @NotNull InteractionResult mobInteract(Player player, @NotNull InteractionHand hand) {

        ItemStack itemstack = player.getItemInHand(hand);

        if (this.level().isClientSide) {
            if (this.isTame() && this.isOwnedBy(player)) {
                return InteractionResult.SUCCESS;
            } else {
                return !this.isTameItem(itemstack) || !(this.getHealth() < this.getMaxHealth()) && this.isTame() ? InteractionResult.PASS : InteractionResult.SUCCESS;
            }
        } else {
            if (this.isTame()) {
                if (this.isOwnedBy(player)) {
                    if (this.isFood(itemstack) && this.getHealth() < this.getMaxHealth()) {
                        this.healFromEatingItem(itemstack);
                        this.usePlayerItem(player, hand, itemstack);
                        return InteractionResult.CONSUME;
                    }

                    InteractionResult interactionresult = super.mobInteract(player, hand);
                    if (!interactionresult.consumesAction() && this.canSitStand()) {
                        this.emptyOwnerInteraction(player, itemstack);
                    }

                    return interactionresult;
                }
            } else if (this.isTameItem(itemstack)) {
                this.usePlayerItem(player, hand, itemstack);
                if (this.random.nextInt(3) == 0 && !ForgeEventFactory.onAnimalTame(this, player)) {
                    this.tame(player);
                    //Tame heart particle
                    this.level().broadcastEntityEvent(this, (byte) 7);
                } else {
                    //Fail particle
                    this.level().broadcastEntityEvent(this, (byte) 6);
                }

                this.setPersistenceRequired();
                return InteractionResult.CONSUME;
            }

            InteractionResult interactionresult1 = super.mobInteract(player, hand);
            if (interactionresult1.consumesAction()) {
                this.setPersistenceRequired();
            }

            return interactionresult1;
        }
    }

    protected void emptyOwnerInteraction(Player player, ItemStack itemStack) {
        if (player.isSecondaryUseActive()) {
            NetworkHooks.openScreen(
                    (ServerPlayer) player,
                    new SimpleMenuProvider(
                            (id, inv, p) -> new PracticalPetMenu(id, inv, this),
                            this.getName()
                    ),
                    buf -> buf.writeVarInt(this.getId())
            );
        } else {
            incrementFollowMode();
            player.displayClientMessage(Component.translatable("action.practicalpets." + followMode().toString()).withStyle(ChatFormatting.GREEN), true);
        }
    }

    public static final LocalDate FOUNDERS_HAT_END_DATE = LocalDate.of(2025, 8, 1);

    public static final String FOUNDERS_HATS_CLAIMED_TAG_ID = PracticalPets.MODID + ":founders_hats_claimed";

    @Override
    public void tame(@NotNull Player player) {
        if (!this.level().isClientSide()) {
            // Only run if this is the first time it's tamed, since I plan to add owner switching later.
            // And also only if it doesn't have a bowtie already,
            // in case I make some pets rarely spawn with a special kind of bowtie or something
            if (!this.isTame() && this.getPetNeckItem().isEmpty()) {
                ItemStack bowtie = new ItemStack(PPItems.PET_BOWTIE.get());

                float hue = this.getRandom().nextFloat();
                int rgb = Color.HSBtoRGB(hue, 1, 1);

                CompoundTag tag = new CompoundTag();
                CompoundTag display = new CompoundTag();
                display.putInt("color", rgb);
                tag.put("display", display);
                bowtie.setTag(tag);

                this.setPetNeckItem(bowtie);
            }

            CompoundTag persistentData = player.getPersistentData();
            CompoundTag persistedData = persistentData.getCompound(Player.PERSISTED_NBT_TAG);
            int foundersHatsClaimed = persistedData.getInt(FOUNDERS_HATS_CLAIMED_TAG_ID);
            if (
                    this.getPetHeadItem().isEmpty()
                            //&& LocalDate.now().isBefore(FOUNDERS_HAT_END_DATE)
                            && foundersHatsClaimed < 5
            ) {
                ItemStack foundersHat = new ItemStack(PPItems.ANNIVERSARY_PET_HAT_0.get());
                AnniversaryPetHat.putPlayerName(foundersHat, player.getName().getString());
                this.setPetHeadItem(foundersHat);
                persistedData.putInt(FOUNDERS_HATS_CLAIMED_TAG_ID, foundersHatsClaimed + 1);
                persistentData.put(Player.PERSISTED_NBT_TAG, persistedData);
                player.sendSystemMessage(Component.translatable("ui.practicalpets.chat.got_founders_hat", foundersHat.getDisplayName(), foundersHatsClaimed + 1));
                player.sendSystemMessage(Component.translatable("ui.practicalpets.info.item.anniversary_pet_hat_0", foundersHat.getDisplayName()));
                player.playSound(PPSounds.PET_LEVEL_UP.get());
            }
        }
        super.tame(player);
    }

    @Override
    public boolean wantsToAttack(@NotNull LivingEntity target, @NotNull LivingEntity owner) {
        if (target instanceof OwnableEntity pet) {
            return !owner.getUUID().equals(pet.getOwnerUUID());
        } else
            return !(target instanceof Player) || !(owner instanceof Player) || ((Player) owner).canHarmPlayer((Player) target);
    }

    @Override
    protected void usePlayerItem(@NotNull Player player, @NotNull InteractionHand hand, @NotNull ItemStack itemStack) {
        if (this.isFood(itemStack) || itemStack.isEdible()) {
            //this.triggerAnim("eating", "eat");
            this.playSound(SoundEvents.GENERIC_EAT, 1.0F, 1.0F);
        }

        super.usePlayerItem(player, hand, itemStack);
    }

    @Override
    //for domestication innovation command drum to work better
    public void setOrderedToSit(boolean bool) {
        super.setOrderedToSit(bool);
        if (bool) this.setShouldFollowOwner(true);
    }

    public boolean hasTarget() {
        return this.entityData.get(HAS_TARGET);
    }

    private void setHasTarget(boolean value) {
        this.entityData.set(HAS_TARGET, value);
    }

    @Override
    public void tick() {
        if (!this.level().isClientSide)
            this.setHasTarget(this.getTarget() != null);
        super.tick();
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (this.level() instanceof ServerLevel serverLevel) {
            this.updatePersistentAnger(serverLevel, true);
        }
    }

    @Nullable
    private UUID persistentAngerTarget;
    private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(20, 39);

    @Override
    public int getRemainingPersistentAngerTime() {
        return this.entityData.get(DATA_REMAINING_ANGER_TIME);
    }

    @Override
    public void setRemainingPersistentAngerTime(int p_30404_) {
        this.entityData.set(DATA_REMAINING_ANGER_TIME, p_30404_);
    }

    @Override
    public void startPersistentAngerTimer() {
        this.setRemainingPersistentAngerTime(2 * PERSISTENT_ANGER_TIME.sample(this.random));
    }

    @Override
    public @Nullable UUID getPersistentAngerTarget() {
        return this.persistentAngerTarget;
    }

    @Override
    public void setPersistentAngerTarget(@Nullable UUID p_30400_) {
        this.persistentAngerTarget = p_30400_;
    }

    private static final EntityDataAccessor<Integer> DATA_REMAINING_ANGER_TIME = SynchedEntityData.defineId(PracticalPet.class, EntityDataSerializers.INT);

    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob partner) {
        PracticalPet baby = (PracticalPet) this.getType().create(level);

        if (baby != null) {
            if (this.isTame()) {
                baby.setOwnerUUID(this.getOwnerUUID());
                baby.setTame(true);
            }

            if (partner.getClass() == this.getClass() && partner instanceof PracticalPet pet) {
                if (this.random.nextBoolean())
                    baby.setVariant(this.getVariant());
                else
                    baby.setVariant(pet.getVariant());
            }
        }

        return baby;
    }

    public boolean sharesOwnerWith(OwnableEntity pet) {
        return PPUtil.petsShareOwner(this, pet);
    }

    @Override
    public boolean isAlliedTo(@NotNull Entity entity) {
        return (entity instanceof OwnableEntity pet && this.sharesOwnerWith(pet)) || super.isAlliedTo(entity);
    }

    public void onDropHeldItemToOwner() {
    }
}
