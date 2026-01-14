package billeyzambie.practicalpets.items;

import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;

public interface AttachablePetCosmetic extends PetCosmetic {
    //I don't remember why this is nullable
    @Nullable ResourceLocation getModelTexture();
    enum AttachBone { HAT, BOWTIE, BACKPACK }
    AttachBone getAttachBone();

    @Nullable
    default ResourceLocation getModelEmissiveTexture() {
        return null;
    }
}