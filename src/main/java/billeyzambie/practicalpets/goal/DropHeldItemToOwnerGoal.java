package billeyzambie.practicalpets.goal;

import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;

public class DropHeldItemToOwnerGoal extends FollowOwnerGoal {
    private final TamableAnimal pet;

    public DropHeldItemToOwnerGoal(TamableAnimal animal, double speedModifier, boolean canFly) {
        super(animal, speedModifier, 0, 0, canFly);
        this.pet = animal;
    }

    @Override
    public boolean canUse() {
        if (pet.level().isClientSide) return false;
        if (pet.getOwner() == null) return false;
        if (pet.getMainHandItem().isEmpty()) return false;
        return super.canUse();
    }

    @Override
    public boolean canContinueToUse() {
        if (pet.level().isClientSide) return false;
        if (pet.getOwner() == null) return false;
        if (pet.getMainHandItem().isEmpty()) return false;
        return super.canContinueToUse();
    }

    @Override
    public void tick() {
        super.tick();

        LivingEntity owner = pet.getOwner();
        if (owner == null) return;

        if (pet.distanceToSqr(owner) <= 4.0) {
            ItemStack held = pet.getMainHandItem();
            if (!held.isEmpty()) {

                ItemEntity drop = new ItemEntity(pet.level(), owner.getX(), owner.getY(), owner.getZ(), held.copy());
                drop.setThrower(pet.getUUID());
                pet.level().addFreshEntity(drop);

                pet.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
                pet.getNavigation().stop();
            }
        }
    }
}
