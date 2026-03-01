package billeyzambie.practicalpets.ui.infobook;

import billeyzambie.practicalpets.misc.PracticalPets;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public record InfoBookCategory(String name, List<InfoBookEntry> entries) {
    public static final List<InfoBookEntry> PETS = List.of(
            new InfoBookPetEntry("banana_duck", 58, 91, List.of(
                    "bananas", "banana_peels", "leveling"
            )),
            new InfoBookPetEntry("duck", 61, 68, List.of(
                    "finding_items", "rubber_ducky_pet_hat", "leveling"
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
                    "shearing", "leveling"
            ))
    );
    public static final List<InfoBookEntry> MISC = List.of(
            new InfoBookEntry("pet_equipment") {
                @Override
                public void appendWidgets(InfoBookWriter writer) {
                    super.appendWidgets(writer);
                    writer.appendWidget(new ImageWidget(
                            117, 73,
                            new ResourceLocation(PracticalPets.MODID,
                                    "textures/gui/info_book/pet_equipment.png"
                            )
                    ));
                    writer.incrementWritingAtY();
                    writer.appendTranslatable("ui.practicalpets.info_book.pet_equipment.body");
                    writer.appendLiteral(" ");
                    this.appendSection("pet_bowtie", writer);
                    this.appendSection("pet_hat", writer);
                    this.appendSection("anniversary_pet_hat_0", writer);
                    this.appendSection("rubber_ducky_pet_hat", writer);
                    this.appendSection("pet_chef_hat", writer);
                    this.appendSection("pet_backpack", writer);
                    this.appendSection("pet_end_rod_launcher", writer);
                    this.appendSection("duck_armor", writer);
                }

                @Override
                protected @NotNull String getSectionName(String section) {
                    return "item.practicalpets." + section;
                }

                @Override
                protected @NotNull String getSectionDescription(String section) {
                    if (section.equals("rubber_ducky_pet_hat"))
                        return "ui.practicalpets.info_book.duck.rubber_ducky_pet_hat.body";
                    return super.getSectionDescription(section);
                }
            },
            new InfoBookEntry("misc") {
                @Override
                public void appendWidgets(InfoBookWriter writer) {
                    super.appendWidgets(writer);
                    writer.incrementWritingAtY();
                    this.appendSection("pet_menu", writer);
                    this.appendSection("double_sneak", writer);
                    this.appendSection("bravery", writer);
                    this.appendSection("leveling", writer);
                    this.appendSection("pet_friendly_fire", writer);
                }

                @Override
                public void appendAfterSectionTitle(String section, InfoBookWriter writer) {
                    switch (section) {
                        case "pet_menu": {
                            writer.appendWidget(new ImageWidget(
                                    83, 91,
                                    new ResourceLocation(PracticalPets.MODID,
                                            "textures/gui/info_book/pet_menu.png"
                                    )
                            ));
                            break;
                        }
                        case "leveling": {
                            writer.appendWidget(new ImageWidget(
                                    101, 67,
                                    new ResourceLocation(PracticalPets.MODID,
                                            "textures/gui/info_book/leveling.png"
                                    )
                            ));
                            break;
                        }
                    }
                }
            }
    );
    public static final List<InfoBookCategory> CATEGORIES = List.of(
            new InfoBookCategory("pets", PETS),
            new InfoBookCategory("misc", MISC)
    );

}