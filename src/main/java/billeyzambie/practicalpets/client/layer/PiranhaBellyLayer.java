package billeyzambie.practicalpets.client.layer;

import billeyzambie.practicalpets.client.model.entity.fish.PiranhaModel;
import billeyzambie.practicalpets.entity.dinosaur.Kiwi;
import billeyzambie.practicalpets.entity.fish.Piranha;
import billeyzambie.practicalpets.misc.PracticalPets;
import billeyzambie.practicalpets.util.PPUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class PiranhaBellyLayer extends RenderLayer<Piranha, PiranhaModel> {
    public PiranhaBellyLayer(RenderLayerParent<Piranha, PiranhaModel> p_117346_) {
        super(p_117346_);
    }

    private static final ResourceLocation[] TEXTURES = {
            new ResourceLocation(PracticalPets.MODID, "textures/entity/piranha/belly.png"),
            new ResourceLocation(PracticalPets.MODID, "textures/entity/piranha/belly1.png")
    };

    @Override
    public void render(@NotNull PoseStack p_117349_, @NotNull MultiBufferSource p_117350_, int p_117351_, @NotNull Piranha piranha, float p_117353_, float p_117354_, float p_117355_, float p_117356_, float p_117357_, float p_117358_) {
        if (piranha.isInvisible())
            return;

        ResourceLocation texture = TEXTURES[piranha.getVariant() % TEXTURES.length];

        int bellyColor = piranha.getBellyColor();

        float red = PPUtil.getColorRed(bellyColor);
        float green = PPUtil.getColorGreen(bellyColor);
        float blue = PPUtil.getColorBlue(bellyColor);

        VertexConsumer vertexconsumer = p_117350_.getBuffer(RenderType.entityTranslucent(texture));
        this.getParentModel().renderToBuffer(p_117349_, vertexconsumer, p_117351_, LivingEntityRenderer.getOverlayCoords(piranha, 0.0F), red, green, blue, 1.0F);
    }
}