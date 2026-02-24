package billeyzambie.practicalpets.misc;

import billeyzambie.practicalpets.network.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class PPNetworking {
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(PracticalPets.MODID, "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    public static void register() {
        int id = 0;
        CHANNEL.registerMessage(id++, QuackAnimPacket.class,
                QuackAnimPacket::encode,
                QuackAnimPacket::decode,
                QuackAnimPacket::handle);
        CHANNEL.registerMessage(id++, DuckIdleFlapPacket.class,
                DuckIdleFlapPacket::encode,
                DuckIdleFlapPacket::decode,
                DuckIdleFlapPacket::handle);
        CHANNEL.registerMessage(id++, PetHatSquishAnimPacket.class,
                PetHatSquishAnimPacket::encode,
                PetHatSquishAnimPacket::decode,
                PetHatSquishAnimPacket::handle);
        CHANNEL.registerMessage(id++, DuckBiteFloorAnimPacket.class,
                DuckBiteFloorAnimPacket::encode,
                DuckBiteFloorAnimPacket::decode,
                DuckBiteFloorAnimPacket::handle);
        CHANNEL.registerMessage(id++, RandomIdle1AnimPacket.class,
                RandomIdle1AnimPacket::encode,
                RandomIdle1AnimPacket::decode,
                RandomIdle1AnimPacket::handle);
        CHANNEL.registerMessage(id++, PigeonSendPacket.class,
                PigeonSendPacket::encode,
                PigeonSendPacket::decode,
                PigeonSendPacket::handle);
        CHANNEL.registerMessage(id++, PlayerPunchAirPacket.class,
                PlayerPunchAirPacket::encode,
                PlayerPunchAirPacket::decode,
                PlayerPunchAirPacket::handle);
        CHANNEL.registerMessage(id++, GiraffeCatLadderButtonPacket.class,
                GiraffeCatLadderButtonPacket::encode,
                GiraffeCatLadderButtonPacket::decode,
                GiraffeCatLadderButtonPacket::handle);
    }
}
