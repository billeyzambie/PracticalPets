package billeyzambie.practicalpets.ui.infobook;

import billeyzambie.practicalpets.misc.PracticalPets;
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
