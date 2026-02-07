package billeyzambie.practicalpets.ui;

import billeyzambie.practicalpets.entity.PracticalPet;
import billeyzambie.practicalpets.entity.dinosaur.Pigeon;
import billeyzambie.practicalpets.entity.otherpet.GiraffeCat;
import billeyzambie.practicalpets.misc.PPNetworking;
import billeyzambie.practicalpets.network.GiraffeCatLadderButtonPacket;
import net.minecraft.client.Minecraft;
import billeyzambie.practicalpets.misc.PracticalPets;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

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
    @Nullable
    private PetAbilityButton petAbilityButton;

    public PracticalPetScreen(PracticalPetMenu menu, Inventory playerInventory, Component component) {
        super(menu, playerInventory, component);
        this.pet = menu.pet;
        this.imageWidth = 176;
        this.imageHeight = 184;
        this.inventoryLabelY += 18;
    }

    @Override
    protected void init() {
        super.init();

        if (pet instanceof Pigeon pigeon) {
            this.addRenderableWidget(this.petAbilityButton = new PetAbilityButton(
                    this.leftPos + 178,
                    this.topPos + 18,
                    button -> Minecraft.getInstance().setScreen(new PigeonSendScreen(pigeon)),
                    pet,
                    new ResourceLocation(PracticalPets.MODID, "textures/gui/mail_icon.png")
            ));
        }
        else if (pet instanceof GiraffeCat) {
            this.addRenderableWidget(this.petAbilityButton = new PetAbilityButton(
                    this.leftPos + 178,
                    this.topPos + 18,
                    button -> {
                        this.onClose();
                        PPNetworking.CHANNEL.sendToServer(new GiraffeCatLadderButtonPacket(pet.getId()));
                    },
                    pet,
                    new ResourceLocation(PracticalPets.MODID, "textures/gui/ladder_icon.png")
            ));
        }

        if (this.petAbilityButton != null)
            this.imageWidth += 2 + this.petAbilityButton.getWidth();
    }

    @Override
    protected void containerTick() {
        super.containerTick();
        if (this.petAbilityButton != null) {
            this.petAbilityButton.tick();
        }
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
        if (this.petAbilityButton != null && this.petAbilityButton.isHovered()) {
            Component tooltipComponent = this.petAbilityButton.getTooltipComponent();
            if (tooltipComponent != null)
                graphics.renderTooltip(this.font, tooltipComponent, mouseX, mouseY);
        }
    }
}
