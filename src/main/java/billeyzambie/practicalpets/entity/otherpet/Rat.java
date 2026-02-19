package billeyzambie.practicalpets.entity.otherpet;

import billeyzambie.practicalpets.entity.CookingPet;
import billeyzambie.practicalpets.entity.PracticalPet;
import billeyzambie.practicalpets.goal.CookGoal;
import billeyzambie.practicalpets.goal.DropHeldItemToOwnerGoal;
import billeyzambie.practicalpets.misc.PPAdvancementTriggers;
import billeyzambie.practicalpets.misc.PPEntities;
import billeyzambie.practicalpets.misc.PPItems;
import billeyzambie.practicalpets.misc.PPTags;
import billeyzambie.practicalpets.util.PPUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BiomeTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraftforge.common.Tags;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Rat extends PracticalPet implements CookingPet {

    private static final EntityDataAccessor<Boolean> IS_ALBINO = SynchedEntityData.defineId(Rat.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> PATTERN_COLOR = SynchedEntityData.defineId(Rat.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> PATTERN_TYPE = SynchedEntityData.defineId(Rat.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> IS_COOKING = SynchedEntityData.defineId(Rat.class, EntityDataSerializers.BOOLEAN);
    private int cookingTicks = 0;

    private final ItemStackHandler cookingIngredients = new ItemStackHandler(COOKING_ITEMS_NEEDED);
    @Override
    public ItemStackHandler getCookingIngredients() {
        return this.cookingIngredients;
    }

    public static final int PATTERN_TYPE_COUNT = 4;
    public static final int COLOR_TYPE_COUNT = 5;
    public static final int COOK_DURATION_TICKS = 200;
    public static final int COOKING_ITEMS_NEEDED = 3;

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(PATTERN_COLOR, 0);
        this.entityData.define(PATTERN_TYPE, 0);
        this.entityData.define(IS_ALBINO, false);
        this.entityData.define(IS_COOKING, false);
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setPatternColor(compoundTag.getInt("PatternColor"));
        this.setPatternType(compoundTag.getInt("PatternType"));
        this.setIsAlbino(compoundTag.getBoolean("IsAlbino"));
        this.readCookingSaveData(compoundTag);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("PatternColor", this.getPatternColor());
        compoundTag.putInt("PatternType", this.getPatternType());
        compoundTag.putBoolean("IsAlbino", this.isAlbino());
        this.addCookingSaveData(compoundTag);
    }

    public int getPatternColor() {
        return this.entityData.get(PATTERN_COLOR);
    }

    public void setPatternColor(int value) {
        this.entityData.set(PATTERN_COLOR, value);
    }

    public int getPatternType() {
        return this.entityData.get(PATTERN_TYPE);
    }

    public void setPatternType(int value) {
        this.entityData.set(PATTERN_TYPE, value);
    }

    public boolean isAlbino() {
        return this.entityData.get(IS_ALBINO);
    }

    public void setIsAlbino(boolean value) {
        this.entityData.set(IS_ALBINO, value);
    }

    @Override
    public void setIsCooking(boolean value) {
        this.entityData.set(IS_COOKING, value);
    }

    @Override
    public boolean isCooking() {
        return this.entityData.get(IS_COOKING);
    }

    @Override
    public boolean cookingTimerRanOut() {
        return cookingTicks >= COOK_DURATION_TICKS;
    }

    @Override
    public boolean cookingIngredientsFull() {
        return PPUtil.countItems(this.getCookingIngredients()) >= COOKING_ITEMS_NEEDED;
    }

    @Override
    public boolean isHoldingCookContainer() {
        return this.getMainHandItem().is(Items.BOWL);
    }

    @Override
    public void cookingSuccess() {
        PPUtil.clear(this.getCookingIngredients());
        var newItem = new ItemStack(PPItems.RATATOUILLE.get());
        int level = this.petLevel();
        if (level > 1) {
            CompoundTag tag = new CompoundTag();
            tag.putInt("RatLevel", level);
            newItem.setTag(tag);
        }
        ItemStack previousItem = this.getMainHandItem();
        if (previousItem.hasCustomHoverName())
            newItem.setHoverName(previousItem.getHoverName());
        this.setItemSlot(EquipmentSlot.MAINHAND, newItem);
        if (this.isOrderedToSit())
            this.setFollowMode(FollowMode.FOLLOWING);

        if (this.getOwner() instanceof ServerPlayer player)
            PPAdvancementTriggers.USED_PET_ABILITY.trigger(player, this, 1);
    }

    @Override
    public int getCookingTicks() {
        return cookingTicks;
    }

    @Override
    public void setCookingTicks(int cookingTicks) {
        this.cookingTicks = cookingTicks;
    }

    public Rat(EntityType<? extends TamableAnimal> entityType, Level level) {
        super(entityType, level);
    }

    private static final HashMap<Integer, Integer> VARIANT_SPAWN_WEIGHTS = new HashMap<>() {{
        put(0, 1);
        put(1, 1);
        put(2, 1);
        put(3, 1);
        put(4, 1);
    }};

    @Override
    public HashMap<Integer, Integer> variantSpawnWeights() {
        return VARIANT_SPAWN_WEIGHTS;
    }

    @Override
    public @NotNull SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor level, @NotNull DifficultyInstance difficulty, @NotNull MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag tag) {
        SpawnGroupData result = super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData, tag);
        this.setPatternColor(this.getVariant());
        this.setPatternType(this.random.nextInt(PATTERN_TYPE_COUNT));
        if (this.random.nextInt(100) == 0) {
            this.setIsAlbino(true);
        }
        return result;
    }

    public static boolean ratCanSpawn(EntityType<? extends Animal> entityType, LevelAccessor levelAccessor, MobSpawnType spawnType, BlockPos blockPos, RandomSource random) {
        return spawnType == MobSpawnType.SPAWNER
                || levelAccessor.getBiome(blockPos).is(Tags.Biomes.IS_SWAMP) && random.nextBoolean()
                || levelAccessor.getBiome(blockPos).is(BiomeTags.IS_FOREST) && random.nextBoolean()
                || !levelAccessor.canSeeSky(blockPos) && blockPos.getY() > 0
                && checkMobSpawnRules(entityType, levelAccessor, spawnType, blockPos, random)
                && levelAccessor.getRawBrightness(blockPos, 0) < 9;
    }

    @Override
    public int getLevel1MaxHealth() {
        return 10;
    }

    @Override
    public int getLevel1AttackDamage() {
        return 2;
    }

    @Override
    public int getLevel10MaxHealth() {
        return 100;
    }

    @Override
    public int getLevel10AttackDamage() {
        return 16;
    }

    @Override
    public float headSizeX() {
        return 4;
    }

    @Override
    public float headSizeY() {
        return 3;
    }

    @Override
    public float headSizeZ() {
        return 3;
    }

    @Override
    public boolean isTameItem(ItemStack itemStack) {
        return itemStack.is(Tags.Items.CROPS)
                || itemStack.is(Tags.Items.SEEDS)
                || itemStack.is(PPTags.Items.FRUITS)
                || super.isTameItem(itemStack);
    }

    @Override
    public HealOverride healOverride(ItemStack itemStack) {
        if (itemStack.is(Tags.Items.CROPS))
            return HealOverride.defineNutrition(3);
        if (itemStack.is(Tags.Items.SEEDS))
            return HealOverride.defineNutrition(2);
        return super.healOverride(itemStack);
    }

    private static final Map<Item, Boolean> BASE_COMMON_CACHE = new ConcurrentHashMap<>();

    public static boolean mobCanBeRobbed(LivingEntity entity) {
        ItemStack stack = entity.getMainHandItem();
        if (stack.isEmpty()) return false;
        if (entity.getType().is(Tags.EntityTypes.BOSSES)) return false;

        Rarity stackRarity = stack.getRarity();
        if (stackRarity == Rarity.COMMON) return true;
        if (stackRarity != Rarity.RARE) return false;

        return isBaseCommon(stack.getItem());
    }

    private static boolean isBaseCommon(Item item) {
        return BASE_COMMON_CACHE.computeIfAbsent(item,
                it -> it.getRarity(ItemStack.EMPTY) == Rarity.COMMON
        );
    }

    @Override
    public boolean shouldDefendOwner(@NotNull LivingEntity target) {
        if (super.shouldDefendOwner(target))
            return true;
        return this.getMainHandItem().isEmpty() && mobCanBeRobbed(target);
    }

    @Override
    public boolean doHurtTarget(@NotNull Entity entity) {
        boolean result = super.doHurtTarget(entity);
        if (
                result
                        && entity instanceof LivingEntity living
                        && mobCanBeRobbed(living)
        ) {
            ItemStack enemyItem = living.getMainHandItem();
            if (enemyItem.isDamageableItem()) {
                float randomFloat = this.getRandom().nextFloat();
                enemyItem.setDamageValue(Mth.lerpInt(
                        Math.min(1, randomFloat + (this.petLevel() - 1) / 9f),
                        enemyItem.getMaxDamage(),
                        enemyItem.getDamageValue()
                ));
            }
            this.setItemSlot(EquipmentSlot.MAINHAND, enemyItem);
            living.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);

            if (this.getOwner() instanceof ServerPlayer player)
                PPAdvancementTriggers.USED_PET_ABILITY.trigger(player, this, 0);
        }
        return result;
    }

    @Override
    public boolean canBeCookingIngredient(ItemStack itemStack) {
        return itemStack.is(Tags.Items.CROPS) && !itemStack.is(PPTags.Items.RAT_COOK_EXCEPTIONS);
    }

    @Override
    public @NotNull InteractionResult mobInteract(Player player, @NotNull InteractionHand hand) {
        InteractionResult cookInteract = this.cookInteract(player, hand);
        if (cookInteract != InteractionResult.PASS)
            return cookInteract;
        return super.mobInteract(player, hand);
    }

    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob partner) {
        Rat baby = PPEntities.RAT.get().create(level);

        if (baby != null) {
            if (this.isTame()) {
                baby.setOwnerUUID(this.getOwnerUUID());
                baby.setTame(true);
            }

            if (partner instanceof Rat ratPartner) {
                if (this.random.nextBoolean()) {
                    baby.setVariant(this.getVariant());
                    baby.setPatternColor(ratPartner.getPatternColor());
                } else {
                    baby.setVariant(ratPartner.getVariant());
                    baby.setPatternColor(this.getPatternColor());
                }

                if (this.random.nextBoolean()) {
                    baby.setPatternType(this.getPatternType());
                } else {
                    baby.setPatternType(ratPartner.getPatternType());
                }

                if (this.random.nextBoolean()) {
                    baby.setIsAlbino(this.isAlbino());
                } else {
                    baby.setIsAlbino(ratPartner.isAlbino());
                }

            }
        }

        return baby;
    }

    @Override
    protected @Nullable SoundEvent getAmbientSound() {
        return SoundEvents.FOX_AMBIENT;
    }

    @Override
    protected @Nullable SoundEvent getHurtSound(@NotNull DamageSource damageSource) {
        return SoundEvents.FOX_HURT;
    }

    @Override
    protected @Nullable SoundEvent getDeathSound() {
        return SoundEvents.FOX_DEATH;
    }

    @Override
    public float getVoicePitch() {
        return super.getVoicePitch() * 1.9f;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(5, new DropHeldItemToOwnerGoal(this, 1.5, false));
        this.goalSelector.addGoal(15, new CookGoal(this));
    }

    @Override
    protected double createMeleeAttackSpeedMultiplier() {
        return 1.5;
    }

    @Override
    public boolean shouldDropHeldItemToOwner() {
        return !this.getMainHandItem().is(Items.BOWL);
    }

    @Override
    protected void dropEquipment() {
        super.dropEquipment();
        ItemStack itemstack = this.getItemBySlot(EquipmentSlot.MAINHAND);
        if (!itemstack.isEmpty()) {
            this.spawnAtLocation(itemstack);
            this.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
        }
        PPUtil.dump(this.getCookingIngredients(), this);
    }

    public float lastWalkTime = 0;
    public float lastRunTime = 0;

    @Override
    public void tick() {
        super.tick();
        this.tickCooking();
    }
}
