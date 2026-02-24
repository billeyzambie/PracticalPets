package billeyzambie.practicalpets.ui.infobook;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.MultiLineTextWidget;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;

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
        writingAtY += widgetHeight;
    }

    private void startWriting() {
        InfoBookPagePair.PAIRS.clear();
        InfoBookPagePair.PAIRS.add(new InfoBookPagePair());
        writingSinglePage = 0;
        writingAtY = 0;
    }

    public void writeInfoBook() {
        startWriting();

        appendWidget(new InfoBookTextWidget(Component.translatable("ui.practicalpets.info_book.welcome")));
        appendWidget(new InfoBookTextWidget(Component.literal("a".repeat(30))));
        appendWidget(Button.builder(Component.literal("XD"), button -> {}).size(InfoBookPagePair.TEXT_MAX_WIDTH, 20).build());
        appendWidget(new InfoBookTextWidget(Component.literal("e".repeat(100))));
        appendWidget(Button.builder(Component.literal("Lol"), button -> {}).size(20, InfoBookPagePair.TEXT_MAX_WIDTH).build());
        appendWidget(new InfoBookTextWidget(Component.literal("o".repeat(300))));
        appendWidget(new InfoBookTextWidget(Component.literal("i".repeat(150))));
        appendWidget(new InfoBookTextWidget(Component.literal("u".repeat(150))));
    }

}
