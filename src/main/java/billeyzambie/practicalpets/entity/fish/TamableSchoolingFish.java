package billeyzambie.practicalpets.entity.fish;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.AbstractSchoolingFish;
import net.minecraft.world.level.Level;

public abstract class TamableSchoolingFish extends AbstractSchoolingFish {
    public TamableSchoolingFish(EntityType<? extends AbstractSchoolingFish> p_27523_, Level p_27524_) {
        super(p_27523_, p_27524_);
    }
}
