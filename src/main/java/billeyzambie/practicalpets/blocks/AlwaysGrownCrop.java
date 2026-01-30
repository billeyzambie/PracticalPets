package billeyzambie.practicalpets.blocks;

import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class AlwaysGrownCrop extends CropBlock {
    public AlwaysGrownCrop(Properties properties) {
        super(properties);
    }

    @Override
    public int getAge(@NotNull BlockState state) {
        return this.getMaxAge();
    }

    @Override
    public boolean isRandomlyTicking(@NotNull BlockState state) {
        return false;
    }

    @Override
    public @NotNull ItemLike getBaseSeedId() {
        return Items.AIR;
    }
}
