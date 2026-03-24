package billeyzambie.practicalpets.goal;

import billeyzambie.practicalpets.entity.base.practicalpet.PetEquipmentWearer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;

import javax.annotation.Nullable;
import java.util.EnumSet;

public class PetEquipmentWearerCosmeticRangedAttackGoal<T extends Mob & PetEquipmentWearer> extends Goal {
    public final T wearer;
    @Nullable
    private LivingEntity target;
    private int attackTime = -1;
    private final double speedModifier;
    private int seeTime;
    private final int attackIntervalMin;
    private final int attackIntervalMax;
    private final float attackRadius;
    private final float attackRadiusSqr;

    public PetEquipmentWearerCosmeticRangedAttackGoal(T wearer, double speedMultiplier) {
        this(wearer, speedMultiplier, 20, 40, 20f);
    }

    public PetEquipmentWearerCosmeticRangedAttackGoal(T p_25773_, double p_25774_, int p_25775_, int p_25776_, float p_25777_) {
        this.wearer = p_25773_;
        this.speedModifier = p_25774_;
        this.attackIntervalMin = p_25775_;
        this.attackIntervalMax = p_25776_;
        this.attackRadius = p_25777_;
        this.attackRadiusSqr = p_25777_ * p_25777_;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        if (!wearer.canPerformCosmeticRangedAttack())
            return false;
        LivingEntity livingentity = this.wearer.getTarget();
        if (livingentity != null && livingentity.isAlive()) {
            this.target = livingentity;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean canContinueToUse() {
        if (!wearer.canPerformCosmeticRangedAttack())
            return false;
        return this.canUse() || this.target.isAlive() && !this.wearer.getNavigation().isDone();
    }

    @Override
    public void stop() {
        this.target = null;
        this.seeTime = 0;
        this.attackTime = -1;
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    public void tick() {
        double d0 = this.wearer.distanceToSqr(this.target.getX(), this.target.getY(), this.target.getZ());
        boolean flag = this.wearer.getSensing().hasLineOfSight(this.target);
        if (flag) {
            ++this.seeTime;
        } else {
            this.seeTime = 0;
        }

        if (!(d0 > (double)this.attackRadiusSqr) && this.seeTime >= 5) {
            this.wearer.getNavigation().stop();
        } else {
            this.wearer.getNavigation().moveTo(this.target, this.speedModifier);
        }

        this.wearer.getLookControl().setLookAt(this.target, 30.0F, 30.0F);
        if (--this.attackTime == 0) {
            if (!flag) {
                return;
            }

            float f = (float)Math.sqrt(d0) / this.attackRadius;
            float f1 = Mth.clamp(f, 0.1F, 1.0F);
            this.wearer.performCosmeticRangedAttack(this.wearer.petCanShootFromSlot().orElseThrow(), target, f1);
            this.attackTime = Mth.floor(f * (float)(this.attackIntervalMax - this.attackIntervalMin) + (float)this.attackIntervalMin);
        } else if (this.attackTime < 0) {
            this.attackTime = Mth.floor(Mth.lerp(Math.sqrt(d0) / (double)this.attackRadius, (double)this.attackIntervalMin, (double)this.attackIntervalMax));
        }

    }
}
