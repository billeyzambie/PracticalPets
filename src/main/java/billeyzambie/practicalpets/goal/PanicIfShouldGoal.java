package billeyzambie.practicalpets.goal;

import billeyzambie.practicalpets.entity.base.practicalpet.PetEquipmentWearer;
import billeyzambie.practicalpets.entity.base.practicalpet.PracticalPet;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.PanicGoal;

public class PanicIfShouldGoal extends PanicGoal {
    public PanicIfShouldGoal(PetEquipmentWearer pathfinderMob, double speedMultiplier) {
        super((PathfinderMob) pathfinderMob, speedMultiplier);
    }

    @Override
    public boolean canUse() {
        if (mob.getTarget() == null && (mob.isOnFire() || mob.isFreezing()))
            return true;
        return ((PetEquipmentWearer) mob).petShouldPanic() && super.canUse();
    }
}
