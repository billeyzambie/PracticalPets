package billeyzambie.practicalpets.goal;

import billeyzambie.practicalpets.entity.dinosaur.Pigeon;

public class PigeonFlyAroundGoal extends FlyAroundLikeCrazyGoal {
    Pigeon pigeon;
    public PigeonFlyAroundGoal(Pigeon p_25691_, double p_25692_) {
        super(p_25691_, p_25692_);
        this.pigeon = p_25691_;
    }

    @Override
    public boolean canUse() {
        return pigeon.isInMission() && super.canUse();
    }
}
