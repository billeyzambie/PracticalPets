package billeyzambie.practicalpets.items;

import billeyzambie.practicalpets.misc.PracticalPets;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

public class AnniversaryPetHat extends PetHat {
    public AnniversaryPetHat(String modelTextureName, float xpMultiplier, Properties properties) {
        super(modelTextureName, xpMultiplier, properties);
        this.modelEmissiveTexture = new ResourceLocation(
                PracticalPets.MODID,
                "textures/entity/pet_equipment/" + modelTextureName + "_emissive.png"
        );
    }

    final ResourceLocation modelEmissiveTexture;

    @Override
    public @Nullable ResourceLocation getModelEmissiveTexture() {
        return modelEmissiveTexture;
    }
}
