package billeyzambie.practicalpets.items;

import billeyzambie.practicalpets.entity.PracticalPet;
import billeyzambie.practicalpets.entity.other.PetEndRodProjectile;
import billeyzambie.practicalpets.misc.ConfigurableBundleItem;
import billeyzambie.practicalpets.misc.PracticalPets;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import javax.swing.text.AttributeSet;
import javax.swing.text.Style;
import java.util.List;
import java.util.Optional;

public class PetEndRodLauncher extends ConfigurableBundleItem implements AttachablePetCosmetic, DyeableItem {
    public PetEndRodLauncher() {
        super(new Properties().stacksTo(1));
    }

    @Override
    protected boolean canInsertItem(ItemStack bundleStack, ItemStack stackToInsert) {
        return stackToInsert.is(Items.END_ROD);
    }

    @Override
    protected int getMaxWeight(ItemStack bundleStack) {
        return 128;
    }

    @Override
    public @NotNull Optional<TooltipComponent> getTooltipImage(@NotNull ItemStack bundleStack) {
        return Optional.empty();
    }

    ResourceLocation modelTexture = new ResourceLocation(
            PracticalPets.MODID,
            "textures/entity/pet_equipment/end_rod_launcher.png"
    );

    @Override
    public ResourceLocation getModelTexture() {
        return modelTexture;
    }

    @Override
    public boolean ignoreLighting(ItemStack instance) {
        return true;
    }

    @Override
    public AttachBone getAttachBone() {
        return AttachBone.BACKPACK;
    }

    @Override
    public Slot slot() {
        return Slot.BACK;
    }

    @Override
    public boolean canBePutOn(PracticalPet pet) {
        return true;
    }

    @Override
    public boolean canPerformRangedAttack(ItemStack stack) {
        //can perform the ranged attack if it has end rods to inside it to shoot
        return this.getContentWeight(stack) > 0;
    }

    @Override
    public void performRangedAttack(ItemStack stack, PracticalPet shooter, LivingEntity target, float distanceFactor) {
        if (this.removeOneMatching(stack, s -> s.is(Items.END_ROD)).isEmpty()) {
            return; // no end rods in the launcher
        }
        shooter.level().addFreshEntity(new PetEndRodProjectile(shooter.level(), shooter, target, Direction.Axis.Y));
        shooter.playSound(SoundEvents.SHULKER_SHOOT, 2.0F, (shooter.getRandom().nextFloat() - shooter.getRandom().nextFloat()) * 0.2F + 1.0F);
        shooter.refreshAnyEquipmentIsBrave(); //the stack might have just run out of end rods
    }

    @Override
    public boolean causesBravery(ItemStack stack) {
        return this.canPerformRangedAttack(stack);
    }

    @Override
    public float damageMultiplier() {
        return 0.9f;
    }

    @Override
    public int getDefaultColor() {
        return 0xFFFFFF;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack bundleStack, Level level, List<Component> tooltipLines, @NotNull TooltipFlag tooltipFlag) {
        super.appendHoverText(bundleStack, level, tooltipLines, tooltipFlag);
        tooltipLines.add(Component.translatable("tooltip.practicalpets.pet_end_rod_launcher")
                .withStyle(ChatFormatting.GRAY));
    }
}
