package billeyzambie.practicalpets;

import billeyzambie.practicalpets.blocks.BananaPeel;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlocks {
    public static final DeferredRegister<Block> REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCKS, PracticalPets.MODID);

    public static final RegistryObject<Block> BANANA_PEEL = REGISTRY.register("banana_peel", BananaPeel::new);

}
