package billeyzambie.practicalpets.network;

import billeyzambie.practicalpets.entity.dinosaur.Pigeon;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

public record PigeonSendPacket(int pigeonId, UUID targetId) {
    public static void encode(PigeonSendPacket packet, FriendlyByteBuf buf) {
        buf.writeVarInt(packet.pigeonId());
        buf.writeUUID(packet.targetId());
    }

    public static PigeonSendPacket decode(FriendlyByteBuf buf) {
        return new PigeonSendPacket(buf.readVarInt(), buf.readUUID());
    }

    public static void handle(PigeonSendPacket packet, Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            ServerPlayer sender = context.get().getSender();
            if (sender == null) {
                return;
            }

            Entity entity = sender.level().getEntity(packet.pigeonId());
            if (!(entity instanceof Pigeon pigeon)) {
                return;
            }

            if (sender.getServer() == null) {
                return;
            }

            ServerPlayer target = sender.getServer().getPlayerList().getPlayer(packet.targetId());
            if (
                    !pigeon.isOwnedBy(sender)
                            || target == null
                            || pigeon.isInvalidMissionTarget(target)
                            || (sender.experienceLevel < 1 && !sender.getAbilities().instabuild)
            ) {
                return;
            }

            sender.giveExperienceLevels(-1);
            sender.playSound(SoundEvents.EXPERIENCE_ORB_PICKUP, 1, 0.9f);

            pigeon.startMission(target);
        });
        context.get().setPacketHandled(true);
    }
}