package billeyzambie.practicalpets.entity.fish;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.AbstractSchoolingFish;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class Piranha extends PracticalFish {
    public Piranha(EntityType<? extends PracticalFish> p_27523_, Level p_27524_) {
        super(p_27523_, p_27524_);
    }

    @Override
    public @NotNull ItemStack getBucketItemStack() {
        return new ItemStack(Items.SALMON_BUCKET);
    }

    @Override
    public float headSizeX() {
        return 2;
    }

    @Override
    public float headSizeY() {
        return 2;
    }

    @Override
    public float headSizeZ() {
        return 2;
    }
}
