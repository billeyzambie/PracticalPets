package billeyzambie.practicalpets.entity.other;

import billeyzambie.practicalpets.entity.fish.base.PracticalFish;
import billeyzambie.practicalpets.misc.PPEntities;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
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
        entityHitResult.getEntity().hurt(this.damageSources().thrown(this, this.getOwner()), 2);
    }

    @Override
    protected void onHit(HitResult hitResult) {
        boolean clientSide = this.level().isClientSide;
        if (!clientSide) {
            PracticalFish fish = this.getFish();
            this.ejectPassengers();
            if (fish != null) {
                Vec3 movement = this.getDeltaMovement();
                fish.move(MoverType.SELF, movement);
                fish.setDeltaMovement(movement);
            }
        }
        super.onHit(hitResult);
        if (!clientSide) {
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
    protected void defineSynchedData() {

    }
}
