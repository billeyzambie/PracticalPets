package billeyzambie.practicalpets;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.eventbus.api.IEventBus;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> REGISTRY = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, "practicalpets");

    public static final RegistryObject<SoundEvent> BANANA_SLIP_SOUND = REGISTRY.register("banana_slip",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("practicalpets", "banana_slip"))
    );

    public static final RegistryObject<SoundEvent> BANANA_DUCK_AMBIENT = REGISTRY.register("banana_duck_ambient",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("practicalpets", "banana_duck_ambient")));

    public static final RegistryObject<SoundEvent> BANANA_DUCK_DEATH = REGISTRY.register("banana_duck_death",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("practicalpets", "banana_duck_death")));

    public static final RegistryObject<SoundEvent> DUCK_AMBIENT = REGISTRY.register("duck_ambient",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("practicalpets", "duck_ambient")));

    public static final RegistryObject<SoundEvent> DUCK_HURT = REGISTRY.register("duck_hurt",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("practicalpets", "duck_hurt")));

    public static final RegistryObject<SoundEvent> DUCK_DEATH = REGISTRY.register("duck_death",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("practicalpets", "duck_death")));

    public static final RegistryObject<SoundEvent> PET_LEVEL_UP = REGISTRY.register("pet_level_up",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("practicalpets", "pet_level_up")));

    public static void register(IEventBus eventBus) {
        REGISTRY.register(eventBus);
    }
}
