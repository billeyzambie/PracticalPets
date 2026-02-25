package billeyzambie.practicalpets.ui.infobook;

import java.util.List;

public record InfoBookCategory(String name, List<InfoBookEntry> entries) {
    public static final List<InfoBookEntry> PETS = List.of(
            new InfoBookPetEntry("banana_duck", 65, 101, List.of(
                    "bananas", "banana_peels"
            ))
    );
    public static final List<InfoBookCategory> CATEGORIES = List.of(
            new InfoBookCategory("pets", PETS)
    );


}