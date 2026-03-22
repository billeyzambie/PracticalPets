package billeyzambie.practicalpets.entity.base;

import billeyzambie.practicalpets.entity.base.practicalpet.IPracticalPet;
import net.minecraft.nbt.CompoundTag;

public interface VanillaPracticalPet extends IPracticalPet {
    boolean practicalPets$shouldFollowOwner();
    void practicalPets$setShouldFollowOwner(boolean value);

    @Override
    default void savePracticalPetData(CompoundTag tag) {
        IPracticalPet.super.savePracticalPetData(tag);
        tag.putBoolean("PracticalPetShouldFollow", this.practicalPets$shouldFollowOwner());
    }

    @Override
    default void loadPracticalPetData(CompoundTag tag) {
        IPracticalPet.super.loadPracticalPetData(tag);
        if (tag.contains("PracticalPetShouldFollow"))
            this.practicalPets$setShouldFollowOwner(tag.getBoolean("PracticalPetShouldFollow"));
    }
}
