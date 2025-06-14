package billeyzambie.practicalpets.network;

import billeyzambie.practicalpets.entity.dinosaur.AbstractDuck;
import billeyzambie.practicalpets.entity.dinosaur.BananaDuck;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class QuackAnimPacket {
    private final int entityId;

    public QuackAnimPacket(int entityId) {
        this.entityId = entityId;
    }

    public static void encode(QuackAnimPacket msg, FriendlyByteBuf buf) {
        buf.writeInt(msg.entityId);
    }

    public static QuackAnimPacket decode(FriendlyByteBuf buf) {
        return new QuackAnimPacket(buf.readInt());
    }

    public static void handle(QuackAnimPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
                Entity entity = Minecraft.getInstance().level.getEntity(msg.entityId);
                if (entity instanceof AbstractDuck duck) {
                    duck.quackAnimationState.start(duck.tickCount);
                }
            });
        });
        ctx.get().setPacketHandled(true);
    }
}
