package billeyzambie.practicalpets.items;

import billeyzambie.practicalpets.misc.PracticalPets;
import billeyzambie.practicalpets.entity.PracticalPet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class PetHat extends Item implements AttachablePetCosmetic {

    public PetHat(String modelTextureName, float xpMultiplier, Item.Properties properties) {
        super(properties.stacksTo(1));
        this.modelTexture = new ResourceLocation(
                PracticalPets.MODID,
                "textures/entity/pet_equipment/" + modelTextureName + ".png"
        );
        this.xpMultiplier = xpMultiplier;
    }
    public PetHat(String modelTextureName, float xpMultiplier) {
        this(modelTextureName, xpMultiplier, new Item.Properties().stacksTo(1));
    }
    public PetHat(String modelTexture) {
        this(modelTexture, 1);
    }

    final float xpMultiplier;

    @Override
    public float petXPMultiplier(ItemStack stack, PracticalPet pet) {
        return xpMultiplier;
    }

    ResourceLocation modelTexture;

    @Override
    public ResourceLocation getModelTexture(ItemStack stack, PracticalPet pet) {
        return modelTexture;
    }

    @Override
    public AttachBone getAttachBone(ItemStack stack, PracticalPet pet) {
        return AttachBone.HAT;
    }

    @Override
    public Slot slot(ItemStack stack, PracticalPet pet) {
        return Slot.HEAD;
    }

    @Override
    public boolean canBePutOn(ItemStack stack, PracticalPet pet) {
        return true;
    }

    @Override
    public boolean causesBravery(ItemStack stack, PracticalPet pet) {
        return false;
    }

}
