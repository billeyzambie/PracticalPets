package billeyzambie.practicalpets.ui.infobook;

import billeyzambie.practicalpets.misc.PracticalPets;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.components.AbstractStringWidget;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//I use the unnecessary `this` very inconsistently across the mod's source code
public class InfoBookWriter {

    public static final boolean DEBUG = false;

    private InfoBookWriter() {}
    public static InfoBookWriter WRITER = new InfoBookWriter();

    private int writingSinglePage;
    private int writingAtY;

    public InfoBookPagePair.Page currentPage() {
        InfoBookPagePair pair = InfoBookPagePair.PAIRS.get(InfoBookPagePair.PAIRS.size() - 1);
        return pair.pages.get(writingSinglePage % 2);
    }

    public void toNextPage() {
        if (++writingSinglePage % 2 == 0) {
            InfoBookPagePair.PAIRS.add(new InfoBookPagePair());
        }
        writingAtY = 0;
    }

    public int toEmptyPagePair() {
        if (writingSinglePage % 2 == 0) {
            if (currentPage().widgets.isEmpty())
                return writingSinglePage / 2;
            writingSinglePage++;
        }
        toNextPage();
        return writingSinglePage / 2;
    }

    private AbstractWidget lastWidget;

    public AbstractWidget appendWidget(AbstractWidget widget) {
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

    public AbstractWidget putWidgetRightOf(AbstractWidget widget, AbstractWidget newWidget, int spacing, int yOffset) {
        newWidget.setY(widget.getY() + yOffset);
        newWidget.setX(widget.getX() + widget.getWidth() + spacing);
        currentPage().addRenderableWidget(newWidget);
        return newWidget;
    }

    public AbstractWidget putWidgetRightOf(AbstractWidget widget, AbstractWidget newWidget) {
        return putWidgetRightOf(widget, newWidget, 1, 0);
    }

    public void appendComponent(Component text) {
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

    public void appendTranslatable(String text) {
        appendComponent(Component.translatable(text));        
    }

    public void appendLiteral(String text) {
        appendComponent(Component.literal(text));
    }

    private final HashMap<InfoBookEntry, Integer> findPagePairIndex = new HashMap<>();

    public int findPagePairIndex(InfoBookEntry entry) {
        return findPagePairIndex.get(entry);
    }

    private void initialize() {
        InfoBookPagePair.PAIRS.clear();
        InfoBookPagePair.PAIRS.add(new InfoBookPagePair());

        findPagePairIndex.clear();

        writingSinglePage = 0;
        writingAtY = 0;
    }

    public void writeInfoBook() {
        initialize();

        appendTranslatable("ui.practicalpets.info_book.welcome");
        appendLiteral(" ");
        appendComponent(Component.translatable("ui.practicalpets.info_book.contents")
                .withStyle(ChatFormatting.BOLD).withStyle(ChatFormatting.UNDERLINE)
        );


        for (InfoBookCategory category : InfoBookCategory.CATEGORIES) {
            for (InfoBookEntry entry : category.entries()) {
                AbstractWidget icon = appendWidget(new ImageWidget(16, 16, new ResourceLocation(
                        PracticalPets.MODID,
                        "textures/gui/info_book/icons/" + category.name() + "/" + entry.name + ".png"
                )));
                putWidgetRightOf(icon, new InfoBookClickableText(
                        Component.translatable(entry.getTranslationKey()),
                        button -> InfoBookScreen.goToPageOf(entry)
                ), 1, 4);
            }
        }

        if (DEBUG)
            writeDebug();

        for (InfoBookCategory category : InfoBookCategory.CATEGORIES) {
            for (InfoBookEntry entry : category.entries()) {
                entry.appendWidgets(this);
                findPagePairIndex.put(entry, entry.getPagePairIndex());
            }
        }

    }

    private void writeDebug() {
        appendLiteral("pee poo §lcum§r ".repeat(30));

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
        appendLiteral("a".repeat(30));
        appendWidget(Button.builder(Component.literal("XD"), button -> {}).size(115, 20).build());
        appendLiteral("e".repeat(100));
        appendWidget(Button.builder(Component.literal("Lol"), button -> {}).size(20, 115).build());
        appendWidget(new InfoBookClickableText(Component.literal("pee pee poo poo"), button -> {
            InfoBookScreen.setPagePairStatic(0);
        }));
        appendLiteral("o".repeat(300));
        appendLiteral("i".repeat(150));
        appendLiteral("u".repeat(150));
    }

}
