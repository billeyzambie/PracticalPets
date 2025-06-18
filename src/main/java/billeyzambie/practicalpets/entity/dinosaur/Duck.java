package billeyzambie.practicalpets.entity.dinosaur;

import billeyzambie.practicalpets.ModEntities;
import billeyzambie.practicalpets.ModSounds;
import billeyzambie.practicalpets.goal.DuckFollowParentGoal;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraftforge.common.Tags;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

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

    @Override
    public int getLevel1MaxHealth() {
        return 6;
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
        return 20;
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
        return ModSounds.DUCK_AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource damageSource) {
        return ModSounds.DUCK_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.DUCK_DEATH.get();
    }

    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob partner) {
        Duck baby = ModEntities.DUCK.get().create(level);

        if (baby != null) {
            if (this.isTame()) {
                baby.setOwnerUUID(this.getOwnerUUID());
                baby.setTame(true);
            }

            if (partner instanceof Duck duckPartner) {
                if (this.random.nextBoolean())
                    baby.setVariant(this.variant());
                else
                    baby.setVariant(duckPartner.variant());
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
    protected Goal getStrollGoal() {
        return new RandomStrollGoal(this, 1d);
    }

    @Override
    protected @Nullable Goal getFollowParentGoal() {
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

    @Override
    public void aiStep() {
        super.aiStep();

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
            if (collisioncontext.isAbove(LiquidBlock.STABLE_SHAPE, this.blockPosition(), true) && !this.level().getFluidState(this.blockPosition().above()).is(FluidTags.WATER)) {
                this.setOnGround(true);
            } else {
                this.setDeltaMovement(this.getDeltaMovement().scale(0.5D).add(0.0D, 0.05D, 0.0D));
            }
        }

        if (!this.isBaby())
            followingDuck = null;

    }

    @Override
    public @NotNull SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor level, @NotNull DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag tag) {
        return super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData, tag);
    }

    public static boolean canSpawn(EntityType<Duck> ignoredDuckEntityType, ServerLevelAccessor serverLevelAccessor, MobSpawnType ignoredMobSpawnType, BlockPos blockPos, RandomSource ignoredRandomSource) {
        return isBrightEnoughToSpawn(serverLevelAccessor, blockPos);
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
}
