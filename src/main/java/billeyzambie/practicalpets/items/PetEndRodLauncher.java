package billeyzambie.practicalpets.items;

import billeyzambie.practicalpets.entity.base.practicalpet.PetEquipmentWearer;
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

import java.util.List;
import java.util.Optional;

public class PetEndRodLauncher extends ConfigurableBundleItem implements EntityModelPetCosmetic, DyeableItem {
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
    public ResourceLocation getModelTexture(ItemStack stack, PetEquipmentWearer wearer) {
        return modelTexture;
    }

    @Override
    public boolean ignoreLighting(ItemStack stack, PetEquipmentWearer wearer) {
        return true;
    }

    @Override
    public Slot slot(ItemStack stack, PetEquipmentWearer wearer) {
        return Slot.BACK;
    }

    @Override
    public boolean canBePutOn(ItemStack stack, PetEquipmentWearer wearer) {
        return true;
    }

    @Override
    public boolean canPerformRangedAttack(ItemStack stack, PetEquipmentWearer wearer) {
        //can perform the ranged attack if it has end rods to inside it to shoot
        return this.getContentWeight(stack) > 0;
    }

    @Override
    public void performRangedAttack(ItemStack stack, PetEquipmentWearer wearer, LivingEntity target, float distanceFactor) {
        if (this.removeOneMatching(stack, s -> s.is(Items.END_ROD)).isEmpty()) {
            return; // no end rods in the launcher
        }
        if (wearer instanceof LivingEntity living) {
            living.level().addFreshEntity(new PetEndRodProjectile(living.level(), living, target, Direction.Axis.Y));
            living.playSound(SoundEvents.SHULKER_SHOOT, 2.0F, (living.getRandom().nextFloat() - living.getRandom().nextFloat()) * 0.2F + 1.0F);
            wearer.refreshPetEquipmentCache(); //the stack might have just run out of end rods}
        }
    }

    @Override
    public boolean causesBravery(ItemStack stack, PetEquipmentWearer wearer) {
        return this.canPerformRangedAttack(stack, wearer);
    }

    @Override
    public float damageMultiplier(ItemStack stack, PetEquipmentWearer wearer) {
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
