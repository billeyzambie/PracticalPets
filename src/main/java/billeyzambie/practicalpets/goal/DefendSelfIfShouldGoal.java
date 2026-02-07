package billeyzambie.practicalpets.goal;

import billeyzambie.practicalpets.entity.PracticalPet;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import org.jetbrains.annotations.NotNull;

public class DefendSelfIfShouldGoal extends HurtByTargetGoal {
    PracticalPet pet;
    public DefendSelfIfShouldGoal(PracticalPet mob, Class<?>... entityClass) {
        super(mob, entityClass);
        this.pet = mob;
    }

    @Override
    public boolean canUse() {
        return this.pet.shouldDefendSelf() && super.canUse();
    }

    @Override
    public boolean canContinueToUse() {
        return this.pet.shouldDefendSelf() && super.canContinueToUse();
    }

    @Override
    protected void alertOther(@NotNull Mob mob, @NotNull LivingEntity target) {
        if (this.pet.shouldDefendSelf())
            super.alertOther(mob, target);
    }
}
