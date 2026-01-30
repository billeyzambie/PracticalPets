package billeyzambie.practicalpets.client.renderer.otherpet;

import billeyzambie.practicalpets.client.PPRenders;
import billeyzambie.practicalpets.client.layer.PetBackStrapLayer;
import billeyzambie.practicalpets.client.model.entity.otherpet.StickBugModel;
import billeyzambie.practicalpets.client.renderer.PracticalPetRenderer;
import billeyzambie.practicalpets.entity.otherpet.StickBug;
import billeyzambie.practicalpets.misc.PracticalPets;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class StickBugRenderer extends PracticalPetRenderer<StickBug, StickBugModel> {
    public StickBugRenderer(EntityRendererProvider.Context context) {
        super(context, new StickBugModel(context.bakeLayer(PPRenders.STICK_BUG)), 0.4f);
        this.addLayer(new PetBackStrapLayer<>(this, BACK_STRAP_TEXTURE));
    }

    private static final ResourceLocation[] TEXTURES = {
            new ResourceLocation(PracticalPets.MODID, "textures/entity/stick_bug/jungle.png"),
            new ResourceLocation(PracticalPets.MODID, "textures/entity/stick_bug/acacia.png"),
            new ResourceLocation(PracticalPets.MODID, "textures/entity/stick_bug/spruce.png"),
            new ResourceLocation(PracticalPets.MODID, "textures/entity/stick_bug/cherry.png")
    };

    private static final ResourceLocation BACK_STRAP_TEXTURE =
            new ResourceLocation(PracticalPets.MODID, "textures/entity/stick_bug/back_strap.png");

    @Override
    public ResourceLocation getTextureLocation(StickBug stickBug) {
        return TEXTURES[stickBug.getVariant()];
    }
}
