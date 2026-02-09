package billeyzambie.practicalpets.goal;

import billeyzambie.practicalpets.entity.StayStillGoalMob;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;

public class StayStillGoal extends Goal {

    private final Mob mob;
    private final StayStillGoalMob stayStillGoalMob;

    public StayStillGoal(Mob mob) {
        this.mob = mob;
        assert mob instanceof StayStillGoalMob : "Mob must implement StayStillGoalMob to use the StayStillGoal";
        this.stayStillGoalMob = (StayStillGoalMob) mob;
        this.setFlags(EnumSet.of(Flag.LOOK, Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        return stayStillGoalMob.shouldStayStill();
    }

    @Override
    public void start() {
        this.mob.getNavigation().stop();
    }
}
