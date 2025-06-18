package billeyzambie.practicalpets.goal;

import billeyzambie.practicalpets.entity.PracticalPet;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;

public class OwnerHurtByTargetIfShouldGoal extends OwnerHurtByTargetGoal {
    public OwnerHurtByTargetIfShouldGoal(PracticalPet pet) {
        super(pet);
    }

    @Override
    public boolean canUse() {
        return ((PracticalPet)mob).shouldDefendOwner() && super.canUse();
    }
}
