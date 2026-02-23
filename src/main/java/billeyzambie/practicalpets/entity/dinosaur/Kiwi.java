package billeyzambie.practicalpets.entity.dinosaur;

import billeyzambie.practicalpets.entity.PracticalPet;
import billeyzambie.practicalpets.misc.PPSounds;
import billeyzambie.practicalpets.misc.PPTags;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Shearable;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.IForgeShearable;
import net.minecraftforge.common.Tags;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

public class Kiwi extends PracticalPet implements IForgeShearable {
    public Kiwi(EntityType<? extends TamableAnimal> entityType, Level level) {
        super(entityType, level);
    }

    private static final HashMap<Integer, Integer> VARIANT_SPAWN_WEIGHTS = new HashMap<>() {{
        put(0, 29);
        put(1, 29);
        put(2, 29);
        put(3, 29);
        put(4, 29);
        put(5, 29);
        put(6, 2);
        put(7, 1);
    }};

    @Override
    public HashMap<Integer, Integer> variantSpawnWeights() {
        return VARIANT_SPAWN_WEIGHTS;
    }

    @Override
    public int getLevel1MaxHealth() {
        return 6;
    }

    @Override
    public int getLevel1AttackDamage() {
        return 2;
    }

    @Override
    public int getLevel10MaxHealth() {
        return 80;
    }

    @Override
    public int getLevel10AttackDamage() {
        return 16;
    }

    @Override
    public float headSizeX() {
        return 3;
    }

    @Override
    public float headSizeY() {
        return 3;
    }

    @Override
    public float headSizeZ() {
        return 4;
    }

    @Override
    public boolean isTameItem(ItemStack itemStack) {
        return itemStack.is(Items.SPIDER_EYE) || itemStack.is(Items.FERMENTED_SPIDER_EYE);
    }

    @Override
    public boolean isFoodThatDoesntTame(ItemStack itemStack) {
        return itemStack.is(Tags.Items.SEEDS) || itemStack.is(PPTags.Items.FRUITS);
    }

    @Override
    protected @Nullable SoundEvent getAmbientSound() {
        return PPSounds.KIWI_AMBIENT.get();
    }

    @Override
    protected @Nullable SoundEvent getHurtSound(@NotNull DamageSource damageSource) {
        return PPSounds.KIWI_HURT.get();
    }

    @Override
    protected @Nullable SoundEvent getDeathSound() {
        return PPSounds.KIWI_DEATH.get();
    }
}
