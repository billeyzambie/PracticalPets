package billeyzambie.practicalpets.ui.infobook;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

public abstract class InfoBookEntry {
    public final String name;
    private int pagePairIndex;

    public InfoBookEntry(String name) {
        this.name = name;
    }

    public String getTranslationKey() {
        return "ui.practicalpets.info_book." + name;
    }

    public int getPagePairIndex() {
        return pagePairIndex;
    }

    public void appendSection(String section, InfoBookWriter writer) {
        boolean isGeneral = section.equals("general") || section.equals("leveling");
        if (writer.cantFitHeight(InfoBookTextWidget.FONT.lineHeight * 2 + 1))
            writer.toNextPage();
        writer.appendComponent(
                Component.translatable(isGeneral
                        ? "ui.practicalpets.info_book." + section + ".title"
                        : getSectionName(section)
                ).withStyle(ChatFormatting.UNDERLINE)
        );
        if (!writer.currentPage().widgets.isEmpty() && !writer.cantFitHeight(1))
            writer.incrementWritingAtY();
        writer.appendTranslatable(getSectionDescription(section));
        appendAfterSection(section, writer);
        if (!writer.currentPage().widgets.isEmpty() && !writer.cantFitHeight(InfoBookTextWidget.FONT.lineHeight))
            writer.appendLiteral(" ");
    }

    protected @NotNull String getSectionDescription(String section) {
        return "ui.practicalpets.info_book." + name + "." + section + ".body";
    }

    protected @NotNull String getSectionName(String section) {
        return "ui.practicalpets.info_book." + name + "." + section + ".title";
    }

    public void appendAfterSection(String section, InfoBookWriter writer) {

    }

    public void appendWidgets(InfoBookWriter writer) {
        this.pagePairIndex = writer.toEmptyPagePair();
        writer.appendComponent(Component.translatable(getTranslationKey())
                .withStyle(ChatFormatting.UNDERLINE)
                .withStyle(ChatFormatting.BOLD)
        );
    }
}