package billeyzambie.practicalpets.misc;

import billeyzambie.practicalpets.items.*;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class PPItems {
    public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, PracticalPets.MODID);

    private static <I extends Item> RegistryObject<I> register(final String name, final Supplier<? extends I> sup) {
        //more stuff here soon
        return REGISTRY.register(name, sup);
    }

    //it's first so that some mods might consider it the mod's icon
    public static final RegistryObject<Item> PET_BOWTIE = register(
            "pet_bowtie",
            () -> new PetBowtie("bowtie")
    );

    public static final RegistryObject<Item> LEATHER_DUCK_ARMOR = register(
            "leather_duck_armor",
            () -> new DyeableDuckArmor("leather", 0.7f, SoundEvents.ARMOR_EQUIP_LEATHER)
    );
    public static final RegistryObject<Item> GOLDEN_DUCK_ARMOR = register(
            "golden_duck_armor",
            () -> new DuckArmor("golden", 0.7f, SoundEvents.ARMOR_EQUIP_GOLD)
    );
    public static final RegistryObject<Item> CHAINMAIL_DUCK_ARMOR = register(
            "chainmail_duck_armor",
            () -> new DuckArmor("chainmail", 0.65f, SoundEvents.ARMOR_EQUIP_CHAIN)
    );
    public static final RegistryObject<Item> IRON_DUCK_ARMOR = register(
            "iron_duck_armor",
            () -> new DuckArmor("iron", 0.6f, SoundEvents.ARMOR_EQUIP_IRON)
    );
    public static final RegistryObject<Item> DIAMOND_DUCK_ARMOR = register(
            "diamond_duck_armor",
            () -> new DuckArmor("diamond", 0.5f, SoundEvents.ARMOR_EQUIP_DIAMOND)
    );
    public static final RegistryObject<Item> NETHERITE_DUCK_ARMOR = register(
            "netherite_duck_armor",
            () -> new DuckArmor("netherite", 0.4f, SoundEvents.ARMOR_EQUIP_NETHERITE)
    );
    //    public static final RegistryObject<Item> END_ROD_DUCK_ARMOR = register(
//    "end_rod_duck_armor",
//            () -> new DyeableDuckArmor("end_rod",0.3f)
//    );
    public static final RegistryObject<Item> POULTRY_BANANA = register(
            "poultry_banana",
            PoultryBanana::new
    );
    public static final RegistryObject<Item> BANANA_PEEL = register(
            "banana_peel",
            () -> new BlockItem(PPBlocks.BANANA_PEEL.get(), new Item.Properties())
    );
    public static final RegistryObject<Item> RATATOUILLE = register(
            "ratatouille",
            () -> new Ratatouille(new Item.Properties().stacksTo(1).food(
                    new FoodProperties.Builder().build()
            ))
    );
    public static final RegistryObject<Item> DIAMOND_NUGGET = register(
            "diamond_nugget",
            () -> new Item(new Item.Properties())
    );
    public static final RegistryObject<Item> CHICKEN_NUGGET = register(
            "chicken_nugget",
            () -> new CustomUseDurationItem(
                    new Item.Properties().food(new FoodProperties.Builder().meat().nutrition(1).fast().saturationMod(0.6f).build()),
                    8
            )
    );
    public static final RegistryObject<Item> POTATO_STICK = register(
            "potato_stick",
            () -> new CustomUseDurationItem(
                    new Item.Properties().food(new FoodProperties.Builder().nutrition(2).fast().saturationMod(0.6f).build()),
                    4
            )
    );
    public static final RegistryObject<Item> KIWI_FEATHERS = register(
            "kiwi_feathers",
            () -> new Item(new Item.Properties())
    );

    public static final RegistryObject<Item> ANNIVERSARY_PET_HAT_0 = register(
            "anniversary_pet_hat_0",
            () -> new AnniversaryPetHat("anniversary_hat_0", 2, new Item.Properties().stacksTo(1).rarity(Rarity.EPIC))
    );
    public static final RegistryObject<Item> RUBBER_DUCKY_PET_HAT = register(
            "rubber_ducky_pet_hat",
            RubberDuckyPetHat::new
    );
    public static final RegistryObject<Item> PET_CHEF_HAT = register(
            "pet_chef_hat",
            () -> new DyeablePetHat("misc", 0xffffff)
    );
    public static final RegistryObject<Item> PET_BACKPACK = register(
            "pet_backpack",
            PetBackpack::new
    );
    public static final RegistryObject<Item> PET_END_ROD_LAUNCHER = register(
            "pet_end_rod_launcher",
            PetEndRodLauncher::new
    );
    public static final RegistryObject<Item> PET_HAT = register(
            "pet_hat",
            () -> new PlainPetHat("misc")
    );

    public static final RegistryObject<ForgeSpawnEggItem> BANANA_DUCK_SPAWN_EGG = register(
            "banana_duck_spawn_egg",
            () -> new ForgeSpawnEggItem(PPEntities.BANANA_DUCK, 0xfecb32, 0xFFF0BB, new Item.Properties())
    );
    public static final RegistryObject<ForgeSpawnEggItem> DUCK_SPAWN_EGG = register(
            "duck_spawn_egg",
            () -> new ForgeSpawnEggItem(PPEntities.DUCK, 0x5E4523, 0x193F0A, new Item.Properties())
    );
    public static final RegistryObject<ForgeSpawnEggItem> RAT_SPAWN_EGG = register(
            "rat_spawn_egg",
            () -> new ForgeSpawnEggItem(PPEntities.RAT, 0x718794, 0xffd1b3, new Item.Properties())
    );
    public static final RegistryObject<ForgeSpawnEggItem> PIGEON_SPAWN_EGG = register(
            "pigeon_spawn_egg",
            () -> new ForgeSpawnEggItem(PPEntities.PIGEON, 0x303437, 0xC5D7E4, new Item.Properties())
    );
    public static final RegistryObject<ForgeSpawnEggItem> STICK_BUG_SPAWN_EGG = register(
            "stick_bug_spawn_egg",
            () -> new ForgeSpawnEggItem(PPEntities.STICK_BUG, 0xBF9E67, 0x159F02, new Item.Properties())
    );
    public static final RegistryObject<ForgeSpawnEggItem> GIRAFFE_CAT_SPAWN_EGG = register(
            "giraffe_cat_spawn_egg",
            () -> new ForgeSpawnEggItem(PPEntities.GIRAFFE_CAT, 0xFFEBCB, 0x7F6751, new Item.Properties())
    );
    public static final RegistryObject<ForgeSpawnEggItem> KIWI_SPAWN_EGG = register(
            "kiwi_spawn_egg",
            () -> new ForgeSpawnEggItem(PPEntities.KIWI, 0x7CA422, 0x6E4F24, new Item.Properties())
    );
}
