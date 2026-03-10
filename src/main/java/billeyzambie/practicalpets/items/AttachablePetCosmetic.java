package billeyzambie.practicalpets.items;

import billeyzambie.animationcontrollers.ACEntity;
import billeyzambie.animationcontrollers.PracticalPetModel;
import billeyzambie.practicalpets.client.layer.PetEquipmentLayer;
import billeyzambie.practicalpets.entity.PracticalPet;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;

public interface AttachablePetCosmetic extends PetCosmetic {
    <T extends Mob & ACEntity, M extends PracticalPetModel<T>> void render(
            PetEquipmentLayer<T, M> layer,
            ItemStack stack,
            PoseStack poseStack,
            MultiBufferSource buffer,
            int packedLight,
            PracticalPet pet,
            float limbSwing,
            float limbSwingAmount,
            float partialticks
    );
}
