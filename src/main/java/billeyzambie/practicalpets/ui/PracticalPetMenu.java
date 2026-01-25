package billeyzambie.practicalpets.ui;

import billeyzambie.practicalpets.client.PPMenus;
import billeyzambie.practicalpets.entity.PracticalPet;
import billeyzambie.practicalpets.entity.dinosaur.Pigeon;
import billeyzambie.practicalpets.items.PetCosmetic;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class PracticalPetMenu extends AbstractContainerMenu {
    public final PracticalPet pet;
    private final Container petContainer;

    public PracticalPetMenu(int id, Inventory playerInv, PracticalPet pet) {
        super(PPMenus.PRACTICAL_PET_MENU.get(), id);
        this.pet = pet;
        this.petContainer = new PracticalPetContainer(pet);

        this.addSlot(new Slot(petContainer, 0, 8, 18) {
            @Override
            public boolean mayPlace(@NotNull ItemStack stack) {
                return stack.getItem() instanceof PetCosmetic cosmetic
                        && cosmetic.slot() == PetCosmetic.Slot.HEAD
                        && cosmetic.canBePutOn(pet);
            }

            @Override
            public int getMaxStackSize() {
                return 1;
            }
        });

        this.addSlot(new Slot(petContainer, 1, 8, 36) {
            @Override
            public boolean mayPlace(@NotNull ItemStack stack) {
                return stack.getItem() instanceof PetCosmetic cosmetic
                        && cosmetic.slot() == PetCosmetic.Slot.NECK
                        && cosmetic.canBePutOn(pet);
            }

            @Override
            public int getMaxStackSize() {
                return 1;
            }
        });

        this.addSlot(new Slot(petContainer, 2, 8, 54) {
            @Override
            public boolean mayPlace(@NotNull ItemStack stack) {
                return stack.getItem() instanceof PetCosmetic cosmetic
                        && cosmetic.slot() == PetCosmetic.Slot.BACK
                        && cosmetic.canBePutOn(pet);
            }

            @Override
            public int getMaxStackSize() {
                return 1;
            }
        });

        this.addSlot(new Slot(petContainer, 3, 8, 72) {
            @Override
            public boolean mayPlace(@NotNull ItemStack stack) {
                return stack.getItem() instanceof PetCosmetic cosmetic
                        && cosmetic.slot() == PetCosmetic.Slot.BODY
                        && cosmetic.canBePutOn(pet);
            }

            @Override
            public int getMaxStackSize() {
                return 1;
            }
        });

        int startX = 8;
        int startY = 102;
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
    public @NotNull ItemStack quickMoveStack(@NotNull Player player, int index) {
        ItemStack result = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        if (!slot.hasItem()) {
            return ItemStack.EMPTY;
        }

        ItemStack stack = slot.getItem();
        result = stack.copy();

        final int PET_SLOT_COUNT = petContainer.getContainerSize();
        final int PLAYER_INV_START = PET_SLOT_COUNT;
        final int PLAYER_INV_END = PLAYER_INV_START + 27;
        final int HOTBAR_START = PLAYER_INV_END;
        final int HOTBAR_END = HOTBAR_START + 9;

        if (index < PET_SLOT_COUNT) {
            if (!this.moveItemStackTo(stack, PLAYER_INV_START, HOTBAR_END, true)) {
                return ItemStack.EMPTY;
            }
        } else {
            if (stack.getItem() instanceof PetCosmetic cosmetic && cosmetic.canBePutOn(pet)) {
                int targetSlot = switch (cosmetic.slot()) {
                    case HEAD -> 0;
                    case NECK -> 1;
                    case BACK -> 2;
                    case BODY -> 3;
                };
                Slot petSlot = this.getSlot(targetSlot);
                if (petSlot.mayPlace(stack) && !petSlot.hasItem()) {
                    if (!this.moveItemStackTo(stack, targetSlot, targetSlot + 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else {
                    if (index >= PLAYER_INV_START && index < PLAYER_INV_END) {
                        if (!this.moveItemStackTo(stack, HOTBAR_START, HOTBAR_END, false)) {
                            return ItemStack.EMPTY;
                        }
                    } else if (index >= HOTBAR_START && index < HOTBAR_END) {
                        if (!this.moveItemStackTo(stack, PLAYER_INV_START, PLAYER_INV_END, false)) {
                            return ItemStack.EMPTY;
                        }
                    }
                }
            } else {
                if (index >= PLAYER_INV_START && index < PLAYER_INV_END) {
                    if (!this.moveItemStackTo(stack, HOTBAR_START, HOTBAR_END, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index >= HOTBAR_START && index < HOTBAR_END) {
                    if (!this.moveItemStackTo(stack, PLAYER_INV_START, PLAYER_INV_END, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            }
        }

        if (stack.isEmpty()) {
            slot.setByPlayer(ItemStack.EMPTY);
        } else {
            slot.setChanged();
        }

        return result;
    }
}
