package billeyzambie.practicalpets.entity.dinosaur;

import billeyzambie.practicalpets.misc.PPEntities;
import billeyzambie.practicalpets.misc.PPItems;
import billeyzambie.practicalpets.misc.PPSounds;
import billeyzambie.practicalpets.goal.DuckFollowParentGoal;
import billeyzambie.practicalpets.network.DuckBiteFloorAnimPacket;
import billeyzambie.practicalpets.network.ModNetworking;
import billeyzambie.practicalpets.misc.PPTags;
import billeyzambie.practicalpets.util.PPUtil;
import billeyzambie.practicalpets.util.WeightedList;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.Tags;
import net.minecraftforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;

public class Duck extends AbstractDuck {
    public final float floatWaveRandomOffset;

    //copied from vanilla chicken
    public float flap;
    public float flapSpeed;
    public float oFlapSpeed;
    public float oFlap;
    public float flapping = 1.0F;

    public static double MOVEMENT_SPEED = 0.25;

    public Duck(EntityType<? extends TamableAnimal> entityType, Level level) {
        super(entityType, level);
        this.setPathfindingMalus(BlockPathTypes.WATER, 0F);
        this.floatWaveRandomOffset = this.getRandom().nextFloat() * Mth.TWO_PI;
    }

    private static final HashMap<Integer, Integer> VARIANT_SPAWN_WEIGHTS = new HashMap<>() {{
        put(0, 72);
        put(1, 72);
        /* 2 is the secret golden duck that doesn't spawn naturally
        but will be spawned by breeding a duck with a penguin when penguins are added */
        put(3, 3);
        put(4, 35);
        put(5, 35);
        put(6, 35);
        put(7, 35);
    }};

    @Override
    public HashMap<Integer, Integer> variantSpawnWeights() {
        return VARIANT_SPAWN_WEIGHTS;
    }

    public static boolean duckCanSpawn(
            EntityType<Duck> type,
            LevelAccessor level,
            MobSpawnType reason,
            BlockPos pos,
            RandomSource rng
    ) {
        if (!Animal.checkAnimalSpawnRules(type, level, reason, pos, rng)) return false;

        final int R = 4;
        final BlockPos ground = pos.below();
        final BlockPos.MutableBlockPos p = new BlockPos.MutableBlockPos();

        for (int dz = -R; dz <= R; dz++) {
            for (int dx = -R; dx <= R; dx++) {
                p.set(ground.getX() + dx, ground.getY(), ground.getZ() + dz);
                if (!level.hasChunkAt(p)) continue;

                if (level.getFluidState(p).is(FluidTags.WATER)) {
                    BlockPos airPos = p.above();
                    if (!level.hasChunkAt(airPos)) continue;
                    if (level.getBlockState(airPos).isAir()
                            && level.canSeeSkyFromBelowWater(airPos)) {
                        return true;
                    }
                }

                p.move(0, 1, 0);
                if (level.hasChunkAt(p) && level.getFluidState(p).is(FluidTags.WATER)) {
                    BlockPos airPos = p.above();
                    if (level.hasChunkAt(airPos)
                            && level.getBlockState(airPos).isAir()
                            && level.canSeeSkyFromBelowWater(airPos)) {
                        return true;
                    }
                }
                p.move(0, -1, 0);
            }
        }
        return false;
    }


    @Override
    public boolean isTameItem(ItemStack itemStack) {
        return itemStack.is(Tags.Items.SEEDS)
                || itemStack.is(Tags.Items.CROPS_CARROT)
                || itemStack.is(Items.MELON_SLICE)
                || itemStack.is(Items.GLISTERING_MELON_SLICE)
                || super.isTameItem(itemStack);
    }

    @Override
    public HealOverride healOverride(ItemStack itemStack) {
        //Seeds and glistering melon aren't edible so a healOverride must be defined
        if (itemStack.is(Tags.Items.SEEDS))
            return HealOverride.defineNutrition(2);
        if (itemStack.is(Items.GLISTERING_MELON_SLICE))
            return HealOverride.defineNutrition(6);
        //Bread is bad for ducks
        if (itemStack.is(Items.BREAD))
            return HealOverride.override(1);
        return super.healOverride(itemStack);
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
    protected SoundEvent getAmbientSound() {
        return PPSounds.DUCK_AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource damageSource) {
        return PPSounds.DUCK_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return PPSounds.DUCK_DEATH.get();
    }

    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob partner) {
        Duck baby = PPEntities.DUCK.get().create(level);

        if (baby != null) {
            if (this.isTame()) {
                baby.setOwnerUUID(this.getOwnerUUID());
                baby.setTame(true);
            }

            if (partner instanceof Duck duckPartner) {
                if (this.random.nextBoolean())
                    baby.setVariant(this.getVariant());
                else
                    baby.setVariant(duckPartner.getVariant());
            }
        }

        return baby;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.getNavigation().setCanFloat(true);
    }

