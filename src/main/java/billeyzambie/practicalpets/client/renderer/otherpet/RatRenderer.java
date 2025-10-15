package billeyzambie.practicalpets.client.renderer.otherpet;

import billeyzambie.practicalpets.client.ModModelLayers;
import billeyzambie.practicalpets.client.layer.RatPatternLayer;
import billeyzambie.practicalpets.client.model.entity.otherpet.RatModel;
import billeyzambie.practicalpets.client.renderer.PracticalPetRenderer;
import billeyzambie.practicalpets.entity.otherpet.Rat;
import billeyzambie.practicalpets.misc.PracticalPets;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class RatRenderer extends PracticalPetRenderer<Rat, RatModel> {
    private final ItemInHandRenderer itemInHandRenderer;

    public RatRenderer(EntityRendererProvider.Context context) {
        super(context, new RatModel(context.bakeLayer(ModModelLayers.RAT)), 0.4f);
        this.addLayer(new RatPatternLayer(this));
        itemInHandRenderer = context.getItemInHandRenderer();
    }

    private static final ResourceLocation ALBINO_TEXTURE =
            new ResourceLocation(PracticalPets.MODID, "textures/entity/rat/albino.png");

    private static final ResourceLocation[] BASE_TEXTURES = {
            new ResourceLocation(PracticalPets.MODID, "textures/entity/rat/rat.png"),
            new ResourceLocation(PracticalPets.MODID, "textures/entity/rat/rat1.png"),
            new ResourceLocation(PracticalPets.MODID, "textures/entity/rat/rat2.png"),
            new ResourceLocation(PracticalPets.MODID, "textures/entity/rat/rat3.png"),
            new ResourceLocation(PracticalPets.MODID, "textures/entity/rat/rat4.png"),
    };

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Rat rat) {
        if (rat.isAlbino())
            return ALBINO_TEXTURE;

        return BASE_TEXTURES[rat.getVariant() % BASE_TEXTURES.length];
    }

    @Override
    public void render(@NotNull Rat entity, float entityYaw, float partialticks, @NotNull PoseStack poseStack, @NotNull MultiBufferSource buffer, int packedLight) {
        super.render(entity, entityYaw, partialticks, poseStack, buffer, packedLight);

        poseStack.pushPose();

        //entityYaw only updated when the pet is walking for some reason, so I didn't use it
        float bodyYaw = Mth.lerp(partialticks, entity.yBodyRotO, entity.yBodyRot);
        poseStack.mulPose(Axis.YP.rotationDegrees(-bodyYaw));

        poseStack.mulPose(Axis.XP.rotationDegrees(180));
        poseStack.translate(0, -24f / 16f, 0);
        for (ModelPart part : this.getModel().pathToItem()) {
            part.translateAndRotate(poseStack);
        }
        poseStack.mulPose(Axis.XP.rotationDegrees(180));

        ItemStack itemstack = entity.getItemBySlot(EquipmentSlot.MAINHAND);
        this.itemInHandRenderer.renderItem(entity, itemstack, ItemDisplayContext.GROUND, false, poseStack, buffer, packedLight);

        poseStack.popPose();
    }

}
