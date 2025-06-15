package billeyzambie.practicalpets;

import billeyzambie.practicalpets.client.ModModelLayers;
import billeyzambie.practicalpets.client.model.entity.dinosaur.BananaDuckArmorModel;
import billeyzambie.practicalpets.client.model.entity.dinosaur.BananaDuckModel;
import billeyzambie.practicalpets.client.model.entity.dinosaur.DuckModel;
import billeyzambie.practicalpets.client.model.entity.pet_equipment.AnniversaryPetHatModel;
import billeyzambie.practicalpets.client.model.entity.pet_equipment.PetBowtieModel;
import billeyzambie.practicalpets.client.renderer.dinosaur.BananaDuckRenderer;
import billeyzambie.practicalpets.client.renderer.dinosaur.DuckRenderer;
import billeyzambie.practicalpets.items.PetBowtie;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = PracticalPets.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class ClientListener {
    @SubscribeEvent
    public static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.BANANA_DUCK.get(), BananaDuckRenderer::new);
        event.registerEntityRenderer(ModEntities.DUCK.get(), DuckRenderer::new);
    }
    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ModModelLayers.BANANA_DUCK, BananaDuckModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.DUCK, DuckModel::createBodyLayer);

        event.registerLayerDefinition(ModModelLayers.BANANA_DUCK_ARMOR, BananaDuckArmorModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.PET_BOWTIE, PetBowtieModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.ANNIVERSARY_PET_HAT, AnniversaryPetHatModel::createBodyLayer);
    }
}