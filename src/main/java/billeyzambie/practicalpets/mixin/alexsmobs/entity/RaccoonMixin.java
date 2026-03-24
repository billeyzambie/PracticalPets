package billeyzambie.practicalpets.mixin.alexsmobs.entity;

import billeyzambie.practicalpets.entity.base.practicalpet.IPracticalPet;
import com.github.alexthe666.alexsmobs.entity.EntityRaccoon;
import com.github.alexthe666.alexsmobs.entity.IFollower;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(EntityRaccoon.class)
public abstract class RaccoonMixin extends TamableAnimal implements IFollower, IPracticalPet {

    private RaccoonMixin(EntityType<? extends TamableAnimal> p_21803_, Level p_21804_) {
        super(p_21803_, p_21804_);
    }

    @Override
    public float getPetHeadSizeX() {
        return 9;
    }

    @Override
    public float getPetHeadSizeY() {
        return 7;
    }

    @Override
    public float getPetHeadSizeZ() {
        return 5;
    }

    @Override
    public double getLevel10MaxHealth() {
        return 100;
    }

    @Override
    public double getLevel10AttackDamage() {
        return 16;
    }

    @Override
    public boolean isGuardingPetAbleToAttack(@Nullable LivingEntity target) {
        return this.petShouldDefendOwner(target);
    }

    @Override
    public void performRangedAttack(@NotNull LivingEntity target, float distanceFactor) {
        if (this.canPerformCosmeticRangedAttack())
            this.performCosmeticRangedAttack(petCanShootFromSlot().orElseThrow(), target, distanceFactor);
    }

}
