package billeyzambie.practicalpets.client.layer;

import billeyzambie.practicalpets.client.PPRenders;
import billeyzambie.practicalpets.client.model.entity.dinosaur.BananaDuckArmorModel;
import billeyzambie.practicalpets.client.model.entity.dinosaur.BananaDuckModel;
import billeyzambie.practicalpets.entity.dinosaur.BananaDuck;
import billeyzambie.practicalpets.items.DuckArmor;
import billeyzambie.practicalpets.items.DyeableDuckArmor;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BananaDuckArmorLayer extends RenderLayer<BananaDuck, BananaDuckModel> {

    private final BananaDuckModel model;

    public BananaDuckArmorLayer(RenderLayerParent<BananaDuck, BananaDuckModel> p_174496_, EntityModelSet p_174497_) {
        super(p_174496_);
        this.model = new BananaDuckArmorModel(p_174497_.bakeLayer(PPRenders.BANANA_DUCK_ARMOR));
    }

    public void render(PoseStack p_117032_, MultiBufferSource p_117033_, int p_117034_, BananaDuck p_117035_, float p_117036_, float p_117037_, float p_117038_, float p_117039_, float p_117040_, float p_117041_) {
        ItemStack itemstack = p_117035_.getBodyItem();
        if (itemstack.getItem() instanceof DuckArmor duckArmorItem) {
            this.model.setupAnim(p_117035_, p_117036_, p_117037_, p_117039_, p_117040_, p_117041_);
            this.getParentModel().copyPropertiesTo(this.model);
            float r;
            float g;
            float b;
            if (duckArmorItem instanceof DyeableDuckArmor dyeableDuckArmorItem) {
                int i = dyeableDuckArmorItem.getColor(itemstack);
                r = (float) (i >> 16 & 255) / 255.0F;
                g = (float) (i >> 8 & 255) / 255.0F;
                b = (float) (i & 255) / 255.0F;
            } else {
                r = 1.0F;
                g = 1.0F;
                b = 1.0F;
            }

            VertexConsumer vertexconsumer = p_117033_.getBuffer(RenderType.entityCutoutNoCull(duckArmorItem.getBananaDuckModelTexture()));
            this.model.renderToBuffer(p_117032_, vertexconsumer, p_117034_, OverlayTexture.NO_OVERLAY, r, g, b, 1.0F);
        }
    }
}