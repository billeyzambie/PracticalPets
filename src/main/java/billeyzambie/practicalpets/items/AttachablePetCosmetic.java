package billeyzambie.practicalpets.items;

import billeyzambie.practicalpets.entity.PracticalPet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;

public interface AttachablePetCosmetic extends PetCosmetic {
    ResourceLocation getModelTexture(ItemStack stack, PracticalPet pet);
    enum AttachBone { HAT, BOWTIE, BACKPACK }
    AttachBone getAttachBone(ItemStack stack, PracticalPet pet);

    @Nullable
    default ResourceLocation getModelEmissiveTexture(ItemStack stack, PracticalPet pet) {
        return null;
    }

    default boolean ignoreLighting(ItemStack stack, PracticalPet pet) {
        return false;
    }
}