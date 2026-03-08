package billeyzambie.practicalpets.petequipment;

import billeyzambie.practicalpets.items.PetCosmetic;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class PetCosmetics {
    private PetCosmetics() {}
    private static final Map<Item, PetCosmetic> additionalPetCosmetics = new HashMap<>();
    static {
        //In the future some vanilla items will be put as pet cosmetics here
        //Probably just the saddle for the duckatrice and other big pets
        //additionalPetCosmetics.put(Items.APPLE, new PetHat());
    }

    /**
     * @return false if the item is already registered as a pet cosmetic, otherwise returns true
     */
    public static boolean register(Item item, PetCosmetic cosmetic) {
        if (additionalPetCosmetics.containsKey(item) || item instanceof PetCosmetic)
            return false;
        additionalPetCosmetics.put(item, cosmetic);
        return true;
    }

    public static Optional<PetCosmetic> getCosmeticForItem(Item item) {
        if (item instanceof PetCosmetic cosmetic)
            return Optional.of(cosmetic);
        return Optional.ofNullable(additionalPetCosmetics.get(item));
    }

    public static Optional<PetCosmetic> getCosmeticForItem(ItemStack stack) {
        if (stack.isEmpty())
            return Optional.empty();
        return getCosmeticForItem(stack.getItem());
    }
}
