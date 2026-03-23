package billeyzambie.practicalpets.client.layer;

import billeyzambie.practicalpets.client.PPRenderLayers;
import billeyzambie.practicalpets.client.model.entity.base.PetEquipmentWearerModel;
import billeyzambie.practicalpets.client.model.entity.pet_equipment.*;
import billeyzambie.practicalpets.entity.base.practicalpet.PetEquipmentWearer;
import billeyzambie.practicalpets.items.AttachablePetCosmetic;
import billeyzambie.practicalpets.items.EntityModelPetCosmetic;
import billeyzambie.practicalpets.items.PetCosmetic;
import billeyzambie.practicalpets.misc.PPItems;
import billeyzambie.practicalpets.petequipment.PetCosmetics;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.HashMap;

public class PetEquipmentLayer<T extends Mob & PetEquipmentWearer, M extends EntityModel<T> & PetEquipmentWearerModel> extends RenderLayer<T, M> {

    public final HashMap<EntityModelPetCosmetic, HierarchicalModel<T>> cosmeticModels = new HashMap<>();

    private void registerCosmeticModel(EntityModelPetCosmetic cosmetic, HierarchicalModel<T> model) {
        cosmeticModels.put(
                cosmetic,
                model
        );
    }

    private void registerCosmeticModel(Item cosmetic, HierarchicalModel<T> model) {
        registerCosmeticModel(
                (EntityModelPetCosmetic) PetCosmetics.getCosmeticForItem(cosmetic).orElseThrow(),
                model
        );
    }

    public PetEquipmentLayer(RenderLayerParent<T, M> parent, EntityRendererProvider.Context context) {
        super(parent);

        registerCosmeticModel(
                PPItems.PET_BOWTIE.get(),
                new PetBowtieModel<>(context.bakeLayer(PPRenderLayers.PET_BOWTIE))
        );
        registerCosmeticModel(
                PPItems.ANNIVERSARY_PET_HAT_0.get(),
                new AnniversaryPetHatModel<>(context.bakeLayer(PPRenderLayers.ANNIVERSARY_PET_HAT))
        );
        registerCosmeticModel(
                PPItems.RUBBER_DUCKY_PET_HAT.get(),
                new RubberDuckyPetHatModel<>(context.bakeLayer(PPRenderLayers.RUBBER_DUCKY_PET_HAT))
        );
        registerCosmeticModel(
                PPItems.PET_CHEF_HAT.get(),
                new PetChefHatModel<>(context.bakeLayer(PPRenderLayers.PET_CHEF_HAT))
        );
        registerCosmeticModel(
                PPItems.PET_BACKPACK.get(),
                new PetBackpackModel<>(context.bakeLayer(PPRenderLayers.PET_BACKPACK))
        );
        registerCosmeticModel(
                PPItems.PET_END_ROD_LAUNCHER.get(),
                new PetBackpackModel<>(context.bakeLayer(PPRenderLayers.PET_END_ROD_LAUNCHER))
        );
        registerCosmeticModel(
                PPItems.PET_HAT.get(),
                new PlainPetHatModel<>(context.bakeLayer(PPRenderLayers.PET_HAT))
        );
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, T entity, float limbSwing, float limbSwingAmount, float partialticks, float r, float g, float b) {


        if (!entity.hidePetEquipment()) for (PetCosmetic.Slot slot : PetCosmetic.Slot.values()) {
            ItemStack cosmeticStack = entity.getEquippedItem(slot);
            var cosmeticOptional = PetCosmetics.getCosmeticForItem(cosmeticStack);
            if (cosmeticOptional.isPresent() && cosmeticOptional.orElseThrow() instanceof AttachablePetCosmetic cosmetic) {

                poseStack.pushPose();

                ////entityYaw only updated when the pet is walking for some reason, so I didn't use it
                //float bodyYaw = Mth.lerp(partialticks, entity.yBodyRotO, entity.yBodyRot);
                //poseStack.mulPose(Axis.YP.rotationDegrees(-bodyYaw));

                switch (slot) {
                    case HEAD -> this.getParentModel().moveToPetHat(poseStack);
                    case NECK -> this.getParentModel().moveToPetBowtie(poseStack);
                    case BACK -> this.getParentModel().moveToPetBackpack(poseStack);
                    default -> throw new AssertionError("Pretty sure this will never happen (error at practicalpetrender at render at switch (cosmetic.getAttachBone()))");
                }

                //poseStack.mulPose(Axis.XP.rotationDegrees(180));
                //poseStack.translate(0, -24f / 16f, 0);
                //for (ModelPart part : pathToAttachment) {
                //    part.translateAndRotate(poseStack);
                //}

                cosmetic.render(
                        this,
                        cosmeticStack,
                        poseStack,
                        buffer,
                        packedLight,
                        entity,
                        limbSwing,
                        limbSwingAmount,
                        partialticks
                );

                poseStack.popPose();
            }

        }
    }
}