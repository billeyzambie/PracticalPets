package billeyzambie.practicalpets.client.renderer.other;

import billeyzambie.practicalpets.client.PPRenders;
import billeyzambie.practicalpets.client.model.entity.pet_equipment.PetEndRodLauncherModel;
import billeyzambie.practicalpets.entity.other.PetEndRodProjectile;
import billeyzambie.practicalpets.misc.PracticalPets;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

public class PetEndRodProjectileRenderer extends EntityRenderer<PetEndRodProjectile> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(
            PracticalPets.MODID,
            "textures/entity/pet_equipment/end_rod_launcher.png"
    );

    private final PetEndRodLauncherModel<Entity> model;

    public PetEndRodProjectileRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new PetEndRodLauncherModel<>(context.bakeLayer(PPRenders.PET_END_ROD_PROJECTILE));
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull PetEndRodProjectile p_114482_) {
        return TEXTURE;
    }

    @Override
    protected int getBlockLightLevel(PetEndRodProjectile p_115869_, BlockPos p_115870_) {
        return 15;
    }

    @Override
    public void render(@NotNull PetEndRodProjectile entity, float entityYaw, float partialticks, @NotNull PoseStack poseStack, @NotNull MultiBufferSource buffer, int packedLight) {
        VertexConsumer vertexconsumer = buffer.getBuffer(RenderType.entityTranslucent(TEXTURE));
        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(partialticks, entity.yRotO, entity.getYRot())));
        poseStack.mulPose(Axis.ZP.rotationDegrees(Mth.lerp(partialticks, entity.xRotO, entity.getXRot())));
        poseStack.popPose();
        this.model.renderToBuffer(poseStack, vertexconsumer, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
    }
}
