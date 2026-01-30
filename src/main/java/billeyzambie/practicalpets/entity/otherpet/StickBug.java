package billeyzambie.practicalpets.entity.otherpet;

import billeyzambie.practicalpets.entity.DancingEntity;
import billeyzambie.practicalpets.entity.PracticalPet;
import billeyzambie.practicalpets.misc.PPDamageTypes;
import billeyzambie.practicalpets.misc.PPTags;
import billeyzambie.practicalpets.util.PPUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.Tags;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

public class StickBug extends PracticalPet implements DancingEntity {
    public float flap;
    public float flapSpeed;
    public float oFlapSpeed;
    public float oFlap;
    private float flapping = 1.0F;
    private float nextFlap = 1.0F;
    private boolean dancingToJukebox;
    @Nullable
    private BlockPos jukebox;

    public StickBug(EntityType<? extends TamableAnimal> entityType, Level level) {
        super(entityType, level);
    }

    private static final HashMap<Integer, Integer> VARIANT_SPAWN_WEIGHTS = new HashMap<>() {{
        put(0, 49);
        put(1, 25);
        put(2, 25);
        put(3, 1);
    }};

    @Override
    public HashMap<Integer, Integer> variantSpawnWeights() {
        return VARIANT_SPAWN_WEIGHTS;
    }

    @Override
    public int getLevel1MaxHealth() {
        return 2;
    }

    @Override
    public int getLevel1AttackDamage() {
        return 1;
    }

    @Override
    public int getLevel10MaxHealth() {
        return 20;
    }

    @Override
    public int getLevel10AttackDamage() {
        return 5;
    }

    @Override
    public float headSizeX() {
        return 3;
    }

    @Override
    public float headSizeY() {
        return 2;
    }

    @Override
    public float headSizeZ() {
        return 3;
    }

    @Override
    public boolean shouldDefendOwner(@NotNull LivingEntity target) {
        return true;
    }

    @Override
    public boolean shouldDefendSelf() {
        return true;
    }

    @Override
    public boolean shouldPanic() {
        return true;
    }

    @Override
    public boolean isTameItem(ItemStack itemStack) {
        return itemStack.is(Tags.Items.CROPS)
                || itemStack.is(Tags.Items.SEEDS)
                || itemStack.is(PPTags.Items.FRUITS)
                || super.isTameItem(itemStack);
    }

    @Override
    public boolean doHurtTarget(@NotNull Entity entity) {
        float f = (float)this.getAttributeValue(Attributes.ATTACK_DAMAGE);
        float f1 = (float)this.getAttributeValue(Attributes.ATTACK_KNOCKBACK);
        if (entity instanceof LivingEntity) {
            f += EnchantmentHelper.getDamageBonus(this.getMainHandItem(), ((LivingEntity)entity).getMobType());
            f1 += (float)EnchantmentHelper.getKnockbackBonus(this);
        }

        int i = EnchantmentHelper.getFireAspect(this);
        if (i > 0) {
            entity.setSecondsOnFire(i * 4);
        }

        boolean flag = this.damageEntity(entity, f);
        if (flag) {
            if (f1 > 0.0F && entity instanceof LivingEntity) {
                ((LivingEntity)entity).knockback(f1 * 0.5F, Mth.sin(this.getYRot() * ((float)Math.PI / 180F)), -Mth.cos(this.getYRot() * ((float)Math.PI / 180F)));
                this.setDeltaMovement(this.getDeltaMovement().multiply(0.6D, 1.0D, 0.6D));
            }

            this.setLastHurtMob(entity);
        }

        this.doHurtEffect(entity, flag);

        return flag;
    }

    @Override
    public boolean damageEntity(Entity target, float amount) {
        return target.hurt(PPDamageTypes.stickBuggedDamage((ServerLevel) this.level()), amount);
    }

    @Override
    public void aiStep() {
        if (this.jukebox == null || !this.jukebox.closerToCenterThan(this.position(), 3.46D) || !this.level().getBlockState(this.jukebox).is(Blocks.JUKEBOX)) {
            this.dancingToJukebox = false;
            this.jukebox = null;
        }

        super.aiStep();
        this.calculateFlapping();
    }

    @Override
    public void setRecordPlayingNearby(BlockPos p_29395_, boolean p_29396_) {
        this.jukebox = p_29395_;
        this.dancingToJukebox = p_29396_;
    }

    public boolean isDancingToJukebox() {
        return this.dancingToJukebox;
    }

    private void calculateFlapping() {
        this.oFlap = this.flap;
        this.oFlapSpeed = this.flapSpeed;
        this.flapSpeed += (float)(this.shouldFlap() ? 4 : -1) * 0.3F;
        this.flapSpeed = Mth.clamp(this.flapSpeed, 0.0F, 1.0F);
        if (this.shouldFlap() && this.flapping < 1.0F) {
            this.flapping = 1.0F;
        }

        this.flapping *= 0.9F;
        Vec3 vec3 = this.getDeltaMovement();
        if (!this.onGround() && vec3.y < 0.0D) {
            this.setDeltaMovement(vec3.multiply(1.0D, 0.6D, 1.0D));
        }

        this.flap += this.flapping * 2.0F;
    }

    public boolean shouldFlap() {
        return !this.onGround()|| this.hasTarget();
    }

    @Override
    protected boolean isFlapping() {
        return this.flyDist > this.nextFlap;
    }

    @Override
    protected void onFlap() {
        this.nextFlap = this.flyDist + this.flapSpeed / 2.0F;
    }

    private boolean isRandomDancing() {
        //TODO: implement stick bug random dancing
        return false;
    }

    @Override
    public boolean isDancing() {
        return this.isDancingToJukebox() || this.isRandomDancing();
    }


    @Override
    protected @Nullable SoundEvent getAmbientSound() {
        return SoundEvents.SHULKER_AMBIENT;
    }

    @Override
    protected @Nullable SoundEvent getHurtSound(@NotNull DamageSource damageSource) {
        return SoundEvents.SHULKER_HURT;
    }

    @Override
    protected @Nullable SoundEvent getDeathSound() {
        return SoundEvents.SHULKER_DEATH;
    }

    @Override
    public float getVoicePitch() {
        return super.getVoicePitch() * 3.6f;
    }
}
