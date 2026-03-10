package billeyzambie.practicalpets.items;

import billeyzambie.practicalpets.entity.PracticalPet;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class PiranhaLauncher extends Item implements ItemModelPetCosmetic {
    public PiranhaLauncher() {
        super(new Properties().stacksTo(1));
    }

    @Override
    public ScaleMode getScaleMode() {
        return ScaleMode.NONE;
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
