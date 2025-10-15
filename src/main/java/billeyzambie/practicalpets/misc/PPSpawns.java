package billeyzambie.practicalpets.misc;

import billeyzambie.practicalpets.entity.dinosaur.Duck;
import billeyzambie.practicalpets.entity.otherpet.Rat;
import net.minecraft.core.Holder;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.world.ModifiableBiomeInfo;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = PracticalPets.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class PPSpawns {

    public static void addBiomeSpawns(Holder<Biome> biome, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
        if (biome.is(BiomeTags.IS_JUNGLE)) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.CREATURE).add(new MobSpawnSettings.SpawnerData(
                    PPEntities.BANANA_DUCK.get(),
                    20, 2, 4
            ));
        }

        if (isTemperateAnimalBiome(biome)) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.CREATURE).add(new MobSpawnSettings.SpawnerData(
                    PPEntities.DUCK.get(),
                    20, 4, 8
            ));
            builder.getMobSpawnSettings().getSpawner(MobCategory.AMBIENT).add(new MobSpawnSettings.SpawnerData(
                    PPEntities.RAT.get(),
                    20, 4, 8
            ));
        }

    }

    private static boolean isTemperateAnimalBiome(Holder<Biome> biome) {
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

    @SubscribeEvent
    public static void registerSpawnPlacements(SpawnPlacementRegisterEvent event) {

        event.register(
                PPEntities.BANANA_DUCK.get(),
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Animal::checkAnimalSpawnRules,
                SpawnPlacementRegisterEvent.Operation.REPLACE
        );

        event.register(
                PPEntities.DUCK.get(),
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Duck::duckCanSpawn,
                SpawnPlacementRegisterEvent.Operation.REPLACE
        );

        event.register(
                PPEntities.RAT.get(),
                SpawnPlacements.Type.NO_RESTRICTIONS,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Rat::ratCanSpawn,
                SpawnPlacementRegisterEvent.Operation.REPLACE
        );
    }
}
