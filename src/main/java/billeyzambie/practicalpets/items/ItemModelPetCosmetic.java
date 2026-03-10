package billeyzambie.practicalpets.items;

import billeyzambie.animationcontrollers.ACEntity;
import billeyzambie.animationcontrollers.PracticalPetModel;
import billeyzambie.practicalpets.client.layer.PetEquipmentLayer;
import billeyzambie.practicalpets.entity.PracticalPet;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

/** A pet accessory that uses the item's head pose for its model on pets.
 * Doesn't have to be equippable on the head if {@link ScaleMode} is {@link ScaleMode#NONE} */
public interface ItemModelPetCosmetic extends AttachablePetCosmetic {
    /** Only {@link ScaleMode#NONE} has been implemented */
    enum ScaleMode {
        /** No scaling. Best for items entirely on top of the head */
        NONE,
        /** Stretches the item from the player's head size to the pet's.
         * Best for items that completely surround the head. */
        STRETCH_XYZ,
        /** Stretches the item from the player's head size to the pet's,
         * but uses the average of the x and z for the y scaling.
         * Best for items that cover the top and the sides of the head. */
        STRETCH_XZ
    }
    ScaleMode getScaleMode();

    float PLAYER_HEAD_ITEM_SCALE = 0.627451f;
    float PLAYER_HEAD_HEIGHT = 8;

    @Override
    default <T extends Mob & ACEntity, M extends PracticalPetModel<T>> void render(
            PetEquipmentLayer<T, M> layer,
            ItemStack stack,
            PoseStack poseStack,
            MultiBufferSource buffer,
            int packedLight,
            PracticalPet pet,
            float limbSwing,
            float limbSwingAmount,
            float partialticks
    ) {
        poseStack.pushPose();

        if (pet.isModelYAxisInverted())
            poseStack.mulPose(Axis.ZP.rotationDegrees(180));

        poseStack.translate(0, (-PLAYER_HEAD_HEIGHT + pet.headSizeY()) / 16f, 0);
        poseStack.scale(PLAYER_HEAD_ITEM_SCALE, PLAYER_HEAD_ITEM_SCALE, PLAYER_HEAD_ITEM_SCALE);

        Minecraft.getInstance().getItemRenderer().renderStatic(
                pet,
                stack,
                ItemDisplayContext.HEAD,
                false,
                poseStack,
                buffer,
                pet.level(),
                packedLight,
                OverlayTexture.NO_OVERLAY,
                pet.getId()
        );

        poseStack.popPose();
    }
}
