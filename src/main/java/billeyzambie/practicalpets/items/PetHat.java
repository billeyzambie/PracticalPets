package billeyzambie.practicalpets.items;

import billeyzambie.practicalpets.entity.base.practicalpet.PetEquipmentWearer;
import billeyzambie.practicalpets.misc.PracticalPets;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class PetHat extends Item implements EntityModelPetCosmetic {

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
    public float petXPMultiplier(ItemStack stack, PetEquipmentWearer wearer) {
        return xpMultiplier;
    }

    ResourceLocation modelTexture;

    @Override
    public ResourceLocation getModelTexture(ItemStack stack, PetEquipmentWearer wearer) {
        return modelTexture;
    }

    @Override
    public Slot slot(ItemStack stack, PetEquipmentWearer wearer) {
        return Slot.HEAD;
    }

    @Override
    public boolean canBePutOn(ItemStack stack, PetEquipmentWearer wearer) {
        return true;
    }

    @Override
    public boolean causesBravery(ItemStack stack, PetEquipmentWearer wearer) {
        return false;
    }

}
