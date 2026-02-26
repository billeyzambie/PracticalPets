package billeyzambie.practicalpets.ui.infobook;

import java.util.List;

public class InfoBookPetEntry extends InfoBookRenderAndSectionEntry{
    public InfoBookPetEntry(String name, int renderWidth, int renderHeight, List<String> sections) {
        super(name, renderWidth, renderHeight, sections);
    }

    @Override
    public String getTranslationKey() {
        return "entity.practicalpets." + name;
    }
}
