package billeyzambie.practicalpets.petequipment;

import billeyzambie.practicalpets.entity.base.practicalpet.PetEquipmentWearer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class PetEquipmentEvents {
    @SubscribeEvent
    public static void onEntityInteract(PlayerInteractEvent.EntityInteract event) {
        Player player = event.getEntity();
        InteractionHand hand = event.getHand();
        Entity target = event.getTarget();

        if (!(target instanceof PetEquipmentWearer wearer))
            return;

        if (wearer.canInteractEventPutPetEquipment(player, hand)) {
            InteractionResult petEquipmentWearerEquip = wearer.petEquipmentWearerEquip(player, hand);
            if (petEquipmentWearerEquip != InteractionResult.PASS) {
                event.setCanceled(true);
                event.setCancellationResult(petEquipmentWearerEquip);
                return;
            }
        }

        if (wearer.canInteractEventShearPetEquipment(player, hand)) {
            InteractionResult petEquipmentWearerShear = wearer.petEquipmentWearerShear(player, hand);
            if (petEquipmentWearerShear != InteractionResult.PASS) {
                event.setCanceled(true);
                event.setCancellationResult(petEquipmentWearerShear);
            }
        }

    }
}
