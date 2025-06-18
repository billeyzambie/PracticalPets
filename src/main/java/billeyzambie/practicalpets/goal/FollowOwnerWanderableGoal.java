package billeyzambie.practicalpets.goal;

import billeyzambie.practicalpets.entity.PracticalPet;
import net.minecraft.world.entity.ai.goal.FollowOwnerGoal;

public class FollowOwnerWanderableGoal extends FollowOwnerGoal {
    private final PracticalPet pet;
    public FollowOwnerWanderableGoal(PracticalPet p_25294_, double p_25295_, float p_25296_, float p_25297_, boolean p_25298_) {
        super(p_25294_, p_25295_, p_25296_, p_25297_, p_25298_);
        this.pet = p_25294_;
    }

    @Override
    public boolean canUse() {
        return this.pet.shouldFollowOwner() && super.canUse();
    }
}
