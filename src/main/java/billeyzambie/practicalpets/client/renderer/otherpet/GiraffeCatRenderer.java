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

public class GiraffeCatRenderer extends PracticalPetRenderer<GiraffeCat, GiraffeCatModel> {
    public GiraffeCatRenderer(EntityRendererProvider.Context context) {
        super(context, new GiraffeCatModel(context.bakeLayer(PPRenders.GIRAFFE_CAT)), 0.4f);
        this.addLayer(new PetBackStrapLayer<>(this, BACK_STRAP_TEXTURE));
    }

    private static final ResourceLocation[] GIRAFFE_TEXTURES = {
            new ResourceLocation(PracticalPets.MODID, "textures/entity/giraffe_cat/giraffe.png"),
            new ResourceLocation(PracticalPets.MODID, "textures/entity/giraffe_cat/giraffe1.png"),
            new ResourceLocation(PracticalPets.MODID, "textures/entity/giraffe_cat/giraffe2.png"),
            new ResourceLocation(PracticalPets.MODID, "textures/entity/giraffe_cat/giraffe3.png"),
            new ResourceLocation(PracticalPets.MODID, "textures/entity/giraffe_cat/giraffe4.png")
    };

    private static final ResourceLocation BACK_STRAP_TEXTURE =
            new ResourceLocation(PracticalPets.MODID, "textures/entity/giraffe_cat/back_strap.png");

    @Override
    public ResourceLocation getTextureLocation(GiraffeCat giraffecat) {
        return GIRAFFE_TEXTURES[giraffecat.getVariant()];
    }
}
