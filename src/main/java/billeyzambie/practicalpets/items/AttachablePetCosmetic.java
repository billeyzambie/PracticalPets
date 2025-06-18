package billeyzambie.practicalpets.items;

import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;

public interface AttachablePetCosmetic extends PetCosmetic {
    @Nullable ResourceLocation getModelTexture();
    enum AttachBone { HAT, BOWTIE }
    AttachBone getAttachBone();

    @Nullable
    default ResourceLocation getModelEmissiveTexture() {
        return null;
    }
}