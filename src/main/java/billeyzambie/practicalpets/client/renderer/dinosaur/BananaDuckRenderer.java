
package billeyzambie.practicalpets.client.renderer.dinosaur;

import billeyzambie.practicalpets.misc.PracticalPets;
import billeyzambie.practicalpets.client.ModModelLayers;
import billeyzambie.practicalpets.client.layer.BananaDuckArmorLayer;
import billeyzambie.practicalpets.client.model.entity.dinosaur.BananaDuckModel;
import billeyzambie.practicalpets.client.renderer.PracticalPetRenderer;
import billeyzambie.practicalpets.entity.dinosaur.BananaDuck;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class BananaDuckRenderer extends PracticalPetRenderer<BananaDuck, BananaDuckModel> {
    public BananaDuckRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new BananaDuckModel(pContext.bakeLayer(ModModelLayers.BANANA_DUCK)), 0.35f);
        this.addLayer(new BananaDuckArmorLayer(this, pContext.getModelSet()));
    }

    ResourceLocation TEXTURE_LOCATION = new ResourceLocation(PracticalPets.MODID, "textures/entity/banana_duck/default.png");
    @Override
    public ResourceLocation getTextureLocation(BananaDuck pEntity) {
        return TEXTURE_LOCATION;
    }

}
