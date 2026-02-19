package billeyzambie.practicalpets.entity;

import billeyzambie.practicalpets.misc.PPTags;
import billeyzambie.practicalpets.util.PPUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;

public interface CookingPet {
    void setIsCooking(boolean value);
    boolean isCooking();
    boolean cookingTimerRanOut();
    void cookingSuccess();
    int getCookingTicks();
    void setCookingTicks(int value);
    ItemStackHandler getCookingIngredients();
    boolean canBeCookingIngredient(ItemStack stack);
    boolean cookingIngredientsFull();
    boolean isHoldingCookContainer();
    default PracticalPet asPet() {
        return (PracticalPet)this;
    }
    default void readCookingSaveData(CompoundTag tag) {
        this.setIsCooking(tag.getBoolean("IsCooking"));
        this.setCookingTicks(tag.getInt("CookingTicks"));
        this.getCookingIngredients().deserializeNBT(tag.getCompound("CookingIngredients"));
    }
    default void addCookingSaveData(CompoundTag tag) {
        tag.putBoolean("IsCooking", this.isCooking());
        tag.putInt("CookingTicks", this.getCookingTicks());
        tag.put("CookingIngredients", this.getCookingIngredients().serializeNBT());
    }
    default int incrementCookingTicks() {
        int result = this.getCookingTicks();
        this.setCookingTicks(result + 1);
        return result;
    }
    default boolean isChef() {
        return asPet().getHeadItem().is(PPTags.Items.PET_CHEF_HATS);
    }
    default InteractionResult cookInteract(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);

        boolean clientSide = asPet().level().isClientSide();
        if (!asPet().hasTarget() && this.isChef()) {
            if (itemstack.is(Items.BOWL) && asPet().getMainHandItem().isEmpty()) {
                if (!clientSide) {
                    asPet().setItemSlot(EquipmentSlot.MAINHAND, itemstack.split(1));
                    this.setCookingTicks(0);
                }
                return InteractionResult.sidedSuccess(clientSide);
            }
            if (this.isHoldingCookContainer()
                    && this.canBeCookingIngredient(itemstack)
                    && !this.cookingIngredientsFull()
            ) {
                if (!clientSide) {
                    ItemHandlerHelper.insertItemStacked(this.getCookingIngredients(), itemstack.split(1), false);
                    //give the player some extra time to put in the next ingredients
                    this.setCookingTicks(this.getCookingTicks() / 2);
                    if (this.cookingIngredientsFull()) {
                        this.startCooking();
                    }
                }
                return InteractionResult.sidedSuccess(clientSide);
            }
        }
        return InteractionResult.PASS;
    }
    default void startCooking() {
        this.setIsCooking(true);
        this.setCookingTicks(0);
    }
    default void tickCooking() {
        if (asPet().level().isClientSide())
            return;

        if (this.isHoldingCookContainer() && !this.isCooking() && this.incrementCookingTicks() > 200) {
            this.cookingInterrupted();
        }

        if (this.isCooking()) {
            this.incrementCookingTicks();
            if (this.cookingTimerRanOut()) {
                this.setIsCooking(false);
            }
        }
    }
    default boolean isCookingFinished() {
        return cookingTimerRanOut() && cookingIngredientsFull();
    }

    default void cookingInterrupted() {
        this.setIsCooking(false);
        asPet().spawnAtLocation(asPet().getMainHandItem().split(1));
        PPUtil.dump(this.getCookingIngredients(), asPet());
    }
}
