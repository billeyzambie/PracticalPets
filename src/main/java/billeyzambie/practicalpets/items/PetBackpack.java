package billeyzambie.practicalpets.items;

import billeyzambie.practicalpets.entity.PracticalPet;
import billeyzambie.practicalpets.misc.PracticalPets;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BundleItem;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class PetBackpack extends BundleItem implements AttachablePetCosmetic, DyeableItem {
    public PetBackpack() {
        super(new Properties().stacksTo(1));
    }

    ResourceLocation modelTexture = new ResourceLocation(
            PracticalPets.MODID,
            "textures/entity/pet_equipment/pet_backpack.png"
    );

    @Override
    public @Nullable ResourceLocation getModelTexture() {
        return modelTexture;
    }

    @Override
    public AttachBone getAttachBone() {
        return AttachBone.BACKPACK;
    }

    @Override
    public Slot slot() {
        return Slot.BACK;
    }

    @Override
    public boolean canBePutOn(PracticalPet pet) {
        return true;
    }

    @Override
    public boolean causesBravery(ItemStack stack) {
        return false;
    }

    @Override
    public int getDefaultColor() {
        return 0xC29262;
    }
}
