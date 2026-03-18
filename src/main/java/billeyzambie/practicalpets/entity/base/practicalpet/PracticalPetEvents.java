package billeyzambie.practicalpets.entity.base.practicalpet;

import billeyzambie.practicalpets.goal.GiraffeCatMeleeAttackGoal;
import billeyzambie.practicalpets.goal.PetEquipmentWearerCosmeticRangedAttackGoal;
import billeyzambie.practicalpets.goal.PetEquipmentWearerMeleeAttackGoal;
import billeyzambie.practicalpets.items.PetCosmetic;
import billeyzambie.practicalpets.petequipment.PetCosmetics;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.WrappedGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;

public final class PracticalPetEvents {

    @Mod.EventBusSubscriber
    public static final class LevelablePetEvents {
        @SubscribeEvent
        public static void onLivingAttack(LivingAttackEvent event) {
            LivingEntity target = event.getEntity();
            Entity attacker = event.getSource().getEntity();

            if (
                    !event.getSource().is(DamageTypes.MOB_ATTACK)
                            || !(attacker instanceof LevelablePet pet)
            )
                return;

            pet.addXpOnHit(target);
        }
    }

    @Mod.EventBusSubscriber
    public static final class PetEquipmentWearerEvents {
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

        public static final Map<Class<? extends Goal>, BiFunction<Goal, PetEquipmentWearer, Goal>> goalMap = new HashMap<>();

        public static final BiFunction<Goal, PetEquipmentWearer, Goal> GOAL_RETURN_SELF =
                (oldGoal, wearer) -> oldGoal;

        public static void registerGoalReturnSelf(Class<? extends Goal> goalClass) {
            goalMap.put(
                    goalClass,
                    GOAL_RETURN_SELF
            );
        }

        static {
            goalMap.put(
                    MeleeAttackGoal.class,
                    (oldGoal, wearer) -> new PetEquipmentWearerMeleeAttackGoal(
                            wearer,
                            ((MeleeAttackGoal) oldGoal).speedModifier,
                            ((MeleeAttackGoal) oldGoal).followingTargetEvenIfNotSeen
                    )
            );
            registerGoalReturnSelf(GiraffeCatMeleeAttackGoal.class);
        }

        @SubscribeEvent
        public static void onEntityJoinLevel(EntityJoinLevelEvent event) {
            if (event.getLevel().isClientSide())
                return;

            var entity = event.getEntity();

            if (!(entity instanceof PetEquipmentWearer))
                return;

            var wearer = (Mob & PetEquipmentWearer) entity;

            for (WrappedGoal wrappedGoal : wearer.goalSelector.getAvailableGoals()) {
                Goal goal = wrappedGoal.getGoal();

                var biFunction = goalMap.get(goal.getClass());
                if (biFunction == null)
                    continue;

                if (biFunction != GOAL_RETURN_SELF) {
                    wearer.goalSelector.removeGoal(goal);
                    wearer.goalSelector.addGoal(
                            wrappedGoal.getPriority(),
                            biFunction.apply(goal, wearer)
                    );
                }

                wearer.goalSelector.addGoal(
                        wrappedGoal.getPriority(),
                        new PetEquipmentWearerCosmeticRangedAttackGoal(
                                wearer,
                                wearer.createWearerCosmeticRangedSpeedModifier()
                        )
                );

                break;
            }

        }
    }
}
