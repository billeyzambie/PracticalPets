package billeyzambie.practicalpets.misc;

import billeyzambie.practicalpets.blocks.AlwaysGrownCrop;
import billeyzambie.practicalpets.blocks.BananaPeel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class PPBlocks {
    public static final DeferredRegister<Block> REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCKS, PracticalPets.MODID);

    public static final RegistryObject<Block> BANANA_PEEL = REGISTRY.register("banana_peel", BananaPeel::new);

    public static final RegistryObject<Block> POTATO_STICKS = REGISTRY.register("potato_sticks",
            () -> new AlwaysGrownCrop(BlockBehaviour.Properties.copy(Blocks.POTATOES))
    );

}