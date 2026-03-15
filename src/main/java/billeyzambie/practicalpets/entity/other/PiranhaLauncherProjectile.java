package billeyzambie.practicalpets.entity.other;

import billeyzambie.practicalpets.entity.fish.base.PracticalFish;
import billeyzambie.practicalpets.misc.PPEntities;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PiranhaLauncherProjectile extends ThrowableProjectile {
    public PiranhaLauncherProjectile(EntityType<? extends PiranhaLauncherProjectile> type, Level level) {
        super(type, level);
    }

    public PiranhaLauncherProjectile(
            Level level,
            @Nullable LivingEntity thrower,
            PracticalFish fish,
            Vec3 position
    ) {
        this(PPEntities.PIRANHA_LAUNCHER_PROJECTILE.get(), level);
        this.setOwner(thrower);
        this.setFish(fish);
        this.setPos(position);
    }

    private boolean hasRider() {
        return !this.getPassengers().isEmpty();
    }

    @Nullable
    public PracticalFish getFish() {
        return this.hasRider() && this.getPassengers().get(0) instanceof PracticalFish fish ? fish : null;
    }

    public void setFish(PracticalFish fish) {
        this.ejectPassengers();
        fish.startRiding(this, true);
    }

    @Override
    public @NotNull EntityDimensions getDimensions(@NotNull Pose pose) {
        PracticalFish fish = this.getFish();
        if (fish != null)
            return fish.getDimensions(fish.getPose());
        return super.getDimensions(pose);
    }

    @Override
    protected void addPassenger(@NotNull Entity p_20349_) {
        super.addPassenger(p_20349_);
        this.refreshDimensions();
    }

    @Override
    protected void onHitEntity(@NotNull EntityHitResult entityHitResult) {
        super.onHitEntity(entityHitResult);
        PracticalFish fish = this.getFish();
        if (fish == null)
            return;
        Entity entity = entityHitResult.getEntity();
        entity.hurt(this.damageSources().thrown(this, this.getOwner()), 2);
        if (entity instanceof LivingEntity living && !fish.sharesOwnerWith(living)) {
            if (living.isAlive())
                fish.setTarget(living);
            fish.setDeltaMovement(Vec3.ZERO);
        }
    }

    @Override
    protected void onHit(HitResult hitResult) {
        boolean clientSide = this.level().isClientSide;
        if (!clientSide) {
            PracticalFish fish = this.getFish();
            if (fish != null) {
                Vec3 movement = this.getDeltaMovement();
                BlockState blockState = fish.getFeetBlockState();
                if (!blockState.isAir()) {
                    //The fish otherwise kinda looked like it was shot on ice when shooting it on the ground
                    movement = movement.scale(blockState.getFriction(level(), blockPosition(), fish));
                }
                fish.move(MoverType.SELF, movement);
                fish.setDeltaMovement(movement);
            }
        }
        super.onHit(hitResult);
        if (!clientSide) {
            this.ejectPassengers();
            this.discard();
        }
    }

    @Override
    protected void positionRider(Entity entity, MoveFunction moveFunction) {
        super.positionRider(entity, moveFunction);
        float yRot = -this.getYRot();
        entity.setYRot(yRot);
        entity.setYBodyRot(yRot);
        entity.setYHeadRot(yRot);
    }

    @Override
    public double getPassengersRidingOffset() {
        return 0;
    }

    @Override
    protected void defineSynchedData() {

    }

    @Override
    public void tick() {
        if (this.getFish() == null) {
            this.discard();
        }
        super.tick();
    }

    @Override
    protected boolean canHitEntity(Entity entity) {
        PracticalFish fish = this.getFish();
        return (
            super.canHitEntity(entity)
                && entity != this.getOwner()
                && entity != fish
                && !(fish != null && (fish.sharesOwnerWith(entity) || entity.getUUID().equals(fish.getOwnerUUID())))
        );
    }
}
