package billeyzambie.practicalpets.goal;

import billeyzambie.practicalpets.entity.fish.base.PracticalFish;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;

import java.util.EnumSet;

public class FishOwnerHurtByTargetGoal extends TargetGoal {
    private final PracticalFish fish;
    private LivingEntity ownerLastHurtBy;
    private int timestamp;

    public FishOwnerHurtByTargetGoal(PracticalFish fish) {
        super(fish, false);
        this.fish = fish;
        this.setFlags(EnumSet.of(Goal.Flag.TARGET));
    }

    @Override
    public boolean canUse() {
        if (this.fish.isTame()) {
            LivingEntity livingentity = this.fish.getOwner();
            if (livingentity == null) {
                return false;
            } else {
                this.ownerLastHurtBy = livingentity.getLastHurtByMob();
                if (!this.fish.shouldDefendOwner(this.ownerLastHurtBy))
                    return false;
                int i = livingentity.getLastHurtByMobTimestamp();
                return i != this.timestamp && this.canAttack(this.ownerLastHurtBy, TargetingConditions.DEFAULT) && this.fish.wantsToAttack(this.ownerLastHurtBy, livingentity);
            }
        } else {
            return false;
        }
    }

    @Override
    public void start() {
        this.mob.setTarget(this.ownerLastHurtBy);
        LivingEntity livingentity = this.fish.getOwner();
        if (livingentity != null) {
            this.timestamp = livingentity.getLastHurtByMobTimestamp();
        }

        super.start();
    }
}