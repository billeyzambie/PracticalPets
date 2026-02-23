package billeyzambie.practicalpets.client.layer;

import billeyzambie.practicalpets.client.model.entity.dinosaur.KiwiModel;
import billeyzambie.practicalpets.entity.dinosaur.Kiwi;
import billeyzambie.practicalpets.misc.PracticalPets;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class KiwiShearedLayer extends RenderLayer<Kiwi, KiwiModel> {
    public KiwiShearedLayer(RenderLayerParent<Kiwi, KiwiModel> p_117346_) {
        super(p_117346_);
    }

    private static final ResourceLocation SHEARED_TEXTURE = new ResourceLocation(
            PracticalPets.MODID,
            "textures/entity/kiwi/sheared.png"
    );

    private static final ResourceLocation INTERMEDIATE_TEXTURE = new ResourceLocation(
            PracticalPets.MODID,
            "textures/entity/kiwi/sheared2.png"
    );

    @Override
    public void render(@NotNull PoseStack p_117349_, @NotNull MultiBufferSource p_117350_, int p_117351_, @NotNull Kiwi kiwi, float p_117353_, float p_117354_, float p_117355_, float p_117356_, float p_117357_, float p_117358_) {
        Kiwi.ShearedState shearedState = kiwi.getShearedState();
        if (kiwi.isInvisible() || shearedState == Kiwi.ShearedState.SHEARABLE)
            return;

        ResourceLocation texture = shearedState == Kiwi.ShearedState.SHEARED ? SHEARED_TEXTURE : INTERMEDIATE_TEXTURE;

        VertexConsumer vertexconsumer = p_117350_.getBuffer(RenderType.entityTranslucent(texture));
        this.getParentModel().renderToBuffer(p_117349_, vertexconsumer, p_117351_, LivingEntityRenderer.getOverlayCoords(kiwi, 0.0F), 1.0F, 1.0F, 1.0F, 1.0F);
    }
}
