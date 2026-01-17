package billeyzambie.practicalpets.items;

import billeyzambie.practicalpets.entity.PracticalPet;
import billeyzambie.practicalpets.misc.ConfigurableBundleItem;
import billeyzambie.practicalpets.misc.PracticalPets;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.BundleItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
    public boolean causesBravery() {
        return true;
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
    }
}
