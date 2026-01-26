package billeyzambie.practicalpets.goal;

import billeyzambie.practicalpets.entity.dinosaur.Pigeon;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.item.ItemEntity;

public class PigeonGetDroppedItemGoal extends Goal {
    private final Pigeon pigeon;
    private final double speedModifier;

    public PigeonGetDroppedItemGoal(Pigeon pigeon, double speedModifier) {
        this.pigeon = pigeon;
        this.speedModifier = speedModifier;
    }

    @Override
    public boolean canUse() {
        return this.pigeon.getTargetItemEntity() != null
                && this.pigeon.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty();
    }

    @Override
    public void tick() {
        ItemEntity targetItemEntity = this.pigeon.getTargetItemEntity();
        if (targetItemEntity != null) {
            this.pigeon.getNavigation().moveTo(targetItemEntity, this.speedModifier);

            if (this.pigeon.distanceToSqr(targetItemEntity) < 1) {
                this.pigeon.setItemSlot(EquipmentSlot.MAINHAND, targetItemEntity.getItem());
                targetItemEntity.discard();
            }
        }
        super.tick();
    }
}
