package billeyzambie.practicalpets.items;

import billeyzambie.practicalpets.entity.base.practicalpet.PetEquipmentWearer;
import billeyzambie.practicalpets.misc.ConfigurableBundleItem;
import billeyzambie.practicalpets.misc.PracticalPets;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class PetBackpack extends ConfigurableBundleItem implements EntityModelPetCosmetic, DyeableItem {
    public PetBackpack() {
        super(new Properties().stacksTo(1));
    }

    ResourceLocation modelTexture = new ResourceLocation(
            PracticalPets.MODID,
            "textures/entity/pet_equipment/pet_backpack.png"
    );

    @Override
    public @Nullable ResourceLocation getModelTexture(ItemStack stack, PetEquipmentWearer wearer) {
        return modelTexture;
    }

    @Override
    public Slot slot(ItemStack stack, PetEquipmentWearer wearer) {
        return Slot.BACK;
    }

    @Override
    public boolean canBePutOn(ItemStack stack, PetEquipmentWearer wearer) {
        return true;
    }

    @Override
    public boolean causesBravery(ItemStack stack, PetEquipmentWearer wearer) {
        return false;
    }

    @Override
    public int getDefaultColor() {
        return 0xC29262;
    }
}
