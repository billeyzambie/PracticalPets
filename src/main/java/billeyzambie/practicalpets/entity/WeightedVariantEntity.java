package billeyzambie.practicalpets.entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;

import java.util.HashMap;
import java.util.Map;

public interface WeightedVariantEntity {
    int getVariant();
    void setVariant(int variant);
    HashMap<Integer, Integer> variantSpawnWeights();
    RandomSource getRandom();

    default int pickRandomWeightedVariant() {
        var weights = variantSpawnWeights();
        if (weights == null)
            return 0;
        int totalWeight = 0;
        for (int weight : weights.values()) {
            totalWeight += weight;
        }

        int randomValue = this.getRandom().nextInt(totalWeight);
        int weightAddedSoFar = 0;

        for (Map.Entry<Integer, Integer> entry : weights.entrySet()) {
            weightAddedSoFar += entry.getValue();
            if (randomValue < weightAddedSoFar) {
                return entry.getKey();
            }
        }

        throw new AssertionError("I don't think this will ever happen (error in pickRandomWeightedVariant in PracticalPet.java)");
    }

    default void loadVariant(CompoundTag tag) {
        setVariant(tag.getInt("Variant"));
    }

    default void saveVariant(CompoundTag tag) {
        tag.putInt("Variant", getVariant());
    }

}
