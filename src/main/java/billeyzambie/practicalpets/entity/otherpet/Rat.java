package billeyzambie.practicalpets.entity.otherpet;

import billeyzambie.practicalpets.entity.PracticalPet;
import billeyzambie.practicalpets.misc.PPEntities;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Rat extends PracticalPet {

    private static final EntityDataAccessor<Boolean> IS_ALBINO = SynchedEntityData.defineId(Rat.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> PATTERN_COLOR = SynchedEntityData.defineId(Rat.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> PATTERN_TYPE = SynchedEntityData.defineId(Rat.class, EntityDataSerializers.INT);
    public static final int PATTERN_TYPE_COUNT = 4;
    public static final int COLOR_TYPE_COUNT = 5;

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(PATTERN_COLOR, 0);
        this.entityData.define(PATTERN_TYPE, 0);
        this.entityData.define(IS_ALBINO, false);
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

    public boolean getIsAlbino() {
        return this.entityData.get(IS_ALBINO);
    }

    public void setIsAlbino(boolean value) {
        this.entityData.set(IS_ALBINO, value);
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setPatternColor(compoundTag.getInt("PatternColor"));
        this.setPatternType(compoundTag.getInt("PatternType"));
        this.setIsAlbino(compoundTag.getBoolean("IsAlbino"));
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("PatternColor", this.getPatternColor());
        compoundTag.putInt("PatternType", this.getPatternType());
        compoundTag.putBoolean("IsAlbino", this.getIsAlbino());
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
        return itemStack.is(Items.APPLE)
                || super.isTameItem(itemStack);
    }

    private static final Map<Item, Boolean> BASE_COMMON_CACHE = new ConcurrentHashMap<>();

    public static boolean itemCanBeRobbed(ItemStack stack) {
        if (stack.isEmpty()) return false;

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
        return itemCanBeRobbed(target.getMainHandItem());
    }

    @Override
    public boolean doHurtTarget(@NotNull Entity entity) {
        boolean result = super.doHurtTarget(entity);
        if (
                result
                        && entity instanceof LivingEntity living
                        && itemCanBeRobbed(living.getMainHandItem())
        ) {
            this.spawnAtLocation(living.getMainHandItem());
            living.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
        }
        return result;
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
                }
                else {
                    baby.setVariant(ratPartner.getVariant());
                    baby.setPatternColor(this.getPatternColor());
                }

                if (this.random.nextBoolean()) {
                    baby.setPatternType(this.getPatternType());
                }
                else {
                    baby.setPatternType(ratPartner.getPatternType());
                }

                if (this.random.nextBoolean()) {
                    baby.setIsAlbino(this.getIsAlbino());
                }
                else {
                    baby.setIsAlbino(ratPartner.getIsAlbino());
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
}
