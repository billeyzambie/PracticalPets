package billeyzambie.practicalpets.goal;

import billeyzambie.practicalpets.entity.PracticalPet;
import net.minecraft.world.entity.ai.goal.PanicGoal;

public class PanicIfShouldGoal extends PanicGoal {
    public PanicIfShouldGoal(PracticalPet pathfinderMob, double speedMultiplier) {
        super(pathfinderMob, speedMultiplier);
    }

    @Override
    public boolean canUse() {
        if (mob.getTarget() == null && (mob.isOnFire() || mob.isFreezing()))
            return true;
        return ((PracticalPet) mob).shouldPanic() && super.canUse();
    }
}
