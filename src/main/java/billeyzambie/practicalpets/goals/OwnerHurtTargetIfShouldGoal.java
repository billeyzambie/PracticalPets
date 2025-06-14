package billeyzambie.practicalpets.goals;

import billeyzambie.practicalpets.entity.LandPracticalPet;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;

public class OwnerHurtTargetIfShouldGoal extends OwnerHurtTargetGoal {
    public OwnerHurtTargetIfShouldGoal(LandPracticalPet pet) {
        super(pet);
    }

    @Override
    public boolean canUse() {
        return ((LandPracticalPet)mob).shouldDefendOwner() && super.canUse();
    }
}
