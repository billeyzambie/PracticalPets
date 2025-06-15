package billeyzambie.practicalpets.entity.dinosaur;

import billeyzambie.practicalpets.ModEntities;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.Tags;
import org.jetbrains.annotations.NotNull;

public class Duck extends AbstractDuck {
    public Duck(EntityType<? extends TamableAnimal> entityType, Level level) {
        super(entityType, level);
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
            return new HealOverride(HealOverrideType.DEFINE_NUTRITION, 2);
        if (itemStack.is(Items.GLISTERING_MELON_SLICE))
            return new HealOverride(HealOverrideType.DEFINE_NUTRITION, 6);
        //Bread is bad for ducks
        if (itemStack.is(Items.BREAD))
            return new HealOverride(HealOverrideType.DEFINE_NUTRITION, 1);
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
    public AgeableMob getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob partner) {
        Duck baby = ModEntities.DUCK.get().create(level);
        if (baby != null) {
            if (this.isTame()) {
                baby.setOwnerUUID(this.getOwnerUUID());
                baby.setTame(true);
            }
        }

        return baby;
    }
}
