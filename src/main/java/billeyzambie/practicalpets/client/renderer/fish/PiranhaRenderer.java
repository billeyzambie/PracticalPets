package billeyzambie.practicalpets.client.renderer.fish;

import billeyzambie.practicalpets.client.PPRenderLayers;
import billeyzambie.practicalpets.client.layer.PiranhaBellyLayer;
import billeyzambie.practicalpets.client.model.entity.fish.PiranhaModel;
import billeyzambie.practicalpets.client.renderer.PracticalPetRenderer;
import billeyzambie.practicalpets.entity.fish.Piranha;
import billeyzambie.practicalpets.misc.PracticalPets;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class PiranhaRenderer extends PracticalPetRenderer<Piranha, PiranhaModel> {
    public PiranhaRenderer(EntityRendererProvider.Context context) {
        super(context, new PiranhaModel(context.bakeLayer(PPRenderLayers.PIRANHA)), 0.3f);
        this.addLayer(new PiranhaBellyLayer(this));
    }

    private static final ResourceLocation[] TEXTURES = {
            new ResourceLocation(PracticalPets.MODID, "textures/entity/piranha/piranha.png"),
            new ResourceLocation(PracticalPets.MODID, "textures/entity/piranha/piranha1.png")
    };

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Piranha piranha) {
        return TEXTURES[piranha.getVariant() % TEXTURES.length];
    }
}
