package billeyzambie.practicalpets.client.ui;

import billeyzambie.practicalpets.client.ModMenus;
import billeyzambie.practicalpets.entity.PracticalPet;
import billeyzambie.practicalpets.items.PetCosmetic;
import billeyzambie.practicalpets.misc.PracticalPetContainer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class PracticalPetMenu extends AbstractContainerMenu {
    private final PracticalPet pet;
    private final Container petContainer;

    public PracticalPetMenu(int id, Inventory playerInv, PracticalPet pet) {
        super(ModMenus.PRACTICAL_PET_MENU.get(), id);
        this.pet = pet;
        this.petContainer = new PracticalPetContainer(pet);

        this.addSlot(new Slot(petContainer, 0, 8, 18) {
            @Override public boolean mayPlace(@NotNull ItemStack stack) {
                return stack.getItem() instanceof PetCosmetic cosmetic
                        && cosmetic.slot() == PetCosmetic.Slot.HEAD
                        && cosmetic.canBePutOn(pet);
            }
            @Override public int getMaxStackSize() { return 1; }
        });

        this.addSlot(new Slot(petContainer, 1, 8, 36) {
            @Override public boolean mayPlace(@NotNull ItemStack stack) {
                return stack.getItem() instanceof PetCosmetic cosmetic
                        && cosmetic.slot() == PetCosmetic.Slot.NECK
                        && cosmetic.canBePutOn(pet);
            }
            @Override public int getMaxStackSize() { return 1; }
        });

        this.addSlot(new Slot(petContainer, 2, 8, 54) {
            @Override public boolean mayPlace(@NotNull ItemStack stack) {
                return stack.getItem() instanceof PetCosmetic cosmetic
                        && cosmetic.slot() == PetCosmetic.Slot.BODY
                        && cosmetic.canBePutOn(pet);
            }
            @Override public int getMaxStackSize() { return 1; }
        });

        int startX = 8;
        int startY = 84;
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                this.addSlot(new Slot(playerInv, col + row * 9 + 9, startX + col * 18, startY + row * 18));
            }
        }
        for (int col = 0; col < 9; col++) {
            this.addSlot(new Slot(playerInv, col, startX + col * 18, startY + 58));
        }
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return petContainer.stillValid(player);
    }

    @Override
    public @NotNull ItemStack quickMoveStack(Player player, int index) {
        // Implement shift-click logic as needed.
        return ItemStack.EMPTY;
    }
}
