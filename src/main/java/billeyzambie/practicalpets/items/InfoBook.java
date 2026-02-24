package billeyzambie.practicalpets.items;

import billeyzambie.practicalpets.ui.infobook.InfoBookScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class InfoBook extends Item {
    public InfoBook() {
        super(new Properties().stacksTo(1));
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(
            @NotNull Level level,
            @NotNull Player player,
            @NotNull InteractionHand hand
    ) {
        boolean clientSide = level.isClientSide();
        if (clientSide) {
            Minecraft.getInstance().setScreen(new InfoBookScreen());
        }
        return InteractionResultHolder.success(player.getItemInHand(hand));
    }
}
