package billeyzambie.practicalpets.items;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class CustomUseDurationItem extends Item {
    private final int useDuration;

    public CustomUseDurationItem(Properties properties, int useDuration) {
        super(properties);
        this.useDuration = useDuration;
    }

    @Override
    public int getUseDuration(@NotNull ItemStack p_41454_) {
        return useDuration;
    }
}
