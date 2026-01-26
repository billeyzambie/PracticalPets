package billeyzambie.practicalpets.network;

import billeyzambie.practicalpets.misc.PPEvents;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public record PlayerPunchAirPacket() {
    public static void encode(PlayerPunchAirPacket packet, FriendlyByteBuf buf) {
    }

    public static PlayerPunchAirPacket decode(FriendlyByteBuf buf) {
        return new PlayerPunchAirPacket();
    }

    public static void handle(PlayerPunchAirPacket packet, Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            ServerPlayer sender = context.get().getSender();
            if (sender == null) {
                return;
            }
            PPEvents.onPlayerPunchAir(sender);
        });
        context.get().setPacketHandled(true);
    }
}