    @Override
    protected Goal createStrollGoal() {
        return new RandomStrollGoal(this, 1d);
    }

    @Override
    protected @Nullable Goal createFollowParentGoal() {
        return new DuckFollowParentGoal(this);
    }

    @Override
    protected @NotNull PathNavigation createNavigation(@NotNull Level level) {
        return new DuckPathNavigation(this, level);
    }

    @Override
    public boolean canStandOnFluid(FluidState fluidState) {
        return fluidState.is(FluidTags.WATER);
    }

    @Override
    protected boolean shouldRegisterFloatGoal() {
        return false;
    }


    @Nullable
    public Duck followingDuck = null;

    public boolean isBeingFollowedByDuckling() {
        return this.level().getEntitiesOfClass(this.getClass(), this.getBoundingBox().inflate(8.0D, 4.0D, 8.0D)).stream().anyMatch(duck -> duck.followingDuck == this);
    }

    private static final VoxelShape BABY_LIQUID_SHAPE = LiquidBlock.STABLE_SHAPE.move(0, 0.25f, 0);

    @Override
    public void aiStep() {
        super.aiStep();

        if (!this.level().isClientSide() && this.isTame()) {
            this.tickBiteFloor();
        }

        //Copied from vanilla chicken
        this.oFlap = this.flap;
        this.oFlapSpeed = this.flapSpeed;
        this.flapSpeed += (this.duckIsFlapping() ? 4.0F : -1.0F) * 0.3F;
        this.flapSpeed = Mth.clamp(this.flapSpeed, 0.0F, 1.0F);
        if (this.duckIsFlapping() && this.flapping < 1.0F) {
            this.flapping = 1.0F;
        }
        this.flapping *= 0.9F;
        this.flap += this.flapping * 2.0F;

        if (this.isInWater()) {
            CollisionContext collisioncontext = CollisionContext.of(this);
            if (
                    collisioncontext.isAbove(this.isBaby() ? BABY_LIQUID_SHAPE : LiquidBlock.STABLE_SHAPE, this.blockPosition(), true)
                            && !this.level().getFluidState(this.blockPosition().above()).is(FluidTags.WATER)
            ) {
                this.setOnGround(true);
            } else {
                this.setDeltaMovement(this.getDeltaMovement().scale(0.5D).add(0.0D, 0.05D, 0.0D));
            }
        }

        if (!this.isBaby())
            followingDuck = null;

    }

    public final AnimationState biteFloorAnimationState = new AnimationState();
    private boolean navigationWasDone = true;

    private void tickBiteFloor() {
        boolean navigationIsDone = this.getNavigation().isDone();
        if (
                navigationIsDone && !this.navigationWasDone
                        && this.getRandom().nextFloat() * (10 - (this.petLevel() - 1) * 2f / 9) < 1
        ) {

            BlockState blockState = this.level().getBlockState(this.blockPosition());

            if (this.isInWater() || blockState.is(PPTags.Blocks.GRASS)) {
                this.sendBiteFloorAnimation();
                if (!this.isInWater() || this.random.nextBoolean()) {
                    this.spawnFoundItem(this.isInWater() ? ItemCanBeFoundIn.WATER : ItemCanBeFoundIn.GRASS);
                }
            }

        }
        this.navigationWasDone = navigationIsDone;
    }

    private void spawnFoundItem(ItemCanBeFoundIn itemWasFoundIn) {
        WeightedList<FoundItemChoice> weightedList = new WeightedList<>();
        for (FoundItemChoice itemChoice : FoundItemChoice.LIST_OF) {
            if (
                    itemChoice.itemCanBeFoundIn == ItemCanBeFoundIn.BOTH
                            || itemChoice.itemCanBeFoundIn == itemWasFoundIn
            ) {
                float finalWeight = itemChoice.defaultWeight();
                if (itemChoice.isTreasure)
                    finalWeight += Math.max(0, (this.petLevel() - 5) / 3f);
                weightedList.add(itemChoice, finalWeight);
            }
        }
        weightedList.getRandomChoice().spawnAtEntity(this);
    }

