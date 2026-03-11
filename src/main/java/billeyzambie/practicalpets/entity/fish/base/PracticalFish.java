package billeyzambie.practicalpets.entity.fish.base;

import billeyzambie.animationcontrollers.ACData;
import billeyzambie.animationcontrollers.BVCData;
import billeyzambie.animationcontrollers.SwimmingAnimationEntity;
import billeyzambie.practicalpets.entity.WeightedVariantEntity;
import billeyzambie.practicalpets.items.PiranhaLauncher;
import billeyzambie.practicalpets.misc.PPEntities;
import billeyzambie.practicalpets.misc.PPSounds;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

public abstract class PracticalFish extends TamableFish implements SwimmingAnimationEntity, WeightedVariantEntity {
    HashMap<String, ACData> ACData = new HashMap<>();

    @Override
    public HashMap<String, ACData> getACData() {
        return ACData;
    }

    HashMap<String, BVCData> BVCData = new HashMap<>();

    @Override
    public HashMap<String, BVCData> getBVCData() {
        return BVCData;
    }

    private float prevLimbSwing = 0;

    @Override
    public float getPrevLimbSwing() {
        return prevLimbSwing;
    }

    @Override
    public void setPrevLimbSwing(float value) {
        this.prevLimbSwing = value;
    }

    private float swimSwing = 0;

    @Override
    public void addToSwimSwing(float value) {
        swimSwing += value;
    }

    @Override
    public float getSwimSwing() {
        return swimSwing;
    }

    private float swimSwingAmount = 0;

    @Override
    public float getSwimSwingAmount() {
        return swimSwingAmount;
    }

    @Override
    public void setSwimSwingAmount(float value) {
        this.swimSwingAmount = value;
    }

    private float swimXRot = 0;
    private float swimXRotO = 0;

    @Override
    public float getSwimXRot(float partialTicks) {
        return Mth.lerp(partialTicks, swimXRotO, swimXRot);
    }

    @Override
    public void setSwimXRot(float value) {
        this.swimXRot = value;
    }

    private float onAirTime = 0;

    @Override
    public float getOnAirTime() {
        return this.onAirTime;
    }

