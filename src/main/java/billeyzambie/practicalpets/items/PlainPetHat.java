package billeyzambie.practicalpets.items;

import billeyzambie.practicalpets.entity.base.practicalpet.PetEquipmentWearer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.ItemStack;

public class PlainPetHat extends PetHat implements DyeableLeatherItem {
    public PlainPetHat(String modelTextureName) {
        super(modelTextureName, 1, new Properties());
    }

    @Override
    public boolean onPetHurt(ItemStack stack, PetEquipmentWearer wearer, DamageSource source, float amount) {
        if (amount >= wearer.getHealth()) {
            wearer.setEquippedItem(ItemStack.EMPTY, this.slot(stack, wearer));
            wearer.playSound(SoundEvents.ITEM_BREAK);
            return true;
        }
        return super.onPetHurt(stack, wearer, source, amount);
    }
}
