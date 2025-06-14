
package billeyzambie.practicalpets.client.renderer.dinosaur;

import billeyzambie.practicalpets.PracticalPets;
import billeyzambie.practicalpets.client.ModModelLayers;
import billeyzambie.practicalpets.client.layer.BananaDuckArmorLayer;
import billeyzambie.practicalpets.client.model.entity.dinosaur.BananaDuckModel;
import billeyzambie.practicalpets.client.renderer.PracticalPetRenderer;
import billeyzambie.practicalpets.entity.dinosaur.BananaDuck;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.HorseArmorLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

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
