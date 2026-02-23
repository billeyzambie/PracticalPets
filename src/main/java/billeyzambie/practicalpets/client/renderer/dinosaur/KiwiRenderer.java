package billeyzambie.practicalpets.client.renderer.dinosaur;

import billeyzambie.practicalpets.client.PPRenders;
import billeyzambie.practicalpets.client.layer.KiwiShearedLayer;
import billeyzambie.practicalpets.client.layer.PetBackStrapLayer;
import billeyzambie.practicalpets.client.model.entity.dinosaur.KiwiModel;
import billeyzambie.practicalpets.client.renderer.PracticalPetRenderer;
import billeyzambie.practicalpets.entity.dinosaur.Kiwi;
import billeyzambie.practicalpets.misc.PracticalPets;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class KiwiRenderer extends PracticalPetRenderer<Kiwi, KiwiModel> {
    public KiwiRenderer(EntityRendererProvider.Context context) {
        super(context, new KiwiModel(context.bakeLayer(PPRenders.KIWI)), 0.35f);
        this.addLayer(new KiwiShearedLayer(this));
        this.addLayer(new PetBackStrapLayer<>(this, BACK_STRAP_TEXTURE));
    }

    private static final ResourceLocation[] TEXTURES = {
            new ResourceLocation(PracticalPets.MODID, "textures/entity/kiwi/kiwi.png"),
            new ResourceLocation(PracticalPets.MODID, "textures/entity/kiwi/kiwi1.png"),
            new ResourceLocation(PracticalPets.MODID, "textures/entity/kiwi/kiwi2.png"),
            new ResourceLocation(PracticalPets.MODID, "textures/entity/kiwi/kiwi3.png"),
            new ResourceLocation(PracticalPets.MODID, "textures/entity/kiwi/kiwi4.png"),
            new ResourceLocation(PracticalPets.MODID, "textures/entity/kiwi/kiwi5.png"),
            new ResourceLocation(PracticalPets.MODID, "textures/entity/kiwi/kiwi6.png"),
            new ResourceLocation(PracticalPets.MODID, "textures/entity/kiwi/kiwi7.png"),
            new ResourceLocation(PracticalPets.MODID, "textures/entity/kiwi/kiwi8.png")
    };

    private static final ResourceLocation BACK_STRAP_TEXTURE =
            new ResourceLocation(PracticalPets.MODID, "textures/entity/kiwi/back_strap.png");

    @Override
    public @NotNull ResourceLocation getTextureLocation(Kiwi kiwi) {
        return TEXTURES[kiwi.getVariant() % TEXTURES.length];
    }
}
