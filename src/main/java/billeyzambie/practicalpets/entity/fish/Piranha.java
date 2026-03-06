package billeyzambie.practicalpets.entity.fish;

import billeyzambie.practicalpets.client.model.entity.fish.PiranhaModel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class Piranha extends PracticalFish {
    public Piranha(EntityType<? extends Piranha> type, Level level) {
        super(type, level);
    }

    private static final HashMap<Integer, Integer> VARIANT_SPAWN_WEIGHTS = new HashMap<>() {{
        put(0, 1);
        put(1, 1);
    }};

    @Override
    public HashMap<Integer, Integer> variantSpawnWeights() {
        return VARIANT_SPAWN_WEIGHTS;
    }

    @Override
    public @NotNull ItemStack getBucketItemStack() {
        return new ItemStack(Items.SALMON_BUCKET);
    }

    @Override
    public float headSizeX() {
        return 2;
    }

    @Override
    public float headSizeY() {
        return 2;
    }

    @Override
    public float headSizeZ() {
        return 2;
    }

    @Override
    public float getMinSwimSwingDelta() {
        return PiranhaModel.MIN_SWIM_SWING_DELTA;
    }

    @Override
    public float getMinSwimSwingAmount() {
        return PiranhaModel.MIN_SWIM_SWING_AMOUNT;
    }
}
