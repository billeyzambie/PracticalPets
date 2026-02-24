package billeyzambie.practicalpets.client.renderer.otherpet;

import billeyzambie.practicalpets.client.PPRenders;
import billeyzambie.practicalpets.client.layer.PetBackStrapLayer;
import billeyzambie.practicalpets.client.model.entity.otherpet.GiraffeCatModel;
import billeyzambie.practicalpets.client.model.entity.otherpet.GiraffeCatNeckPieceModel;
import billeyzambie.practicalpets.client.renderer.PracticalPetRenderer;
import billeyzambie.practicalpets.entity.otherpet.GiraffeCat;
import billeyzambie.practicalpets.misc.PracticalPets;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

public class GiraffeCatRenderer extends PracticalPetRenderer<GiraffeCat, GiraffeCatModel> {
    private final GiraffeCatNeckPieceModel neckPieceModel;

    public GiraffeCatRenderer(EntityRendererProvider.Context context) {
        super(context, new GiraffeCatModel(context.bakeLayer(PPRenders.GIRAFFE_CAT)), 0.5f);
        this.addLayer(new PetBackStrapLayer<>(this, BACK_STRAP_TEXTURE));
        this.neckPieceModel = new GiraffeCatNeckPieceModel(context.bakeLayer(PPRenders.GIRAFFE_CAT_NECK_PIECE));
    }

    private static final ResourceLocation[] GIRAFFE_TEXTURES = {
            new ResourceLocation(PracticalPets.MODID, "textures/entity/giraffe_cat/giraffe.png"),
            new ResourceLocation(PracticalPets.MODID, "textures/entity/giraffe_cat/giraffe1.png"),
            new ResourceLocation(PracticalPets.MODID, "textures/entity/giraffe_cat/giraffe2.png"),
            new ResourceLocation(PracticalPets.MODID, "textures/entity/giraffe_cat/giraffe3.png"),
            new ResourceLocation(PracticalPets.MODID, "textures/entity/giraffe_cat/giraffe4.png")
    };

    private static final ResourceLocation[] CAT_TEXTURES = {
            new ResourceLocation(PracticalPets.MODID, "textures/entity/giraffe_cat/white.png"),
            new ResourceLocation(PracticalPets.MODID, "textures/entity/giraffe_cat/tuxedo.png"),
            new ResourceLocation(PracticalPets.MODID, "textures/entity/giraffe_cat/redtabby.png"),
            new ResourceLocation(PracticalPets.MODID, "textures/entity/giraffe_cat/siamesecat.png"),
            new ResourceLocation(PracticalPets.MODID, "textures/entity/giraffe_cat/britishshorthair.png"),
            new ResourceLocation(PracticalPets.MODID, "textures/entity/giraffe_cat/calico.png"),
            new ResourceLocation(PracticalPets.MODID, "textures/entity/giraffe_cat/persian.png"),
            new ResourceLocation(PracticalPets.MODID, "textures/entity/giraffe_cat/ragdoll.png"),
            new ResourceLocation(PracticalPets.MODID, "textures/entity/giraffe_cat/tabby.png"),
            new ResourceLocation(PracticalPets.MODID, "textures/entity/giraffe_cat/jellie.png"),
            new ResourceLocation(PracticalPets.MODID, "textures/entity/giraffe_cat/allblackcat.png"),
            new ResourceLocation(PracticalPets.MODID, "textures/entity/giraffe_cat/perry.png"),
            new ResourceLocation(PracticalPets.MODID, "textures/entity/giraffe_cat/pizza.png"),
            new ResourceLocation(PracticalPets.MODID, "textures/entity/giraffe_cat/sugar.png"),
            new ResourceLocation(PracticalPets.MODID, "textures/entity/giraffe_cat/lily.png"),
            new ResourceLocation(PracticalPets.MODID, "textures/entity/giraffe_cat/budder.png")
    };

    private static final ResourceLocation BACK_STRAP_TEXTURE =
            new ResourceLocation(PracticalPets.MODID, "textures/entity/giraffe_cat/back_strap.png");

    @Override
    public @NotNull ResourceLocation getTextureLocation(GiraffeCat giraffecat) {
        return giraffecat.isCatHybrid()
                ? CAT_TEXTURES[giraffecat.getVariant() % CAT_TEXTURES.length]
                : GIRAFFE_TEXTURES[giraffecat.getVariant() % GIRAFFE_TEXTURES.length];
    }

    @Override
    public boolean shouldRender(@NotNull GiraffeCat giraffeCat, @NotNull Frustum p_115469_, double p_115470_, double p_115471_, double p_115472_) {
        return giraffeCat.isNeckStretched() || super.shouldRender(giraffeCat, p_115469_, p_115470_, p_115471_, p_115472_);
    }

    @Override
    public void render(@NotNull GiraffeCat giraffeCat, float entityYaw, float partialticks, @NotNull PoseStack poseStack, @NotNull MultiBufferSource buffer, int packedLight) {
        super.render(giraffeCat, entityYaw, partialticks, poseStack, buffer, packedLight);

        if (!giraffeCat.isNeckStretched() || giraffeCat.isInvisible())
            return;

        poseStack.pushPose();

        //entityYaw only updated when the pet is walking for some reason, so I didn't use it
        float bodyYaw = Mth.lerp(partialticks, giraffeCat.yBodyRotO, giraffeCat.yBodyRot);
        poseStack.mulPose(Axis.YP.rotationDegrees(-bodyYaw));

        poseStack.mulPose(Axis.XP.rotationDegrees(180));

        poseStack.translate(0, -24f / 16f, 0);

        for (ModelPart part : model.pathToNeck2()) {
            part.translateAndRotate(poseStack);
        }

        for (
                float f = GiraffeCat.STANDING_NECK_BOTTOM;
                f < giraffeCat.getVisibleLadderHeight(partialticks);
                f += GiraffeCat.NECK_HEIGHT
        ) {
            poseStack.pushPose();

            poseStack.translate(0, -f, 0.2f / 16f);

            VertexConsumer vertexconsumer = buffer.getBuffer(RenderType.entityTranslucent(this.getTextureLocation(giraffeCat)));
            neckPieceModel.renderToBuffer(poseStack, vertexconsumer, packedLight, LivingEntityRenderer.getOverlayCoords(giraffeCat, 0.0F), this.model.redMultiplier, this.model.greenMultiplier, this.model.blueMultiplier, 1.0F);

            poseStack.popPose();
        }

        poseStack.popPose();

    }
}
