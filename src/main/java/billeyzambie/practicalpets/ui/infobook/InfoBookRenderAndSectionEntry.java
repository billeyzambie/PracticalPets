package billeyzambie.practicalpets.ui.infobook;

import billeyzambie.practicalpets.misc.PracticalPets;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public abstract class InfoBookRenderAndSectionEntry extends InfoBookEntry {
    private final int renderWidth;
    private final int renderHeight;
    private final List<String> sections;

    public InfoBookRenderAndSectionEntry(String name, int renderWidth, int renderHeight, List<String> sections) {
        super(name);
        this.renderWidth = renderWidth;
        this.renderHeight = renderHeight;
        this.sections = sections;
    }

    public void appendSection(String section, InfoBookWriter writer) {
        boolean isGeneral = section.equals("general") || section.equals("leveling");
        if (writer.cantFitHeight(InfoBookTextWidget.FONT.lineHeight * 2 + 1))
            writer.toNextPage();
        writer.appendComponent(
                Component.translatable(isGeneral
                        ? "ui.practicalpets.info_book." + section + ".title"
                        : "ui.practicalpets.info_book." + name + "." + section + ".title"
                ).withStyle(ChatFormatting.UNDERLINE)
        );
        if (!writer.currentPage().widgets.isEmpty() && !writer.cantFitHeight(1))
            writer.incrementWritingAtY();
        writer.appendTranslatable("ui.practicalpets.info_book." + name + "." + section + ".body");
        if (!writer.currentPage().widgets.isEmpty() && !writer.cantFitHeight(InfoBookTextWidget.FONT.lineHeight))
            writer.appendLiteral(" ");
    }

    @Override
    public void appendWidgets(InfoBookWriter writer) {
        super.appendWidgets(writer);
        writer.appendWidget(new ImageWidget(
                renderWidth,
                renderHeight,
                new ResourceLocation(
                        PracticalPets.MODID,
                        "textures/gui/info_book/renders/" + name + ".png"
                )
        ));
        appendSection("general", writer);
        for (String section : sections) {
            appendSection(section, writer);
        }
    }
}
