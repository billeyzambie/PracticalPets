package billeyzambie.practicalpets.items;

import billeyzambie.practicalpets.PracticalPets;
import billeyzambie.practicalpets.entity.PracticalPet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class PetBowtie extends Item implements AttachablePetCosmetic, DyeableItem {
    public PetBowtie(String modelTextureName) {
        super(new Item.Properties().stacksTo(1));
        this.modelTexture = new ResourceLocation(
                PracticalPets.MODID,
                "textures/entity/pet_equipment/" + modelTextureName + ".png"
        );
    }

    ResourceLocation modelTexture;

    @Override
    public int getDefaultColor() {
        return 11546150;
    }

    @Override
    public ResourceLocation getModelTexture() {
        return modelTexture;
    }

    @Override
    public AttachBone getAttachBone() {
        return AttachBone.BOWTIE;
    }

    @Override
    public Slot slot() {
        return Slot.NECK;
    }

    @Override
    public boolean canBePutOn(PracticalPet pet) {
        return true;
    }

    @Override
    public boolean causesBravery() {
        return false;
    }
}
