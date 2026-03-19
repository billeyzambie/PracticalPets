package billeyzambie.practicalpets.goal;

import billeyzambie.practicalpets.entity.base.practicalpet.PetEquipmentWearer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;

public class PetEquipmentWearerMeleeAttackGoal extends MeleeAttackGoal {
    PetEquipmentWearer wearer;

    public PetEquipmentWearerMeleeAttackGoal(PetEquipmentWearer wearer, double speedMultiplier, boolean followingTargetEvenIfNotSeen) {
        super((PathfinderMob) wearer, speedMultiplier, followingTargetEvenIfNotSeen);
        this.wearer = wearer;
    }

    @Override
    public boolean canUse() {
        return !wearer.canPerformCosmeticRangedAttack() && super.canUse();
    }

    @Override
    public boolean canContinueToUse() {
        return !wearer.canPerformCosmeticRangedAttack() && super.canContinueToUse();
    }

    @Override
    protected double getAttackReachSqr(LivingEntity p_25556_) {
        return super.getAttackReachSqr(p_25556_) * wearer.getPetReachMultiplier() * wearer.getPetReachMultiplier();
    }
}
