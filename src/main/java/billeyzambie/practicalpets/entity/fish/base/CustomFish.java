package billeyzambie.practicalpets.entity.fish.base;

import com.mojang.datafixers.DataFixUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

//leader and schoolSize were private in the vanilla AbstractSchoolingFish
public abstract class CustomFish extends AbstractFish {
    @Nullable
    protected CustomFish leader;
    protected int schoolSize = 1;

    public CustomFish(EntityType<? extends CustomFish> p_27523_, Level p_27524_) {
        super(p_27523_, p_27524_);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(5, new FollowFlockLeaderGoal(this));
    }

    @Override
    public int getMaxSpawnClusterSize() {
        return this.getMaxSchoolSize();
    }

    public int getMaxSchoolSize() {
        return super.getMaxSpawnClusterSize();
    }

    @Override
    protected boolean canRandomSwim() {
        return !this.isFollower();
    }

    public boolean isFollower() {
        return this.leader != null && this.leader.isAlive();
    }

    public CustomFish startFollowing(CustomFish p_27526_) {
        this.leader = p_27526_;
        p_27526_.addFollower();
        return p_27526_;
    }

    public void stopFollowing() {
        this.leader.removeFollower();
        this.leader = null;
    }

    private void addFollower() {
        ++this.schoolSize;
    }

    private void removeFollower() {
        --this.schoolSize;
    }

    public boolean canBeFollowed() {
        return this.hasFollowers() && this.schoolSize < this.getMaxSchoolSize();
    }

    @Override
    public void tick() {
        super.tick();
        if (this.hasFollowers() && this.level().random.nextInt(200) == 1) {
            List<? extends AbstractFish> list = this.level().getEntitiesOfClass(this.getClass(), this.getBoundingBox().inflate(8.0D, 8.0D, 8.0D));
            if (list.size() <= 1) {
                this.schoolSize = 1;
            }
        }

    }

    public boolean hasFollowers() {
        return this.schoolSize > 1;
    }

    public boolean inRangeOfLeader() {
        return this.distanceToSqr(this.leader) <= 121.0D;
    }

    public void pathToLeader() {
        if (this.isFollower()) {
            this.getNavigation().moveTo(this.leader, 1.0D);
        }

    }

    public void addFollowers(Stream<? extends CustomFish> p_27534_) {
        p_27534_.limit((long)(this.getMaxSchoolSize() - this.schoolSize)).filter((p_27538_) -> {
            return p_27538_ != this;
        }).forEach((p_27536_) -> {
            p_27536_.startFollowing(this);
        });
    }

    @Override
    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_27528_, DifficultyInstance p_27529_, MobSpawnType p_27530_, @Nullable SpawnGroupData p_27531_, @Nullable CompoundTag p_27532_) {
        super.finalizeSpawn(p_27528_, p_27529_, p_27530_, p_27531_, p_27532_);
        if (p_27531_ == null) {
            p_27531_ = new CustomFish.SchoolSpawnGroupData(this);
        } else {
            this.startFollowing(((CustomFish.SchoolSpawnGroupData)p_27531_).leader);
        }

        return p_27531_;
    }

    public static class SchoolSpawnGroupData implements SpawnGroupData {
        public final CustomFish leader;

        public SchoolSpawnGroupData(CustomFish p_27553_) {
            this.leader = p_27553_;
        }
    }

    protected static class FollowFlockLeaderGoal extends Goal {
        private static final int INTERVAL_TICKS = 200;
        private final CustomFish mob;
        private int timeToRecalcPath;
        private int nextStartTick;

        public FollowFlockLeaderGoal(CustomFish p_25249_) {
            this.mob = p_25249_;
            this.nextStartTick = this.nextStartTick(p_25249_);
        }

        protected int nextStartTick(CustomFish p_25252_) {
            return reducedTickDelay(200 + p_25252_.getRandom().nextInt(200) % 20);
        }

        @Override
        public boolean canUse() {
            if (this.mob.hasFollowers()) {
                return false;
            } else if (this.mob.isFollower()) {
                return true;
            } else if (this.nextStartTick > 0) {
                --this.nextStartTick;
                return false;
            } else {
                this.nextStartTick = this.nextStartTick(this.mob);
                Predicate<CustomFish> predicate = (p_25258_) -> {
                    return p_25258_.canBeFollowed() || !p_25258_.isFollower();
                };
                List<? extends CustomFish> list = this.mob.level().getEntitiesOfClass(this.mob.getClass(), this.mob.getBoundingBox().inflate(8.0D, 8.0D, 8.0D), predicate);
                CustomFish abstractschoolingfish = DataFixUtils.orElse(list.stream().filter(CustomFish::canBeFollowed).findAny(), this.mob);
                abstractschoolingfish.addFollowers(list.stream().filter((p_25255_) -> {
                    return !p_25255_.isFollower();
                }));
                return this.mob.isFollower();
            }
        }

        @Override
        public boolean canContinueToUse() {
            return this.mob.isFollower() && this.mob.inRangeOfLeader();
        }

        @Override
        public void start() {
            this.timeToRecalcPath = 0;
        }

        @Override
        public void stop() {
            this.mob.stopFollowing();
        }

        @Override
        public void tick() {
            if (--this.timeToRecalcPath <= 0) {
                this.timeToRecalcPath = this.adjustedTickDelay(10);
                this.mob.pathToLeader();
            }
        }
    }


    protected static class FishSwimGoal extends RandomSwimmingGoal {
        private final PracticalFish fish;

        public FishSwimGoal(PracticalFish fish) {
            super(fish, 1.0D, 40);
            this.fish = fish;
        }

        @Override
        public boolean canUse() {
            return this.fish.canRandomSwim() && super.canUse();
        }
    }

    protected static class CopyFlockLeaderTargetGoal extends TargetGoal {
        private final PracticalFish fish;

        public CopyFlockLeaderTargetGoal(PracticalFish fish) {
            super(fish, true);
            this.fish = fish;
            this.setFlags(EnumSet.of(Goal.Flag.TARGET));
        }

        @Override
        public boolean canUse() {
            return fish.isFollower() && fish.leader.getTarget() != null;
        }

        @Override
        public void start() {
            fish.setTarget(fish.leader.getTarget());
            super.start();
        }
    }

    //custom code start: (maybe?)

    protected void loadCustomData(CompoundTag tag) {
    }

    protected void saveCustomData(CompoundTag tag) {
    }

    @Override
    public final void readAdditionalSaveData(@NotNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.loadCustomData(tag);
    }

    @Override
    public final void addAdditionalSaveData(@NotNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        this.saveCustomData(tag);
    }

    @Override
    public final void loadFromBucketTag(@NotNull CompoundTag tag) {
        super.loadFromBucketTag(tag);
        this.loadCustomData(tag);
    }

    @Override
    public final void saveToBucketTag(@NotNull ItemStack stack) {
        super.saveToBucketTag(stack);
        this.saveCustomData(stack.getOrCreateTag());
    }
}
