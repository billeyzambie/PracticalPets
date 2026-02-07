package billeyzambie.practicalpets.client.renderer.otherpet;

import billeyzambie.practicalpets.client.PPRenders;
import billeyzambie.practicalpets.client.layer.PetBackStrapLayer;
import billeyzambie.practicalpets.client.model.entity.otherpet.GiraffeCatModel;
import billeyzambie.practicalpets.client.model.entity.otherpet.GiraffeCatModel;
import billeyzambie.practicalpets.client.renderer.PracticalPetRenderer;
import billeyzambie.practicalpets.entity.otherpet.GiraffeCat;
import billeyzambie.practicalpets.entity.otherpet.GiraffeCat;
import billeyzambie.practicalpets.misc.PracticalPets;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class GiraffeCatRenderer extends PracticalPetRenderer<GiraffeCat, GiraffeCatModel> {
    public GiraffeCatRenderer(EntityRendererProvider.Context context) {
        super(context, new GiraffeCatModel(context.bakeLayer(PPRenders.GIRAFFE_CAT)), 0.5f);
        this.addLayer(new PetBackStrapLayer<>(this, BACK_STRAP_TEXTURE));
    }

    private static final ResourceLocation[] GIRAFFE_TEXTURES = {
            new ResourceLocation(PracticalPets.MODID, "textures/entity/giraffe_cat/giraffe.png"),
            new ResourceLocation(PracticalPets.MODID, "textures/entity/giraffe_cat/giraffe1.png"),
            new ResourceLocation(PracticalPets.MODID, "textures/entity/giraffe_cat/giraffe2.png"),
            new ResourceLocation(PracticalPets.MODID, "textures/entity/giraffe_cat/giraffe3.png"),
            new ResourceLocation(PracticalPets.MODID, "textures/entity/giraffe_cat/giraffe4.png")
    };

    private static final ResourceLocation[] CAT_TEXTURES = {
            new ResourceLocation(PracticalPets.MODID, "textures/entity/giraffe_cat/white.png"),
            new ResourceLocation(PracticalPets.MODID, "textures/entity/giraffe_cat/tuxedo.png"),
            new ResourceLocation(PracticalPets.MODID, "textures/entity/giraffe_cat/redtabby.png"),
            new ResourceLocation(PracticalPets.MODID, "textures/entity/giraffe_cat/siamesecat.png"),
            new ResourceLocation(PracticalPets.MODID, "textures/entity/giraffe_cat/britishshorthair.png"),
            new ResourceLocation(PracticalPets.MODID, "textures/entity/giraffe_cat/calico.png"),
            new ResourceLocation(PracticalPets.MODID, "textures/entity/giraffe_cat/persian.png"),
            new ResourceLocation(PracticalPets.MODID, "textures/entity/giraffe_cat/ragdoll.png"),
            new ResourceLocation(PracticalPets.MODID, "textures/entity/giraffe_cat/tabby.png"),
            new ResourceLocation(PracticalPets.MODID, "textures/entity/giraffe_cat/jellie.png"),
            new ResourceLocation(PracticalPets.MODID, "textures/entity/giraffe_cat/allblackcat.png")
    };

    private static final ResourceLocation BACK_STRAP_TEXTURE =
            new ResourceLocation(PracticalPets.MODID, "textures/entity/giraffe_cat/back_strap.png");

    @Override
    public @NotNull ResourceLocation getTextureLocation(GiraffeCat giraffecat) {
        return giraffecat.isCatHybrid()
                ? CAT_TEXTURES[giraffecat.getVariant() % CAT_TEXTURES.length]
                : GIRAFFE_TEXTURES[giraffecat.getVariant() % GIRAFFE_TEXTURES.length];
    }
}
