
package billeyzambie.practicalpets.client.renderer.dinosaur;

import billeyzambie.practicalpets.PracticalPets;
import billeyzambie.practicalpets.client.ModModelLayers;
import billeyzambie.practicalpets.client.layer.BananaDuckArmorLayer;
import billeyzambie.practicalpets.client.layer.DuckArmorLayer;
import billeyzambie.practicalpets.client.model.entity.dinosaur.BananaDuckModel;
import billeyzambie.practicalpets.client.model.entity.dinosaur.DuckModel;
import billeyzambie.practicalpets.client.renderer.PracticalPetRenderer;
import billeyzambie.practicalpets.entity.dinosaur.BananaDuck;
import billeyzambie.practicalpets.entity.dinosaur.Duck;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.HorseArmorLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class DuckRenderer extends PracticalPetRenderer<Duck, DuckModel> {
    public DuckRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new DuckModel(pContext.bakeLayer(ModModelLayers.DUCK)), 0.35f);
        this.addLayer(new DuckArmorLayer(this, pContext.getModelSet()));
    }

    ResourceLocation TEXTURE_LOCATION = new ResourceLocation(PracticalPets.MODID, "textures/entity/duck/pekin.png");

    @Override
    public ResourceLocation getTextureLocation(Duck pEntity) {
        return TEXTURE_LOCATION;
    }
}
