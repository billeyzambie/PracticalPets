package billeyzambie.practicalpets.mixin.entity.minecraft;

import net.minecraft.nbt.CompoundTag;

public interface VanillaWanderablePet {
    boolean practicalPets$shouldFollowOwner();
    void practicalPets$setShouldFollowOwner(boolean value);

    default void practicalPets$saveShouldFollowOwner(CompoundTag tag) {
        tag.putBoolean("PracticalPetShouldFollow", this.practicalPets$shouldFollowOwner());
    }

    default void practicalPets$loadShouldFollowOwner(CompoundTag tag) {
        if (tag.contains("PracticalPetShouldFollow"))
            this.practicalPets$setShouldFollowOwner(tag.getBoolean("PracticalPetShouldFollow"));
    }
}