    private void sendBiteFloorAnimation() {
        ModNetworking.CHANNEL.send(
                PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> this),
                new DuckBiteFloorAnimPacket(this.getId())
        );
    }

    @Override
    public @NotNull SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor level, @NotNull DifficultyInstance difficulty, @NotNull MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag tag) {
        return super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData, tag);
    }

    @Override
    protected float getStandingEyeHeight(@NotNull Pose pose, @NotNull EntityDimensions dimensions) {
        return dimensions.height * 12.5f / 13;
    }

    static class DuckPathNavigation extends GroundPathNavigation {
        DuckPathNavigation(Duck p_33969_, Level p_33970_) {
            super(p_33969_, p_33970_);
        }

        @Override
        protected boolean hasValidPathType(@NotNull BlockPathTypes p_33974_) {
            return p_33974_ == BlockPathTypes.WATER || super.hasValidPathType(p_33974_);
        }

        @Override
        public boolean isStableDestination(@NotNull BlockPos pos) {
            FluidState fluid = this.level.getFluidState(pos);
            return fluid.is(FluidTags.WATER) || super.isStableDestination(pos);
        }
    }

    enum ItemCanBeFoundIn {GRASS, WATER, BOTH}

    record FoundItemChoice(
            @Nullable Item item,
            @Nullable ResourceLocation lootTable,
            ItemCanBeFoundIn itemCanBeFoundIn,
            boolean isTreasure,
            float defaultWeight
    ) {
        public void spawnAtEntity(LivingEntity entity) {
            if (item == null && lootTable == null) {
                throw new IllegalArgumentException("Duck.FoundItemChoice item and lootTable cannot both be null");
            }
            if (item != null) {
                if (lootTable != null)
                    throw new IllegalArgumentException("Duck.FoundItemChoice item and lootTable cannot both be defined");
                entity.spawnAtLocation(item);
            } else {
                PPUtil.dropLootTableAtEntity(entity, lootTable);
            }
        }

        public static final List<FoundItemChoice> LIST_OF = List.of(
                new FoundItemChoice(
                        null,
                        BuiltInLootTables.FISHING_JUNK,
                        ItemCanBeFoundIn.BOTH,
                        false,
                        9
                ),
                new FoundItemChoice(
                        null,
                        BuiltInLootTables.FISHING_JUNK,
                        ItemCanBeFoundIn.WATER,
                        false,
                        30
                ),
                new FoundItemChoice(
                        null,
                        BuiltInLootTables.FISHING,
                        ItemCanBeFoundIn.WATER,
                        true,
                        9
                ),
                new FoundItemChoice(
                        PPItems.RUBBER_DUCKY_PET_HAT.get(),
                        null,
                        ItemCanBeFoundIn.WATER,
                        true,
                        1
                ),
                new FoundItemChoice(
                        PPItems.DIAMOND_NUGGET.get(),
                        null,
                        ItemCanBeFoundIn.BOTH,
                        true,
                        1
                ),
                new FoundItemChoice(
                        PPItems.CHICKEN_NUGGET.get(),
                        null,
                        ItemCanBeFoundIn.BOTH,
                        false,
                        8
                ),
                new FoundItemChoice(
                        Items.IRON_NUGGET,
                        null,
                        ItemCanBeFoundIn.BOTH,
                        true,
                        1
                ),
                new FoundItemChoice(
                        Items.GOLD_NUGGET,
                        null,
                        ItemCanBeFoundIn.BOTH,
                        true,
                        1
                ),
                new FoundItemChoice(
                        Items.WHEAT_SEEDS,
                        null,
                        ItemCanBeFoundIn.BOTH,
                        true,
                        30
                ),
                new FoundItemChoice(
                        Items.CARROT,
                        null,
                        ItemCanBeFoundIn.BOTH,
                        true,
                        10
                ),
                new FoundItemChoice(
                        Items.MELON_SEEDS,
                        null,
                        ItemCanBeFoundIn.GRASS,
                        true,
                        10
                ),
                new FoundItemChoice(
                        Items.BEETROOT_SEEDS,
                        null,
                        ItemCanBeFoundIn.GRASS,
                        true,
                        10
                ),
                new FoundItemChoice(
                        Items.PUMPKIN_SEEDS,
                        null,
                        ItemCanBeFoundIn.GRASS,
                        true,
                        10
                )
        );

    }


}
