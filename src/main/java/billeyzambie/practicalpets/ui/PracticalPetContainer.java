package billeyzambie.practicalpets.ui;

import billeyzambie.practicalpets.entity.base.practicalpet.PracticalPet;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class PracticalPetContainer implements Container {
    private final PracticalPet pet;

    public PracticalPetContainer(PracticalPet pet) {
        this.pet = pet;
    }

    @Override
    public int getContainerSize() {
        return 4;
    }

    @Override
    public boolean isEmpty() {
        return pet.getPetHeadItem().isEmpty()
                && pet.getPetNeckItem().isEmpty()
                && pet.getPetBackItem().isEmpty()
                && pet.getPetBodyItem().isEmpty();
    }

    @Override
    public @NotNull ItemStack getItem(int slot) {
        return switch (slot) {
            case 0 -> pet.getPetHeadItem();
            case 1 -> pet.getPetNeckItem();
            case 2 -> pet.getPetBackItem();
            case 3 -> pet.getPetBodyItem();
            default -> ItemStack.EMPTY;
        };
    }

    @Override
    public @NotNull ItemStack removeItem(int slot, int amount) {
        ItemStack current = getItem(slot);
        if (current.isEmpty()) return ItemStack.EMPTY;
        ItemStack split = current.split(amount);
        setItem(slot, current);
        return split;
    }

    @Override
    public @NotNull ItemStack removeItemNoUpdate(int slot) {
        ItemStack current = getItem(slot);
        setItem(slot, ItemStack.EMPTY);
        return current;
    }

    @Override
    public void setItem(int slot, @NotNull ItemStack stack) {
        switch (slot) {
            case 0 -> pet.setPetHeadItem(stack);
            case 1 -> pet.setPetNeckItem(stack);
            case 2 -> pet.setPetBackItem(stack);
            case 3 -> pet.setPetBodyItem(stack);
        }
    }

    @Override
    public void setChanged() {
        //So that it gets called when end rods are put in or out of the end rod launcher
        this.pet.refreshPetEquipmentCache();
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return pet.isAlive() && pet.distanceTo(player) < 8.0F;
    }

    @Override
    public void clearContent() {
        setItem(0, ItemStack.EMPTY);
        setItem(1, ItemStack.EMPTY);
        setItem(2, ItemStack.EMPTY);
        setItem(3, ItemStack.EMPTY);
    }
}
