package billeyzambie.practicalpets.entity.fish;

import billeyzambie.practicalpets.client.model.entity.fish.AnglerfishModel;
import billeyzambie.practicalpets.entity.fish.base.BreedableFish;
import billeyzambie.practicalpets.entity.fish.base.PracticalFish;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class Anglerfish extends PracticalFish {
    public Anglerfish(EntityType<? extends Anglerfish> type, Level level) {
        super(type, level);
    }

    @Override
    protected float getDeathSoundPitch() {
        return 0.65f;
    }

    @Override
    protected void addPiranhaLauncherData(CompoundTag tag) {
        int variant = this.getVariant();
        if (variant != 0) {
            tag.putInt("Variant", variant);
        }
    }

    @Override
    public float getMinSwimSwingDelta() {
        return AnglerfishModel.MIN_SWIM_SWING_DELTA;
    }

    @Override
    public float getMinSwimSwingAmount() {
        return AnglerfishModel.MIN_SWIM_SWING_AMOUNT;
    }

    private static final HashMap<Integer, Integer> VARIANT_SPAWN_WEIGHTS = new HashMap<>() {{
        put(0, 6);
        put(1, 6);
        put(2, 6);
        put(3, 1);
        put(4, 6);
        put(5, 6);
        put(6, 6);
        put(7, 6);
    }};

    @Override
    public HashMap<Integer, Integer> variantSpawnWeights() {
        return VARIANT_SPAWN_WEIGHTS;
    }

    @Override
    public float getPetHeadSizeX() {
        return 7;
    }

    @Override
    public float getPetHeadSizeY() {
        return 7;
    }

    @Override
    public float getPetHeadSizeZ() {
        return 7;
    }

    @Override
    public BreedableFish getBreedOffspring(@NotNull ServerLevel level, @NotNull BreedableFish partner) {
        Anglerfish baby = (Anglerfish) this.getType().create(level);

        if (baby != null) {
            if (this.isTame()) {
                baby.setOwnerUUID(this.getOwnerUUID());
                baby.setTame(true);
            }

            if (partner instanceof Anglerfish anglerfish) {
                if (this.random.nextBoolean())
                    baby.setVariant(this.getVariant());
                else
                    baby.setVariant(anglerfish.getVariant());
            }
        }

        return baby;
    }

    @Override
    public ItemStack getBucketItemStack() {
        return null;
    }
}