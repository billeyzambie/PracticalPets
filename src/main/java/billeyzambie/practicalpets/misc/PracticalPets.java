package billeyzambie.practicalpets.misc;

import billeyzambie.practicalpets.client.PPMenus;
import billeyzambie.practicalpets.client.ui.PracticalPetScreen;
import billeyzambie.practicalpets.network.ModNetworking;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(PracticalPets.MODID)
public class PracticalPets
{
    // Define mod id in a common place for everything to reference
    public static final String MODID = "practicalpets";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();
    // Create a Deferred Register to hold Items which will all be registered under the "practicalpets" namespace
    // Create a Deferred Register to hold CreativeModeTabs which will all be registered under the "practicalpets" namespace
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    // Creates a new Block with the id "practicalpets:example_block", combining the namespace and path

    // Creates a new food item with the id "practicalpets:example_id", nutrition 1 and saturation 2
    // Creates a new food item with the id "practicalpets:example_id", nutrition 1 and saturation 2

    // Creates a creative tab with the id "practicalpets:example_tab" for the example item, that is placed after the combat tab
    public static final RegistryObject<CreativeModeTab> MOD_TAB = CREATIVE_MODE_TABS.register("practicalpets", () -> CreativeModeTab.builder()
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> new ItemStack(PPItems.PET_BOWTIE.get()))
            .title(Component.literal("Practical Pets"))
            .displayItems((parameters, output) -> {
                //spawn eggs
                output.accept(PPItems.BANANA_DUCK_SPAWN_EGG.get());
                output.accept(PPItems.DUCK_SPAWN_EGG.get());
                output.accept(PPItems.RAT_SPAWN_EGG.get());
                //food obtained from pets
                output.accept(PPItems.POULTRY_BANANA.get());
                output.accept(PPItems.RATATOUILLE.get());
                //other items obtained from pets
                output.accept(PPItems.BANANA_PEEL.get());
                //pet equipment
                output.accept(PPItems.LEATHER_DUCK_ARMOR.get());
                output.accept(PPItems.GOLDEN_DUCK_ARMOR.get());
                output.accept(PPItems.CHAINMAIL_DUCK_ARMOR.get());
                output.accept(PPItems.IRON_DUCK_ARMOR.get());
                output.accept(PPItems.DIAMOND_DUCK_ARMOR.get());
                output.accept(PPItems.NETHERITE_DUCK_ARMOR.get());
                output.accept(PPItems.PET_BOWTIE.get());
                output.accept(PPItems.ANNIVERSARY_PET_HAT_0.get());
                output.accept(PPItems.RUBBER_DUCKY_PET_HAT.get());
                output.accept(PPItems.PET_CHEF_HAT.get());
                output.accept(PPItems.PET_BACKPACK.get());
                output.accept(PPItems.PET_END_ROD_LAUNCHER.get());
                //output.accept(ModItems.END_ROD_DUCK_ARMOR.get());
            }).build());

    public PracticalPets(FMLJavaModLoadingContext context)
    {
        IEventBus modEventBus = context.getModEventBus();

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register the Deferred Register to the mod event bus so blocks get registered
        PPBlocks.REGISTRY.register(modEventBus);
        // Register the Deferred Register to the mod event bus so items get registered
        PPItems.REGISTRY.register(modEventBus);
        // Register the Deferred Register to the mod event bus so tabs get registered
        CREATIVE_MODE_TABS.register(modEventBus);

        PPSounds.REGISTRY.register(modEventBus);
        PPEntities.REGISTRY.register(modEventBus);
        PPMenus.REGISTRY.register(modEventBus);
        MinecraftForge.EVENT_BUS.register(PPEvents.class);

        //
        final DeferredRegister<Codec<? extends BiomeModifier>> biomeModifiers = DeferredRegister.create(ForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, MODID);
        biomeModifiers.register(modEventBus);
        biomeModifiers.register("add_pet_spawns", PPBiomeModifier::makeCodec);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

        // Register our mod's ForgeConfigSpec so that Forge can create and load the config file for us
        context.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
       //// Some common setup code
       //LOGGER.info("HELLO FROM COMMON SETUP");

       ModNetworking.register();
       //if (Config.logDirtBlock)
       //    LOGGER.info("DIRT BLOCK >> {}", ForgeRegistries.BLOCKS.getKey(Blocks.DIRT));

       //LOGGER.info(Config.magicNumberIntroduction + Config.magicNumber);

       //Config.items.forEach((item) -> LOGGER.info("ITEM >> {}", item.toString()));
    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {
        //if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS)
            //event.accept(ModItems.BANANA_PEEL);
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        // Do something when the server starts
        //LOGGER.info("HELLO from server starting");
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            MenuScreens.register(PPMenus.PRACTICAL_PET_MENU.get(), PracticalPetScreen::new);

            //ai generated:
            Minecraft.getInstance().getItemColors().register(
                    (stack, tintIndex) -> tintIndex == 0 ? ((DyeableLeatherItem) stack.getItem()).getColor(stack) : -1,
                    PPItems.LEATHER_DUCK_ARMOR.get(),
                    PPItems.PET_BOWTIE.get(),
                    PPItems.RUBBER_DUCKY_PET_HAT.get(),
                    PPItems.PET_CHEF_HAT.get(),
                    PPItems.PET_BACKPACK.get(),
                    PPItems.PET_END_ROD_LAUNCHER.get()
            );
        }
    }
}
