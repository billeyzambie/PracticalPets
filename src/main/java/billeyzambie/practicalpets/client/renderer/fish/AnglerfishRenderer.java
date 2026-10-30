package billeyzambie.practicalpets.client.renderer.fish;

import billeyzambie.practicalpets.client.PPRenderLayers;
import billeyzambie.practicalpets.client.model.entity.fish.AnglerfishModel;
import billeyzambie.practicalpets.client.renderer.PracticalPetRenderer;
import billeyzambie.practicalpets.entity.fish.Anglerfish;
import billeyzambie.practicalpets.misc.PracticalPets;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class AnglerfishRenderer extends PracticalPetRenderer<Anglerfish, AnglerfishModel> {
    public AnglerfishRenderer(EntityRendererProvider.Context context) {
        super(context, new AnglerfishModel(context.bakeLayer(PPRenderLayers.ANGLERFISH)), 0.4f);
    }

    private static final ResourceLocation[] TEXTURES = {
            new ResourceLocation(PracticalPets.MODID, "textures/entity/anglerfish/brown.png"),
            new ResourceLocation(PracticalPets.MODID, "textures/entity/anglerfish/cyan.png"),
            new ResourceLocation(PracticalPets.MODID, "textures/entity/anglerfish/purple.png"),
            new ResourceLocation(PracticalPets.MODID, "textures/entity/anglerfish/golden.png"),
            new ResourceLocation(PracticalPets.MODID, "textures/entity/anglerfish/white.png"),
            new ResourceLocation(PracticalPets.MODID, "textures/entity/anglerfish/black.png"),
            new ResourceLocation(PracticalPets.MODID, "textures/entity/anglerfish/green.png"),
            new ResourceLocation(PracticalPets.MODID, "textures/entity/anglerfish/blue.png")
    };

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Anglerfish anglerfish) {
        return TEXTURES[anglerfish.getVariant() % TEXTURES.length];
    }
}
