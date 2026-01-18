package billeyzambie.practicalpets.items;

import billeyzambie.practicalpets.misc.PracticalPets;
import billeyzambie.practicalpets.entity.PracticalPet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class PetHat extends Item implements AttachablePetCosmetic {

    public PetHat(String modelTextureName, float xpMultiplier, Item.Properties properties) {
        super(properties);
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
    public float petXPMultiplier() {
        return xpMultiplier;
    }

    ResourceLocation modelTexture;

    @Override
    public ResourceLocation getModelTexture() {
        return modelTexture;
    }

    @Override
    public AttachBone getAttachBone() {
        return AttachBone.HAT;
    }

    @Override
    public Slot slot() {
        return Slot.HEAD;
    }

    @Override
    public boolean canBePutOn(PracticalPet pet) {
        return true;
    }

    @Override
    public boolean causesBravery(ItemStack stack) {
        return false;
    }

}
