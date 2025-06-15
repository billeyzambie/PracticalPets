package billeyzambie.practicalpets;

import billeyzambie.practicalpets.entity.dinosaur.BananaDuck;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = PracticalPets.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class CommonListener {
    @SubscribeEvent
    public static void registerEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(
                ModEntities.BANANA_DUCK.get(),
                BananaDuck.createMobAttributes()
                        .add(Attributes.MAX_HEALTH, 6)
                        .add(Attributes.MOVEMENT_SPEED, 0.25)
                        .add(Attributes.ATTACK_DAMAGE, 2)
                        .build()
        );
        event.put(
                ModEntities.DUCK.get(),
                BananaDuck.createMobAttributes()
                        .add(Attributes.MAX_HEALTH, 6)
                        .add(Attributes.MOVEMENT_SPEED, 0.25)
                        .add(Attributes.ATTACK_DAMAGE, 2)
                        .build()
        );
    }
    @SubscribeEvent
    public static void registerSpawnPlacements(SpawnPlacementRegisterEvent event) {
        event.register(
                ModEntities.BANANA_DUCK.get(),
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.WORLD_SURFACE,
                BananaDuck::canSpawn,
                SpawnPlacementRegisterEvent.Operation.OR
        );
    }
}