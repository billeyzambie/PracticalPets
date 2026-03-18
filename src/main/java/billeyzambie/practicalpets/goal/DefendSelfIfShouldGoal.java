package billeyzambie.practicalpets.goal;

import billeyzambie.practicalpets.entity.base.practicalpet.PetEquipmentWearer;
import billeyzambie.practicalpets.entity.base.practicalpet.PracticalPet;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import org.jetbrains.annotations.NotNull;

public class DefendSelfIfShouldGoal extends HurtByTargetGoal {
    PetEquipmentWearer pet;
    public DefendSelfIfShouldGoal(PetEquipmentWearer mob, Class<?>... entityClass) {
        super((PathfinderMob) mob, entityClass);
        this.pet = mob;
    }

    @Override
    public boolean canUse() {
        return this.pet.petShouldDefendSelf() && super.canUse();
    }

    @Override
    public boolean canContinueToUse() {
        return this.pet.petShouldDefendSelf() && super.canContinueToUse();
    }

    @Override
    protected void alertOther(@NotNull Mob mob, @NotNull LivingEntity target) {
        if (this.pet.petShouldDefendSelf())
            super.alertOther(mob, target);
    }
}
