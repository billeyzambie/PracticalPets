package billeyzambie.practicalpets.misc;

import billeyzambie.practicalpets.entity.dinosaur.BananaDuck;
import billeyzambie.practicalpets.entity.dinosaur.Duck;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = PracticalPets.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class PPEntities {
    public static final DeferredRegister<EntityType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, PracticalPets.MODID);

    public static final RegistryObject<EntityType<BananaDuck>> BANANA_DUCK = REGISTRY.register(
            "banana_duck",
            () -> EntityType.Builder.of(BananaDuck::new, MobCategory.CREATURE)
                    .sized(0.6f, 0.7f)
                    .build("banana_duck")
    );
    public static final RegistryObject<EntityType<Duck>> DUCK = REGISTRY.register(
            "duck",
            () -> EntityType.Builder.of(Duck::new, MobCategory.CREATURE)
                    .sized(0.6f, 0.6f)
                    .build("duck")
    );

    @SubscribeEvent
    public static void registerEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(
                BANANA_DUCK.get(),
                BananaDuck.createMobAttributes()
                        .add(Attributes.MAX_HEALTH, 6)
                        .add(Attributes.MOVEMENT_SPEED, 0.25)
                        .add(Attributes.ATTACK_DAMAGE, 2)
                        .build()
        );
        event.put(
                DUCK.get(),
                Duck.createMobAttributes()
                        .add(Attributes.MAX_HEALTH, 6)
                        .add(Attributes.MOVEMENT_SPEED, Duck.MOVEMENT_SPEED)
                        .add(Attributes.ATTACK_DAMAGE, 2)
                        .build()
        );

        SpawnPlacements.register(
                BANANA_DUCK.get(),
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Animal::checkAnimalSpawnRules
        );
        SpawnPlacements.register(
                DUCK.get(),
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Duck::duckCanSpawn
        );

    }
}
