package billeyzambie.practicalpets.client.renderer;

import billeyzambie.animationcontrollers.PracticalPetModel;
import billeyzambie.practicalpets.ModItems;
import billeyzambie.practicalpets.client.ModModelLayers;
import billeyzambie.practicalpets.client.model.entity.pet_equipment.AnniversaryPetHatModel;
import billeyzambie.practicalpets.client.model.entity.pet_equipment.PetBowtieModel;
import billeyzambie.practicalpets.entity.LandPracticalPet;
import billeyzambie.practicalpets.items.AttachablePetCosmetic;
import billeyzambie.practicalpets.items.PetCosmetic;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;

public abstract class PracticalPetRenderer<T extends Mob, M extends PracticalPetModel<T>> extends MobRenderer<T, M> {

    final HashMap<Item, HierarchicalModel<T>> cosmeticModels = new HashMap<>();

    public PracticalPetRenderer(EntityRendererProvider.Context context, M model, float shadowRadius) {
        super(context, model, shadowRadius);

        cosmeticModels.put(
                ModItems.PET_BOWTIE.get(),
                new PetBowtieModel<>(context.bakeLayer(ModModelLayers.PET_BOWTIE))
        );
        cosmeticModels.put(
                ModItems.ANNIVERSARY_PET_HAT_0.get(),
                new AnniversaryPetHatModel<>(context.bakeLayer(ModModelLayers.ANNIVERSARY_PET_HAT))
        );
    }

    @Override
    public void render(T entity, float entityYaw, float partialticks, PoseStack poseStack,
                       @NotNull MultiBufferSource buffer, int packedLight) {

        float scale = entity.getScale();
        poseStack.scale(scale, scale, scale);

        super.render(entity, entityYaw, partialticks, poseStack, buffer, packedLight);

        if (entity instanceof LandPracticalPet pet) {
            for (PetCosmetic.Slot slot : PetCosmetic.Slot.values()) {
                ItemStack cosmeticStack = pet.getEquippedItem(slot);
                if (!cosmeticStack.isEmpty() && cosmeticStack.getItem() instanceof AttachablePetCosmetic cosmetic) {

                    poseStack.pushPose();

                    //entityYaw only updated when the pet is walking for some reason, so I didn't use it
                    float bodyYaw = Mth.lerp(partialticks, entity.yBodyRotO, entity.yBodyRot);
                    poseStack.mulPose(Axis.YP.rotationDegrees(-bodyYaw));

                    List<ModelPart> pathToAttachment;

                    switch (cosmetic.getAttachBone()) {
                        case HAT -> pathToAttachment = this.getModel().pathToHat();
                        case BOWTIE -> pathToAttachment = this.getModel().pathToBowtie();
                        default ->
                                throw new IllegalStateException("Pretty sure this will never happen (error at practicalpetrender at render at switch (cosmetic.getAttachBone()))");
                    }

                    var cosmeticModel = cosmeticModels.get(cosmetic);
                    if (cosmeticModel == null) {
                        throw new Error("Model not defined in the cosmeticModels hashmap for cosmetic of " + cosmetic.getClass());
                    }

                    cosmeticModel.root().resetPose();

                    poseStack.mulPose(Axis.XP.rotationDegrees(180));

                    poseStack.translate(0, -24f / 16f, 0);

                    for (ModelPart part : pathToAttachment) {
                        part.translateAndRotate(poseStack);
                    }

                    cosmeticModel.setupAnim(entity, 0, 0, 0, 0, 0);

                    float r = 1, g = 1, b = 1;
                    if (cosmetic instanceof DyeableLeatherItem dyeableItem) {
                        int i = dyeableItem.getColor(cosmeticStack);
                        r = (i >> 16 & 255) / 255f;
                        g = (i >> 8 & 255) / 255f;
                        b = (i & 255) / 255f;
                    }

                    VertexConsumer vertexConsumer;

                    ResourceLocation texture = cosmetic.getModelTexture();
                    if (texture != null) {
                        vertexConsumer = buffer.getBuffer(RenderType.entityCutoutNoCull(texture));
                        cosmeticModel.renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, r, g, b, 1);
                    }

                    ResourceLocation emissiveTexture = cosmetic.getModelEmissiveTexture();
                    if (emissiveTexture != null) {
                        vertexConsumer = buffer.getBuffer(RenderType.eyes(emissiveTexture));
                        cosmeticModel.renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, r, g, b, 1);
                    }

                    poseStack.popPose();
                }

            }
        }
    }
}
