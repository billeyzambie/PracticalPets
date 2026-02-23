package billeyzambie.practicalpets.misc;

import billeyzambie.practicalpets.entity.dinosaur.Kiwi;
import billeyzambie.practicalpets.entity.dinosaur.Pigeon;
import billeyzambie.practicalpets.entity.otherpet.GiraffeCat;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class PPSerializers {
    public static final DeferredRegister<EntityDataSerializer<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.Keys.ENTITY_DATA_SERIALIZERS, PracticalPets.MODID);

    public static final RegistryObject<EntityDataSerializer<Pigeon.MissionPhase>> PIGEON_MISSION_PHASE = REGISTRY.register("pigeon_mission_phase", () -> EntityDataSerializer.simpleEnum(Pigeon.MissionPhase.class));

    public static final RegistryObject<EntityDataSerializer<GiraffeCat.CurrentAbility>> GIRAFFE_CAT_ABILITY = REGISTRY.register("giraffe_cat_ability", () -> EntityDataSerializer.simpleEnum(GiraffeCat.CurrentAbility.class));

    public static final RegistryObject<EntityDataSerializer<Kiwi.ShearedState>> KIWI_SHEARED_STATE = REGISTRY.register("kiwi_sheared_state", () -> EntityDataSerializer.simpleEnum(Kiwi.ShearedState.class));
}
