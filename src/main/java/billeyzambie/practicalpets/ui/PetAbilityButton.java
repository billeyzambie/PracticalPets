package billeyzambie.practicalpets.ui;

import billeyzambie.practicalpets.entity.PracticalPet;
import billeyzambie.practicalpets.entity.dinosaur.Pigeon;
import billeyzambie.practicalpets.entity.otherpet.GiraffeCat;
import billeyzambie.practicalpets.items.PetBackpack;
import billeyzambie.practicalpets.misc.PracticalPets;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PetAbilityButton extends Button {
    private static final ResourceLocation OUTLINE =
            new ResourceLocation(PracticalPets.MODID, "textures/gui/button_outline.png");
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(PracticalPets.MODID, "textures/gui/button.png");
    private final PracticalPet pet;
    private final ResourceLocation icon;

    public PetAbilityButton(int x, int y, Button.OnPress onPress, PracticalPet pet, ResourceLocation icon) {
        super(x, y, 18, 18, Component.empty(), onPress, DEFAULT_NARRATION);
        this.pet = pet;
        this.icon = icon;
        this.tick();
    }

    @Override
    public void renderWidget(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        boolean pressed = this.isActive()
                && this.isHoveredOrFocused()
                && Minecraft.getInstance().mouseHandler.isLeftPressed();
        int v = 0;
        if (!this.isActive()) {
            v = 36;
        } else if (pressed) {
            v = 18;
        } else if (this.isHoveredOrFocused()) {
            v = 54;
        }

        graphics.blit(OUTLINE, this.getX() - 1, this.getY() - 1, 0, 0, this.width + 2, this.height + 2, 20, 20);
        graphics.blit(TEXTURE, this.getX(), this.getY(), 0, v, this.width, this.height, 18, 72);

        float tint = this.isActive() ? 1.0F : 0.5f;

        graphics.setColor(tint * 0.25f, tint * 0.25f, tint * 0.25f, 1);
        graphics.blit(this.icon, this.getX() + 1, this.getY() + 1, 0, 0, 18, 18, 18, 18);
        graphics.setColor(tint, tint, tint, 1);
        graphics.blit(this.icon, this.getX(), this.getY(), 0, 0, 18, 18, 18, 18);
        graphics.setColor(1, 1, 1, 1);
    }

    public void tick() {
        if (this.pet instanceof Pigeon) {
            this.pigeonTick();
        }
        else if (this.pet instanceof GiraffeCat) {
            this.giraffeCatTick();
        }
    }

    private static final Component NO_BACKPACK_TOOLTIP = Component.translatable("ui.practicalpets.pigeon_send.need_backpack").withStyle(ChatFormatting.GRAY);
    private static final Component EMPTY_BACKPACK_TOOLTIP = Component.translatable("ui.practicalpets.pigeon_send.need_backpack_item").withStyle(ChatFormatting.GRAY);
    private static final Component NOT_ENOUGH_LEVELS = Component.translatable("ui.practicalpets.pigeon_send.need_levels").withStyle(ChatFormatting.GRAY);

    private void pigeonTick() {
        ItemStack pigeonBackStack = this.pet.getBackItem();
        Item pigeonBackItem = pigeonBackStack.getItem();
        this.active = false;
        if (pigeonBackItem instanceof PetBackpack backpack) {
            if (backpack.getContentWeight(pigeonBackStack) > 0) {
                LocalPlayer player = Minecraft.getInstance().player;
                if (player != null && (player.experienceLevel > 0 || player.getAbilities().instabuild)) {
                    this.active = true;
                    this.tooltip = null;
                } else {
                    this.tooltip = NOT_ENOUGH_LEVELS;
                }
            } else {
                this.tooltip = EMPTY_BACKPACK_TOOLTIP;
            }
        } else {
            this.tooltip = NO_BACKPACK_TOOLTIP;
        }
    }

    private static final Component ACTIVATE_LADDER_TOOLTIP = Component.translatable("ui.practicalpets.giraffe_cat_ladder.activate").withStyle(ChatFormatting.GRAY);
    private static final Component DEACTIVATE_LADDER_TOOLTIP = Component.translatable("ui.practicalpets.giraffe_cat_ladder.deactivate").withStyle(ChatFormatting.GRAY);

    private void giraffeCatTick() {
        GiraffeCat giraffeCat = (GiraffeCat) pet;
        this.active = true;
        if (giraffeCat.isLadder()) {
            this.tooltip = DEACTIVATE_LADDER_TOOLTIP;
        }
        else if (giraffeCat.noCurrentAbility()) {
            this.tooltip = ACTIVATE_LADDER_TOOLTIP;
        }
        else {
            this.active = false;
            this.tooltip = null;
        }
    }

    @Nullable
    private Component tooltip;

    public @Nullable Component getTooltipComponent() {
        return this.tooltip;
    }

}
