package billeyzambie.practicalpets.client.ui;

import billeyzambie.practicalpets.entity.PracticalPet;
import billeyzambie.practicalpets.misc.PracticalPets;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public class PracticalPetScreen extends AbstractContainerScreen<PracticalPetMenu> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(PracticalPets.MODID, "textures/gui/pet_menu.png");
    private static final ResourceLocation HEAD_SLOT_ICON =
            new ResourceLocation(PracticalPets.MODID, "textures/gui/slot/pet_head.png");
    private static final ResourceLocation NECK_SLOT_ICON =
            new ResourceLocation(PracticalPets.MODID, "textures/gui/slot/pet_neck.png");
    private static final ResourceLocation BACK_SLOT_ICON =
            new ResourceLocation(PracticalPets.MODID, "textures/gui/slot/pet_back.png");
    private static final ResourceLocation BODY_SLOT_ICON =
            new ResourceLocation(PracticalPets.MODID, "textures/gui/slot/pet_body.png");

    PracticalPet pet;

    public PracticalPetScreen(PracticalPetMenu menu, Inventory playerInventory, Component component) {
        super(menu, playerInventory, component);
        this.pet = menu.pet;
        this.imageWidth = 176;
        this.imageHeight = 184;
        this.inventoryLabelY += 18;
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTicks, int mouseX, int mouseY) {
        graphics.blit(TEXTURE, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);

        if (!this.menu.getSlot(0).hasItem()) {
            graphics.blit(HEAD_SLOT_ICON, this.leftPos + 8, this.topPos + 18, 0, 0, 16, 16, 16, 16);
        }
        if (!this.menu.getSlot(1).hasItem()) {
            graphics.blit(NECK_SLOT_ICON, this.leftPos + 8, this.topPos + 36, 0, 0, 16, 16, 16, 16);
        }
        if (!this.menu.getSlot(2).hasItem()) {
            graphics.blit(BACK_SLOT_ICON, this.leftPos + 8, this.topPos + 54, 0, 0, 16, 16, 16, 16);
        }
        if (!this.menu.getSlot(3).hasItem()) {
            graphics.blit(BODY_SLOT_ICON, this.leftPos + 8, this.topPos + 72, 0, 0, 16, 16, 16, 16);
        }

        InventoryScreen.renderEntityInInventoryFollowsMouse(
                graphics,
                this.leftPos + 51, this.topPos + 78,
                17,
                (this.leftPos + 51) - this.mouseX,
                (this.topPos + 50 - this.pet.getEyeHeight()) - this.mouseY,
                this.pet
        );
    }

    float mouseX;
    float mouseY;

    @Override
    public void render(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        this.mouseX = mouseX;
        this.mouseY = mouseY;
        this.renderBackground(graphics);
        super.render(graphics, mouseX, mouseY, partialTicks);
        this.renderTooltip(graphics, mouseX, mouseY);
    }
}
