package billeyzambie.practicalpets.client.layer;

import billeyzambie.practicalpets.client.model.entity.otherpet.RatModel;
import billeyzambie.practicalpets.entity.otherpet.Rat;
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

public class RatPatternLayer extends RenderLayer<Rat, RatModel> {
    private static final ResourceLocation[] PATTERN_TEXTURES = {
            new ResourceLocation(PracticalPets.MODID, "textures/entity/rat/rata.png"),
            new ResourceLocation(PracticalPets.MODID, "textures/entity/rat/rat1a.png"),
            new ResourceLocation(PracticalPets.MODID, "textures/entity/rat/rat2a.png"),
            new ResourceLocation(PracticalPets.MODID, "textures/entity/rat/rat3a.png"),
            new ResourceLocation(PracticalPets.MODID, "textures/entity/rat/rat4a.png"),
            new ResourceLocation(PracticalPets.MODID, "textures/entity/rat/ratb.png"),
            new ResourceLocation(PracticalPets.MODID, "textures/entity/rat/rat1b.png"),
            new ResourceLocation(PracticalPets.MODID, "textures/entity/rat/rat2b.png"),
            new ResourceLocation(PracticalPets.MODID, "textures/entity/rat/rat3b.png"),
            new ResourceLocation(PracticalPets.MODID, "textures/entity/rat/rat4b.png"),
            new ResourceLocation(PracticalPets.MODID, "textures/entity/rat/ratc.png"),
            new ResourceLocation(PracticalPets.MODID, "textures/entity/rat/rat1c.png"),
            new ResourceLocation(PracticalPets.MODID, "textures/entity/rat/rat2c.png"),
            new ResourceLocation(PracticalPets.MODID, "textures/entity/rat/rat3c.png"),
            new ResourceLocation(PracticalPets.MODID, "textures/entity/rat/rat4c.png"),
            new ResourceLocation(PracticalPets.MODID, "textures/entity/rat/ratd.png"),
            new ResourceLocation(PracticalPets.MODID, "textures/entity/rat/rat1d.png"),
            new ResourceLocation(PracticalPets.MODID, "textures/entity/rat/rat2d.png"),
            new ResourceLocation(PracticalPets.MODID, "textures/entity/rat/rat3d.png"),
            new ResourceLocation(PracticalPets.MODID, "textures/entity/rat/rat4d.png")
    };

    public RatPatternLayer(RenderLayerParent<Rat, RatModel> p_117045_) {
        super(p_117045_);
    }

    @Override
    public void render(@NotNull PoseStack p_117058_, @NotNull MultiBufferSource p_117059_, int p_117060_, Rat rat, float p_117062_, float p_117063_, float p_117064_, float p_117065_, float p_117066_, float p_117067_) {
        if (rat.getIsAlbino() || rat.isInvisible())
            return;

        int textureIndex = rat.getPatternType() * Rat.COLOR_TYPE_COUNT + rat.getPatternColor();
        ResourceLocation texture = PATTERN_TEXTURES[textureIndex];

        VertexConsumer vertexconsumer = p_117059_.getBuffer(RenderType.entityTranslucent(texture));
        this.getParentModel().renderToBuffer(p_117058_, vertexconsumer, p_117060_, LivingEntityRenderer.getOverlayCoords(rat, 0.0F), 1.0F, 1.0F, 1.0F, 1.0F);

    }
}
