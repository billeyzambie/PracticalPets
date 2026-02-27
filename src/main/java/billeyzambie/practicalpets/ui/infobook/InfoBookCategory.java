package billeyzambie.practicalpets.ui.infobook;

import java.util.List;

public record InfoBookCategory(String name, List<InfoBookEntry> entries) {
    public static final List<InfoBookEntry> PETS = List.of(
            new InfoBookPetEntry("banana_duck", 58, 91, List.of(
                    "bananas", "banana_peels", "leveling"
            )),
            new InfoBookPetEntry("duck", 61, 68, List.of(
                    "finding_items", "rubbery_ducky_pet_hat", "leveling"
            )),
            new InfoBookPetEntry("rat", 112, 60, List.of(
                    "robbing", "cooking", "crossbreeding", "leveling"
            )),
            new InfoBookPetEntry("pigeon", 60, 64, List.of(
                    "reaching_items", "sending_to_player", "leveling"
            )),
            new InfoBookPetEntry("stick_bug", 114, 73, List.of(
                    "invisibility", "dancing", "leveling"
            )),
            new InfoBookPetEntry("giraffe_cat", 121, 116, List.of(
                    "ladder", "yeeting", "crossbreeding", "leveling"
            )),
            new InfoBookPetEntry("kiwi", 65, 66, List.of(
                    "feathers", "pet_hat", "leveling"
            ))
    );
    public static final List<InfoBookCategory> CATEGORIES = List.of(
            new InfoBookCategory("pets", PETS)
    );


}