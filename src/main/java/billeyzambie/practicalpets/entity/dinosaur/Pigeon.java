package billeyzambie.practicalpets.entity.dinosaur;

import billeyzambie.practicalpets.entity.PracticalPet;
import billeyzambie.practicalpets.goal.*;
import billeyzambie.practicalpets.items.PetBackpack;
import billeyzambie.practicalpets.misc.PPEntities;
import billeyzambie.practicalpets.misc.PPSerializers;
import billeyzambie.practicalpets.misc.PPSounds;
import billeyzambie.practicalpets.util.PPUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.*;
import net.minecraftforge.common.Tags;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Pigeon extends PracticalPet {
    public Pigeon(EntityType<? extends TamableAnimal> entityType, Level level) {
        super(entityType, level);
    }

    private static final HashMap<Integer, Integer> VARIANT_SPAWN_WEIGHTS = new HashMap<>() {{
        put(0, 48);
        put(1, 4);
        put(2, 24);
        put(3, 8);
        put(4, 8);
        put(5, 8);
        put(6, 1);
    }};

    @Override
    public HashMap<Integer, Integer> variantSpawnWeights() {
        return VARIANT_SPAWN_WEIGHTS;
    }

    @Override
    protected @NotNull ResourceLocation getDefaultLootTable() {
        return PPUtil.CHICKEN_LOOT;
    }

    @Override
    public int getLevel1MaxHealth() {
        return 4;
    }

    @Override
    public int getLevel1AttackDamage() {
        return 1;
    }

    @Override
    public int getLevel10MaxHealth() {
        return 60;
    }

    @Override
    public int getLevel10AttackDamage() {
        return 12;
    }

    @Override
    public float headSizeX() {
        return 3;
    }

    @Override
    public float headSizeY() {
        return 3;
    }

    @Override
    public float headSizeZ() {
        return 3;
    }

    @Override
    public boolean isTameItem(ItemStack itemStack) {
        return itemStack.is(Tags.Items.SEEDS)
                || itemStack.is(Tags.Items.CROPS_CARROT)
                || itemStack.is(Items.MELON_SLICE)
                || itemStack.is(Items.GLISTERING_MELON_SLICE)
                || super.isTameItem(itemStack);
    }

    @Override
    public HealOverride healOverride(ItemStack itemStack) {
        //Seeds and glistering melon aren't edible so a healOverride must be defined
        if (itemStack.is(Tags.Items.SEEDS))
            return HealOverride.defineNutrition(2);
        if (itemStack.is(Items.GLISTERING_MELON_SLICE))
            return HealOverride.defineNutrition(6);
        //Bread isn't very good for pigeons
        if (itemStack.is(Items.BREAD))
            return HealOverride.override(1);
        return super.healOverride(itemStack);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return PPSounds.PIGEON_AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource damageSource) {
        return PPSounds.PIGEON_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return PPSounds.PIGEON_HURT.get();
    }

    @Override
    public float getVoicePitch() {
        float voicePitch = super.getVoicePitch();
        //make the death sound deeper than the hit sound
        if (!this.isAlive())
            voicePitch *= 0.6f;
        return voicePitch;
    }

    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob partner) {
        Pigeon baby = PPEntities.PIGEON.get().create(level);

        if (baby != null) {
            if (this.isTame()) {
                baby.setOwnerUUID(this.getOwnerUUID());
                baby.setTame(true);
            }

            if (partner instanceof Pigeon pigeonPartner) {
                if (this.random.nextBoolean())
                    baby.setVariant(this.getVariant());
                else
                    baby.setVariant(pigeonPartner.getVariant());
            }
        }

        return baby;
    }

    @Override
    protected PathNavigation createNavigation(Level level) {
        if (this.isInWalkMode()) {
            return super.createNavigation(level);
        } else {
            FlyingPathNavigation flyingpathnavigation = new FlyingPathNavigation(this, level);
            flyingpathnavigation.setCanOpenDoors(false);
            flyingpathnavigation.setCanFloat(true);
            flyingpathnavigation.setCanPassDoors(true);
            return flyingpathnavigation;
        }
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (this.getMissionPhase() == MissionPhase.TRAVELING) {
            return false;
        }
        boolean result = super.hurt(source, amount);
        if (result && this.isInWalkMode()) {
            this.toFlyMode();
        }
        return result;
    }

    @Override
    public void setTarget(@Nullable LivingEntity p_21544_) {
        super.setTarget(p_21544_);
        if (this.getTarget() != null && this.isInWalkMode()) {
            this.toFlyMode();
        }
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new PigeonFlyAroundGoal(this, this.createPanicSpeedMultiplier()));
        this.goalSelector.addGoal(52, new DropHeldItemToOwnerGoal(this, this.createFollowOwnerSpeed(), true));
        this.goalSelector.addGoal(51, new PigeonGetDroppedItemGoal(this, this.createFollowOwnerSpeed()));
    }

    @Override
    protected @Nullable Goal createStrollGoal() {
        if (this.isInWalkMode())
            return super.createStrollGoal();
        else
            return new ParrotWanderGoal(this, 1f);
    }

    @Override
    protected @Nullable Goal createPanicGoal() {
        return new FlyPanicGoal(this, this.createPanicSpeedMultiplier());
    }

    //copied from vanilla chicken
    public float flap;
    public float flapSpeed;
    public float oFlapSpeed;
    public float oFlap;
    public float flapping = 1.0F;
    private float nextFlap = 1.0F;

    private int pickRandomBiteFloorTime() {
        return this.random.nextInt(600, 1200);
    }

    private int timeToBiteFloor = this.pickRandomBiteFloorTime();

    public enum MovementMode {WALKING, FLYING, IN_MISSION}

    private MovementMode movementMode = MovementMode.WALKING;

    public boolean isInWalkMode() {
        return this.movementMode == MovementMode.WALKING;
    }

    public void toWalkMode() {
        movementMode = MovementMode.WALKING;
        this.moveControl = new MoveControl(this);
        this.navigation = this.createNavigation(this.level());
        this.movementSwitchTime = this.pickRandomMovementSwitchTime();
        this.refreshStrollGoal();
    }

    public void toFlyMode() {
        movementMode = MovementMode.FLYING;
        this.setFlyingMovement();
        this.movementSwitchTime = this.pickRandomMovementSwitchTime();
        this.refreshStrollGoal();
    }

    private void setFlyingMovement() {
        this.moveControl = new FlyingMoveControl(this, 10, false);
        this.navigation = this.createNavigation(this.level());
    }

    private int pickRandomMovementSwitchTime() {
        return this.isInWalkMode() ? this.random.nextInt(300, 600) : 100;
    }

    private int movementSwitchTime = this.pickRandomMovementSwitchTime();

    public enum MissionPhase {NONE, STARTED, TRAVELING, ARRIVED}

    /*Mission phase needs to be on the client too, to make the pigeon invisible
     while it's "traveling"*/
    private static final EntityDataAccessor<MissionPhase> MISSION_PHASE = SynchedEntityData.defineId(
            Pigeon.class,
            PPSerializers.PIGEON_MISSION_PHASE.get()
    );

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(MISSION_PHASE, MissionPhase.NONE);
    }

    public MissionPhase getMissionPhase() {
        return this.entityData.get(MISSION_PHASE);
    }

    public void setMissionPhase(MissionPhase missionPhase) {
        this.entityData.set(MISSION_PHASE, missionPhase);
    }

    public boolean isInMission() {
        return this.getMissionPhase() != MissionPhase.NONE;
    }

    @Override
    public boolean shouldFollowOwner() {
        return super.shouldFollowOwner() && !this.isInMission() && this.getTargetItemEntity() == null;
    }

    private UUID missionTargetUUID;
    private String missionTargetName;
    //So that the game can tell the target the sender's name even if the sender left.
    private String missionOwnerName;
    private ResourceLocation missionOriginDimension;
    private BlockPos missionOriginPosition;

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.timeToBiteFloor = compoundTag.getInt("TimeToBiteFloor");
        this.movementMode = MovementMode.values()[compoundTag.getInt("MovementMode")];
        this.movementSwitchTime = compoundTag.getInt("MovementSwitchTime");
        this.setMissionPhase(MissionPhase.values()[compoundTag.getInt("MissionPhase")]);

        if (compoundTag.hasUUID("MissionTargetUUID")) {
            this.missionTargetUUID = compoundTag.getUUID("MissionTargetUUID");
            this.missionTargetName = compoundTag.getString("MissionTargetName");
            this.missionOwnerName = compoundTag.getString("MissionOwnerName");
            this.missionOriginPosition = new BlockPos(
                    compoundTag.getInt("MissionOriginX"),
                    compoundTag.getInt("MissionOriginY"),
                    compoundTag.getInt("MissionOriginZ")
            );
            this.missionOriginDimension = new ResourceLocation(compoundTag.getString("MissionOriginDimension"));
        }

        if (
                compoundTag.hasUUID("TargetItemEntityUUID")
                        && this.level() instanceof ServerLevel level
                        && level.getEntity(compoundTag.getUUID("TargetItemEntityUUID"))
                        instanceof ItemEntity itemEntity
        ) {
            this.targetItemEntity = itemEntity;
        }

        if (!this.isInWalkMode()) {
            this.setFlyingMovement();
        }

        this.refreshStrollGoal();
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("TimeToBiteFloor", timeToBiteFloor);
        compoundTag.putInt("MovementMode", movementMode.ordinal());
        compoundTag.putInt("MovementSwitchTime", movementSwitchTime);
        compoundTag.putInt("MissionPhase", this.getMissionPhase().ordinal());
        if (missionTargetUUID != null) {
            compoundTag.putUUID("MissionTargetUUID", missionTargetUUID);
            compoundTag.putString("MissionTargetName", missionTargetName);
            compoundTag.putString("MissionOwnerName", missionOwnerName);
            compoundTag.putString("MissionOriginDimension", this.missionOriginDimension.toString());
            compoundTag.putInt("MissionOriginX", this.missionOriginPosition.getX());
            compoundTag.putInt("MissionOriginY", this.missionOriginPosition.getY());
            compoundTag.putInt("MissionOriginZ", this.missionOriginPosition.getZ());
        }

        if (targetItemEntity != null) {
            compoundTag.putUUID("TargetItemEntityUUID", targetItemEntity.getUUID());
        }
    }

    @Override
    protected void customServerAiStep() {
        super.customServerAiStep();

        if (targetItemEntity != null && targetItemEntity.isRemoved())
            this.targetItemEntity = null;

        switch (this.movementMode) {
            case WALKING -> {
                Path path = this.getNavigation().getPath();
                if (path != null) {
                    BlockPos target = path.getTarget();
                    if (
                            target.getY() >= this.getY() + 1.5
                                    || target.getY() < this.getY() - 1.5
                                    || this.blockPosition().distToCenterSqr(target.getCenter()) > 100
                    ) {
                        this.toFlyMode();
                    }
                }

                if (!this.onGround())
                    --this.movementSwitchTime;

                if (--this.movementSwitchTime <= 0 || this.targetItemEntity != null) {
                    this.toFlyMode();
                }

            }
            case FLYING -> {
                if (--this.movementSwitchTime <= 0 && this.onGround() && !this.hasTarget() && this.targetItemEntity == null) {
                    this.toWalkMode();
                    this.setDeltaMovement(Vec3.ZERO);
                }
            }
            case IN_MISSION -> {
                this.tickMission();
            }
        }

        if (--this.timeToBiteFloor <= 0 && this.getNavigation().isDone()) {
            if (!this.isInSittingPose() && this.isInWalkMode() && this.onGround())
                this.sendRandomIdle1Packet();
            this.timeToBiteFloor = this.pickRandomBiteFloorTime();
        }
    }

    @Override
    public void aiStep() {
        super.aiStep();

        this.updateSwingTime();

        //Copied from vanilla chicken
        this.oFlap = this.flap;
        this.oFlapSpeed = this.flapSpeed;
        this.flapSpeed += (!this.onGround() ? 4.0F : -1.0F) * 0.3F;
        this.flapSpeed = Mth.clamp(this.flapSpeed, 0.0F, 1.0F);
        if (!this.onGround() && this.flapping < 1.0F) {
            this.flapping = 1.0F;
        }
        this.flapping *= 0.9F;
        this.flap += this.flapping * 2.0F;

        Vec3 vec3 = this.getDeltaMovement();
        if (!this.onGround() && vec3.y < 0.0D) {
            this.setDeltaMovement(vec3.multiply(1.0D, 0.6D, 1.0D));
        }
    }

    @Override
    public boolean isInvisible() {
        return this.getMissionPhase() == MissionPhase.TRAVELING || super.isInvisible();
    }

    @Override
    public boolean hideEquipment() {
        return this.getMissionPhase() == MissionPhase.TRAVELING || super.hideEquipment();
    }

    @Override
    public @NotNull InteractionResult mobInteract(Player player, @NotNull InteractionHand hand) {
        return this.isInMission() ? InteractionResult.PASS : super.mobInteract(player, hand);
    }

    private void tickMission() {
        switch (this.getMissionPhase()) {
            case STARTED -> {
                if (--this.movementSwitchTime <= 0) {
                    this.movementSwitchTime = this.pickRandomMovementSwitchTime();
                    this.setMissionPhase(MissionPhase.TRAVELING);
                }
            }
            case TRAVELING -> {
                if (--this.movementSwitchTime <= 0) {
                    this.movementSwitchTime = this.pickRandomMovementSwitchTime();
                    this.setMissionPhase(MissionPhase.ARRIVED);
                    Player target = this.level().getPlayerByUUID(this.missionTargetUUID);
                    if (target == null) {
                        if (this.getOwner() != null) {
                            this.getOwner().sendSystemMessage(Component.translatable(
                                    "ui.practicalpets.pigeon_send.target_left",
                                    this.missionTargetName
                            ).withStyle(ChatFormatting.RED));
                        }
                        this.returnFromMission();
                    } else {
                        ItemStack backStack = this.getBackItem();
                        Item backItem = backStack.getItem();
                        if (
                                backItem instanceof PetBackpack backpack
                                        && backpack.getContentWeight(backStack) > 0
                        ) {
                            backpack.dropContents(backStack, target);
                            target.sendSystemMessage(Component.translatable(
                                    "ui.practicalpets.pigeon_send.target_success",
                                    this.missionOwnerName,
                                    this.getDisplayName()
                            ).withStyle(ChatFormatting.GREEN));
                            this.teleportTo(target.getX(), target.getY(), target.getZ());
                        } else {
                            this.returnFromMission();
                        }

                    }
                }
            }
            case ARRIVED -> {
                if (--this.movementSwitchTime <= 0) {
                    this.returnFromMission();
                    LivingEntity owner = this.getOwner();
                    if (!(owner instanceof Player player))
                        return;
                    player.sendSystemMessage(Component.translatable(
                            "ui.practicalpets.pigeon_send.sender_success",
                            this.getDisplayName(),
                            this.missionTargetName
                    ).withStyle(ChatFormatting.GREEN));
                }
            }
        }
    }

    public void startMission(Player target) {
        LivingEntity owner = this.getOwner();
        if (!(owner instanceof Player player))
            return;
        this.missionOwnerName = player.getDisplayName().getString();

        this.toFlyMode();
        this.movementMode = MovementMode.IN_MISSION;
        this.setMissionPhase(MissionPhase.STARTED);

        this.missionTargetUUID = target.getUUID();
        this.missionTargetName = target.getDisplayName().getString();

        this.missionOriginDimension = this.level().dimension().location();
        this.missionOriginPosition = this.blockPosition();

        this.setFollowMode(FollowMode.FOLLOWING);
    }

    public void returnFromMission() {
        this.setMissionPhase(MissionPhase.NONE);
        this.toFlyMode();
        this.setFollowMode(FollowMode.FOLLOWING);
        MinecraftServer server = this.getServer();
        if (server == null)
            return;
        ResourceKey<Level> targetDimension = ResourceKey.create(
                Registries.DIMENSION,
                this.missionOriginDimension
        );
        ServerLevel targetLevel = server.getLevel(targetDimension);
        if (targetLevel == null)
            return;
        Entity entity = this.changeDimension(targetLevel);
        Pigeon pigeonToTeleport = entity instanceof Pigeon pigeon ? pigeon : this;
        if (pigeonToTeleport.level().dimension().equals(targetDimension))
            pigeonToTeleport.teleportTo(
                    this.missionOriginPosition.getX() + 0.5,
                    this.missionOriginPosition.getY(),
                    this.missionOriginPosition.getZ() + 0.5
            );
    }

    public boolean isInvalidMissionTarget(Player target) {
        return target.distanceToSqr(this) < 32 * 32 || !this.level().dimension().equals(target.level().dimension());
    }

    @Nullable
    private ItemEntity targetItemEntity;

    @Nullable
    public ItemEntity getTargetItemEntity() {
        return this.targetItemEntity;
    }

    public void removeTargetItemEntity() {
        this.targetItemEntity = null;
    }

    @Override
    public boolean shouldDropHeldItemToOwner() {
        return true;
    }

    public static void makePigeonPickUpTargetItem(ServerPlayer player) {
        HitResult hit = player.pick(32f, 0f, false);
        if (
                hit instanceof BlockHitResult blockHit
        ) {
            AABB itemBox = AABB.ofSize(blockHit.getLocation(), 1.1, 1.1, 1.1);
            List<ItemEntity> itemEntities = player.level().getEntitiesOfClass(ItemEntity.class, itemBox);
            if (itemEntities.isEmpty())
                return;
            ItemEntity itemEntity = itemEntities.get(0);
            if (itemEntity != null) {
                player.level().getEntitiesOfClass(
                                Pigeon.class,
                                player.getBoundingBox().inflate(64),
                                p -> p.isOwnedBy(player) && !p.isOrderedToSit() && p.targetItemEntity == null && p.isAlive()
                        ).stream().min(Comparator.comparingDouble(p -> p.distanceToSqr(player)))

                        .ifPresent(pigeon -> pigeon.targetItemEntity = itemEntity);
            }
        }
    }

}
