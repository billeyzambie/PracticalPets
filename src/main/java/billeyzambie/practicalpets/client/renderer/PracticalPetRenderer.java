package billeyzambie.practicalpets.client.renderer;

import billeyzambie.animationcontrollers.ACEntity;
import billeyzambie.animationcontrollers.PracticalPetModel;
import billeyzambie.practicalpets.client.PPRenderLayers;
import billeyzambie.practicalpets.client.layer.PetEquipmentLayer;
import billeyzambie.practicalpets.client.model.entity.pet_equipment.*;
import billeyzambie.practicalpets.misc.PPItems;
import billeyzambie.practicalpets.entity.PracticalPet;
import billeyzambie.practicalpets.items.AttachablePetCosmetic;
import billeyzambie.practicalpets.items.PetCosmetic;
import billeyzambie.practicalpets.petequipment.PetCosmetics;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.LightTexture;
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

public abstract class PracticalPetRenderer<T extends Mob & ACEntity, M extends PracticalPetModel<T>> extends MobRenderer<T, M> {

    public PracticalPetRenderer(EntityRendererProvider.Context context, M model, float shadowRadius) {
        super(context, model, shadowRadius);
        this.addLayer(new PetEquipmentLayer<>(this, context));
    }

    @Override
    public void render(T entity, float entityYaw, float partialticks, PoseStack poseStack,
                       @NotNull MultiBufferSource buffer, int packedLight) {
        float scale = entity.getScale();
        poseStack.scale(scale, scale, scale);

        super.render(entity, entityYaw, partialticks, poseStack, buffer, packedLight);

    }
}
