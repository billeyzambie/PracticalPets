package billeyzambie.practicalpets.misc;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class PPTags {
    public static class Blocks {
        public static final TagKey<Block> GRASS = tag("grass");

        private static TagKey<Block> tag(String name)
        {
            return BlockTags.create(new ResourceLocation(PracticalPets.MODID, name));
        }
    }
    public static class Items {
        public static final TagKey<Item> LEVEL_1_TO_POULTRY_BANANA = tag("level_1_to_poultry_banana");
        public static final TagKey<Item> LEVEL_5_TO_POULTRY_BANANA = tag("level_5_to_poultry_banana");
        public static final TagKey<Item> LEVEL_7_TO_POULTRY_BANANA = tag("level_7_to_poultry_banana");
        public static final TagKey<Item> LEVEL_10_TO_POULTRY_BANANA = tag("level_10_to_poultry_banana");

        private static TagKey<Item> tag(String name)
        {
            return ItemTags.create(new ResourceLocation(PracticalPets.MODID, name));
        }
    }
}
