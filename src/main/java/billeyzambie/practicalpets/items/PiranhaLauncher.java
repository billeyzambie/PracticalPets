package billeyzambie.practicalpets.items;

import billeyzambie.animationcontrollers.ACEntity;
import billeyzambie.animationcontrollers.PracticalPetModel;
import billeyzambie.practicalpets.client.layer.PetEquipmentLayer;
import billeyzambie.practicalpets.entity.PracticalPet;
import billeyzambie.practicalpets.entity.fish.Piranha;
import billeyzambie.practicalpets.entity.fish.base.PracticalFish;
import billeyzambie.practicalpets.entity.other.PiranhaLauncherProjectile;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PiranhaLauncher extends Item implements ItemModelPetCosmetic, DyeableItem {
    public PiranhaLauncher() {
        super(new Properties().stacksTo(1).durability(256));
    }

    public static int getFishCount(ItemStack stack) {
        return getFishCount(stack.getOrCreateTag());
    }

    public static int getFishCount(CompoundTag launcherTag) {
        return launcherTag.getInt("FishCount");
    }

    public static boolean isLoaded(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        if (tag == null)
            return false;
        return getFishCount(tag) > 0;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        CompoundTag launcherTag = itemstack.getOrCreateTag();
        if (getFishCount(launcherTag) < 1) {
            return InteractionResultHolder.pass(itemstack);
        }
        player.getCooldowns().addCooldown(this, 10);
        if (!level.isClientSide()) {
            this.shoot(itemstack, launcherTag, level, player, player.position());
            itemstack.hurtAndBreak(1, player, p -> {
                p.broadcastBreakEvent(hand);
                this.dropAllFish(itemstack, launcherTag, p.level(), p, p.position());
            });
        }

        player.awardStat(Stats.ITEM_USED.get(this));

        return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
    }

    public void dropAllFish(
            ItemStack piranhaLauncher,
            CompoundTag launcherTag,
            Level level,
            @Nullable LivingEntity thrower,
            Vec3 throwerPosition
    ) {
        ListTag fishes = launcherTag.getList("Fishes", Tag.TAG_COMPOUND);
        if (fishes.isEmpty())
            return;

        launcherTag.remove("Fishes");

        for (Tag fishTag : fishes) {
            PracticalFish fish = PracticalFish.createFromPiranhaLauncherTag(
                    piranhaLauncher,
                    (CompoundTag) fishTag,
                    level,
                    thrower
            );
            fish.setPos(throwerPosition);
            level.addFreshEntity(fish);
        }
    }

    @Nullable
    public Projectile shoot(
            ItemStack piranhaLauncher,
            CompoundTag launcherTag,
            Level level,
            @Nullable LivingEntity thrower,
            Vec3 throwerPosition
    ) {
        ListTag fishes = launcherTag.getList("Fishes", Tag.TAG_COMPOUND);
        if (fishes.isEmpty())
            return null;

        CompoundTag fishTag = (CompoundTag) fishes.remove(fishes.size() - 1);
        launcherTag.put("Fishes", fishes);

        launcherTag.putInt("FishCount", fishes.size());

        PracticalFish fish = PracticalFish.createFromPiranhaLauncherTag(
                piranhaLauncher,
                fishTag,
                level,
                thrower
        );
        fish.setPos(throwerPosition);
        level.addFreshEntity(fish);

        Vec3 projectilePosition;
        if (thrower != null) {
            projectilePosition = thrower.getEyePosition().add(0, -0.2,0);
        } else {
            projectilePosition = throwerPosition;
        }

        PiranhaLauncherProjectile projectile = new PiranhaLauncherProjectile(level, thrower, fish, projectilePosition);
        if (thrower != null)
            projectile.shootFromRotation(thrower, thrower.getXRot(), thrower.getYHeadRot(), 0, 1.0f, 1f);
        level.addFreshEntity(projectile);

        level.playSound(null, throwerPosition.x(), throwerPosition.y(), throwerPosition.z(), SoundEvents.SNOWBALL_THROW, SoundSource.NEUTRAL, 0.5f, 0.4f / (level.getRandom().nextFloat() * 0.4f + 0.8f));
        level.playSound(null, throwerPosition.x(), throwerPosition.y(), throwerPosition.z(), SoundEvents.BUCKET_EMPTY_FISH, SoundSource.NEUTRAL, 1, 1);

        return projectile;
    }

    public boolean canInsertFishType(ItemStack stack, PracticalFish fish) {
        return fish instanceof Piranha;
    }

    /**
     * @param fish is a {@link PracticalFish} instead of a {@link Piranha} in case I add
     *             a piranha launcher enchantment in the future that allows it to shoot electric eels
     *             or something
     */
    public boolean tryInsertFish(ItemStack stack, PracticalFish fish, Player player) {
        if (
                !this.canInsertFishType(stack, fish)
                || fish.isTame() && !fish.isOwnedBy(player)
                || fish.isBaby()
        )
            return false;

        CompoundTag launcherTag = stack.getOrCreateTag();

        if (getFishCount(launcherTag) >= 64)
            return false;

        if (player.level().isClientSide())
            return true;

        ListTag fishes = launcherTag.getList("Fishes", Tag.TAG_COMPOUND);
        fishes.add(fish.makePiranhaLauncherTag());
        launcherTag.put("Fishes", fishes);

        launcherTag.putUUID("LastOwnerUUID", player.getUUID());
        launcherTag.putInt("FishCount", fishes.size());

        fish.playSound(fish.getPickupSound(), 1, 1);
        fish.discard();

        return true;
    }

    @Override
    public boolean canAttackBlock(BlockState p_43291_, Level p_43292_, BlockPos p_43293_, Player p_43294_) {
        return !p_43294_.isCreative();
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components , TooltipFlag flag) {
        super.appendHoverText(stack, level, components , flag);

        CompoundTag tag = stack.getTag();
        int fishCount;
        if (tag == null) {
            fishCount = 0;
        }
        else {
            fishCount = tag.getInt("FishCount");
        }

        MutableComponent tooltip;

        switch (fishCount) {
            case 0 -> tooltip = Component.translatable("tooltip.practicalpets.piranha_launcher.empty");
            case 1 -> tooltip = Component.translatable("tooltip.practicalpets.piranha_launcher.one_fish");
            default -> tooltip = Component.translatable("tooltip.practicalpets.piranha_launcher.fishes", fishCount);
        }

        components.add(tooltip.withStyle(ChatFormatting.GRAY));
    }

    //Pet accessory properties:
    @Override
    public ScaleMode getScaleMode(ItemStack stack, PracticalPet pet) {
        return ScaleMode.NONE;
    }

    @Override
    public Slot slot(ItemStack stack, PracticalPet pet) {
        return Slot.HEAD;
    }

    @Override
    public boolean canBePutOn(ItemStack stack, PracticalPet pet) {
        return true;
    }

    @Override
    public boolean causesBravery(ItemStack stack, PracticalPet pet) {
        return canPerformRangedAttack(stack, pet);
    }

    @Override
    public boolean canPerformRangedAttack(ItemStack stack, PracticalPet pet) {
        return getFishCount(stack) > 0;
    }

    @Override
    public void performRangedAttack(ItemStack stack, PracticalPet shooter, LivingEntity target, float distanceFactor) {
        this.shoot(stack, stack.getOrCreateTag(), shooter.level(), shooter, shooter.position());
        shooter.refreshPetEquipmentCache();
        //Fix client showing the pet's piranha launcher still having a piranha inside
        if (getFishCount(stack) == 0) {
            shooter.setHeadItem(ItemStack.EMPTY);
            shooter.setHeadItem(stack);
        }

        stack.hurtAndBreak(1, shooter, p -> {
            p.setHeadItem(ItemStack.EMPTY);
            p.playSound(SoundEvents.ITEM_BREAK);
            this.dropAllFish(stack, stack.getOrCreateTag(), p.level(), p, p.position());
        });
    }

    @Override
    public SoundEvent getEquipSound(ItemStack stack, PracticalPet pet) {
        return SoundEvents.ARMOR_EQUIP_IRON;
    }

    @Override
    public <T extends Mob & ACEntity, M extends PracticalPetModel<T>> void onRenderModelOnPetBefore(
            PetEquipmentLayer<T, M> layer,
            ItemStack stack,
            PoseStack poseStack,
            MultiBufferSource buffer,
            int packedLight,
            PracticalPet pet,
            float limbSwing,
            float limbSwingAmount,
            float partialticks
    ) {
        float headY = pet.headSizeY();
        if (headY < 3) {
            float scale = headY / 3f;
            poseStack.scale(scale, scale, scale);
        }
    }

    @Override
    public int getDefaultColor() {
        return 0xffffff;
    }
}
