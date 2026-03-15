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
    /** Only {@link ScaleMode#NONE} has been implemented
     * Also oops I forgot helmets aren't item models on java lol */
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
    ScaleMode getScaleMode(ItemStack stack, PracticalPet pet);

    float PLAYER_HEAD_ITEM_SCALE = 0.627451f;

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


        onRenderModelOnPetBefore(
                layer,
                stack,
                poseStack,
                buffer,
                packedLight,
                pet,
                limbSwing,
                limbSwingAmount,
                partialticks
        );
        poseStack.translate(0, -4 / 16f, 0);
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
        onRenderModelOnPetAfter(
                layer,
                stack,
                poseStack,
                buffer,
                packedLight,
                pet,
                limbSwing,
                limbSwingAmount,
                partialticks
        );

        poseStack.popPose();
    }

    default <T extends Mob & ACEntity, M extends PracticalPetModel<T>> void onRenderModelOnPetBefore(
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

    }

    default <T extends Mob & ACEntity, M extends PracticalPetModel<T>> void onRenderModelOnPetAfter(
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

    }
}
