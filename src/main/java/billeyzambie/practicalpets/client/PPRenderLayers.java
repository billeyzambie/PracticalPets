package billeyzambie.practicalpets.client;

import billeyzambie.practicalpets.client.model.entity.dinosaur.*;
import billeyzambie.practicalpets.client.model.entity.fish.PiranhaModel;
import billeyzambie.practicalpets.client.model.entity.otherpet.GiraffeCatModel;
import billeyzambie.practicalpets.client.model.entity.otherpet.GiraffeCatNeckPieceModel;
import billeyzambie.practicalpets.client.model.entity.otherpet.RatModel;
import billeyzambie.practicalpets.client.model.entity.otherpet.StickBugModel;
import billeyzambie.practicalpets.client.model.entity.pet_equipment.*;
import billeyzambie.practicalpets.client.renderer.dinosaur.BananaDuckRenderer;
import billeyzambie.practicalpets.client.renderer.dinosaur.DuckRenderer;
import billeyzambie.practicalpets.client.renderer.dinosaur.KiwiRenderer;
import billeyzambie.practicalpets.client.renderer.dinosaur.PigeonRenderer;
import billeyzambie.practicalpets.client.renderer.fish.PiranhaRenderer;
import billeyzambie.practicalpets.client.renderer.other.NothingRenderer;
import billeyzambie.practicalpets.client.renderer.other.PetEndRodProjectileRenderer;
import billeyzambie.practicalpets.client.renderer.otherpet.GiraffeCatRenderer;
import billeyzambie.practicalpets.client.renderer.otherpet.RatRenderer;
import billeyzambie.practicalpets.client.renderer.otherpet.StickBugRenderer;
import billeyzambie.practicalpets.misc.PPEntities;
import billeyzambie.practicalpets.misc.PracticalPets;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = PracticalPets.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class PPRenderLayers {
    public static final ModelLayerLocation PET_END_ROD_PROJECTILE = new ModelLayerLocation(
            new ResourceLocation(PracticalPets.MODID, "pet_end_rod_projectile_layer"), "main"
    );

    public static final ModelLayerLocation BANANA_DUCK = new ModelLayerLocation(
            new ResourceLocation(PracticalPets.MODID, "banana_duck_layer"), "main"
    );
    public static final ModelLayerLocation DUCK = new ModelLayerLocation(
            new ResourceLocation(PracticalPets.MODID, "duck_layer"), "main"
    );
    public static final ModelLayerLocation RAT = new ModelLayerLocation(
            new ResourceLocation(PracticalPets.MODID, "rat_layer"), "main"
    );
    public static final ModelLayerLocation PIGEON = new ModelLayerLocation(
            new ResourceLocation(PracticalPets.MODID, "pigeon_layer"), "main"
    );
    public static final ModelLayerLocation STICK_BUG = new ModelLayerLocation(
            new ResourceLocation(PracticalPets.MODID, "stick_bug_layer"), "main"
    );
    public static final ModelLayerLocation GIRAFFE_CAT = new ModelLayerLocation(
            new ResourceLocation(PracticalPets.MODID, "giraffe_cat_layer"), "main"
    );
    public static final ModelLayerLocation KIWI = new ModelLayerLocation(
            new ResourceLocation(PracticalPets.MODID, "kiwi_layer"), "main"
    );
    public static final ModelLayerLocation PIRANHA = new ModelLayerLocation(
            new ResourceLocation(PracticalPets.MODID, "piranha_layer"), "main"
    );

    public static final ModelLayerLocation GIRAFFE_CAT_NECK_PIECE = new ModelLayerLocation(
            new ResourceLocation(PracticalPets.MODID, "giraffe_cat_neck_piece_layer"), "main"
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
    public static final ModelLayerLocation PET_CHEF_HAT = new ModelLayerLocation(
            new ResourceLocation(PracticalPets.MODID, "pet_chef_hat_layer"), "main"
    );
    public static final ModelLayerLocation PET_BACKPACK = new ModelLayerLocation(
            new ResourceLocation(PracticalPets.MODID, "pet_backpack_layer"), "main"
    );
    public static final ModelLayerLocation PET_END_ROD_LAUNCHER = new ModelLayerLocation(
            new ResourceLocation(PracticalPets.MODID, "pet_end_rod_launcher_layer"), "main"
    );
    public static final ModelLayerLocation PET_HAT = new ModelLayerLocation(
            new ResourceLocation(PracticalPets.MODID, "pet_hat_layer"), "main"
    );

    @SubscribeEvent
    public static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(PPEntities.PET_END_ROD_PROJECTILE.get(), PetEndRodProjectileRenderer::new);
        event.registerEntityRenderer(PPEntities.THROWN_PET_CARRIER.get(), NothingRenderer::new);

        event.registerEntityRenderer(PPEntities.BANANA_DUCK.get(), BananaDuckRenderer::new);
        event.registerEntityRenderer(PPEntities.DUCK.get(), DuckRenderer::new);
        event.registerEntityRenderer(PPEntities.RAT.get(), RatRenderer::new);
        event.registerEntityRenderer(PPEntities.PIGEON.get(), PigeonRenderer::new);
        event.registerEntityRenderer(PPEntities.STICK_BUG.get(), StickBugRenderer::new);
        event.registerEntityRenderer(PPEntities.GIRAFFE_CAT.get(), GiraffeCatRenderer::new);
        event.registerEntityRenderer(PPEntities.KIWI.get(), KiwiRenderer::new);
        event.registerEntityRenderer(PPEntities.PIRANHA.get(), PiranhaRenderer::new);
    }
    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(PPRenderLayers.PET_END_ROD_PROJECTILE, PetEndRodLauncherModel::createBodyLayer);

        event.registerLayerDefinition(PPRenderLayers.BANANA_DUCK, BananaDuckModel::createBodyLayer);
        event.registerLayerDefinition(PPRenderLayers.DUCK, DuckModel::createBodyLayer);
        event.registerLayerDefinition(PPRenderLayers.RAT, RatModel::createBodyLayer);
        event.registerLayerDefinition(PPRenderLayers.PIGEON, PigeonModel::createBodyLayer);
        event.registerLayerDefinition(PPRenderLayers.STICK_BUG, StickBugModel::createBodyLayer);
        event.registerLayerDefinition(PPRenderLayers.GIRAFFE_CAT, GiraffeCatModel::createBodyLayer);
        event.registerLayerDefinition(PPRenderLayers.KIWI, KiwiModel::createBodyLayer);
        event.registerLayerDefinition(PPRenderLayers.PIRANHA, PiranhaModel::createBodyLayer);

        event.registerLayerDefinition(PPRenderLayers.GIRAFFE_CAT_NECK_PIECE, GiraffeCatNeckPieceModel::createBodyLayer);

        event.registerLayerDefinition(PPRenderLayers.BANANA_DUCK_ARMOR, BananaDuckArmorModel::createBodyLayer);
        event.registerLayerDefinition(PPRenderLayers.DUCK_ARMOR, DuckArmorModel::createBodyLayer);
        event.registerLayerDefinition(PPRenderLayers.PET_BOWTIE, PetBowtieModel::createBodyLayer);
        event.registerLayerDefinition(PPRenderLayers.ANNIVERSARY_PET_HAT, AnniversaryPetHatModel::createBodyLayer);
        event.registerLayerDefinition(PPRenderLayers.RUBBER_DUCKY_PET_HAT, RubberDuckyPetHatModel::createBodyLayer);
        event.registerLayerDefinition(PPRenderLayers.PET_CHEF_HAT, PetChefHatModel::createBodyLayer);
        event.registerLayerDefinition(PPRenderLayers.PET_BACKPACK, PetBackpackModel::createBodyLayer);
        event.registerLayerDefinition(PPRenderLayers.PET_END_ROD_LAUNCHER, PetEndRodLauncherModel::createBodyLayer);
        event.registerLayerDefinition(PPRenderLayers.PET_HAT, PlainPetHatModel::createBodyLayer);
    }
}
