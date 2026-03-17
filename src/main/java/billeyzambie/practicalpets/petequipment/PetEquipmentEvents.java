package billeyzambie.practicalpets.petequipment;

import billeyzambie.practicalpets.entity.base.practicalpet.PetEquipmentWearer;
import billeyzambie.practicalpets.items.PetCosmetic;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

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

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        LivingEntity entity = event.getEntity();
        float amount = event.getAmount();
        DamageSource source = event.getSource();

        if (!(entity instanceof PetEquipmentWearer wearer))
            return;

        for (PetCosmetic.Slot slot : PetCosmetic.Slot.values()) {
            ItemStack cosmeticStack = wearer.getEquippedItem(slot);
            Optional<PetCosmetic> cosmeticOptional = PetCosmetics.getCosmeticForItem(cosmeticStack);
            if (cosmeticOptional.isPresent()) {
                var cosmetic = cosmeticOptional.orElseThrow();
                amount *= cosmetic.damageMultiplier(cosmeticStack, wearer);
            }
        }

        for (PetCosmetic.Slot slot : PetCosmetic.Slot.values()) {
            ItemStack cosmeticStack = wearer.getEquippedItem(slot);
            Optional<PetCosmetic> cosmeticOptional = PetCosmetics.getCosmeticForItem(cosmeticStack);
            if (cosmeticOptional.isPresent()) {
                var cosmetic = cosmeticOptional.orElseThrow();
                if (cosmetic.onPetHurt(cosmeticStack, wearer, source, amount)) {
                    event.setCanceled(true);
                    return;
                }
            }
        }

        event.setAmount(amount);
    }

    @SubscribeEvent
    public static void onLivingAttack(LivingAttackEvent event) {
        LivingEntity target = event.getEntity();
        Entity attacker = event.getSource().getEntity();

        if (
                !event.getSource().is(DamageTypes.MOB_ATTACK)
                    || !(attacker instanceof PetEquipmentWearer wearer)
        )
            return;

        for (PetCosmetic.Slot slot : PetCosmetic.Slot.values()) {
            ItemStack cosmeticStack = wearer.getEquippedItem(slot);
            PetCosmetics.getCosmeticForItem(cosmeticStack).ifPresent(
                    cosmetic -> cosmetic.onPetHit(cosmeticStack, wearer, target)
            );

        }
    }
}
