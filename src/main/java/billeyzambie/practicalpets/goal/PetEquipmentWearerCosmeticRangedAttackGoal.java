package billeyzambie.practicalpets.goal;

import billeyzambie.practicalpets.entity.base.practicalpet.PetEquipmentWearer;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;

public class PetEquipmentWearerCosmeticRangedAttackGoal extends RangedAttackGoal {
    PetEquipmentWearer wearer;

    public PetEquipmentWearerCosmeticRangedAttackGoal(PetEquipmentWearer wearer, double p_25774_, int p_25775_, int p_25776_, float p_25777_) {
        super(wearer, p_25774_, p_25775_, p_25776_, p_25777_);
        this.wearer = wearer;
    }

    public PetEquipmentWearerCosmeticRangedAttackGoal(PetEquipmentWearer wearer, double speedMultiplier) {
        this(wearer, speedMultiplier, 20, 40, 20f);
    }

    @Override
    public boolean canUse() {
        return wearer.canPerformCosmeticRangedAttack() && super.canUse();
    }

    @Override
    public boolean canContinueToUse() {
        return wearer.canPerformCosmeticRangedAttack() && super.canContinueToUse();
    }
}
