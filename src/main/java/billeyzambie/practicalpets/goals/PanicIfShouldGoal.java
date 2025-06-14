package billeyzambie.practicalpets.goals;

import billeyzambie.practicalpets.entity.LandPracticalPet;
import net.minecraft.world.entity.ai.goal.PanicGoal;

public class PanicIfShouldGoal extends PanicGoal {
    public PanicIfShouldGoal(LandPracticalPet pathfinderMob, double speedMultiplier) {
        super(pathfinderMob, speedMultiplier);
    }

    @Override
    public boolean canUse() {
        if (mob.getTarget() == null && (mob.isOnFire() || mob.isFreezing()))
            return true;
        return ((LandPracticalPet) mob).shouldPanic() && super.canUse();
    }
}
