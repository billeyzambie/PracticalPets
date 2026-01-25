package billeyzambie.practicalpets.goal;

import billeyzambie.practicalpets.entity.PracticalPet;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.util.AirAndWaterRandomPos;
import net.minecraft.world.entity.ai.util.AirRandomPos;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.ai.util.HoverRandomPos;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

public class FlyPanicGoal extends PanicIfShouldGoal {
    public FlyPanicGoal(PracticalPet p_25691_, double p_25692_) {
        super(p_25691_, p_25692_);
    }

    @Override
    protected boolean findRandomPosition() {
        Vec3 vec3 = getPosition();
        if (vec3 == null) {
            return false;
        } else {
            this.posX = vec3.x;
            this.posY = vec3.y;
            this.posZ = vec3.z;
            return true;
        }
    }

    @Nullable
    protected Vec3 getPosition() {
        Vec3 vec3 = this.mob.getViewVector(0.0F);
        Vec3 vec31 = HoverRandomPos.getPos(this.mob, 8, 7, vec3.x, vec3.z, ((float)Math.PI / 2F), 3, 1);
        return vec31 != null ? vec31 : AirAndWaterRandomPos.getPos(this.mob, 8, 4, -2, vec3.x, vec3.z, (double)((float)Math.PI / 2F));
    }
}
