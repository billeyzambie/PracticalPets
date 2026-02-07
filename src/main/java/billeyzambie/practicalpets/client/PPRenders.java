package billeyzambie.practicalpets.client;

import billeyzambie.practicalpets.client.model.entity.dinosaur.*;
import billeyzambie.practicalpets.client.model.entity.otherpet.GiraffeCatModel;
import billeyzambie.practicalpets.client.model.entity.otherpet.GiraffeCatNeckPieceModel;
import billeyzambie.practicalpets.client.model.entity.otherpet.RatModel;
import billeyzambie.practicalpets.client.model.entity.otherpet.StickBugModel;
import billeyzambie.practicalpets.client.model.entity.pet_equipment.*;
import billeyzambie.practicalpets.client.renderer.dinosaur.BananaDuckRenderer;
import billeyzambie.practicalpets.client.renderer.dinosaur.DuckRenderer;
import billeyzambie.practicalpets.client.renderer.dinosaur.PigeonRenderer;
import billeyzambie.practicalpets.client.renderer.other.PetEndRodProjectileRenderer;
import billeyzambie.practicalpets.client.renderer.otherpet.GiraffeCatRenderer;
import billeyzambie.practicalpets.client.renderer.otherpet.RatRenderer;
import billeyzambie.practicalpets.client.renderer.otherpet.StickBugRenderer;
import billeyzambie.practicalpets.entity.other.PetEndRodProjectile;
import billeyzambie.practicalpets.misc.PPEntities;
import billeyzambie.practicalpets.misc.PracticalPets;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = PracticalPets.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class PPRenders {
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

    @SubscribeEvent
    public static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(PPEntities.PET_END_ROD_PROJECTILE.get(), PetEndRodProjectileRenderer::new);

        event.registerEntityRenderer(PPEntities.BANANA_DUCK.get(), BananaDuckRenderer::new);
        event.registerEntityRenderer(PPEntities.DUCK.get(), DuckRenderer::new);
        event.registerEntityRenderer(PPEntities.RAT.get(), RatRenderer::new);
        event.registerEntityRenderer(PPEntities.PIGEON.get(), PigeonRenderer::new);
        event.registerEntityRenderer(PPEntities.STICK_BUG.get(), StickBugRenderer::new);
        event.registerEntityRenderer(PPEntities.GIRAFFE_CAT.get(), GiraffeCatRenderer::new);
    }
    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(PPRenders.PET_END_ROD_PROJECTILE, PetEndRodLauncherModel::createBodyLayer);

        event.registerLayerDefinition(PPRenders.BANANA_DUCK, BananaDuckModel::createBodyLayer);
        event.registerLayerDefinition(PPRenders.DUCK, DuckModel::createBodyLayer);
        event.registerLayerDefinition(PPRenders.RAT, RatModel::createBodyLayer);
        event.registerLayerDefinition(PPRenders.PIGEON, PigeonModel::createBodyLayer);
        event.registerLayerDefinition(PPRenders.STICK_BUG, StickBugModel::createBodyLayer);
        event.registerLayerDefinition(PPRenders.GIRAFFE_CAT, GiraffeCatModel::createBodyLayer);

        event.registerLayerDefinition(PPRenders.GIRAFFE_CAT_NECK_PIECE, GiraffeCatNeckPieceModel::createBodyLayer);

        event.registerLayerDefinition(PPRenders.BANANA_DUCK_ARMOR, BananaDuckArmorModel::createBodyLayer);
        event.registerLayerDefinition(PPRenders.DUCK_ARMOR, DuckArmorModel::createBodyLayer);
        event.registerLayerDefinition(PPRenders.PET_BOWTIE, PetBowtieModel::createBodyLayer);
        event.registerLayerDefinition(PPRenders.ANNIVERSARY_PET_HAT, AnniversaryPetHatModel::createBodyLayer);
        event.registerLayerDefinition(PPRenders.RUBBER_DUCKY_PET_HAT, RubberDuckyPetHatModel::createBodyLayer);
        event.registerLayerDefinition(PPRenders.PET_CHEF_HAT, PetChefHatModel::createBodyLayer);
        event.registerLayerDefinition(PPRenders.PET_BACKPACK, PetBackpackModel::createBodyLayer);
        event.registerLayerDefinition(PPRenders.PET_END_ROD_LAUNCHER, PetEndRodLauncherModel::createBodyLayer);
    }
}
