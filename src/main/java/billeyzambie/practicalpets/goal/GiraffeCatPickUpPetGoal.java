package billeyzambie.practicalpets.goal;

import billeyzambie.practicalpets.entity.otherpet.GiraffeCat;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.List;

public class GiraffeCatPickUpPetGoal extends Goal {
    private static final TargetingConditions PICK_UP_TARGETING = TargetingConditions.forNonCombat().range(16.0D).ignoreLineOfSight();
    private final GiraffeCat giraffeCat;
    private final Level level;
    private final double speedModifier;
    private TamableAnimal pet;

    public GiraffeCatPickUpPetGoal(GiraffeCat giraffeCat, double speedModifier) {
        this.giraffeCat = giraffeCat;
        this.level = giraffeCat.level();
        this.speedModifier = speedModifier;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        if (
                !this.giraffeCat.noCurrentAbility()
                || giraffeCat.getTarget() == null
                || !giraffeCat.isTame()
        ) {
            return false;
        }
        else {
            this.pet = this.getPetToPickUp();
            return this.pet != null;
        }
    }

    @Override
    public boolean canContinueToUse() {
        return this.giraffeCat.canPickUp(this.pet)
                && this.giraffeCat.noCurrentAbility()
                && giraffeCat.getTarget() != null;
    }

    @Override
    public void stop() {
        this.pet = null;
    }

    @Override
    public void tick() {
        this.giraffeCat.getLookControl().setLookAt(this.pet);
        this.giraffeCat.getNavigation().moveTo(this.pet, this.speedModifier);
        float minDistanceSqr = (this.pet.getBbWidth() + this.giraffeCat.getBbWidth()) + 0.5f;
        minDistanceSqr *= minDistanceSqr;
        if (this.giraffeCat.distanceToSqr(this.pet) < minDistanceSqr) {
            this.giraffeCat.startYeeting(pet);
        }
    }

    @Nullable
    private TamableAnimal getPetToPickUp() {
        List<? extends TamableAnimal> list = this.level.getNearbyEntities(TamableAnimal.class, PICK_UP_TARGETING, this.giraffeCat, this.giraffeCat.getBoundingBox().inflate(16.0D));
        double d0 = Double.MAX_VALUE;
        TamableAnimal result = null;

        for(TamableAnimal pet : list) {
            if (
                    this.giraffeCat.distanceToSqr(pet) < d0
                    && this.giraffeCat.canPickUp(pet)
            ) {
                result = pet;
                d0 = this.giraffeCat.distanceToSqr(pet);
            }
        }

        return result;
    }

}
