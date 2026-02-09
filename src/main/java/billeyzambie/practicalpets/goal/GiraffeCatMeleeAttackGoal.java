package billeyzambie.practicalpets.goal;

import billeyzambie.practicalpets.entity.PracticalPet;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;

public class GiraffeCatMeleeAttackGoal extends Goal {
    private final PracticalPet pet;
    private LivingEntity target;
    private int attackTime;
    private int runTime = 0;

    public GiraffeCatMeleeAttackGoal(PracticalPet p_25658_) {
        this.pet = p_25658_;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        if (pet.canPerformRangedAttack())
            return false;
        LivingEntity livingentity = this.pet.getTarget();
        if (livingentity == null) {
            return false;
        } else {
            this.target = livingentity;
            return true;
        }
    }

    @Override
    public boolean canContinueToUse() {
        if (pet.canPerformRangedAttack())
            return false;
        if (!this.target.isAlive()) {
            return false;
        } else if (this.pet.distanceToSqr(this.target) > 225.0D) {
            return false;
        } else {
            return !this.pet.getNavigation().isDone() || this.canUse();
        }
    }

    @Override
    public void start() {
        super.start();
        this.runTime = 0;
    }

    @Override
    public void stop() {
        this.target = null;
        this.pet.getNavigation().stop();
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    public void tick() {
        this.runTime++;
        this.pet.getLookControl().setLookAt(this.target, 30.0F, 30.0F);
        double d0 = (double)(this.pet.getBbWidth() * 2.0F * this.pet.getBbWidth() * 2.0F);
        double d1 = this.pet.distanceToSqr(this.target.getX(), this.target.getY(), this.target.getZ());
        double d2 = 0.8D;
        if (d1 > d0 && d1 < 16.0D || this.runTime > 100) {
            d2 = 1.33D;
        } else if (d1 < 225.0D) {
            d2 = 0.6D;
        }

        this.pet.getNavigation().moveTo(this.target, d2);
        this.attackTime = Math.max(this.attackTime - 1, 0);
        if (!(d1 > d0)) {
            if (this.attackTime <= 0) {
                this.attackTime = 20;
                this.pet.doHurtTarget(this.target);
            }
        }
    }
}
