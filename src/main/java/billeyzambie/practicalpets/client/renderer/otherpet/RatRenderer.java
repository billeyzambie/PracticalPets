package billeyzambie.practicalpets.client.renderer.otherpet;

import billeyzambie.practicalpets.client.ModModelLayers;
import billeyzambie.practicalpets.client.layer.RatPatternLayer;
import billeyzambie.practicalpets.client.model.entity.otherpet.RatModel;
import billeyzambie.practicalpets.client.renderer.PracticalPetRenderer;
import billeyzambie.practicalpets.entity.otherpet.Rat;
import billeyzambie.practicalpets.misc.PracticalPets;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class RatRenderer extends PracticalPetRenderer<Rat, RatModel> {
    public RatRenderer(EntityRendererProvider.Context context) {
        super(context, new RatModel(context.bakeLayer(ModModelLayers.RAT)), 0.4f);
        this.addLayer(new RatPatternLayer(this));
    }

    private static final ResourceLocation ALBINO_TEXTURE =
            new ResourceLocation(PracticalPets.MODID, "textures/entity/rat/albino.png");

    private static final ResourceLocation[] BASE_TEXTURES = {
            new ResourceLocation(PracticalPets.MODID, "textures/entity/rat/rat.png"),
            new ResourceLocation(PracticalPets.MODID, "textures/entity/rat/rat1.png"),
            new ResourceLocation(PracticalPets.MODID, "textures/entity/rat/rat2.png"),
            new ResourceLocation(PracticalPets.MODID, "textures/entity/rat/rat3.png"),
            new ResourceLocation(PracticalPets.MODID, "textures/entity/rat/rat4.png"),
    };

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Rat rat) {
        if (rat.getIsAlbino())
            return ALBINO_TEXTURE;

        return BASE_TEXTURES[rat.variant()];
    }
}
