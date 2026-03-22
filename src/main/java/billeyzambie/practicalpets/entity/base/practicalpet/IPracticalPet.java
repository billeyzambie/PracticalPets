package billeyzambie.practicalpets.entity.base.practicalpet;

import net.minecraft.nbt.CompoundTag;

public interface IPracticalPet extends PetEquipmentWearer, LevelablePet, GuardingOwnerFollowingPet {
    default void savePracticalPetData(CompoundTag tag) {
        this.savePetCosmetics(tag);
        this.savePetLevelingData(tag);
        this.saveGuardingPetData(tag);
    }
    default void loadPracticalPetData(CompoundTag tag) {
        this.loadPetCosmetics(tag);
        this.loadPetLevelingData(tag);
        this.loadGuardingPetData(tag);
    }
}
