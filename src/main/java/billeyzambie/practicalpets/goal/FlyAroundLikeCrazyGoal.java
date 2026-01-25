package billeyzambie.practicalpets.goal;

import billeyzambie.practicalpets.entity.PracticalPet;

public class FlyAroundLikeCrazyGoal extends FlyPanicGoal {
    public FlyAroundLikeCrazyGoal(PracticalPet p_25691_, double p_25692_) {
        super(p_25691_, p_25692_);
    }

    @Override
    public boolean canUse() {
        return this.findRandomPosition();
    }
}
