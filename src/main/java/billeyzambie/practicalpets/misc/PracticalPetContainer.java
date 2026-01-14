package billeyzambie.practicalpets.misc;

import billeyzambie.practicalpets.entity.PracticalPet;
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
        return pet.getHeadItem().isEmpty()
                && pet.getNeckItem().isEmpty()
                && pet.getBackItem().isEmpty()
                && pet.getBodyItem().isEmpty();
    }

    @Override
    public @NotNull ItemStack getItem(int slot) {
        return switch (slot) {
            case 0 -> pet.getHeadItem();
            case 1 -> pet.getNeckItem();
            case 2 -> pet.getBackItem();
            case 3 -> pet.getBodyItem();
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
            case 0 -> pet.setHeadItem(stack);
            case 1 -> pet.setNeckItem(stack);
            case 2 -> pet.setBackItem(stack);
            case 3 -> pet.setBodyItem(stack);
        }
    }

    @Override
    public void setChanged() {}

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
