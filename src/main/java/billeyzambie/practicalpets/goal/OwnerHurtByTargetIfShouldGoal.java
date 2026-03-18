package billeyzambie.practicalpets.goal;

import billeyzambie.practicalpets.entity.base.practicalpet.PetEquipmentWearer;
import billeyzambie.practicalpets.entity.base.practicalpet.PracticalPet;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import org.jetbrains.annotations.Nullable;

public class OwnerHurtByTargetIfShouldGoal extends OwnerHurtByTargetGoal {
    public final PetEquipmentWearer pet;
    public final TamableAnimal tamableAnimal;
    public OwnerHurtByTargetIfShouldGoal(PetEquipmentWearer pet) {
        super((TamableAnimal) pet);
        this.pet = pet;
        this.tamableAnimal = (TamableAnimal) pet;
    }

    @Nullable
    public LivingEntity getTarget() {
        var owner = tamableAnimal.getOwner();
        if (owner == null)
            return null;
        return owner.getLastHurtByMob();
    }

    @Override
    public boolean canUse() {
        var target = getTarget();
        return target != null && pet.petShouldDefendOwner(target) && super.canUse();
    }
    @Override
    public boolean canContinueToUse() {
        var target = getTarget();
        return target != null && pet.petShouldDefendOwner(target) && super.canContinueToUse();
    }
}
