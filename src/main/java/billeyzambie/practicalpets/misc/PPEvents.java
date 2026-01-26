package billeyzambie.practicalpets.misc;

import billeyzambie.practicalpets.entity.PracticalPet;
import billeyzambie.practicalpets.entity.dinosaur.Pigeon;
import billeyzambie.practicalpets.network.PlayerPunchAirPacket;
import billeyzambie.practicalpets.util.DelayedTaskManager;
import billeyzambie.practicalpets.util.PPUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraft.world.entity.LivingEntity;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Mod.EventBusSubscriber
public class PPEvents {
    private static List<TamableAnimal> pets;

    @SubscribeEvent
    public static void onEntityLoad(EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof LivingEntity entity) {
            if (entity.getPersistentData().contains("practicalpets_just_slipped")) {
                entity.getPersistentData().remove("practicalpets_just_slipped");
            }
        }
    }

    private static final HashMap<UUID, Integer> lastSneakTime = new HashMap<>();
    private static final HashMap<UUID, Boolean> wasSneaking = new HashMap<>();
    private static final int DOUBLE_SNEAK_WINDOW = 10;

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;

        Player player = event.player;
        if (!player.level().isClientSide()) {
            UUID playerId = player.getUUID();

            boolean isSneaking = player.isCrouching();
            boolean wasPreviouslySneaking = wasSneaking.getOrDefault(playerId, false);

            if (isSneaking && !wasPreviouslySneaking) {
                int currentTime = player.tickCount;
                int lastTime = lastSneakTime.getOrDefault(playerId, 0);

                if (currentTime - lastTime < DOUBLE_SNEAK_WINDOW) {
                    teleportStandingPets(player);
                    lastSneakTime.remove(playerId);
                } else {
                    lastSneakTime.put(playerId, currentTime);
                }
            }

            wasSneaking.put(playerId, isSneaking);
        }
    }

    private static void teleportStandingPets(Player player) {
        if (!(player instanceof ServerPlayer serverPlayer))
            return;

        List<TamableAnimal> pets = serverPlayer.level().getEntitiesOfClass(TamableAnimal.class, player.getBoundingBox().inflate(64),
                pet -> pet instanceof PracticalPet &&
                        pet.isTame() && pet.getOwnerUUID() != null && pet.getOwnerUUID().equals(player.getUUID())
                        && ((PracticalPet) pet).followMode() == PracticalPet.FollowMode.FOLLOWING
        );
        pets.forEach(pet -> {
            pet.teleportTo(player.getX(), player.getY(), player.getZ());
        });
    }

    @SubscribeEvent
    public static void onPlayerPunchBlock(PlayerInteractEvent.LeftClickBlock event) {
        if (event.getAction() == PlayerInteractEvent.LeftClickBlock.Action.START) {
            Player player = event.getEntity();
            if (player instanceof ServerPlayer serverPlayer) {
                serverPlayer.server.execute(() -> Pigeon.makePigeonPickUpTargetItem(serverPlayer));
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerPunchAirClient(PlayerInteractEvent.LeftClickEmpty event) {
        assert Minecraft.getInstance().player != null;
        PPNetworking.CHANNEL.sendToServer(new PlayerPunchAirPacket());
    }

    //Called by onPlayerPunchAirClient
    public static void onPlayerPunchAir(ServerPlayer player) {
        Pigeon.makePigeonPickUpTargetItem(player);
    }
}
