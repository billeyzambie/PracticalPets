package billeyzambie.practicalpets.util;

import billeyzambie.practicalpets.misc.PracticalPets;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class PPTags {
    public static class Blocks {
        public static final TagKey<Block> GRASS = tag("grass");

        private static TagKey<Block> tag(String name)
        {
            return BlockTags.create(new ResourceLocation(PracticalPets.MODID, name));
        }
    }
}
