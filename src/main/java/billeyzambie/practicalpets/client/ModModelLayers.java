package billeyzambie.practicalpets.client;

import billeyzambie.practicalpets.client.model.entity.dinosaur.BananaDuckArmorModel;
import billeyzambie.practicalpets.client.model.entity.dinosaur.BananaDuckModel;
import billeyzambie.practicalpets.client.model.entity.dinosaur.DuckArmorModel;
import billeyzambie.practicalpets.client.model.entity.dinosaur.DuckModel;
import billeyzambie.practicalpets.client.model.entity.otherpet.RatModel;
import billeyzambie.practicalpets.client.model.entity.pet_equipment.AnniversaryPetHatModel;
import billeyzambie.practicalpets.client.model.entity.pet_equipment.PetBowtieModel;
import billeyzambie.practicalpets.client.model.entity.pet_equipment.RubberDuckyPetHatModel;
import billeyzambie.practicalpets.client.renderer.dinosaur.BananaDuckRenderer;
import billeyzambie.practicalpets.client.renderer.dinosaur.DuckRenderer;
import billeyzambie.practicalpets.client.renderer.otherpet.RatRenderer;
import billeyzambie.practicalpets.misc.PPEntities;
import billeyzambie.practicalpets.misc.PracticalPets;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = PracticalPets.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModModelLayers {
    public static final ModelLayerLocation BANANA_DUCK = new ModelLayerLocation(
            new ResourceLocation(PracticalPets.MODID, "banana_duck_layer"), "main"
    );
    public static final ModelLayerLocation DUCK = new ModelLayerLocation(
            new ResourceLocation(PracticalPets.MODID, "duck_layer"), "main"
    );
    public static final ModelLayerLocation RAT = new ModelLayerLocation(
            new ResourceLocation(PracticalPets.MODID, "rat_layer"), "main"
    );

    public static final ModelLayerLocation BANANA_DUCK_ARMOR = new ModelLayerLocation(
            new ResourceLocation(PracticalPets.MODID, "banana_duck_armor_layer"), "main"
    );

    public static final ModelLayerLocation DUCK_ARMOR = new ModelLayerLocation(
            new ResourceLocation(PracticalPets.MODID, "duck_armor_layer"), "main"
    );

    public static final ModelLayerLocation PET_BOWTIE = new ModelLayerLocation(
            new ResourceLocation(PracticalPets.MODID, "pet_bowtie_layer"), "main"
    );
    public static final ModelLayerLocation ANNIVERSARY_PET_HAT = new ModelLayerLocation(
            new ResourceLocation(PracticalPets.MODID, "anniversary_pet_hat_layer"), "main"
    );
    public static final ModelLayerLocation RUBBER_DUCKY_PET_HAT = new ModelLayerLocation(
            new ResourceLocation(PracticalPets.MODID, "rubber_ducky_pet_hat_layer"), "main"
    );

    @SubscribeEvent
    public static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(PPEntities.BANANA_DUCK.get(), BananaDuckRenderer::new);
        event.registerEntityRenderer(PPEntities.DUCK.get(), DuckRenderer::new);
        event.registerEntityRenderer(PPEntities.RAT.get(), RatRenderer::new);
    }
    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ModModelLayers.BANANA_DUCK, BananaDuckModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.DUCK, DuckModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.RAT, RatModel::createBodyLayer);

        event.registerLayerDefinition(ModModelLayers.BANANA_DUCK_ARMOR, BananaDuckArmorModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.DUCK_ARMOR, DuckArmorModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.PET_BOWTIE, PetBowtieModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.ANNIVERSARY_PET_HAT, AnniversaryPetHatModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.RUBBER_DUCKY_PET_HAT, RubberDuckyPetHatModel::createBodyLayer);
    }
}
