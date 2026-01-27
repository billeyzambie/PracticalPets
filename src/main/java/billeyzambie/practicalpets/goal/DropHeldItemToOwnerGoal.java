package billeyzambie.practicalpets.goal;

import billeyzambie.practicalpets.entity.PracticalPet;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;

public class DropHeldItemToOwnerGoal extends FollowOwnerWanderableGoal {
    private final PracticalPet pet;

    public DropHeldItemToOwnerGoal(PracticalPet pet, double speedModifier, boolean canFly) {
        super(pet, speedModifier, 0, 0, canFly);
        this.pet = pet;
    }

    @Override
    public boolean canUse() {
        if (pet.getOwner() == null) return false;
        if (pet.getMainHandItem().isEmpty()) return false;
        if (!pet.shouldDropHeldItemToOwner()) return false;
        return this.vanillaFollowOwnerCanUse();
    }

    @Override
    public boolean canContinueToUse() {
        if (pet.getOwner() == null) return false;
        if (pet.getMainHandItem().isEmpty()) return false;
        if (!pet.shouldDropHeldItemToOwner()) return false;
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
