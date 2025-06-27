package billeyzambie.practicalpets.goal;

import billeyzambie.practicalpets.entity.dinosaur.Duck;
import net.minecraft.world.entity.ai.goal.Goal;

import javax.annotation.Nullable;
import java.util.List;

public class DuckFollowParentGoal extends Goal {
    private final Duck duck;
    private int timeToRecalcPath = 0;

    public DuckFollowParentGoal(Duck duck) {
        this.duck = duck;
    }

    @Override
    public boolean canUse() {
        if (duck.getAge() >= 0 || duck.shouldntFollowParent()) return false;

        List<Duck> nearby = duck.level().getEntitiesOfClass(Duck.class, duck.getBoundingBox().inflate(8.0D, 4.0D, 8.0D));
        Duck bestCandidate = null;
        double closestDist = Double.MAX_VALUE;

        for (Duck candidate : nearby) {
            if (candidate == duck) continue;


            if (!candidate.isBeingFollowedByDuckling() && followsAdult(candidate)) {
                double distSq = duck.distanceToSqr(candidate);
                if (distSq < closestDist && distSq >= 0.25D * candidate.getScale()) {
                    closestDist = distSq;
                    bestCandidate = candidate;
                }
            }

        }

        if (bestCandidate != null) {
            duck.followingDuck = bestCandidate;
            return true;
        }

        duck.followingDuck = null;
        return false;
    }

    @Override
    public boolean canContinueToUse() {
        if (duck.getAge() >= 0 || duck.followingDuck == null || !duck.followingDuck.isAlive()) {
            return false;
        }

        double distSq = duck.distanceToSqr(duck.followingDuck);
        return distSq >= 0.25D * duck.followingDuck.getScale() && distSq <= 256.0D;
    }

    @Override
    public void start() {
        timeToRecalcPath = 0;
    }

    @Override
    public void stop() {
        duck.followingDuck = null;
    }

    @Override
    public void tick() {
        if (--timeToRecalcPath <= 0 && duck.followingDuck != null) {
            timeToRecalcPath = this.adjustedTickDelay(10);

            double distSq = duck.distanceToSqr(duck.followingDuck);
            double speed = distSq > 4 ? 1.15 : Math.max(0.8, duck.followingDuck.getSpeed() / Duck.MOVEMENT_SPEED);

            duck.getNavigation().moveTo(duck.followingDuck, speed);
        }
    }

    private boolean followsAdult(@Nullable Duck candidate) {
        Duck current = candidate;
        while (current != null) {
            if (current.getAge() >= 0) return true;
            current = current.followingDuck;
        }
        return false;
    }
}