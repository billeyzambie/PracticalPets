package billeyzambie.practicalpets.items;

import billeyzambie.animationcontrollers.ACEntity;
import billeyzambie.animationcontrollers.PracticalPetModel;
import billeyzambie.practicalpets.client.layer.PetEquipmentLayer;
import billeyzambie.practicalpets.entity.base.practicalpet.PetEquipmentWearer;
import billeyzambie.practicalpets.entity.base.practicalpet.PracticalPet;
import billeyzambie.practicalpets.util.PPUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;

public interface EntityModelPetCosmetic extends AttachablePetCosmetic {
    ResourceLocation getModelTexture(ItemStack stack, PetEquipmentWearer wearer);

    @Nullable
    default ResourceLocation getModelEmissiveTexture(ItemStack stack, PetEquipmentWearer wearer) {
        return null;
    }

    default boolean ignoreLighting(ItemStack stack, PetEquipmentWearer wearer) {
        return false;
    }

    @Override
    default <T extends Mob & ACEntity, M extends PracticalPetModel<T>> void render(
            PetEquipmentLayer<T, M> layer,
            ItemStack stack,
            PoseStack poseStack,
            MultiBufferSource buffer,
            int packedLight,
            PracticalPet wearer,
            float limbSwing,
            float limbSwingAmount,
            float partialticks
    ) {
        poseStack.pushPose();

        if (!wearer.isModelYAxisInverted())
            poseStack.mulPose(Axis.ZP.rotationDegrees(180));

        float r = 1, g = 1, b = 1;
        if (this instanceof DyeableLeatherItem dyeableItem) {
            int color = dyeableItem.getColor(stack);
            r = PPUtil.getColorRed(color);
            g = PPUtil.getColorGreen(color);
            b = PPUtil.getColorBlue(color);
        }

        VertexConsumer vertexConsumer;
        if (this.ignoreLighting(stack, wearer)) {
            packedLight = LightTexture.FULL_BLOCK;
        }
        var cosmeticModel = layer.cosmeticModels.get(this);
        if (cosmeticModel == null) {
            throw new Error("Model not defined in the cosmeticModels hashmap for cosmetic of " + this.getClass());
        }
        cosmeticModel.root().resetPose();
        cosmeticModel.setupAnim((T) wearer, 0, 0, 0, 0, 0);
        ResourceLocation texture = this.getModelTexture(stack, wearer);
        vertexConsumer = buffer.getBuffer(RenderType.entityCutoutNoCull(texture));
        cosmeticModel.renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, r, g, b, 1);
        ResourceLocation emissiveTexture = this.getModelEmissiveTexture(stack, (PracticalPet) wearer);
        if (emissiveTexture != null) {
            vertexConsumer = buffer.getBuffer(RenderType.eyes(emissiveTexture));
            cosmeticModel.renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, r, g, b, 1);
        }

        poseStack.popPose();
    }
}