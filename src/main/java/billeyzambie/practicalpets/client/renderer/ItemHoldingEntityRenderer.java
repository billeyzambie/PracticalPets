package billeyzambie.practicalpets.client.renderer;

import billeyzambie.practicalpets.client.model.entity.ItemHoldingEntityModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public interface ItemHoldingEntityRenderer {
    ItemInHandRenderer getItemInHandRenderer();

    default <T extends LivingEntity, M extends HierarchicalModel<T> & ItemHoldingEntityModel> void renderItem(LivingEntityRenderer<T, M> livingEntityRenderer, @NotNull T entity, float partialticks, @NotNull PoseStack poseStack, @NotNull MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();

        //entityYaw only updated when the entity is walking for some reason, so I didn't use it
        float bodyYaw = Mth.lerp(partialticks, entity.yBodyRotO, entity.yBodyRot);
        poseStack.mulPose(Axis.YP.rotationDegrees(-bodyYaw));

        poseStack.mulPose(Axis.XP.rotationDegrees(180));
        poseStack.translate(0, -24f / 16f, 0);
        for (ModelPart part : livingEntityRenderer.getModel().pathToItem()) {
            part.translateAndRotate(poseStack);
        }
        poseStack.mulPose(Axis.XP.rotationDegrees(180));

        ItemStack itemstack = entity.getItemBySlot(EquipmentSlot.MAINHAND);
        this.getItemInHandRenderer().renderItem(entity, itemstack, ItemDisplayContext.GROUND, false, poseStack, buffer, packedLight);

        poseStack.popPose();
    }
}
