package billeyzambie.practicalpets.misc;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import net.minecraft.ChatFormatting;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.tooltip.BundleTooltip;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public abstract class ConfigurableBundleItem extends Item {
    private static final String TAG_ITEMS = "Items";

    protected boolean canInsertItem(ItemStack bundleStack, ItemStack stackToInsert) {
        return true;
    }

    public ConfigurableBundleItem(Item.Properties properties) {
        super(properties);
    }

    protected int getMaxWeight(ItemStack bundleStack) {
        return 64;
    }

    protected int getBundleInBundleWeight(ItemStack parentBundle, ItemStack nestedBundle) {
        return 4;
    }

    protected int getBarColorForStack(ItemStack bundleStack) {
        return Mth.color(0.4F, 0.4F, 1.0F);
    }

    public float getFullnessDisplay(ItemStack bundleStack) {
        int maxWeight = this.getMaxWeight(bundleStack);
        if (maxWeight <= 0) {
            return 0.0F;
        }
        return (float) this.getContentWeight(bundleStack) / (float) maxWeight;
    }

    @Override
    public boolean overrideStackedOnOther(ItemStack bundleStack, @NotNull Slot targetSlot, @NotNull ClickAction clickAction, @NotNull Player player) {
        if (bundleStack.getCount() != 1 || clickAction != ClickAction.SECONDARY) {
            return false;
        } else {
            ItemStack slotStack = targetSlot.getItem();
            if (slotStack.isEmpty()) {
                this.playRemoveOneSound(player);
                removeOne(bundleStack).ifPresent(removedStack -> this.add(bundleStack, targetSlot.safeInsert(removedStack)));
            } else if (slotStack.getItem().canFitInsideContainerItems()
                    && this.canInsertItem(bundleStack, slotStack)) {
                int maxWeight = this.getMaxWeight(bundleStack);
                int currentWeight = this.getContentWeight(bundleStack);
                int itemWeight = this.getWeight(bundleStack, slotStack);
                int maxItemsWeCanAdd = (maxWeight - currentWeight) / itemWeight;

                int inserted = this.add(bundleStack,
                        targetSlot.safeTake(slotStack.getCount(), maxItemsWeCanAdd, player));
                if (inserted > 0) {
                    this.playInsertSound(player);
                }
            }

            return true;
        }
    }

    @Override
    public boolean overrideOtherStackedOnMe(ItemStack bundleStack, @NotNull ItemStack cursorStack, @NotNull Slot clickedSlot, @NotNull ClickAction clickAction, @NotNull Player player, @NotNull SlotAccess slotAccess) {
        if (bundleStack.getCount() != 1) return false;

        if (clickAction == ClickAction.SECONDARY && clickedSlot.allowModification(player)) {
            if (cursorStack.isEmpty()) {
                removeOne(bundleStack).ifPresent(removedStack -> {
                    this.playRemoveOneSound(player);
                    slotAccess.set(removedStack);
                });
            } else if (cursorStack.getItem().canFitInsideContainerItems()
                    && this.canInsertItem(bundleStack, cursorStack)) {
                int inserted = this.add(bundleStack, cursorStack);
                if (inserted > 0) {
                    this.playInsertSound(player);
                    cursorStack.shrink(inserted);
                }
            }

            return true;
        } else {
            return false;
        }
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, Player player, @NotNull InteractionHand hand) {
        ItemStack bundleStack = player.getItemInHand(hand);
        if (dropContents(bundleStack, player)) {
            this.playDropContentsSound(player);
            player.awardStat(Stats.ITEM_USED.get(this));
            return InteractionResultHolder.sidedSuccess(bundleStack, level.isClientSide());
        } else {
            return InteractionResultHolder.fail(bundleStack);
        }
    }

    @Override
    public boolean isBarVisible(@NotNull ItemStack bundleStack) {
        return this.getContentWeight(bundleStack) > 0;
    }

    @Override
    public int getBarWidth(@NotNull ItemStack bundleStack) {
        int maxWeight = this.getMaxWeight(bundleStack);
        if (maxWeight <= 0) {
            return 0;
        }
        return Math.min(1 + 12 * this.getContentWeight(bundleStack) / maxWeight, 13);
    }

    @Override
    public int getBarColor(@NotNull ItemStack bundleStack) {
        return this.getBarColorForStack(bundleStack);
    }

    protected int add(ItemStack bundleStack, ItemStack stackToInsert) {
        if (stackToInsert.isEmpty()) {
            return 0;
        }

        if (!stackToInsert.getItem().canFitInsideContainerItems()) {
            return 0;
        }

        if (!this.canInsertItem(bundleStack, stackToInsert)) {
            return 0;
        }

        CompoundTag tag = bundleStack.getOrCreateTag();
        if (!tag.contains(TAG_ITEMS)) {
            tag.put(TAG_ITEMS, new ListTag());
        }

        int maxWeight = this.getMaxWeight(bundleStack);
        int currentWeight = this.getContentWeight(bundleStack);
        int itemWeight = this.getWeight(bundleStack, stackToInsert);
        int countToAdd = Math.min(
                stackToInsert.getCount(),
                (maxWeight - currentWeight) / itemWeight
        );

        if (countToAdd == 0) {
            return 0;
        }

        ListTag itemsTagList = tag.getList(TAG_ITEMS, Tag.TAG_COMPOUND);
        Optional<CompoundTag> existingEntryOpt = getMatchingItem(stackToInsert, itemsTagList);

        int result = countToAdd;

        if (existingEntryOpt.isPresent()) {
            CompoundTag existingEntryTag = existingEntryOpt.get();
            ItemStack existingStack = ItemStack.of(existingEntryTag);
            int maxAmountThatCanBeAddedToThisStack = existingStack.getMaxStackSize() - existingStack.getCount();
            int countToAddToThisStack = Math.min(countToAdd, maxAmountThatCanBeAddedToThisStack);
            countToAdd -= countToAddToThisStack;
            if (countToAddToThisStack > 0) {
                existingStack.grow(countToAddToThisStack);
                existingStack.save(existingEntryTag);

                itemsTagList.remove(existingEntryTag);
                itemsTagList.add(0, existingEntryTag);
            }
        }

        if (countToAdd > 0) {
            ItemStack insertedCopy = stackToInsert.copyWithCount(countToAdd);
            CompoundTag newEntryTag = new CompoundTag();
            insertedCopy.save(newEntryTag);
            itemsTagList.add(0, newEntryTag);
        }

        return result;
    }

    private static Optional<CompoundTag> getMatchingItem(ItemStack targetStack, ListTag itemListTag) {
        return targetStack.is(Items.BUNDLE)
                ? Optional.empty()
                : itemListTag.stream()
                .filter(CompoundTag.class::isInstance)
                .map(CompoundTag.class::cast)
                .filter(entryTag -> ItemStack.isSameItemSameTags(ItemStack.of(entryTag), targetStack))
                .findFirst();
    }

    protected int getWeight(ItemStack bundleStack, ItemStack containedStack) {
        if (containedStack.is(Items.BUNDLE)) {
            return this.getBundleInBundleWeight(bundleStack, containedStack)
                    + this.getContentWeight(containedStack);
        } else {
            if ((containedStack.is(Items.BEEHIVE) || containedStack.is(Items.BEE_NEST)) && containedStack.hasTag()) {
                CompoundTag blockEntityTag = BlockItem.getBlockEntityData(containedStack);
                if (blockEntityTag != null && !blockEntityTag.getList("Bees", Tag.TAG_COMPOUND).isEmpty()) {
                    return 64;
                }
            }

            return Math.max(1, 64 / containedStack.getMaxStackSize());
        }
    }

    protected int getContentWeight(ItemStack bundleStack) {
        return getContents(bundleStack)
                .mapToInt(contentStack ->
                        this.getWeight(bundleStack, contentStack) * contentStack.getCount())
                .sum();
    }

    private static Optional<ItemStack> removeOne(ItemStack bundleStack) {
        CompoundTag tag = bundleStack.getOrCreateTag();
        if (!tag.contains(TAG_ITEMS)) {
            return Optional.empty();
        } else {
            ListTag itemsTagList = tag.getList(TAG_ITEMS, Tag.TAG_COMPOUND);
            if (itemsTagList.isEmpty()) {
                return Optional.empty();
            } else {
                CompoundTag firstEntryTag = itemsTagList.getCompound(0);
                ItemStack removedStack = ItemStack.of(firstEntryTag);
                itemsTagList.remove(0);
                if (itemsTagList.isEmpty()) {
                    bundleStack.removeTagKey(TAG_ITEMS);
                }

                return Optional.of(removedStack);
            }
        }
    }

    private static boolean dropContents(ItemStack bundleStack, Player player) {
        CompoundTag tag = bundleStack.getOrCreateTag();
        if (!tag.contains(TAG_ITEMS)) {
            return false;
        } else {
            if (player instanceof ServerPlayer) {
                ListTag itemsTagList = tag.getList(TAG_ITEMS, Tag.TAG_COMPOUND);

                for (int i = 0; i < itemsTagList.size(); ++i) {
                    CompoundTag itemTag = itemsTagList.getCompound(i);
                    ItemStack contentStack = ItemStack.of(itemTag);
                    player.drop(contentStack, true);
                }
            }

            bundleStack.removeTagKey(TAG_ITEMS);
            return true;
        }
    }

    private static Stream<ItemStack> getContents(ItemStack bundleStack) {
        CompoundTag tag = bundleStack.getTag();
        if (tag == null) {
            return Stream.empty();
        } else {
            ListTag itemsTagList = tag.getList(TAG_ITEMS, Tag.TAG_COMPOUND);
            return itemsTagList.stream()
                    .map(CompoundTag.class::cast)
                    .map(ItemStack::of);
        }
    }

    @Override
    public @NotNull Optional<TooltipComponent> getTooltipImage(@NotNull ItemStack bundleStack) {
        NonNullList<ItemStack> contents = NonNullList.create();
        getContents(bundleStack).forEach(contents::add);
        return Optional.of(new BundleTooltip(contents, this.getContentWeight(bundleStack)));
    }

    @Override
    public void appendHoverText(@NotNull ItemStack bundleStack, Level level, List<Component> tooltipLines, @NotNull TooltipFlag tooltipFlag) {
        tooltipLines.add(
                Component.translatable(
                                "item.minecraft.bundle.fullness",
                                this.getContentWeight(bundleStack),
                                this.getMaxWeight(bundleStack)
                        )
                        .withStyle(ChatFormatting.GRAY)
        );
    }

    @Override
    public void onDestroyed(@NotNull ItemEntity bundleItemEntity) {
        ItemUtils.onContainerDestroyed(bundleItemEntity, getContents(bundleItemEntity.getItem()));
    }

    private void playRemoveOneSound(Entity entity) {
        entity.playSound(
                SoundEvents.BUNDLE_REMOVE_ONE,
                0.8F,
                0.8F + entity.level().getRandom().nextFloat() * 0.4F
        );
    }

    private void playInsertSound(Entity entity) {
        entity.playSound(
                SoundEvents.BUNDLE_INSERT,
                0.8F,
                0.8F + entity.level().getRandom().nextFloat() * 0.4F
        );
    }

    private void playDropContentsSound(Entity entity) {
        entity.playSound(
                SoundEvents.BUNDLE_DROP_CONTENTS,
                0.8F,
                0.8F + entity.level().getRandom().nextFloat() * 0.4F
        );
    }
}
