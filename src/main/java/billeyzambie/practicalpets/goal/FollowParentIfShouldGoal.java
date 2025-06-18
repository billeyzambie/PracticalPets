package billeyzambie.practicalpets.goal;

import billeyzambie.practicalpets.entity.PracticalPet;
import net.minecraft.world.entity.ai.goal.FollowParentGoal;

public class FollowParentIfShouldGoal extends FollowParentGoal {
    private final PracticalPet pet;

    public FollowParentIfShouldGoal(PracticalPet pet, double p_25320_) {
        super(pet, p_25320_);
        this.pet = pet;
    }

    @Override
    public boolean canUse() {
        return !pet.shouldntFollowParent() && super.canUse();
    }

    @Override
    public boolean canContinueToUse() {
        return !pet.shouldntFollowParent() && super.canContinueToUse();
    }
}
