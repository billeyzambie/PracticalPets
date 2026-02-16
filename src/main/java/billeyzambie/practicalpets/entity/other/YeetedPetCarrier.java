package billeyzambie.practicalpets.entity.other;

import billeyzambie.practicalpets.entity.otherpet.GiraffeCat;
import billeyzambie.practicalpets.misc.PPEntities;
import billeyzambie.practicalpets.misc.PPEvents;
import billeyzambie.practicalpets.util.PPUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class YeetedPetCarrier extends Projectile {

    public static final int BLOCKS_THAT_TAKE_A_SECOND_TO_REACH = 10;

    public YeetedPetCarrier(EntityType<? extends YeetedPetCarrier> p_37391_, Level p_37392_) {
        super(p_37391_, p_37392_);
        this.setUpBoundingBox();
    }

    public YeetedPetCarrier(GiraffeCat owner, TamableAnimal pet, LivingEntity target) {
        this(PPEntities.THROWN_PET_CARRIER.get(), owner.level());
        this.setPos(owner.getX(), owner.getY() + owner.getPassengersRidingOffset(), owner.getZ());
        this.setOwner(owner);
        this.target = target;
        pet.startRiding(this, true);
        this.setUpBoundingBox();

        Vec3 targetPosition = target.position();
        float yeetTime = 20 * (
                (float) Math.sqrt(owner.distanceToSqr(targetPosition))
        ) / BLOCKS_THAT_TAKE_A_SECOND_TO_REACH;
        Vec3 toPosition = targetPosition.add(target.getDeltaMovement().scale(yeetTime));
        Vec3 fromPosition = owner.position();
        float velocityY = (float) (toPosition.y / yeetTime
                - fromPosition.y / yeetTime
                + 0.5 * this.getGravity() * yeetTime);
        this.setDeltaMovement(
                (toPosition.x - fromPosition.x) / yeetTime,
                velocityY,
                (toPosition.z - fromPosition.z) / yeetTime
        );
    }

    private void setUpBoundingBox() {
        TamableAnimal pet = this.getPet();
        if (pet != null)
            this.setBoundingBox(pet.getBoundingBox());
    }

    private boolean hasRider() {
        return !this.getPassengers().isEmpty();
    }

    @Nullable
    private TamableAnimal getPet() {
        if (this.hasRider() && this.getPassengers().get(0) instanceof TamableAnimal pet) {
            return pet;
        }
        return null;
    }

    @Override
    public double getPassengersRidingOffset() {
        return 0;
    }

    @Override
    public boolean shouldRiderSit() {
        return false;
    }

    private LivingEntity target;

    @Override
    protected void addAdditionalSaveData(@NotNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        if (this.target != null)
            tag.putUUID("TargetUUID", this.target.getUUID());
    }

    @Override
    protected void readAdditionalSaveData(@NotNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.setUpBoundingBox();
        if (tag.hasUUID("TargetUUID")
                && this.level() instanceof ServerLevel serverLevel
                && serverLevel.getEntity(tag.getUUID("TargetUUID")) instanceof LivingEntity living
        ) {
            this.target = living;
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult p_37258_) {
        super.onHitBlock(p_37258_);
        TamableAnimal pet = this.getPet();
        if (pet == null)
            return;
        pet.setDeltaMovement(this.getDeltaMovement());
        if (this.target == null)
            return;
        if (pet.getNavigation().createPath( target, 1) != null) {
            this.copyTargetTo(pet);
        }
    }

    @Override
    protected void onHitEntity(@NotNull EntityHitResult hitResult) {
        super.onHitEntity(hitResult);
        Entity entity = hitResult.getEntity();
        TamableAnimal pet = this.getPet();
        if (pet == null)
            return;
        if (!(this.getOwner() instanceof GiraffeCat giraffeCat))
            return;
        AttributeInstance petAttack = pet.getAttribute(Attributes.ATTACK_DAMAGE);
        if (petAttack != null) {
            if (entity == this.target) {
                this.copyTargetTo(pet);
                pet.doHurtTarget(target);
            }
        }
        giraffeCat.doHurtTarget(entity);
    }

    private void copyTargetTo(TamableAnimal pet) {
        pet.setTarget(target);
        PPEvents.yeetedPetsThatGotTarget.add(pet);
        //Probably does nothing
        pet.getNavigation().stop();
    }

    @Override
    protected void onHit(@NotNull HitResult p_37406_) {
        super.onHit(p_37406_);
        if (!this.level().isClientSide) {
            this.discard();
        }
    }

    @Override
    protected boolean canHitEntity(@NotNull Entity entity) {
        if (this.getPet() != null) {
            if (entity instanceof TamableAnimal pet && PPUtil.petsShareOwner(this.getPet(), pet))
                return false;
            if (this.getPet().isAlliedTo(entity))
                return false;
        }
        return super.canHitEntity(entity);
    }

    @Override
    protected void defineSynchedData() {

    }

    protected float getGravity() {
        return 0.08f;
    }

    @Override
    public void tick() {
        if (!this.hasRider() || !this.getPet().isAlive())
            this.discard();
        super.tick();
        HitResult hitresult = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity);

        if (hitresult.getType() != HitResult.Type.MISS && !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, hitresult)) {
            //Undo the last position change to fix thrown pets sometimes suffocating
            //Not sure if it worked
            Vec3 vec3 = this.getDeltaMovement();
            double d2 = this.getX() - vec3.x;
            double d0 = this.getY() - vec3.y;
            double d1 = this.getZ() - vec3.z;
            this.setPos(d2, d0, d1);
            this.onHit(hitresult);
        }

        this.checkInsideBlocks();
        Vec3 vec3 = this.getDeltaMovement();
        double d2 = this.getX() + vec3.x;
        double d0 = this.getY() + vec3.y;
        double d1 = this.getZ() + vec3.z;
        this.updateRotation();

        if (!this.isNoGravity()) {
            Vec3 vec31 = this.getDeltaMovement();
            this.setDeltaMovement(vec31.x, vec31.y - (double) this.getGravity(), vec31.z);
        }

        //No air resistance so that the physics equation to throw the pet exactly
        // in public ThrownPetCarrier(GiraffeCat owner, TamableAnimal pet, LivingEntity target) was solvable

        this.setPos(d2, d0, d1);
    }
}