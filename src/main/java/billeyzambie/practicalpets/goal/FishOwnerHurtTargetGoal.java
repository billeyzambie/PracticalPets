package billeyzambie.practicalpets.goal;

import billeyzambie.practicalpets.entity.fish.base.PracticalFish;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;

import java.util.EnumSet;

public class FishOwnerHurtTargetGoal extends TargetGoal {
    private final PracticalFish fish;
    private LivingEntity ownerLastHurt;
    private int timestamp;

    public FishOwnerHurtTargetGoal(PracticalFish fish) {
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
                this.ownerLastHurt = livingentity.getLastHurtMob();
                if (!this.fish.shouldDefendOwner(this.ownerLastHurt))
                    return false;
                int i = livingentity.getLastHurtMobTimestamp();
                return i != this.timestamp && this.canAttack(this.ownerLastHurt, TargetingConditions.DEFAULT) && this.fish.wantsToAttack(this.ownerLastHurt, livingentity);
            }
        } else {
            return false;
        }
    }

    @Override
    public void start() {
        this.mob.setTarget(this.ownerLastHurt);
        LivingEntity livingentity = this.fish.getOwner();
        if (livingentity != null) {
            this.timestamp = livingentity.getLastHurtMobTimestamp();
        }

        super.start();
    }
}