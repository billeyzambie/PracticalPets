package billeyzambie.practicalpets.entity;

import billeyzambie.animationcontrollers.ACData;
import billeyzambie.animationcontrollers.ACEntity;
import billeyzambie.practicalpets.items.RubberDuckyPetHat;
import billeyzambie.practicalpets.misc.PPItems;
import billeyzambie.practicalpets.misc.PPSounds;
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
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public abstract class PracticalPet extends TamableAnimal implements ACEntity {

    HashMap<String, ACData> ACData = new HashMap<>();

    @Override
    public HashMap<String, ACData> getACData() {
        return ACData;
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
    private static final EntityDataAccessor<ItemStack> BODY_ITEM = SynchedEntityData.defineId(PracticalPet.class, EntityDataSerializers.ITEM_STACK);
    private static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(PracticalPet.class, EntityDataSerializers.INT);


    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(HAS_TARGET, false);
        this.entityData.define(SHOULD_FOLLOW_OWNER, true);
        this.entityData.define(PET_LEVEL, 1);
        this.entityData.define(PET_XP, 0f);
        this.entityData.define(HEAD_ITEM, ItemStack.EMPTY);
        this.entityData.define(NECK_ITEM, ItemStack.EMPTY);
        this.entityData.define(BODY_ITEM, ItemStack.EMPTY);
        this.entityData.define(VARIANT, 0);
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setShouldFollowOwner(compoundTag.getBoolean("ShouldFollowOwner"));
        this.setPetLevel(compoundTag.getInt("PetLevel"));
        this.setPetXP(compoundTag.getFloat("PetXP"));
        this.setHeadItem(ItemStack.of(compoundTag.getCompound("HeadItem")));
        this.setNeckItem(ItemStack.of(compoundTag.getCompound("NeckItem")));
        this.setBodyItem(ItemStack.of(compoundTag.getCompound("BodyItem")));
        this.setVariant(compoundTag.getInt("Variant"));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putBoolean("ShouldFollowOwner", this.shouldFollowOwner());
        compoundTag.putInt("PetLevel", this.petLevel());
        compoundTag.putFloat("PetXP", this.petXP());
        compoundTag.put("HeadItem", getHeadItem().save(new CompoundTag()));
        compoundTag.put("NeckItem", getNeckItem().save(new CompoundTag()));
        compoundTag.put("BodyItem", getBodyItem().save(new CompoundTag()));
        compoundTag.putInt("Variant", this.variant());
    }

    public abstract HashMap<Integer, Integer> variantSpawnWeights();
    @Override
    public @NotNull SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor level, @NotNull DifficultyInstance difficulty, MobSpawnType spawnType,
                                                 @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag tag) {
        int selectedVariant = pickRandomWeightedVariant();
        this.setVariant(selectedVariant);

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

        throw new AssertionError("I don't this this will ever happen (error in pickRandomWeightedVariant in PracticalPet.java)");
    }

    public ItemStack getEquippedItem(PetCosmetic.Slot slot) {
        switch (slot) {
            case HEAD -> {
                return getHeadItem();
            }
            case NECK -> {
                return getNeckItem();
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

    private void refreshAnyEquipmentIsBrave() {
        for (PetCosmetic.Slot slot : PetCosmetic.Slot.values()) {
            ItemStack cosmeticStack = this.getEquippedItem(slot);
            if (
                    !cosmeticStack.isEmpty()
                            && cosmeticStack.getItem() instanceof PetCosmetic cosmetic
                            && cosmetic.causesBravery()
            ) {
                anyEquipmentIsBrave = true;
                return;
            }
        }
        anyEquipmentIsBrave = false;
    }

    public boolean anyEquipmentIsBrave() {
        return anyEquipmentIsBrave;
    }

    public boolean shouldDefendOwner() {
        return anyEquipmentIsBrave();
    }

    public abstract int getLevel1MaxHealth();

    public abstract int getLevel1AttackDamage();

    public abstract int getLevel10MaxHealth();

    public abstract int getLevel10AttackDamage();

    protected void setAttributesAccordingToPetLevel() {
        int level = this.petLevel();
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(
                Math.round(Mth.lerp((level - 1) / 9d, getLevel1MaxHealth(), getLevel10MaxHealth()) / 2d) * 2
        );
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(
                Mth.lerp((level - 1) / 9d, getLevel1AttackDamage(), getLevel10AttackDamage())
        );
    }

    public boolean isLevelable() {
        return isTame() && !isBaby();
    }

    public boolean shouldPanic() {
        return !anyEquipmentIsBrave();
    }

    public PracticalPet(EntityType<? extends TamableAnimal> entityType, Level level) {
        super(entityType, level);
        setAttributesAccordingToPetLevel();
    }

    @Override
    public final boolean isFood(ItemStack itemStack) {
        return isTameItem(itemStack) || isFoodThatDoesntTame(itemStack);
    }

    public boolean isTameItem(ItemStack itemStack) {
        return false;
    }

    public boolean isFoodThatDoesntTame(ItemStack itemStack) {
        return false;
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
        }
        else if (healOverride == null) {
            healAmount = foodProperties.getNutrition();
        }
        else {
            healAmount = foodProperties.getNutrition();
            switch (healOverride.type()) {
                case OVERRIDE -> healAmount = healOverride.value();
                case ADD -> healAmount += healOverride.value();
                case MULTIPLY -> healAmount *= healOverride.value();
            }
        }

        this.heal(healAmount);
    }

    @Override
    protected void registerGoals() {
        if (shouldRegisterFloatGoal())
            this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(10, new PanicIfShouldGoal(this, 1.3D));
        this.goalSelector.addGoal(20, new SitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(50, new MeleeAttackGoal(this, 1.25, false));
        this.goalSelector.addGoal(60, new FollowOwnerWanderableGoal(this, 1.0D, 10.0F, 5.0F, false));
        this.goalSelector.addGoal(70, new PredicateTemptGoal(this, 1.0D, PracticalPet::isFood, false));

        Goal followParentGoal = getFollowParentGoal();
        if (followParentGoal != null)
            this.goalSelector.addGoal(80, followParentGoal);

        this.goalSelector.addGoal(90, new BreedGoal(this, 0.8D));

        Goal strollGoal = getStrollGoal();
        if (strollGoal != null)
            this.goalSelector.addGoal(100, strollGoal);

        this.goalSelector.addGoal(110, new LookAtPlayerGoal(this, Player.class, 10.0F));

        this.targetSelector.addGoal(20, new OwnerHurtByTargetIfShouldGoal(this));
        this.targetSelector.addGoal(30, new OwnerHurtTargetIfShouldGoal(this));
    }

    @Nullable
    protected Goal getFollowParentGoal() {
        return new FollowParentGoal(this, 1.1D);
    }

    @Nullable
    protected Goal getStrollGoal() {
        return new WaterAvoidingRandomStrollGoal(this, 1, 1.0000001E-5F);
    }

    protected boolean shouldRegisterFloatGoal() {
        return true;
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
        if (result && this.getHeadItem().getItem() instanceof RubberDuckyPetHat && source.getEntity() != null) {
            RubberDuckyPetHat.applyEffect(this, source.getEntity());
        }
        return result;
    }

    @Override
    public boolean doHurtTarget(@NotNull Entity entity) {
        boolean result = super.doHurtTarget(entity);
        if (result && entity instanceof Mob) {
            float amount = 1;
            if (!entity.isAlive())
                amount += 2;
            addPetXP(amount);
        }
        if (this.getHeadItem().getItem() instanceof RubberDuckyPetHat) {
            RubberDuckyPetHat.applyEffect(this, entity);
        }
        return result;
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

    public int variant() {
        return this.entityData.get(VARIANT);
    }

    public void setVariant(int variant) {
        this.entityData.set(VARIANT, variant);
        setAttributesAccordingToPetLevel();
    }

    public int petLevel() {
        return this.entityData.get(PET_LEVEL);
    }

    public void setPetLevel(int petLevel) {
        this.entityData.set(PET_LEVEL, petLevel);
        setAttributesAccordingToPetLevel();
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

            this.level().playSound(null, this.blockPosition(), PPSounds.PET_LEVEL_UP.get(), SoundSource.NEUTRAL, 1.0F, 1.0F);

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
    protected void dropAllDeathLoot(DamageSource p_21192_) {
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

    @Override
    public @NotNull InteractionResult mobInteract(Player player, InteractionHand hand) {

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
                return !this.isFood(itemstack) || !(this.getHealth() < this.getMaxHealth()) && this.isTame() ? InteractionResult.PASS : InteractionResult.SUCCESS;
            }
        } else {
            if (this.isTame()) {
                if (this.isOwnedBy(player)) {
                    if (item instanceof PetCosmetic cosmetic && cosmetic.canBePutOn(this)) {
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
                    if (!interactionresult.consumesAction() && canSitStand()) {
                        incrementFollowMode();
                        player.displayClientMessage(Component.translatable("action.practicalpets." + followMode().toString()).withStyle(ChatFormatting.GREEN), true);
                    }

                    return interactionresult;
                }
            } else if (this.isFood(itemstack)) {
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

    @Override
    public void tame(Player player) {
        //only run if this is the first time it's tamed, since I plan to add owner switching later
        if (!isTame() && getNeckItem().isEmpty()) {
            ItemStack bowtie = new ItemStack(PPItems.PET_BOWTIE.get());

            float hue = level().random.nextFloat();
            int rgb = java.awt.Color.HSBtoRGB(hue, 1.0f, 1.0f);

            CompoundTag tag = new CompoundTag();
            CompoundTag display = new CompoundTag();
            display.putInt("color", rgb);
            tag.put("display", display);
            bowtie.setTag(tag);

            setNeckItem(bowtie);
        }
        super.tame(player);
    }

    @Override
    public boolean wantsToAttack(LivingEntity target, LivingEntity owner) {
        if (target instanceof TamableAnimal pet) {
            return !pet.isTame() || pet.getOwner() != owner;
        } else
            return !(target instanceof Player) || !(owner instanceof Player) || ((Player) owner).canHarmPlayer((Player) target);
    }

    @Override
    protected void usePlayerItem(Player player, InteractionHand hand, ItemStack itemStack) {
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
}
