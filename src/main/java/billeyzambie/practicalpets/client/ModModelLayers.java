package billeyzambie.practicalpets.client;

import billeyzambie.practicalpets.PracticalPets;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

public class ModModelLayers {
    public static final ModelLayerLocation BANANA_DUCK = new ModelLayerLocation(
            new ResourceLocation(PracticalPets.MODID, "banana_duck_layer"), "main"
    );
    public static final ModelLayerLocation BANANA_DUCK_ARMOR = new ModelLayerLocation(
            new ResourceLocation(PracticalPets.MODID, "banana_duck_armor_layer"), "main"
    );
    public static final ModelLayerLocation PET_BOWTIE = new ModelLayerLocation(
            new ResourceLocation(PracticalPets.MODID, "pet_bowtie_layer"), "main"
    );
    public static final ModelLayerLocation ANNIVERSARY_PET_HAT = new ModelLayerLocation(
            new ResourceLocation(PracticalPets.MODID, "anniversary_pet_hat_layer"), "main"
    );
}
