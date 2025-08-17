package billeyzambie.practicalpets.misc;

import net.minecraft.core.Holder;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.world.ModifiableBiomeInfo;

public class PracticalPetSpawns {

    public static void addBiomeSpawns(Holder<Biome> biome, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
        if (biome.is(BiomeTags.IS_JUNGLE)) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.CREATURE).add(new MobSpawnSettings.SpawnerData(
                    PPEntities.BANANA_DUCK.get(),
                    20, 2, 4
            ));
        }

        if (isDuckBiome(biome)) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.CREATURE).add(new MobSpawnSettings.SpawnerData(
                    PPEntities.DUCK.get(),
                    200, 2, 4
            ));
        }

    }

    private static boolean isDuckBiome(Holder<Biome> biome) {
        return (biome.is(Tags.Biomes.IS_SWAMP)
                || biome.is(BiomeTags.IS_JUNGLE)
                || biome.is(BiomeTags.IS_RIVER)
                        || biome.is(Tags.Biomes.IS_PLAINS)
                        || biome.is(BiomeTags.IS_FOREST)
                        || biome.is(Tags.Biomes.IS_LUSH)
                        || biome.is(Tags.Biomes.IS_WET)
        ) && !biome.is(Tags.Biomes.IS_HOT)
                && !biome.is(Tags.Biomes.IS_COLD);
    }
}
