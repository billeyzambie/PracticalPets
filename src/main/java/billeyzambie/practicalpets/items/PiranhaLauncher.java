package billeyzambie.practicalpets.items;

import billeyzambie.practicalpets.entity.PracticalPet;
import billeyzambie.practicalpets.entity.fish.Piranha;
import billeyzambie.practicalpets.entity.fish.base.PracticalFish;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class PiranhaLauncher extends Item implements ItemModelPetCosmetic {
    public PiranhaLauncher() {
        super(new Properties().stacksTo(1));
    }

    public int getFishCount(ItemStack stack) {
        return getFishCount(stack.getOrCreateTag());
    }

    public int getFishCount(CompoundTag launcherTag) {
        return launcherTag.getInt("FishCount");
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        CompoundTag launcherTag = itemstack.getOrCreateTag();
        if (this.getFishCount(launcherTag) < 1) {
            return InteractionResultHolder.pass(itemstack);
        }
        player.getCooldowns().addCooldown(this, 20);
        if (!level.isClientSide()) {
            this.shoot(itemstack, launcherTag, level, player, player.position());
        }

        player.awardStat(Stats.ITEM_USED.get(this));

        return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
    }

    public void shoot(
            ItemStack piranhaLauncher,
            CompoundTag launcherTag,
            Level level,
            @Nullable LivingEntity thrower,
            Vec3 throwerPosition
    ) {
        ListTag fishes = launcherTag.getList("Fishes", Tag.TAG_COMPOUND);
        if (fishes.isEmpty())
            return;

        CompoundTag fishTag = (CompoundTag) fishes.remove(fishes.size() - 1);
        launcherTag.put("Fishes", fishes);

        launcherTag.putInt("FishCount", fishes.size());

        PracticalFish fish = PracticalFish.createFromPiranhaLauncherTag(piranhaLauncher, fishTag, level, thrower);
        fish.setPos(throwerPosition);
        level.addFreshEntity(fish);

        level.playSound(null, throwerPosition.x(), throwerPosition.y(), throwerPosition.z(), SoundEvents.SNOWBALL_THROW, SoundSource.NEUTRAL, 0.5F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
    }

    public boolean canInsertFish(ItemStack stack, PracticalFish fish) {
        return fish instanceof Piranha;
    }

    /**
     * @param fish is a {@link PracticalFish} instead of a {@link Piranha} in case I add
     *             a piranha launcher enchantment in the future that allows it to shoot electric eels
     *             or something
     */
    public boolean tryInsertFish(ItemStack stack, PracticalFish fish, Player player) {
        if (
                !this.canInsertFish(stack, fish)
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

        return true;
    }

    @Override
    public @Nullable CompoundTag getShareTag(ItemStack stack) {
        CompoundTag result = new CompoundTag();
        CompoundTag serverTag = stack.getTag();
        if (serverTag != null) {
            Tag serverDisplay = serverTag.get("display");
            if (serverDisplay != null) {
                result.put("display", serverDisplay);
            }
            result.putInt("FishCount", serverTag.getInt("FishCount"));
        }
        return result;
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
        return getFishCount(stack) > 1;
    }

    @Override
    public void performRangedAttack(ItemStack stack, PracticalPet shooter, LivingEntity target, float distanceFactor) {
        this.shoot(stack, stack.getOrCreateTag(), shooter.level(), shooter, shooter.position());
        shooter.refreshPetEquipmentCache();
    }
}
