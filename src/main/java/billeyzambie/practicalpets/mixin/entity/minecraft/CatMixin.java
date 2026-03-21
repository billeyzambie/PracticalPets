package billeyzambie.practicalpets.mixin.entity.minecraft;

import billeyzambie.practicalpets.entity.base.practicalpet.PetEquipmentWearer;
import billeyzambie.practicalpets.items.PetCosmetic;
import billeyzambie.practicalpets.misc.PPItems;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.Optional;

@Mixin(Cat.class)
public class CatMixin extends TamableAnimal implements PetEquipmentWearer {
    private CatMixin(EntityType<? extends TamableAnimal> p_21803_, Level p_21804_) {
        super(p_21803_, p_21804_);
    }

    /**
     * Make sure to call {@link PetEquipmentWearer#refreshPetEquipmentCache()}
     */
    @Override
    public void setPetHeadItemRaw(ItemStack stack) {

    }

    /**
     * Make sure to call {@link PetEquipmentWearer#refreshPetEquipmentCache()}
     */
    @Override
    public void setPetNeckItemRaw(ItemStack stack) {

    }

    /**
     * Make sure to call {@link PetEquipmentWearer#refreshPetEquipmentCache()}
     */
    @Override
    public void setPetBackItemRaw(ItemStack stack) {

    }

    /**
     * Make sure to call {@link PetEquipmentWearer#refreshPetEquipmentCache()}
     */
    @Override
    public void setPetBodyItemRaw(ItemStack stack) {

    }

    @Unique
    private static final ItemStack lol = new ItemStack(PPItems.RUBBER_DUCKY_PET_HAT.get());
    @Unique
    private static final ItemStack xd = new ItemStack(PPItems.PET_BOWTIE.get());

    @Override
    public ItemStack getPetHeadItem() {
        return lol;
    }

    @Override
    public ItemStack getPetNeckItem() {
        return xd;
    }

    @Override
    public ItemStack getPetBackItem() {
        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack getPetBodyItem() {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean anyEquipmentIsBrave() {
        return false;
    }

    @Override
    public float getPetReachMultiplier() {
        return 1;
    }

    @Override
    public float getGuardPowerMultiplier() {
        return 1;
    }

    @Override
    public Optional<PetCosmetic.Slot> canShootFromSlot() {
        return Optional.empty();
    }

    @Override
    public void setAnyEquipmentIsBrave(boolean value) {

    }

    @Override
    public void setPetReachMultiplier(float value) {

    }

    @Override
    public void setGuardPowerMultiplier(float value) {

    }

    @Override
    public void setCanShootFromSlot(Optional<PetCosmetic.Slot> value) {

    }

    @Override
    public float getPetHeadSizeX() {
        return 5;
    }

    @Override
    public float getPetHeadSizeY() {
        return 4;
    }

    @Override
    public float getPetHeadSizeZ() {
        return 5;
    }


    @Override
    public Component getDeathMessage() {
        return null;
    }

    @Override
    public int getPetLevel() {
        return 1;
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel p_146743_, AgeableMob p_146744_) {
        return null;
    }

    @Override
    public void performRangedAttack(LivingEntity p_33317_, float p_33318_) {

    }
}