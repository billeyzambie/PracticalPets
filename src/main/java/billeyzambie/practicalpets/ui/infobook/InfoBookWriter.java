package billeyzambie.practicalpets.ui.infobook;

import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
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

    private void appendWidget(AbstractWidget widget) {
        int widgetHeight = widget.getHeight();
        if (writingAtY + widgetHeight > InfoBookPagePair.TEXT_MAX_HEIGHT) {
            toNextPage();
        }
        widget.setY(writingAtY);
        currentPage().addRenderableWidget(widget);
        writingAtY += widgetHeight + 1;
    }

    private void appendText(Component text) {
        List<FormattedCharSequence> lines = InfoBookTextWidget.FONT.split(text, InfoBookPagePair.TEXT_MAX_WIDTH);
        List<FormattedCharSequence> currentlyAddingLines = new ArrayList<>();

        for (var line : lines) {
            currentlyAddingLines.add(line);
            if (
                    writingAtY + InfoBookTextWidget.FONT.lineHeight * (currentlyAddingLines.size() + 1)
                    > InfoBookPagePair.TEXT_MAX_HEIGHT
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
        appendText(Component.literal("a".repeat(30)));
        appendWidget(Button.builder(Component.literal("XD"), button -> {}).size(InfoBookPagePair.TEXT_MAX_WIDTH, 20).build());
        appendText(Component.literal("e".repeat(100)));
        appendWidget(Button.builder(Component.literal("Lol"), button -> {}).size(20, InfoBookPagePair.TEXT_MAX_WIDTH).build());
        appendText(Component.literal("pee pee poo poo"));
        appendText(Component.literal("o".repeat(300)));
        appendText(Component.literal("i".repeat(150)));
        appendText(Component.literal("u".repeat(150)));
    }

}
