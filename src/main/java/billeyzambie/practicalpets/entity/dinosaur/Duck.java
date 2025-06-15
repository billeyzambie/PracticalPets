package billeyzambie.practicalpets.entity.dinosaur;

import billeyzambie.practicalpets.ModEntities;
import billeyzambie.practicalpets.ModSounds;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.Tags;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class Duck extends AbstractDuck {
    public Duck(EntityType<? extends TamableAnimal> entityType, Level level) {
        super(entityType, level);
    }

    private static final HashMap<Integer, Integer> VARIANT_SPAWN_WEIGHTS = new HashMap<>() {{
        put(0, 72);
        put(1, 72);
        // 2 is the secret golden duck that doesn't spawn naturally
        // but will be spawned by breeding a duck with a penguin when penguins are added
        put(3, 3);
        put(4, 35);
        put(5, 35);
        put(6, 35);
        put(7, 35);
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
        return 100;
    }

    @Override
    public int getLevel10AttackDamage() {
        return 20;
    }

    @Override
    public boolean isTameItem(ItemStack itemStack) {
        return itemStack.is(Tags.Items.SEEDS)
                || itemStack.is(Tags.Items.CROPS_CARROT)
                || itemStack.is(Items.MELON_SLICE)
                || itemStack.is(Items.GLISTERING_MELON_SLICE)
                || super.isTameItem(itemStack);
    }

    @Override
    public HealOverride healOverride(ItemStack itemStack) {
        //Seeds and glistering melon aren't edible so a healOverride must be defined
        if (itemStack.is(Tags.Items.SEEDS))
            return HealOverride.defineNutrition(3);
        if (itemStack.is(Items.GLISTERING_MELON_SLICE))
            return HealOverride.defineNutrition(6);
        //Bread is bad for ducks
        if (itemStack.is(Items.BREAD))
            return HealOverride.override(1);
        return super.healOverride(itemStack);
    }

    @Override
    public float headSizeX() {
        return 4;
    }

    @Override
    public float headSizeY() {
        return 3;
    }

    @Override
    public float headSizeZ() {
        return 3;
    }


    @Override
    protected SoundEvent getAmbientSound() {
        return ModSounds.DUCK_AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource damageSource) {
        return ModSounds.DUCK_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.DUCK_DEATH.get();
    }

    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob partner) {
        Duck baby = ModEntities.DUCK.get().create(level);

        if (baby != null) {
            if (this.isTame()) {
                baby.setOwnerUUID(this.getOwnerUUID());
                baby.setTame(true);
            }

            if (partner instanceof Duck duckPartner) {
                if (this.random.nextBoolean())
                    baby.setVariant(this.variant());
                else
                    baby.setVariant(duckPartner.variant());
            }
        }

        return baby;
    }
}
