package billeyzambie.practicalpets.mixin.alexsmobs;

import billeyzambie.practicalpets.entity.base.practicalpet.IPracticalPet;
import com.github.alexthe666.alexsmobs.entity.EntityRaccoon;
import com.github.alexthe666.alexsmobs.entity.IFollower;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(EntityRaccoon.class)
public abstract class AlexsMobsGenericPetMixin extends TamableAnimal implements IFollower, IPracticalPet {
    private AlexsMobsGenericPetMixin(EntityType<? extends TamableAnimal> p_21803_, Level p_21804_) {
        super(p_21803_, p_21804_);
    }

    @Override
    public boolean petIsCurrentlyFollowingOwner() {
        return !this.isSitting() && this.shouldFollow();
    }

    @Shadow(remap = false) abstract public boolean isSitting();
    @Shadow(remap = false) public boolean forcedSit;
    @Shadow(remap = false) abstract public void setCommand(int value);

    @Override
    public FollowMode getFollowMode() {
        if (this.petIsCurrentlyGuarding())
            return FollowMode.GUARDING;
        if (this.isSitting())
            return FollowMode.SITTING;
        if (this.shouldFollow())
            return FollowMode.FOLLOWING;
        return FollowMode.WANDERING;
    }

    @Override
    public void setFollowMode(FollowMode value) {
        this.setOrderedToSit(this.forcedSit = value == FollowMode.SITTING);
        if (value == FollowMode.GUARDING)
            this.petStartGuarding();
        else {
            this.petStopGuarding();

            int command = (value.ordinal() + 1) % 3;
            this.setCommand(command);
        }
    }
}
