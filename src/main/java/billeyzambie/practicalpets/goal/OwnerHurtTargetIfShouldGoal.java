package billeyzambie.practicalpets.goal;

import billeyzambie.practicalpets.entity.PracticalPet;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;

public class OwnerHurtTargetIfShouldGoal extends OwnerHurtTargetGoal {
    public OwnerHurtTargetIfShouldGoal(PracticalPet pet) {
        super(pet);
    }

    @Override
    public boolean canUse() {
        return ((PracticalPet)mob).shouldDefendOwner() && super.canUse();
    }
    @Override
    public boolean canContinueToUse() {
        return ((PracticalPet)mob).shouldDefendOwner() && super.canContinueToUse();
    }
}
