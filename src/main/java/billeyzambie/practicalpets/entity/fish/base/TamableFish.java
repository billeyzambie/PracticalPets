package billeyzambie.practicalpets.entity.fish.base;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public abstract class TamableFish extends BreedableFish {
    public TamableFish(EntityType<? extends TamableFish> p_27523_, Level p_27524_) {
        super(p_27523_, p_27524_);
    }
}
