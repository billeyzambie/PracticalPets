package billeyzambie.practicalpets.network;

import billeyzambie.practicalpets.entity.dinosaur.AbstractDuck;
import billeyzambie.practicalpets.entity.dinosaur.Duck;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class DuckBiteFloorAnimPacket {
    private final int entityId;

    public DuckBiteFloorAnimPacket(int entityId) {
        this.entityId = entityId;
    }

    public static void encode(DuckBiteFloorAnimPacket msg, FriendlyByteBuf buf) {
        buf.writeInt(msg.entityId);
    }

    public static DuckBiteFloorAnimPacket decode(FriendlyByteBuf buf) {
        return new DuckBiteFloorAnimPacket(buf.readInt());
    }

    public static void handle(DuckBiteFloorAnimPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
                assert Minecraft.getInstance().level != null;
                Entity entity = Minecraft.getInstance().level.getEntity(msg.entityId);
                if (entity instanceof Duck duck) {
                    duck.biteFloorAnimationState.start(duck.tickCount);
                }
            });
        });
        ctx.get().setPacketHandled(true);
    }
}
