package billeyzambie.practicalpets.items;

import billeyzambie.practicalpets.entity.PracticalPet;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.ItemStack;

public class PlainPetHat extends PetHat implements DyeableLeatherItem {
    public PlainPetHat(String modelTextureName) {
        super(modelTextureName, 1, new Properties());
    }

    @Override
    public boolean onPetHurt(ItemStack stack, PracticalPet pet, DamageSource source, float amount) {
        if (amount >= pet.getHealth()) {
            pet.setEquippedItem(ItemStack.EMPTY, this.slot());
            pet.playSound(SoundEvents.ITEM_BREAK);
            return false;
        }
        return super.onPetHurt(stack, pet, source, amount);
    }
}
