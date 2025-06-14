package billeyzambie.practicalpets;

import billeyzambie.practicalpets.entity.dinosaur.BananaDuck;
import billeyzambie.practicalpets.entity.dinosaur.Duck;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, PracticalPets.MODID);

    public static final RegistryObject<EntityType<BananaDuck>> BANANA_DUCK = REGISTRY.register(
            "banana_duck",
            () -> EntityType.Builder.of(BananaDuck::new, MobCategory.CREATURE)
                    .sized(0.6f, 0.7f)
                    .build("banana_duck")
    );
    public static final RegistryObject<EntityType<Duck>> DUCK = REGISTRY.register(
            "banana_duck",
            () -> EntityType.Builder.of(Duck::new, MobCategory.CREATURE)
                    .sized(0.6f, 0.6f)
                    .build("duck")
    );
}
