package billeyzambie.practicalpets.entity.fish;

import billeyzambie.practicalpets.client.model.entity.fish.PiranhaModel;
import billeyzambie.practicalpets.entity.fish.base.BreedableFish;
import billeyzambie.practicalpets.entity.fish.base.PracticalFish;
import billeyzambie.practicalpets.misc.PPItems;
import billeyzambie.practicalpets.util.PPUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Position;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BiomeTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

public class Piranha extends PracticalFish {
    public Piranha(EntityType<? extends Piranha> type, Level level) {
        super(type, level);
    }

    private static final HashMap<Integer, Integer> VARIANT_SPAWN_WEIGHTS = new HashMap<>() {{
        put(0, 1);
        put(1, 1);
    }};

    @Override
    public HashMap<Integer, Integer> variantSpawnWeights() {
        return VARIANT_SPAWN_WEIGHTS;
    }

    @Override
    public @NotNull ItemStack getBucketItemStack() {
        return new ItemStack(PPItems.PIRANHA_BUCKET.get());
    }

    @Override
    public float headSizeX() {
        return 2;
    }

    @Override
    public float headSizeY() {
        return 2;
    }

    @Override
    public float headSizeZ() {
        return 2;
    }

    @Override
    public float getMinSwimSwingDelta() {
        return PiranhaModel.MIN_SWIM_SWING_DELTA;
    }

    @Override
    public float getMinSwimSwingAmount() {
        return PiranhaModel.MIN_SWIM_SWING_AMOUNT;
    }

    private static final EntityDataAccessor<Integer> BELLY_COLOR = SynchedEntityData.defineId(Piranha.class, EntityDataSerializers.INT);

    public static final int DEFAULT_BELLY_COLOR = 8268561;

    public int getBellyColor() {
        return this.entityData.get(BELLY_COLOR);
    }

    public void setBellyColor(int color) {
        this.entityData.set(BELLY_COLOR, color);
    }

    @Override
    protected float getDeathSoundPitch() {
        return 0.7f;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(BELLY_COLOR, DEFAULT_BELLY_COLOR);
    }

    @Override
    protected void loadBucketAndWorldSharedData(@NotNull CompoundTag tag) {
        super.loadBucketAndWorldSharedData(tag);
        if (tag.contains("BellyColor"))
            this.setBellyColor(tag.getInt("BellyColor"));
        if (tag.contains("CompactVariant")) {
            int compactVariant = tag.getInt("CompactVariant");
            int bellyColor = compactVariant & 0xffffff;
            int variant = compactVariant >> 24;
            this.setBellyColor(bellyColor);
            this.setVariant(variant);
        }
    }

    @Override
    protected void saveBucketAndWorldSharedData(CompoundTag tag) {
        super.saveBucketAndWorldSharedData(tag);
        tag.putInt("BellyColor", getBellyColor());
    }

    @Override
    protected void addPiranhaLauncherData(CompoundTag tag) {
        int bellyColor = this.getBellyColor();
        int variant = this.getVariant();
        if (bellyColor != DEFAULT_BELLY_COLOR) {
            bellyColor &= 0xffffff;
            tag.putInt("CompactVariant", bellyColor | variant << 24);
        }
        else if (variant != 0) {
            tag.putInt("Variant", variant);
        }
    }

    @Override
    public @Nullable SpawnGroupData finalizeSpawn(ServerLevelAccessor p_27528_, DifficultyInstance p_27529_, MobSpawnType p_27530_, @Nullable SpawnGroupData p_27531_, @Nullable CompoundTag p_27532_) {
        if (this.getRandom().nextInt(50) == 0){
            this.setBellyColor(Mth.hsvToRgb(
                    this.getRandom().nextFloat(),
                    0.86f,
                    0.49f
            ));
        }

        return super.finalizeSpawn(p_27528_, p_27529_, p_27530_, p_27531_, p_27532_);
    }

    @Override
    protected boolean shouldRegisterAlertOthers() {
        return true;
    }

    private int getNearbyPiranhaCount() {
        return this.level().getEntitiesOfClass(
                Piranha.class,
                this.getBoundingBox().inflate(5, 5, 5)
        ).size();
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.targetSelector.addGoal(9, new NearestAttackableTargetGoal<>(
                this, LivingEntity.class, 15 * 20, true, true,
                this::canRandomlyAttack
        ));
    }

