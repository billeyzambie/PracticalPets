package billeyzambie.practicalpets.mixin.vanilla;

import billeyzambie.practicalpets.compat.DomesticationInnovationHelper;
import billeyzambie.practicalpets.entity.base.VanillaPracticalPet;
import billeyzambie.practicalpets.util.PPUtil;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.animal.Parrot;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(value = {
        Cat.class,
        Wolf.class,
        Parrot.class
}, priority = 999)
public abstract class VanillaPetMixin extends TamableAnimal implements VanillaPracticalPet {
    private VanillaPetMixin(EntityType<? extends TamableAnimal> p_21803_, Level p_21804_) {
        super(p_21803_, p_21804_);
    }

    @Override
    public boolean petIsCurrentlyFollowingOwner() {
        return !this.isOrderedToSit() && this.practicalPets$shouldFollowOwner();
    }

    @Override
    public FollowMode getFollowMode() {
        if (this.petIsCurrentlyGuarding())
            return FollowMode.GUARDING;
        if (this.isOrderedToSit())
            return FollowMode.SITTING;
        if (PPUtil.isDIInstalled())
            return DomesticationInnovationHelper.getFollowModeForVanillaPet(this);
        if (this.practicalPets$shouldFollowOwner())
            return FollowMode.FOLLOWING;
        return FollowMode.WANDERING;
    }

    @Override
    public void setFollowMode(FollowMode value) {
        this.setOrderedToSit(value == FollowMode.SITTING);
        this.practicalPets$setShouldFollowOwner(value == FollowMode.FOLLOWING || value == FollowMode.SITTING);
        if (value == FollowMode.GUARDING)
            this.petStartGuarding();
        else
            this.petStopGuarding();
        if (PPUtil.isDIInstalled()) {
            DomesticationInnovationHelper.setPetCommandFromFollowMode(this, value);
        }
    }

    @Unique
    private boolean practicalPets$shouldFollowOwner;

    @Override
    public boolean practicalPets$shouldFollowOwner() {
        return practicalPets$shouldFollowOwner;
    }

    @Override
    public void practicalPets$setShouldFollowOwner(boolean value) {
        this.practicalPets$shouldFollowOwner = value;
    }
}