    @Override
    public void setOnAirTime(float value) {
        this.onAirTime = value;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide()) {
            this.swimXRotO = this.swimXRot;
            this.tickSwimAnim();
        }
    }

    public PracticalFish(EntityType<? extends PracticalFish> p_27523_, Level p_27524_) {
        super(p_27523_, p_27524_);
    }

    @Override
    protected @NotNull SoundEvent getFlopSound() {
        return SoundEvents.COD_FLOP;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.COD_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource p_28281_) {
        return SoundEvents.COD_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return PPSounds.FISH_DEATH.get();
    }

    @Override
    public final float getVoicePitch() {
        return this.isAlive()
                ? this.getCustomVoicePitch()
                : this.getDeathSoundPitch() - 0.1f + 0.2f * this.getRandom().nextFloat();
    }

    abstract protected float getDeathSoundPitch();

    private float getCustomVoicePitch() {
        return super.getVoicePitch();
    }

    private static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(PracticalFish.class, EntityDataSerializers.INT);

    @Override
    public int getVariant() {
        return this.entityData.get(VARIANT);
    }

    @Override
    public void setVariant(int variant) {
        this.entityData.set(VARIANT, variant);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(VARIANT, 0);
    }

    @Override
    protected final void loadCustomData(CompoundTag tag) {
        super.loadCustomData(tag);
        this.loadExtraData(tag);
    }

    @Override
    protected final void saveCustomData(CompoundTag tag) {
        super.saveCustomData(tag);
        this.saveExtraData(tag);
    }

    protected void loadExtraData(CompoundTag tag) {
        this.loadVariant(tag);
    }

    protected void saveExtraData(CompoundTag tag) {
        this.saveVariant(tag);
    }

    public final CompoundTag makePiranhaLauncherTag() {
        CompoundTag result = new CompoundTag();
        result.remove("display");

        this.saveExtraData(result);
        this.optimizePiranhaLauncherSave(result);

        if (this.getHealth() != this.getMaxHealth())
            result.putFloat("Health", this.getHealth());

        int age = this.getAge();
        if (age != 0)
            result.putInt("Age", age);

        Component component = this.getCustomName();
        if (component != null) {
            result.putString("CustomName", Component.Serializer.toJson(component));
        }


        String typeId = this.getEncodeId();
        if (typeId != null && !typeId.equals(PPEntities.PIRANHA.getId().toString())) {
            result.putString("id", typeId);
        }
        return result;
    }

    protected abstract void optimizePiranhaLauncherSave(CompoundTag tag);

    public static PracticalFish createFromPiranhaLauncherTag(
            ItemStack piranhaLauncher,
            CompoundTag fishTag,
            Level level,
            @Nullable LivingEntity thrower
    ) {
        if (!fishTag.contains("id", Tag.TAG_STRING)) {
            fishTag.putString("id", PPEntities.PIRANHA.getId().toString());
        }
        PracticalFish fish = (PracticalFish) EntityType.create(fishTag, level).orElseThrow();

        Player owner = null;
        if (thrower == null) {
            CompoundTag launcherTag = piranhaLauncher.getTag();
            if (launcherTag != null && launcherTag.hasUUID("LastOwnerUUID")) {
                fish.setTame(true);
                fish.setOwnerUUID(launcherTag.getUUID("LastOwnerUUID"));
            }
        } else if (thrower instanceof Player player) {
            owner = player;
        } else if (thrower instanceof OwnableEntity pet && pet.getOwner() instanceof Player petOwner) {
            owner = petOwner;
        }
        if (owner != null) {
            fish.setTame(true);
            fish.setOwnerUUID(owner.getUUID());
        }
        return fish;
    }

    @Override
    public @Nullable SpawnGroupData finalizeSpawn(ServerLevelAccessor p_27528_, DifficultyInstance p_27529_, MobSpawnType p_27530_, @Nullable SpawnGroupData p_27531_, @Nullable CompoundTag p_27532_) {
        this.setVariant(this.pickRandomWeightedVariant());
        return super.finalizeSpawn(p_27528_, p_27529_, p_27530_, p_27531_, p_27532_);
    }

    private AvoidEntityGoal<Player> avoidPlayersGoal;

    @Override
    protected void reassessTameGoals() {
        if (this.avoidPlayersGoal == null) {
            this.avoidPlayersGoal = new AvoidEntityGoal<>(this, Player.class, 8.0F, 1.25D, 1.1D, EntitySelector.NO_SPECTATORS::test);
        }

        this.goalSelector.removeGoal(this.avoidPlayersGoal);
        if (!this.isTame()) {
            this.goalSelector.addGoal(4, this.avoidPlayersGoal);
        }

    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.25D, true));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.25D));
        this.goalSelector.addGoal(6, new FishSwimGoal(this));
        this.goalSelector.addGoal(8, new FollowFlockLeaderGoal(this));
        HurtByTargetGoal hurtByTargetGoal = new HurtByTargetGoal(this);
        if (shouldRegisterAlertOthers())
            hurtByTargetGoal = hurtByTargetGoal.setAlertOthers();
        this.targetSelector.addGoal(1, hurtByTargetGoal);
        this.targetSelector.addGoal(5, new CopyFlockLeaderTargetGoal(this));
    }

    protected boolean shouldRegisterAlertOthers() {
        return false;
    }

    @Override
    protected void usePlayerItem(@NotNull Player player, @NotNull InteractionHand hand, @NotNull ItemStack itemStack) {
        super.usePlayerItem(player, hand, itemStack);

        if (this.isFood(itemStack)) {
            this.playSound(SoundEvents.GENERIC_EAT, 1.0F, 1.0F);
        }
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        boolean clientSide = this.level().isClientSide();
        if (this.isFood(stack)) {
            if (this.getHealth() < this.getMaxHealth()) {
                if (!clientSide) {
                    FoodProperties foodProperties = stack.getFoodProperties(this);
                    float healAmount = foodProperties == null ? 2
                            : foodProperties.getNutrition();
                    this.heal(healAmount);
                }
                return InteractionResult.sidedSuccess(clientSide);
            }
            if (!this.isTame()) {
                if (!clientSide) {
                    this.usePlayerItem(player, hand, stack);
                    if (this.random.nextInt(3) == 0) {
                        this.tame(player);
                        //Tame heart particle
                        this.level().broadcastEntityEvent(this, (byte) 7);
                    } else {
                        //Fail particle
                        this.level().broadcastEntityEvent(this, (byte) 6);
                    }
                }
                return InteractionResult.sidedSuccess(clientSide);
            }
        } else if (stack.getItem() instanceof PiranhaLauncher piranhaLauncher) {
            if (piranhaLauncher.tryInsertFish(stack, this, player)) {
                return InteractionResult.sidedSuccess(clientSide);
            }
        }
        return super.mobInteract(player, hand);
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        Entity entity = source.getEntity();
        if (entity instanceof Player player) {
            ItemStack mainHandStack = player.getMainHandItem();
            if (
                    mainHandStack.getItem() instanceof PiranhaLauncher launcherItem
                    && launcherItem.tryInsertFish(mainHandStack, this, player)
            ) {
                return false;
            }
        }
        if (entity instanceof LivingEntity living && this.isOwnedBy(living) && !living.isShiftKeyDown())
            return false;
        return super.hurt(source, amount);
    }
}