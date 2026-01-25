package billeyzambie.practicalpets.client.layer;

import billeyzambie.animationcontrollers.PracticalPetModel;
import billeyzambie.practicalpets.entity.PracticalPet;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class PetBackStrapLayer<T extends PracticalPet, M extends PracticalPetModel<T>> extends RenderLayer<T, M> {
    private final ResourceLocation texture;

    public PetBackStrapLayer(RenderLayerParent<T, M> p_117045_, ResourceLocation texture) {
        super(p_117045_);
        this.texture = texture;
    }

    @Override
    public void render(@NotNull PoseStack p_117058_, @NotNull MultiBufferSource p_117059_, int p_117060_, @NotNull T pet, float p_117062_, float p_117063_, float p_117064_, float p_117065_, float p_117066_, float p_117067_) {
        if (pet.getBackItem().isEmpty() || pet.hideEquipment())
            return;

        float prevRed = this.getParentModel().redMultiplier;
        float prevGreen = this.getParentModel().greenMultiplier;
        float prevBlue = this.getParentModel().blueMultiplier;
        this.getParentModel().redMultiplier = 1f;
        this.getParentModel().greenMultiplier = 1f;
        this.getParentModel().blueMultiplier = 1f;

        VertexConsumer vertexconsumer = p_117059_.getBuffer(RenderType.entityTranslucent(this.texture));
        this.getParentModel().renderToBuffer(p_117058_, vertexconsumer, p_117060_, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);

        this.getParentModel().redMultiplier = prevRed;
        this.getParentModel().greenMultiplier = prevBlue;
        this.getParentModel().blueMultiplier = prevGreen;
    }
}
