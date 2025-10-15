package billeyzambie.practicalpets.misc;

import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ModifiableBiomeInfo;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

//Copied from Alex's Mobs
public class PPBiomeModifier implements BiomeModifier {
    private static final RegistryObject<Codec<? extends BiomeModifier>> SERIALIZER = RegistryObject.create(new ResourceLocation(PracticalPets.MODID, "add_pet_spawns"), ForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, PracticalPets.MODID);

    @Override
    public void modify(Holder<Biome> biome, Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
        if (phase == Phase.ADD) {
            PPSpawns.addBiomeSpawns(biome, builder);
        }
    }

    @Override
    public Codec<? extends BiomeModifier> codec() {
        return SERIALIZER.get();
    }

    public static Codec<PPBiomeModifier> makeCodec() {
        return Codec.unit(PPBiomeModifier::new);
    }
}
