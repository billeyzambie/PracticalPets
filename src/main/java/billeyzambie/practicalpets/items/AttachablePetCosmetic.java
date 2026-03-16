package billeyzambie.practicalpets.items;

import billeyzambie.practicalpets.client.layer.PetEquipmentLayer;
import billeyzambie.practicalpets.client.model.entity.base.PetEquipmentWearerModel;
import billeyzambie.practicalpets.entity.base.practicalpet.PetEquipmentWearer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;

public interface AttachablePetCosmetic extends PetCosmetic {
    <T extends Mob & PetEquipmentWearer, M extends EntityModel<T> & PetEquipmentWearerModel> void render(
            PetEquipmentLayer<T, M> layer,
            ItemStack stack,
            PoseStack poseStack,
            MultiBufferSource buffer,
            int packedLight,
            T wearer,
            float limbSwing,
            float limbSwingAmount,
            float partialticks
    );
}
