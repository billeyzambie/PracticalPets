package billeyzambie.practicalpets.client.menus;

import billeyzambie.practicalpets.client.ModMenus;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class BananaDuckMenu extends AbstractContainerMenu {

    private final IItemHandler duckInventory;

    public BananaDuckMenu(int containerId, Inventory playerInventory, IItemHandler duckInventory) {
        super(ModMenus.BANANA_DUCK_MENU.get(), containerId);
        this.duckInventory = duckInventory;

        // ✅ Add the Banana Duck's inventory slots
        this.addSlot(new SlotItemHandler(duckInventory, 0, 80, 20)); // Slot for hat
    }

    @Override
    public ItemStack quickMoveStack(Player p_38941_, int p_38942_) {
        return null;
    }

    @Override
    public boolean stillValid(Player p_38874_) {
        return false;
    }
}
