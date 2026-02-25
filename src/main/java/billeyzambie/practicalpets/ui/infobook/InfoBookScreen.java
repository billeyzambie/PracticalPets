package billeyzambie.practicalpets.ui.infobook;

import billeyzambie.practicalpets.misc.PracticalPets;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.PageButton;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import org.jetbrains.annotations.NotNull;

public class InfoBookScreen extends Screen {
    public static final int IMAGE_WIDTH = 292;
    public static final int IMAGE_HEIGHT = 180;

    private static final int BACK_BUTTON_LEFT_POS = 0;
    private static final int NAVIGATION_BUTTON_TOP_POS = IMAGE_HEIGHT + 1;
    private static final int FORWARD_BUTTON_LEFT_POS = IMAGE_WIDTH - 21;

    private static final int LEFT_PAGE_NUMBER_X = 24;
    private static final int PAGE_NUMBER_Y = IMAGE_HEIGHT - 18;
    private static final int RIGHT_PAGE_NUMBER_X = IMAGE_WIDTH - LEFT_PAGE_NUMBER_X;

    private int leftPos;
    private int topPos;
    private int currentPagePairIndex = 0;
    private PageButton forwardButton;
    private PageButton backButton;

    static final public ResourceLocation BACKGROUND = new ResourceLocation(
            PracticalPets.MODID,
            "textures/gui/info_book.png"
    );

    public InfoBookScreen() {
        super(Component.translatable("item.practicalpets.info_book"));
    }

    public void setPagePair(int index) {
        if (this.currentPagePairIndex == index)
            return;
        this.currentPagePairIndex = index;
        this.createWidgets();
        Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.BOOK_PAGE_TURN, 1.0F));
    }

    @Override
    protected void init() {
        this.leftPos = (this.width - IMAGE_WIDTH) / 2;
        this.topPos = (this.height - IMAGE_HEIGHT) / 2;
        this.createWidgets();
    }

    private static final Font FONT = Minecraft.getInstance().font;
    public static final int PAGE_NUMBER_COLOR = 0xD1BFA1;

    private int leftPageNumberOffset;

    @Override
    public void render(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(graphics);
        super.render(graphics, mouseX, mouseY, partialTicks);
        graphics.drawString(FONT,
                Integer.toString(this.currentPagePairIndex * 2 + 1),
                this.leftPos + LEFT_PAGE_NUMBER_X,
                this.topPos + PAGE_NUMBER_Y,
                PAGE_NUMBER_COLOR,
                false
        );
        String string = Integer.toString(this.currentPagePairIndex * 2 + 2);
        graphics.drawString(FONT,
                string,
                this.leftPos + RIGHT_PAGE_NUMBER_X + leftPageNumberOffset,
                this.topPos + PAGE_NUMBER_Y,
                PAGE_NUMBER_COLOR,
                false
        );
    }

    private InfoBookPagePair getCurrentPagePair() {
        return InfoBookPagePair.PAIRS.get(currentPagePairIndex);
    }

    @Override
    public void renderBackground(@NotNull GuiGraphics graphics) {
        super.renderBackground(graphics);
        graphics.blit(BACKGROUND, this.leftPos, this.topPos, 0, 0, IMAGE_WIDTH, IMAGE_HEIGHT, 512, 256);
    }

    @Override
    public boolean keyPressed(int p_98278_, int p_98279_, int p_98280_) {
        if (super.keyPressed(p_98278_, p_98279_, p_98280_)) {
            return true;
        } else {
            return switch (p_98278_) {
                case 266 -> {
                    this.backButton.onPress();
                    yield true;
                }
                case 267 -> {
                    this.forwardButton.onPress();
                    yield true;
                }
                default -> false;
            };
        }
    }

    protected void pageBack() {
        if (this.currentPagePairIndex > 0) {
            --this.currentPagePairIndex;
        }

        this.createWidgets();
    }

    protected void pageForward() {
        if (this.currentPagePairIndex < this.getNumPages() - 1) {
            ++this.currentPagePairIndex;
        }

        this.createWidgets();
    }

    private void createWidgets() {
        this.clearWidgets();
        this.forwardButton = this.addRenderableWidget(new PageButton(
                        this.leftPos + FORWARD_BUTTON_LEFT_POS,
                        this.topPos + NAVIGATION_BUTTON_TOP_POS,
                        true,
                        button -> this.pageForward(),
                        true
                )
        );
        this.backButton = this.addRenderableWidget(new PageButton(
                        this.leftPos + BACK_BUTTON_LEFT_POS,
                        this.topPos + NAVIGATION_BUTTON_TOP_POS,
                        false,
                        button -> this.pageBack(),
                        true
                )
        );
        for (InfoBookPagePair.Page page : this.getCurrentPagePair().pages) {
            for (InfoBookPagePair.Page.DefaultPositionWidget defaultPositionWidget : page.widgets) {
                AbstractWidget widget = defaultPositionWidget.widget();
                int x = defaultPositionWidget.x();
                int y = defaultPositionWidget.y();
                widget.setX(x + this.leftPos + page.textLeftPos());
                widget.setY(y + this.topPos + page.textTopPos());
                this.addRenderableWidget(widget);
            }
        }
        this.forwardButton.visible = this.currentPagePairIndex < this.getNumPages() - 1;
        this.backButton.visible = this.currentPagePairIndex > 0;

        this.leftPageNumberOffset = - FONT.width(Integer.toString(this.currentPagePairIndex * 2 + 2)) + 1;
    }

    private int getNumPages() {
        return InfoBookPagePair.PAIRS.size();
    }

}