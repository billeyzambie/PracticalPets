package billeyzambie.practicalpets.goal;

import billeyzambie.practicalpets.entity.CookingPet;
import billeyzambie.practicalpets.entity.PracticalPet;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;

public class CookGoal extends Goal {

    private final PracticalPet pet;
    private final CookingPet cookingPet;

    public CookGoal(PracticalPet pet) {
        this.pet = pet;
        assert pet instanceof CookingPet : "Pet must implement CookingPet to use the CookGoal";
        this.cookingPet = (CookingPet) pet;
        this.setFlags(EnumSet.of(Flag.LOOK, Goal.Flag.JUMP, Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        return cookingPet.isCooking() && pet.getTarget() == null;
    }

    @Override
    public void start() {
        this.pet.getNavigation().stop();
    }

    @Override
    public void stop() {
        if (cookingPet.isCookingFinished()) {
            cookingPet.cookingSuccess();
        }
        else {
            cookingPet.cookingInterrupted();
        }
    }
}
