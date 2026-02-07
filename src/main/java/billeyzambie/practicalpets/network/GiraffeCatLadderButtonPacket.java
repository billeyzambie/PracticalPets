package billeyzambie.practicalpets.network;

import billeyzambie.practicalpets.entity.otherpet.GiraffeCat;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public record GiraffeCatLadderButtonPacket(int giraffeCatId) {
    public static void encode(GiraffeCatLadderButtonPacket packet, FriendlyByteBuf buf) {
        buf.writeVarInt(packet.giraffeCatId());
    }

    public static GiraffeCatLadderButtonPacket decode(FriendlyByteBuf buf) {
        return new GiraffeCatLadderButtonPacket(buf.readVarInt());
    }

    public static void handle(GiraffeCatLadderButtonPacket packet, Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            ServerPlayer sender = context.get().getSender();
            if (sender == null) {
                return;
            }

            Entity entity = sender.level().getEntity(packet.giraffeCatId());
            if (!(entity instanceof GiraffeCat giraffeCat)) {
                return;
            }

            giraffeCat.toggleLadder();
        });
        context.get().setPacketHandled(true);
    }
}