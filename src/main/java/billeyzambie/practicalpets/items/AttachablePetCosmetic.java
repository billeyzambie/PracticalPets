package billeyzambie.practicalpets.items;

import billeyzambie.practicalpets.entity.LandPracticalPet;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

import javax.annotation.Nullable;

public interface AttachablePetCosmetic extends PetCosmetic {
    ResourceLocation getModelTexture();
    enum AttachBone { HAT, BOWTIE }
    AttachBone getAttachBone();

    @Nullable
    default ResourceLocation getModelEmissiveTexture() {
        return null;
    }
}