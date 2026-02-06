package billeyzambie.practicalpets.entity;

import billeyzambie.animationcontrollers.ACData;
import billeyzambie.animationcontrollers.ACEntity;
import billeyzambie.animationcontrollers.BVCData;
import billeyzambie.practicalpets.entity.dinosaur.Pigeon;
import billeyzambie.practicalpets.misc.*;
import billeyzambie.practicalpets.network.RandomIdle1AnimPacket;
import billeyzambie.practicalpets.ui.PracticalPetMenu;
import billeyzambie.practicalpets.items.RubberDuckyPetHat;
import billeyzambie.practicalpets.goal.*;
import billeyzambie.practicalpets.items.PetCosmetic;
import billeyzambie.practicalpets.items.PetHat;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.monster.RangedAttackMob;
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
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.time.LocalDate;
import java.util.*;
import java.util.List;

public abstract class PracticalPet extends TamableAnimal implements ACEntity, NeutralMob, RangedAttackMob {

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
            return super.toString().toLowerCase(Locale.ENGLISH);
        }
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
        this.setPetLevel(compoundTag.getInt("PetLevel"));
        this.setPetXP(compoundTag.getFloat("PetXP"));
        this.setHeadItem(ItemStack.of(compoundTag.getCompound("HeadItem")));
        this.setNeckItem(ItemStack.of(compoundTag.getCompound("NeckItem")));
        this.setBackItem(ItemStack.of(compoundTag.getCompound("BackItem")));
        this.setBodyItem(ItemStack.of(compoundTag.getCompound("BodyItem")));
        this.setVariant(compoundTag.getInt("Variant"));
        this.setIsRainbow(compoundTag.getBoolean("IsRainbow"));
        this.readPersistentAngerSaveData(this.level(), compoundTag);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putBoolean("ShouldFollowOwner", this.shouldFollowOwner());
        compoundTag.putInt("PetLevel", this.petLevel());
        compoundTag.putFloat("PetXP", this.petXP());
        compoundTag.put("HeadItem", this.getHeadItem().save(new CompoundTag()));
        compoundTag.put("NeckItem", this.getNeckItem().save(new CompoundTag()));
        compoundTag.put("BackItem", this.getBackItem().save(new CompoundTag()));
        compoundTag.put("BodyItem", this.getBodyItem().save(new CompoundTag()));
        compoundTag.putInt("Variant", this.getVariant());
        compoundTag.putBoolean("IsRainbow", this.isRainbow());
        this.addPersistentAngerSaveData(compoundTag);
    }

    public abstract HashMap<Integer, Integer> variantSpawnWeights();

    @Override
    public @NotNull SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor level, @NotNull DifficultyInstance difficulty, @NotNull MobSpawnType spawnType,
                                                 @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag tag) {
        int selectedVariant = this.pickRandomWeightedVariant();
        this.setVariant(selectedVariant);

        this.setPetLevel(1);

        if (this.random.nextInt(300) == 0)
            this.setIsRainbow(true);

        return super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData, tag);
    }

    private int pickRandomWeightedVariant() {
        var weights = variantSpawnWeights();
        if (weights == null)
            return 0;
        int totalWeight = 0;
        for (int weight : weights.values()) {
            totalWeight += weight;
        }

        int randomValue = this.random.nextInt(totalWeight);
        int weightAddedSoFar = 0;

        for (Map.Entry<Integer, Integer> entry : weights.entrySet()) {
            weightAddedSoFar += entry.getValue();
            if (randomValue < weightAddedSoFar) {
                return entry.getKey();
            }
        }

        throw new AssertionError("I don't think this will ever happen (error in pickRandomWeightedVariant in PracticalPet.java)");
    }

    public final AnimationState randomIdle1AnimationState = new AnimationState();

    protected void sendRandomIdle1Packet() {
        PPNetworking.CHANNEL.send(
                PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> this),
                new RandomIdle1AnimPacket(this.getId())
        );
    }

    public ItemStack getEquippedItem(PetCosmetic.Slot slot) {
        switch (slot) {
            case HEAD -> {
                return getHeadItem();
            }
            case NECK -> {
                return getNeckItem();
            }
            case BACK -> {
                return getBackItem();
            }
            case BODY -> {
                return getBodyItem();
            }
        }
        throw new AssertionError("Missing case for getting pet equipment in slot " + slot);
    }

    public void setEquippedItem(ItemStack itemStack, PetCosmetic.Slot slot) {
        switch (slot) {
            case HEAD -> setHeadItem(itemStack);
            case NECK -> setNeckItem(itemStack);
            case BACK -> setBackItem(itemStack);
            case BODY -> setBodyItem(itemStack);
        }
    }

    public ItemStack getHeadItem() {
        return this.entityData.get(HEAD_ITEM);
    }

    public void setHeadItem(ItemStack itemStack) {
        if (itemStack.isEmpty() && this.getHeadItem().getItem() instanceof PetCosmetic cosmetic) {
            playSound(cosmetic.getEquipSound());
        }
        this.entityData.set(HEAD_ITEM, itemStack);
        refreshAnyEquipmentIsBrave();
        if (itemStack.getItem() instanceof PetCosmetic cosmetic) {
            this.playSound(cosmetic.getEquipSound());
        }
    }

    public ItemStack getNeckItem() {
        return this.entityData.get(NECK_ITEM);
    }

    public void setNeckItem(ItemStack itemStack) {
        if (itemStack.isEmpty() && this.getNeckItem().getItem() instanceof PetCosmetic cosmetic) {
            playSound(cosmetic.getEquipSound());
        }
        this.entityData.set(NECK_ITEM, itemStack);
        refreshAnyEquipmentIsBrave();
        if (itemStack.getItem() instanceof PetCosmetic cosmetic) {
            this.playSound(cosmetic.getEquipSound());
        }
    }

    public ItemStack getBackItem() {
        return this.entityData.get(BACK_ITEM);
    }

    public void setBackItem(ItemStack itemStack) {
        if (itemStack.isEmpty() && this.getBackItem().getItem() instanceof PetCosmetic cosmetic) {
            playSound(cosmetic.getEquipSound());
        }
        this.entityData.set(BACK_ITEM, itemStack);
        refreshAnyEquipmentIsBrave();
        if (itemStack.getItem() instanceof PetCosmetic cosmetic) {
            this.playSound(cosmetic.getEquipSound());
        }
    }

    public ItemStack getBodyItem() {
        return this.entityData.get(BODY_ITEM);
    }

    public void setBodyItem(ItemStack itemStack) {
        if (itemStack.isEmpty() && this.getBodyItem().getItem() instanceof PetCosmetic cosmetic) {
            playSound(cosmetic.getEquipSound());
        }
        this.entityData.set(BODY_ITEM, itemStack);
        refreshAnyEquipmentIsBrave();
        if (itemStack.getItem() instanceof PetCosmetic cosmetic) {
            this.playSound(cosmetic.getEquipSound());
        }
    }

    private boolean anyEquipmentIsBrave = false;

    public void refreshAnyEquipmentIsBrave() {
        boolean hasBraveEquipment = false;
        Optional<PetCosmetic.Slot> rangedSlot = Optional.empty();
        for (PetCosmetic.Slot slot : PetCosmetic.Slot.values()) {
            ItemStack cosmeticStack = this.getEquippedItem(slot);
            if (
                    !cosmeticStack.isEmpty()
                            && cosmeticStack.getItem() instanceof PetCosmetic cosmetic
            ) {
                if (cosmetic.causesBravery(cosmeticStack)) {
                    hasBraveEquipment = true;
                }
                if (rangedSlot.isEmpty() && cosmetic.canPerformRangedAttack(cosmeticStack)) {
                    rangedSlot = Optional.of(slot);
                }
            }
        }
        this.anyEquipmentIsBrave = hasBraveEquipment;
        this.canShootFromSlot = rangedSlot;
    }

    public boolean anyEquipmentIsBrave() {
        return anyEquipmentIsBrave;
    }

    public boolean shouldDefendOwner(@NotNull LivingEntity target) {
        return this.anyEquipmentIsBrave() || (this.getMaxHealth() >= 20 && this.getAttributeValue(Attributes.ATTACK_DAMAGE) >= 3);
    }
    public boolean shouldDefendSelf() {
        return this.anyEquipmentIsBrave() || (this.getMaxHealth() >= 20 && this.getAttributeValue(Attributes.ATTACK_DAMAGE) >= 3);
    }

    public abstract int getLevel1MaxHealth();

    public abstract int getLevel1AttackDamage();

    public abstract int getLevel10MaxHealth();

    public abstract int getLevel10AttackDamage();

    protected void setAttributesAccordingToPetLevel() {
        int level = this.petLevel();
        double progress1to10 = (level - 1) / 9d;
        //if (progress1to10 < 1)
        //    progress1to10 *= progress1to10;
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(
                Math.round(Mth.lerp(progress1to10, getLevel1MaxHealth(), getLevel10MaxHealth()) / 2d) * 2
        );
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(
                Mth.lerp(progress1to10, getLevel1AttackDamage(), getLevel10AttackDamage())
        );
    }

    public boolean isLevelable() {
        return isTame() && !isBaby();
    }

    public boolean shouldPanic() {
        return !this.shouldDefendSelf();
    }

    public PracticalPet(EntityType<? extends TamableAnimal> entityType, Level level) {
        super(entityType, level);
        setAttributesAccordingToPetLevel();
    }

    @Override
    public final boolean isFood(@NotNull ItemStack itemStack) {
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
                default -> {}
            }
        }
        else {
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
        this.goalSelector.addGoal(30, new RangedAttackIfShouldGoal(this, this.createMeleeAttackSpeedMultiplier(), 20, 40, 20f));
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

        this.targetSelector.addGoal(10, new OwnerHurtByTargetIfShouldGoal(this));
        this.targetSelector.addGoal(20,
                this.shouldRegisterSpreadingAnger()
                        ? new DefendSelfIfShouldGoal(this).setAlertOthers()
                        : new DefendSelfIfShouldGoal(this)
        );
        this.targetSelector.addGoal(30, new OwnerHurtTargetIfShouldGoal(this));
        if (this.shouldRegisterSpreadingAnger())
            this.targetSelector.addGoal(40, new ResetUniversalAngerTargetGoal<>(this, true));
    }

    protected @NotNull Goal createMeleeAttackGoal() {
        return new MeleeAttackIfShouldGoal(this, this.createMeleeAttackSpeedMultiplier(), false);
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
        return 1.25D;
    }

    protected double createMeleeAttackSpeedMultiplier() {
        return 1.25;
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
        for (PetCosmetic.Slot slot : PetCosmetic.Slot.values()) {
            ItemStack cosmeticStack = this.getEquippedItem(slot);
            if (!cosmeticStack.isEmpty() && cosmeticStack.getItem() instanceof PetCosmetic cosmetic)
                amount *= cosmetic.damageMultiplier();
        }
        boolean result = super.hurt(source, amount);
        if (result) {
            if (this.getHeadItem().getItem() instanceof RubberDuckyPetHat && source.getEntity() != null) {
                RubberDuckyPetHat.applyEffect(this, source.getEntity());
            }
            if (this.isTame())
                this.setFollowMode(FollowMode.FOLLOWING);
        }

        return result;
    }

    @Override
    public boolean doHurtTarget(@NotNull Entity entity) {
        boolean result = super.doHurtTarget(entity);
        this.doHurtEffect(entity, result);
        return result;
    }

    public void doHurtEffect(@NotNull Entity entity, boolean entityGotHurt) {
        if (entityGotHurt && entity instanceof Mob mob) {
            float amount = 1;
            if (!mob.isAlive())
                amount += mob.getMaxHealth() / 10;
            addPetXP(amount);
        }
        if (this.getHeadItem().getItem() instanceof RubberDuckyPetHat) {
            RubberDuckyPetHat.applyEffect(this, entity);
        }
    }

    public boolean damageEntity(Entity target, float amount) {
        return target.hurt(this.damageSources().mobAttack(this), amount);
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

    public int getVariant() {
        return this.entityData.get(VARIANT);
    }

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

    public int petLevel() {
        return this.entityData.get(PET_LEVEL);
    }

    public void setPetLevel(int petLevel) {
        this.entityData.set(PET_LEVEL, petLevel);
        setAttributesAccordingToPetLevel();
    }

    public void setPetLevelPlus(int petLevel) {
        this.setPetLevel(petLevel);
        this.setPetXP(getTotalPetXPNeededForLevel(petLevel));
        this.setHealth((float) this.getAttribute(Attributes.MAX_HEALTH).getValue());
        this.playLevelUpSound();
    }

    public float petXP() {
        return this.entityData.get(PET_XP);
    }

    private void setPetXP(float petXP) {
        this.entityData.set(PET_XP, petXP);
    }

    public void addPetXP(float amount) {
        if (!isLevelable())
            return;

        float multiplier = 1;

        if (getHeadItem().getItem() instanceof PetHat petHat)
            multiplier *= petHat.petXPMultiplier();

        setPetXP(petXP() + amount * multiplier);

        if (petXP() >= getTotalPetXPNeededForLevel(petLevel() + 1)) {
            upgradeToLevel(petLevel() + 1);
        }
    }

    public void upgradeToLevel(int level) {
        int previousLevel = petLevel();
        setPetLevel(level);
        if (!level().isClientSide()) {

            this.playLevelUpSound();

            Component message = Component.translatable(
                    "ui.practicalpets.chat.level_up",
                    Component.literal(this.getName().getString()).withStyle(ChatFormatting.LIGHT_PURPLE),
                    Component.literal(String.valueOf(level)).withStyle(ChatFormatting.BLUE)
            ).withStyle(ChatFormatting.WHITE);

            double radius = 16.0;
            AABB area = new AABB(this.blockPosition()).inflate(radius);
            List<Player> nearbyPlayers = this.level().getEntitiesOfClass(Player.class, area);

            for (Player player : nearbyPlayers) {
                player.sendSystemMessage(message);
            }

            if (this.getOwner() instanceof ServerPlayer owner && !nearbyPlayers.contains(owner)) {
                owner.sendSystemMessage(message);
            }

        }

        setHealth(getMaxHealth());
    }

    private void playLevelUpSound() {
        this.level().playSound(null, this.blockPosition(), PPSounds.PET_LEVEL_UP.get(), SoundSource.NEUTRAL,
                1.0F, (float)Math.pow(2, (this.petLevel() - 2) / 12d));
    }

    /**
     * unused
     */
    private static float xpFormula1(int level) {
        if (level == 1)
            return 0;
        return 40 * (level - 1) + 2.5f * level * level;
    }

    private static float xpFormula2(int level) {
        return 40 * (level - 1) + 10 * (level - 1) * (level - 1) * (level - 1);
    }

    public float getTotalPetXPNeededForLevel(int level) {
        return xpFormula2(level);
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
    protected void dropAllDeathLoot(@NotNull DamageSource p_21192_) {
        super.dropAllDeathLoot(p_21192_);
        dropAllEquipment();
    }

    public void dropAllEquipment() {
        for (PetCosmetic.Slot slot : PetCosmetic.Slot.values()) {
            ItemStack stack = this.getEquippedItem(slot).copy();
            if (!this.isAlive() && this.hasCustomName()) {
                Component itemName = this.getDisplayName().copy().setStyle(Style.EMPTY.withColor(ChatFormatting.GOLD));
                stack.setHoverName(itemName);
            }
            this.spawnAtLocation(stack);
            this.setEquippedItem(ItemStack.EMPTY, slot);
        }
    }

    public boolean hideEquipment() {
        return false;
    }

    @Override
    public @NotNull InteractionResult mobInteract(Player player, @NotNull InteractionHand hand) {

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
                    this.level().playSound(player, this.getX(), this.getY(), this.getZ(), SoundEvents.SHEEP_SHEAR, this.getSoundSource(), 1.0F, this.random.nextFloat() * 0.4F + 0.8F);
                    return InteractionResult.SUCCESS;
                } else {
                    itemstack.hurtAndBreak(1, player, (lambdaPlayer) -> {
                        lambdaPlayer.broadcastBreakEvent(hand);
                    });
                    this.dropAllEquipment();
                    return InteractionResult.CONSUME;
                }
            }

        }

        if (this.level().isClientSide) {
            if (this.isTame() && this.isOwnedBy(player)) {
                return InteractionResult.SUCCESS;
            } else {
                return !this.isTameItem(itemstack) || !(this.getHealth() < this.getMaxHealth() ) && this.isTame() ? InteractionResult.PASS : InteractionResult.SUCCESS;
            }
        } else {
            if (this.isTame()) {
                if (this.isOwnedBy(player)) {
                    if (item instanceof PetCosmetic cosmetic && cosmetic.canBePutOn(this) && !player.isSecondaryUseActive()) {
                        PetCosmetic.Slot slot = cosmetic.slot();
                        ItemStack currentCosmetic = this.getEquippedItem(slot);
                        if (currentCosmetic.isEmpty()) {
                            this.setEquippedItem(itemstack.copy(), slot);
                            this.usePlayerItem(player, hand, itemstack);
                            return InteractionResult.CONSUME;
                        } else {
                            this.spawnAtLocation(currentCosmetic);
                            this.setEquippedItem(ItemStack.EMPTY, slot);
                            return InteractionResult.SUCCESS;
                        }
                    }
                    if (this.isFood(itemstack) && this.getHealth() < this.getMaxHealth()) {
                        this.healFromEatingItem(itemstack);
                        this.usePlayerItem(player, hand, itemstack);
                        return InteractionResult.CONSUME;
                    }

                    InteractionResult interactionresult = super.mobInteract(player, hand);
                    if (!interactionresult.consumesAction() && this.canSitStand()) {
                        if (player.isSecondaryUseActive()) {
                            NetworkHooks.openScreen(
                                    (ServerPlayer) player,
                                    new SimpleMenuProvider(
                                            (id, inv, p) -> new PracticalPetMenu(id, inv, this),
                                            this.getName()
                                    ),
                                    buf -> buf.writeVarInt(this.getId())
                            );
                        }
                        else {
                            incrementFollowMode();
                            player.displayClientMessage(Component.translatable("action.practicalpets." + followMode().toString()).withStyle(ChatFormatting.GREEN), true);
                        }
                    }

                    return interactionresult;
                }
            } else if (this.isTameItem(itemstack)) {
                this.usePlayerItem(player, hand, itemstack);
                if (this.random.nextInt(3) == 0 && !ForgeEventFactory.onAnimalTame(this, player)) {
                    this.tame(player);
                    this.setOrderedToSit(true);
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

    public static final LocalDate FOUNDERS_HAT_END_DATE = LocalDate.of(2025, 8, 1);

    public static final String FOUNDERS_HATS_CLAIMED_TAG_ID = PracticalPets.MODID + ":founders_hats_claimed";
    @Override
    public void tame(@NotNull Player player) {
        if (!this.level().isClientSide()) {
            // Only run if this is the first time it's tamed, since I plan to add owner switching later.
            // And also only if it doesn't have a bowtie already,
            // in case I make some pets rarely spawn with a special kind of bowtie or something
            if (!this.isTame() && this.getNeckItem().isEmpty()) {
                ItemStack bowtie = new ItemStack(PPItems.PET_BOWTIE.get());

                float hue = this.getRandom().nextFloat();
                int rgb = Color.HSBtoRGB(hue, 1, 1);

                CompoundTag tag = new CompoundTag();
                CompoundTag display = new CompoundTag();
                display.putInt("color", rgb);
                tag.put("display", display);
                bowtie.setTag(tag);

                this.setNeckItem(bowtie);
            }

            CompoundTag persistentData = player.getPersistentData();
            CompoundTag persistedData = persistentData.getCompound(Player.PERSISTED_NBT_TAG);
            int foundersHatsClaimed = persistedData.getInt(FOUNDERS_HATS_CLAIMED_TAG_ID);
            if (
                    this.getHeadItem().isEmpty()
                            //&& LocalDate.now().isBefore(FOUNDERS_HAT_END_DATE)
                            && foundersHatsClaimed < 5
            ) {
                ItemStack foundersHat = new ItemStack(PPItems.ANNIVERSARY_PET_HAT_0.get());
                this.setHeadItem(foundersHat);
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
        if (target instanceof TamableAnimal pet) {
            return !pet.isTame() || pet.getOwner() != owner;
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
        this.setRemainingPersistentAngerTime(PERSISTENT_ANGER_TIME.sample(this.random));
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

    private Optional<PetCosmetic.Slot> canShootFromSlot = Optional.empty();

    public boolean canPerformRangedAttack() {
        return this.canPerformInnateRangedAttack() || this.canPerformCosmeticRangedAttack();
    }

    //There's no mob for which this is true currently, but there might be in the future.
    //Probably when/if I port velvet worms from bedrock edition.
    public boolean canPerformInnateRangedAttack() {
        return false;
    }

    public boolean canPerformCosmeticRangedAttack() {
        return this.canShootFromSlot.isPresent();
    }

    @Override
    public void performRangedAttack(@NotNull LivingEntity target, float distanceFactor) {
        if (this.canPerformCosmeticRangedAttack())
            this.performCosmeticRangedAttack(canShootFromSlot.orElseThrow(), target, distanceFactor);
        else if (this.canPerformInnateRangedAttack()) {
            this.performInnateRangedAttack(target, distanceFactor);
        }
    }

    public void performInnateRangedAttack(@NotNull LivingEntity target, float distanceFactor) {

    }

    public void performCosmeticRangedAttack(PetCosmetic.Slot slot, @NotNull LivingEntity target, float distanceFactor) {
        ItemStack equippedStack = this.getEquippedItem(slot);
        Item equippedItem = equippedStack.getItem();
        if (equippedItem instanceof PetCosmetic cosmeticItem) {
            cosmeticItem.performRangedAttack(equippedStack, this, target, distanceFactor);
        }
    }

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
}
