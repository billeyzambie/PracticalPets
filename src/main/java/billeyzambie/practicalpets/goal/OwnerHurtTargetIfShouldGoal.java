package billeyzambie.practicalpets.goal;

import billeyzambie.practicalpets.entity.PracticalPet;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import org.jetbrains.annotations.Nullable;

public class OwnerHurtTargetIfShouldGoal extends OwnerHurtTargetGoal {
    PracticalPet pet;
    public OwnerHurtTargetIfShouldGoal(PracticalPet pet) {
        super(pet);
        this.pet = pet;
    }

    @Nullable
    public LivingEntity getTarget() {
        var owner = pet.getOwner();
        if (owner == null)
            return null;
        return owner.getLastHurtMob();
    }

    @Override
    public boolean canUse() {
        var target = getTarget();
        return target != null && ((PracticalPet)mob).shouldDefendOwner(target) && super.canUse();
    }
    @Override
    public boolean canContinueToUse() {
        var target = getTarget();
        return target != null && ((PracticalPet)mob).shouldDefendOwner(target) && super.canContinueToUse();
    }
}
