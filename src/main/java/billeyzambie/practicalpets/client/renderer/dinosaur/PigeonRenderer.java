package billeyzambie.practicalpets.client.renderer.dinosaur;

import billeyzambie.practicalpets.client.PPRenders;
import billeyzambie.practicalpets.client.layer.PetBackStrapLayer;
import billeyzambie.practicalpets.client.model.entity.dinosaur.PigeonModel;
import billeyzambie.practicalpets.client.renderer.PracticalPetRenderer;
import billeyzambie.practicalpets.entity.dinosaur.Pigeon;
import billeyzambie.practicalpets.misc.PracticalPets;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class PigeonRenderer extends PracticalPetRenderer<Pigeon, PigeonModel> {
    public PigeonRenderer(EntityRendererProvider.Context context) {
        super(context, new PigeonModel(context.bakeLayer(PPRenders.PIGEON)), 0.25f);
        this.addLayer(new PetBackStrapLayer<>(this, BACK_STRAP_TEXTURE));
    }

    private static final ResourceLocation BACK_STRAP_TEXTURE = new ResourceLocation(PracticalPets.MODID, "textures/entity/pigeon/back_strap.png");

    private static final ResourceLocation[] TEXTURES = {
            new ResourceLocation(PracticalPets.MODID, "textures/entity/pigeon/pigeon.png"),
            new ResourceLocation(PracticalPets.MODID, "textures/entity/pigeon/pigeon1.png"),
            new ResourceLocation(PracticalPets.MODID, "textures/entity/pigeon/pigeon2.png"),
            new ResourceLocation(PracticalPets.MODID, "textures/entity/pigeon/pigeon3.png"),
            new ResourceLocation(PracticalPets.MODID, "textures/entity/pigeon/pigeon4.png"),
            new ResourceLocation(PracticalPets.MODID, "textures/entity/pigeon/pigeon5.png"),
            new ResourceLocation(PracticalPets.MODID, "textures/entity/pigeon/pigeon6.png")
    };

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Pigeon pigeon) {
        return TEXTURES[pigeon.getVariant() % TEXTURES.length];
    }
}
