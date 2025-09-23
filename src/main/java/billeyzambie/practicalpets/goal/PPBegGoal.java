package billeyzambie.practicalpets.goal;

import java.util.EnumSet;
import javax.annotation.Nullable;

import billeyzambie.practicalpets.entity.PracticalPet;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class PPBegGoal extends Goal {
    private final PracticalPet pet;
    @Nullable
    private Player player;
    private final Level level;
    private final float lookDistance = 3;
    private int lookTime;
    private final TargetingConditions begTargeting;

    public PPBegGoal(PracticalPet p_25063_) {
        this.pet = p_25063_;
        this.level = p_25063_.level();
        this.begTargeting = TargetingConditions.forNonCombat().range(lookDistance);
        this.setFlags(EnumSet.of(Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        this.player = this.level.getNearestPlayer(this.begTargeting, this.pet);
        return this.player != null && this.playerHoldingInteresting(this.player);
    }

    @Override
    public boolean canContinueToUse() {
        assert this.player != null;
        if (!this.player.isAlive()) {
            return false;
        } else if (this.pet.distanceToSqr(this.player) > (double)(this.lookDistance * this.lookDistance)) {
            return false;
        } else {
            return this.lookTime > 0 && this.playerHoldingInteresting(this.player);
        }
    }

    @Override
    public void start() {
        this.pet.setIsInterested(true);
        this.lookTime = this.adjustedTickDelay(40 + this.pet.getRandom().nextInt(40));
    }

    @Override
    public void stop() {
        this.pet.setIsInterested(false);
        this.player = null;
    }

    @Override
    public void tick() {
        assert this.player != null;
        this.pet.getLookControl().setLookAt(this.player.getX(), this.player.getEyeY(), this.player.getZ(), 10.0F, (float)this.pet.getMaxHeadXRot());
        --this.lookTime;
    }

    private boolean playerHoldingInteresting(Player p_25067_) {
        for(InteractionHand interactionhand : InteractionHand.values()) {
            ItemStack itemstack = p_25067_.getItemInHand(interactionhand);
            if (this.pet.isFood(itemstack)) {
                return true;
            }
        }

        return false;
    }
}