package billeyzambie.practicalpets.items;

import billeyzambie.practicalpets.PracticalPets;
import billeyzambie.practicalpets.entity.dinosaur.AbstractDuck;
import billeyzambie.practicalpets.entity.LandPracticalPet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.Item;

public class DuckArmor extends Item implements PetCosmetic {

    public DuckArmor(String materialName, float damageMultiplier, SoundEvent equipSound) {
        super(new Item.Properties().stacksTo(1));

        this.bananaDuckModelTexture = new ResourceLocation(
                PracticalPets.MODID,
                "textures/entity/banana_duck/armor/" + materialName + ".png"
        );

        this.damageMultiplier = damageMultiplier;
        this.equipSound = equipSound;
    }
    private final SoundEvent equipSound;

    private final ResourceLocation bananaDuckModelTexture;
    private final float damageMultiplier;

    public ResourceLocation getBananaDuckModelTexture() {
        return this.bananaDuckModelTexture;
    }
    @Override
    public float damageMultiplier() {
        return this.damageMultiplier;
    }

    @Override
    public Slot slot() {
        return Slot.BODY;
    }

    @Override
    public boolean canBePutOn(LandPracticalPet pet) {
        return pet instanceof AbstractDuck;
    }

    @Override
    public boolean causesBravery() {
        return true;
    }

    @Override
    public SoundEvent getEquipSound() {
        return equipSound;
    }
}