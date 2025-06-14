package billeyzambie.practicalpets.items;

import billeyzambie.practicalpets.entity.dinosaur.AbstractDuck;
import billeyzambie.practicalpets.entity.LandPracticalPet;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.DyeableLeatherItem;

public class DyeableDuckArmor extends DuckArmor implements DyeableLeatherItem {

    public DyeableDuckArmor(String materialName, float damageMultiplier, SoundEvent equipSound) {
        super(materialName, damageMultiplier, equipSound);
    }

}