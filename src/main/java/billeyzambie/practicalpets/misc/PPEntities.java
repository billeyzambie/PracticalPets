package billeyzambie.practicalpets.misc;

import billeyzambie.practicalpets.entity.dinosaur.BananaDuck;
import billeyzambie.practicalpets.entity.dinosaur.Duck;
import billeyzambie.practicalpets.entity.dinosaur.Pigeon;
import billeyzambie.practicalpets.entity.other.YeetedPetCarrier;
import billeyzambie.practicalpets.entity.otherpet.GiraffeCat;
import billeyzambie.practicalpets.entity.otherpet.Rat;
import billeyzambie.practicalpets.entity.other.PetEndRodProjectile;
import billeyzambie.practicalpets.entity.otherpet.StickBug;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = PracticalPets.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class PPEntities {
    public static final DeferredRegister<EntityType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, PracticalPets.MODID);

    //non mob entities
    public static final RegistryObject<EntityType<PetEndRodProjectile>> PET_END_ROD_PROJECTILE = REGISTRY.register(
            "pet_end_rod_projectile",
            () -> EntityType.Builder.<PetEndRodProjectile>of(PetEndRodProjectile::new, MobCategory.MISC)
                    .sized(0.3125F, 0.3125F)
                    .build("pet_end_rod_projectile")
    );
    public static final RegistryObject<EntityType<YeetedPetCarrier>> THROWN_PET_CARRIER = REGISTRY.register(
            "thrown_pet_carrier",
            () -> EntityType.Builder.<YeetedPetCarrier>of(YeetedPetCarrier::new, MobCategory.CREATURE)
                    .sized(0.5f, 0.5f)
                    .build("thrown_pet_carrier")
    );

    //mobs
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
    public static final RegistryObject<EntityType<Rat>> RAT = REGISTRY.register(
            "rat",
            () -> EntityType.Builder.of(Rat::new, MobCategory.CREATURE)
                    .sized(0.6f, 0.33f)
                    .build("rat")
    );
    public static final RegistryObject<EntityType<Pigeon>> PIGEON = REGISTRY.register(
            "pigeon",
            () -> EntityType.Builder.of(Pigeon::new, MobCategory.CREATURE)
                    .sized(0.4f, 0.65f)
                    .build("pigeon")
    );
    public static final RegistryObject<EntityType<StickBug>> STICK_BUG = REGISTRY.register(
            "stick_bug",
            () -> EntityType.Builder.of(StickBug::new, MobCategory.CREATURE)
                    .sized(0.6f, 0.4f)
                    .build("stick_bug")
    );
    public static final RegistryObject<EntityType<GiraffeCat>> GIRAFFE_CAT = REGISTRY.register(
            "giraffe_cat",
            () -> EntityType.Builder.of(GiraffeCat::new, MobCategory.CREATURE)
                    .sized(0.6f, 0.7f)
                    .build("giraffe_cat")
    );

    @SubscribeEvent
    public static void registerEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(
                BANANA_DUCK.get(),
                BananaDuck.createMobAttributes()
                        .add(Attributes.MAX_HEALTH, 6)
                        .add(Attributes.MOVEMENT_SPEED, 0.2)
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
        event.put(
                RAT.get(),
                Rat.createMobAttributes()
                        .add(Attributes.MAX_HEALTH, 10)
                        .add(Attributes.MOVEMENT_SPEED, 0.2)
                        .add(Attributes.ATTACK_DAMAGE, 2)
                        .build()
        );
        event.put(
                PIGEON.get(),
                Pigeon.createMobAttributes()
                        .add(Attributes.MAX_HEALTH, 4)
                        .add(Attributes.MOVEMENT_SPEED, 0.2)
                        .add(Attributes.FLYING_SPEED, 0.6)
                        .add(Attributes.ATTACK_DAMAGE, 1)
                        .build()
        );
        event.put(
                STICK_BUG.get(),
                StickBug.createMobAttributes()
                        .add(Attributes.MAX_HEALTH, 2)
                        .add(Attributes.MOVEMENT_SPEED, 0.2)
                        .add(Attributes.ATTACK_DAMAGE, 1)
                        .build()
        );
        event.put(
                GIRAFFE_CAT.get(),
                GiraffeCat.createMobAttributes()
                        .add(Attributes.MAX_HEALTH, 16)
                        .add(Attributes.MOVEMENT_SPEED, 0.25)
                        .add(Attributes.ATTACK_DAMAGE, 3)
                        .add(Attributes.FOLLOW_RANGE, 64)
                        .build()
        );

    }
}
