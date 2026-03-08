package billeyzambie.practicalpets.items;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;

public interface AttachablePetCosmetic extends PetCosmetic {
    ResourceLocation getModelTexture();
    enum AttachBone { HAT, BOWTIE, BACKPACK }
    AttachBone getAttachBone();

    @Nullable
    default ResourceLocation getModelEmissiveTexture() {
        return null;
    }

    default boolean ignoreLighting(ItemStack instance) {
        return false;
    }
}