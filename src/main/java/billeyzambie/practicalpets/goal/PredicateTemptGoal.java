package billeyzambie.practicalpets.goal;

import billeyzambie.practicalpets.entity.PracticalPet;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.function.BiPredicate;

/**
 * This is a copy of {@link net.minecraft.world.entity.ai.goal.TemptGoal} that accepts a predicate
 * instead of an {@link net.minecraft.world.item.crafting.Ingredient}
 */
public class PredicateTemptGoal extends Goal {
    private static final TargetingConditions TEMP_TARGETING = TargetingConditions.forNonCombat().range(10.0D).ignoreLineOfSight();
    private final TargetingConditions targetingConditions;
    protected final PracticalPet pet;
    private final double speedModifier;
    private double px;
    private double py;
    private double pz;
    private double pRotX;
    private double pRotY;
    @Nullable
    protected Player player;
    private int calmDown;
    private boolean isRunning;
    private final BiPredicate<PracticalPet, ItemStack> predicate;
    private final boolean canScare;
    /**
     * This is a copy of {@link net.minecraft.world.entity.ai.goal.TemptGoal} that takes a predicate
     * instead of an {@link net.minecraft.world.item.crafting.Ingredient}
     */
    public PredicateTemptGoal(PracticalPet p_25939_, double p_25940_, BiPredicate<PracticalPet, ItemStack> predicate, boolean p_25942_) {
        this.pet = p_25939_;
        this.speedModifier = p_25940_;
        this.predicate = predicate;
        this.canScare = p_25942_;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        this.targetingConditions = TEMP_TARGETING.copy().selector(this::shouldFollow);
    }

    public boolean canUse() {
        if (this.calmDown > 0) {
            --this.calmDown;
            return false;
        } else {
            this.player = this.pet.level().getNearestPlayer(this.targetingConditions, this.pet);
            return this.player != null;
        }
    }

    private boolean shouldFollow(LivingEntity entity) {
            return this.predicate.test(pet, entity.getMainHandItem()) || this.predicate.test(pet, entity.getOffhandItem());
    }

    public boolean canContinueToUse() {
        if (this.canScare()) {
            if (this.pet.distanceToSqr(this.player) < 36.0D) {
                if (this.player.distanceToSqr(this.px, this.py, this.pz) > 0.010000000000000002D) {
                    return false;
                }

                if (Math.abs((double)this.player.getXRot() - this.pRotX) > 5.0D || Math.abs((double)this.player.getYRot() - this.pRotY) > 5.0D) {
                    return false;
                }
            } else {
                this.px = this.player.getX();
                this.py = this.player.getY();
                this.pz = this.player.getZ();
            }

            this.pRotX = (double)this.player.getXRot();
            this.pRotY = (double)this.player.getYRot();
        }

        return this.canUse();
    }

    protected boolean canScare() {
        return this.canScare;
    }

    public void start() {
        this.px = this.player.getX();
        this.py = this.player.getY();
        this.pz = this.player.getZ();
        this.isRunning = true;
    }

    public void stop() {
        this.player = null;
        this.pet.getNavigation().stop();
        this.calmDown = reducedTickDelay(100);
        this.isRunning = false;
    }

    public void tick() {
        this.pet.getLookControl().setLookAt(this.player, (float)(this.pet.getMaxHeadYRot() + 20), (float)this.pet.getMaxHeadXRot());
        if (this.pet.distanceToSqr(this.player) < 6.25D) {
            this.pet.getNavigation().stop();
        } else {
            this.pet.getNavigation().moveTo(this.player, this.speedModifier);
        }

    }

    public boolean isRunning() {
        return this.isRunning;
    }
}
