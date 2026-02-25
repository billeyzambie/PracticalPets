package billeyzambie.practicalpets.ui.infobook;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

public abstract class InfoBookEntry {
    public final String name;
    private int pagePairIndex;

    public InfoBookEntry(String name) {
        this.name = name;
    }

    public abstract String getTranslationKey();

    public int getPagePairIndex() {
        return pagePairIndex;
    }

    public void appendWidgets(InfoBookWriter writer) {
        this.pagePairIndex = writer.toEmptyPagePair();
        writer.appendComponent(Component.translatable(getTranslationKey())
                .withStyle(ChatFormatting.UNDERLINE)
                .withStyle(ChatFormatting.BOLD)
        );
    }
}