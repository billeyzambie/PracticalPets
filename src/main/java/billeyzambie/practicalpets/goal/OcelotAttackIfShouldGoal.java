package billeyzambie.practicalpets.goal;

import billeyzambie.practicalpets.entity.PracticalPet;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.OcelotAttackGoal;
import net.minecraft.world.entity.animal.Ocelot;

public class OcelotAttackIfShouldGoal extends OcelotAttackGoal {
    PracticalPet pet;

    public OcelotAttackIfShouldGoal(PathfinderMob p_25552_) {
        super(p_25552_);
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
