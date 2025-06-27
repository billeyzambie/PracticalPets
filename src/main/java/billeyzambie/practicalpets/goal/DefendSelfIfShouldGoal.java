package billeyzambie.practicalpets.goal;

import billeyzambie.practicalpets.entity.PracticalPet;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import org.jetbrains.annotations.NotNull;

public class DefendSelfIfShouldGoal extends HurtByTargetGoal {
    public DefendSelfIfShouldGoal(PathfinderMob mob, Class<?>... entityClass) {
        super(mob, entityClass);
    }

    @Override
    protected void alertOther(@NotNull Mob mob, @NotNull LivingEntity target) {
        if (mob instanceof PracticalPet pet && pet.shouldDefendSelf())
            super.alertOther(mob, target);
    }
}
