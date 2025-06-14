package billeyzambie.practicalpets.entity.dinosaur;

import billeyzambie.practicalpets.entity.LandPracticalPet;
import billeyzambie.practicalpets.goals.FollowOwnerWanderableGoal;
import billeyzambie.practicalpets.network.ModNetworking;
import billeyzambie.practicalpets.network.QuackAnimPacket;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.phys.Vec3;

public abstract class AbstractDuck extends LandPracticalPet {

    public AbstractDuck(EntityType<? extends TamableAnimal> entityType, Level level) {
        super(entityType, level);
    }

    public final AnimationState quackAnimationState = new AnimationState();

    public static final Ingredient FOOD_ITEMS = Ingredient.of(Items.BREAD);

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        return super.mobInteract(player, hand);
    }

    @Override
    public void playAmbientSound() {
        super.playAmbientSound();
        if (!this.level().isClientSide && this.getTarget() == null) {
            ModNetworking.CHANNEL.send(
                    net.minecraftforge.network.PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> this),
                    new QuackAnimPacket(this.getId())
            );
        }
    }

    @Override
    public void playHurtSound(DamageSource damageSource) {
        super.playHurtSound(damageSource);
        if (!this.level().isClientSide && this.getTarget() == null) {
            ModNetworking.CHANNEL.send(
                    net.minecraftforge.network.PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> this),
                    new QuackAnimPacket(this.getId())
            );
        }
    }

    @Override
    public void aiStep() {
        super.aiStep();
        Vec3 vec3 = this.getDeltaMovement();
        if (!this.onGround() && vec3.y < 0.0D) {
            this.setDeltaMovement(vec3.multiply(1.0D, 0.6D, 1.0D));
        }
    }

    @Override
    public void tick() {
        super.tick();
    }
}