    private boolean canRandomlyAttack(LivingEntity target) {
        return !(target instanceof Piranha) &&
                (this.isTame()
                    ? target.getType().getCategory() == MobCategory.MONSTER
                        || (target instanceof Player && getServer() != null && getServer().isPvpAllowed())
                    : target.getType().getCategory() != MobCategory.CREATURE
                ) && this.hasFollowers() && this.getNearbyPiranhaCount() * 5 >= target.getHealth();
    }

    public static boolean piranhaCanSpawn(
            EntityType<? extends Piranha> type,
            LevelAccessor level,
            MobSpawnType spawnType,
            BlockPos pos,
            RandomSource random
    ) {
        boolean waterAnimalCanSpawn = checkSurfaceWaterAnimalSpawnRules(type, level, spawnType, pos, random);
        boolean shouldPiranhaSpawn = level.getBiome(pos).is(BiomeTags.IS_JUNGLE) || random.nextBoolean();
        return waterAnimalCanSpawn && shouldPiranhaSpawn;
    }

    @Override
    public BreedableFish getBreedOffspring(@NotNull ServerLevel level, @NotNull BreedableFish partner) {
        Piranha baby = (Piranha) this.getType().create(level);

        if (baby != null) {
            if (this.isTame()) {
                baby.setOwnerUUID(this.getOwnerUUID());
                baby.setTame(true);
            }

            if (partner instanceof Piranha piranha) {
                if (this.random.nextBoolean())
                    baby.setVariant(this.getVariant());
                else
                    baby.setVariant(piranha.getVariant());

                int bellyColor = this.getBellyColor();
                int otherBellyColor = piranha.getBellyColor();

                float bellyColorR = PPUtil.getColorRed(bellyColor);
                float bellyColorG = PPUtil.getColorGreen(bellyColor);
                float bellyColorB = PPUtil.getColorBlue(bellyColor);

                float otherBellyColorR = PPUtil.getColorRed(otherBellyColor);
                float otherBellyColorG = PPUtil.getColorGreen(otherBellyColor);
                float otherBellyColorB = PPUtil.getColorBlue(otherBellyColor);

                baby.setBellyColor(Mth.color(
                        PPUtil.clamp01((bellyColorR + otherBellyColorR) / 2f + (float)random.nextGaussian() * 0.1f),
                        PPUtil.clamp01((bellyColorG + otherBellyColorG) / 2f + (float)random.nextGaussian() * 0.1f),
                        PPUtil.clamp01((bellyColorB + otherBellyColorB) / 2f + (float)random.nextGaussian() * 0.1f)
                ));

            }
        }

        return baby;
    }

    //@Override
    //public void push(Entity p_21294_) {
    //    if (!this.isLaunched) {
    //        super.push(p_21294_);
    //    }
    //}

    @Override
    public boolean isWithinRestriction(BlockPos blockPos) {
        if (this.isLaunched) {
            return blockPos.distSqr(this.blockPosition()) <= 25;
        }
        return super.isWithinRestriction(blockPos);
    }

    @Override
    protected void customServerAiStep() {
        super.customServerAiStep();
        if (!this.isLaunched)
            return;
        if (this.isInWater()) {
            this.isLaunched = false;
            return;
        }
        if (this.tickCount % 2 == 0)
            return;
        LivingEntity target = this.getTarget();
        if (target == null || !isWithinRestriction(target.blockPosition()))
            return;
        float width = this.getBbWidth();
        float height = this.getBbHeight();
        AABB targetBoundingBox = target.getBoundingBox();
        double x = Mth.lerp(
                this.random.nextDouble(),
                targetBoundingBox.minX + width / 2f,
                targetBoundingBox.maxX - width / 2f
        );
        double y = Mth.lerp(
                this.random.nextDouble(),
                targetBoundingBox.minY,
                targetBoundingBox.maxY - height
        );
        double z = Mth.lerp(
                this.random.nextDouble(),
                targetBoundingBox.minZ + width / 2f,
                targetBoundingBox.maxZ - width / 2f
        );
        this.resetFallDistance();
        this.setDeltaMovement(Vec3.ZERO);
        this.setPos(x, y, z);
        this.doHurtTarget(target);
    }
}