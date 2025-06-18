package billeyzambie.practicalpets.network;

import billeyzambie.practicalpets.entity.dinosaur.AbstractDuck;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class DuckIdleFlapPacket {
    private final int entityId;
    private final int timerValue;

    public DuckIdleFlapPacket(int entityId, int timerValue) {
        this.entityId = entityId;
        this.timerValue = timerValue;
    }

    public static void encode(DuckIdleFlapPacket msg, FriendlyByteBuf buf) {
        buf.writeInt(msg.entityId);
        buf.writeInt(msg.timerValue);
    }

    public static DuckIdleFlapPacket decode(FriendlyByteBuf buf) {
        return new DuckIdleFlapPacket(buf.readInt(), buf.readInt());
    }

    public static void handle(DuckIdleFlapPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
                assert Minecraft.getInstance().level != null;
                Entity entity = Minecraft.getInstance().level.getEntity(msg.entityId);
                if (entity instanceof AbstractDuck duck) {
                    duck.setIdleFlapTime(msg.timerValue);
                }
            });
        });
        ctx.get().setPacketHandled(true);
    }
}