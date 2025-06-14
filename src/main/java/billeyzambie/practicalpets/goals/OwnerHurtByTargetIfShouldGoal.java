package billeyzambie.practicalpets.goals;

import billeyzambie.practicalpets.entity.LandPracticalPet;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;

public class OwnerHurtByTargetIfShouldGoal extends OwnerHurtByTargetGoal {
    public OwnerHurtByTargetIfShouldGoal(LandPracticalPet pet) {
        super(pet);
    }

    @Override
    public boolean canUse() {
        return ((LandPracticalPet)mob).shouldDefendOwner() && super.canUse();
    }
}
