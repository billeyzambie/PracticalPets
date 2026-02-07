package billeyzambie.practicalpets.misc;

import billeyzambie.practicalpets.entity.PracticalPet;
import billeyzambie.practicalpets.entity.dinosaur.Pigeon;
import billeyzambie.practicalpets.entity.otherpet.GiraffeCat;
import billeyzambie.practicalpets.network.PlayerPunchAirPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;

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

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        Player player = event.player;
        if (!player.level().isClientSide()) {
            tickDoubleSneakPetTp(player);
            @Nullable GiraffeCat climbedGiraffeCat = climbedGiraffeCats.get(player);
            if (climbedGiraffeCat != null && player.onGround()) {
                landAfterClimbingGiraffeCat(player, climbedGiraffeCat);
            }
        }
    }

    public static final HashMap<Player, GiraffeCat> climbedGiraffeCats = new HashMap<>();

    private static void landAfterClimbingGiraffeCat(Player player, GiraffeCat giraffeCat) {
        Vec3 playerPos = player.position();
        if (
                giraffeCat.isAlive()
                        && giraffeCat.isLadder()
                        && playerPos.y >= giraffeCat.position().y + giraffeCat.getLadderHeight() - 1
                        && giraffeCat.followMode() != PracticalPet.FollowMode.WANDERING
        ) {
            giraffeCat.teleportTo(playerPos.x, playerPos.y, playerPos.z);
            giraffeCat.stopLadder();
            giraffeCat.setOrderedToSit(false);
            giraffeCat.setShouldFollowOwner(true);
        }
        climbedGiraffeCats.remove(player);
    }

    private static final HashMap<UUID, Integer> lastSneakTime = new HashMap<>();
    private static final HashMap<UUID, Boolean> wasSneaking = new HashMap<>();
    private static final int DOUBLE_SNEAK_WINDOW = 10;

    private static void tickDoubleSneakPetTp(Player player) {
        UUID playerId = player.getUUID();

        boolean isSneaking = player.isShiftKeyDown();
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

    private static void teleportStandingPets(Player player) {
        if (!(player instanceof ServerPlayer serverPlayer))
            return;

        List<PracticalPet> pets = serverPlayer.level().getEntitiesOfClass(PracticalPet.class, player.getBoundingBox().inflate(64),
                pet ->
                        pet.isTame() && pet.getOwnerUUID() != null && pet.getOwnerUUID().equals(player.getUUID())
                        && !pet.isOrderedToSit() && pet.shouldFollowOwner()
        );
        pets.forEach(pet -> {
            pet.teleportTo(player.getX(), player.getY(), player.getZ());
            pet.setDeltaMovement(Vec3.ZERO);
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
