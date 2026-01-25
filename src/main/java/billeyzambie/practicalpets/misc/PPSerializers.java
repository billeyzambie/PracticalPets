package billeyzambie.practicalpets.misc;

import billeyzambie.practicalpets.entity.dinosaur.Pigeon;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class PPSerializers {
public static final DeferredRegister<EntityDataSerializer<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.Keys.ENTITY_DATA_SERIALIZERS, "practicalpets");

    public static final RegistryObject<EntityDataSerializer<Pigeon.MissionPhase>> PIGEON_MISSION_PHASE = REGISTRY.register("pigeon_mission_phase", () -> EntityDataSerializer.simpleEnum(Pigeon.MissionPhase.class));
}
