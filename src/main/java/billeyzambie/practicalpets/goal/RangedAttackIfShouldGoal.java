package billeyzambie.practicalpets.goal;

import billeyzambie.practicalpets.entity.PracticalPet;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.monster.RangedAttackMob;

public class RangedAttackIfShouldGoal extends RangedAttackGoal {
    PracticalPet pet;

    public RangedAttackIfShouldGoal(RangedAttackMob p_25773_, double p_25774_, int p_25775_, int p_25776_, float p_25777_) {
        super(p_25773_, p_25774_, p_25775_, p_25776_, p_25777_);
        this.pet = (PracticalPet) p_25773_;
    }

    @Override
    public boolean canUse() {
        return super.canUse() && pet.canPerformRangedAttack();
    }

    @Override
    public boolean canContinueToUse() {
        return super.canContinueToUse() && pet.canPerformRangedAttack();
    }
}
