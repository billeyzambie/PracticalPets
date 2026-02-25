package billeyzambie.practicalpets.ui.infobook;

import billeyzambie.practicalpets.misc.PracticalPets;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractStringWidget;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;

import java.util.ArrayList;
import java.util.List;

//I use the unnecessary `this` very inconsistently across the mod's source code
public class InfoBookWriter {
    private InfoBookWriter() {}
    public static InfoBookWriter WRITER = new InfoBookWriter();

    private int writingSinglePage;
    private int writingAtY;

    private InfoBookPagePair.Page currentPage() {
        InfoBookPagePair pair = InfoBookPagePair.PAIRS.get(InfoBookPagePair.PAIRS.size() - 1);
        return pair.pages.get(writingSinglePage % 2);
    }

    private void toNextPage() {
        if (++writingSinglePage % 2 == 0) {
            InfoBookPagePair.PAIRS.add(new InfoBookPagePair());
        }
        writingAtY = 0;
    }

    private void toNextPagePair() {
        if (writingSinglePage % 2 == 0)
            writingSinglePage++;
        toNextPage();
    }

    private AbstractWidget lastWidget;

    private AbstractWidget appendWidget(AbstractWidget widget) {
        if (lastWidget != null) {
            if (!(widget instanceof AbstractStringWidget) || !(lastWidget instanceof AbstractStringWidget)) {
                writingAtY += 1;
            }
        }
        int widgetHeight = widget.getHeight();
        if (writingAtY + widgetHeight > InfoBookPagePair.ELEMENT_MAX_HEIGHT) {
            toNextPage();
        }
        widget.setY(writingAtY);
        currentPage().addRenderableWidget(widget);
        writingAtY += widgetHeight;
        this.lastWidget = widget;
        return widget;
    }

    private AbstractWidget putWidgetRightOf(AbstractWidget widget, AbstractWidget newWidget, int spacing) {
        newWidget.setY(widget.getY());
        newWidget.setX(widget.getX() + widget.getWidth() + spacing);
        currentPage().addRenderableWidget(newWidget);
        return newWidget;
    }

    private AbstractWidget putWidgetRightOf(AbstractWidget widget, AbstractWidget newWidget) {
        return putWidgetRightOf(widget, newWidget, 1);
    }

    private void appendText(Component text) {
        List<FormattedCharSequence> lines = InfoBookTextWidget.FONT.split(text, InfoBookPagePair.ELEMENT_MAX_WIDTH);
        List<FormattedCharSequence> currentlyAddingLines = new ArrayList<>();

        for (var line : lines) {
            currentlyAddingLines.add(line);
            if (
                    writingAtY + InfoBookTextWidget.FONT.lineHeight * (currentlyAddingLines.size() + 1)
                    > InfoBookPagePair.ELEMENT_MAX_HEIGHT
            ) {
                List<FormattedCharSequence> widgetLines = new ArrayList<>(currentlyAddingLines);
                appendWidget(new InfoBookTextWidget(widgetLines));
                currentlyAddingLines.clear();
            }
        }
        appendWidget(new InfoBookTextWidget(currentlyAddingLines));
    }

    private void initialize() {
        InfoBookPagePair.PAIRS.clear();
        InfoBookPagePair.PAIRS.add(new InfoBookPagePair());
        writingSinglePage = 0;
        writingAtY = 0;
    }

    public void writeInfoBook() {
        initialize();

        appendWidget(new InfoBookTextWidget(Component.translatable("ui.practicalpets.info_book.welcome")));
        appendText(Component.literal("pee poo §lcum§r ".repeat(30)));
        AbstractWidget fancyDuck = appendWidget(new ImageWidget(32, 32, new ResourceLocation(
                PracticalPets.MODID,
                "textures/gui/fancy_duck.png"
        )));
        putWidgetRightOf(fancyDuck, new ImageWidget(16, 16, new ResourceLocation(
                PracticalPets.MODID,
                "textures/item/pet_bowtie.png"
        )));
        appendWidget(new ImageWidget(16, 16, new ResourceLocation(
                PracticalPets.MODID,
                "textures/item/anniversary_pet_hat_0.png"
        )));
        appendText(Component.literal("a".repeat(30)));
        appendWidget(Button.builder(Component.literal("XD"), button -> {}).size(InfoBookPagePair.ELEMENT_MAX_WIDTH, 20).build());
        appendText(Component.literal("e".repeat(100)));
        appendWidget(Button.builder(Component.literal("Lol"), button -> {}).size(20, InfoBookPagePair.ELEMENT_MAX_WIDTH).build());
        appendWidget(new InfoBookClickableText(Component.literal("pee pee poo poo"), button -> {
            InfoBookScreen.setPagePairStatic(0);
        }));
        appendText(Component.literal("o".repeat(300)));
        appendText(Component.literal("i".repeat(150)));
        toNextPagePair();
        appendText(Component.literal("u".repeat(150)));
    }

}
