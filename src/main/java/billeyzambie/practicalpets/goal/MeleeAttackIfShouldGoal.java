package billeyzambie.practicalpets.goal;

import billeyzambie.practicalpets.entity.PracticalPet;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;

public class MeleeAttackIfShouldGoal extends MeleeAttackGoal {
    PracticalPet pet;

    public MeleeAttackIfShouldGoal(PathfinderMob p_25552_, double p_25553_, boolean p_25554_) {
        super(p_25552_, p_25553_, p_25554_);
        this.pet = (PracticalPet) p_25552_;
    }

    @Override
    public boolean canUse() {
        return super.canUse() && !pet.canPerformRangedAttack();
    }

    @Override
    public boolean canContinueToUse() {
        return super.canContinueToUse() && !pet.canPerformRangedAttack();
    }
}
