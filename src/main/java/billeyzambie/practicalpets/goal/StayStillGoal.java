package billeyzambie.practicalpets.goal;

import billeyzambie.practicalpets.entity.StayStillGoalMob;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;

public class StayStillGoal extends Goal {

    private final Mob mob;
    private final StayStillGoalMob StayStillGoalMob;

    public StayStillGoal(Mob mob) {
        this.mob = mob;
        assert mob instanceof StayStillGoalMob : "Pet must implement StayStillGoalMob to use the CookGoal";
        this.StayStillGoalMob = (StayStillGoalMob) mob;
        this.setFlags(EnumSet.of(Flag.LOOK, Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        return StayStillGoalMob.shouldStayStill();
    }

    @Override
    public void start() {
        this.mob.getNavigation().stop();
    }
}
