package billeyzambie.practicalpets.network;

import billeyzambie.practicalpets.entity.PracticalPet;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PetHatSquishAnimPacket {
    private final int entityId;

    public PetHatSquishAnimPacket(int entityId) {
        this.entityId = entityId;
    }

    public static void encode(PetHatSquishAnimPacket msg, FriendlyByteBuf buf) {
        buf.writeInt(msg.entityId);
    }

    public static PetHatSquishAnimPacket decode(FriendlyByteBuf buf) {
        return new PetHatSquishAnimPacket(buf.readInt());
    }

    public static void handle(PetHatSquishAnimPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
                assert Minecraft.getInstance().level != null;
                Entity entity = Minecraft.getInstance().level.getEntity(msg.entityId);
                if (entity instanceof PracticalPet pet) {
                    pet.squishHatAnimationState.start(pet.tickCount);
                }
            });
        });
        ctx.get().setPacketHandled(true);
    }
}
