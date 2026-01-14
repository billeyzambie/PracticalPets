package billeyzambie.practicalpets.datagen;

import billeyzambie.practicalpets.misc.PPItems;
import billeyzambie.practicalpets.misc.PracticalPets;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, PracticalPets.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        simpleItem(PPItems.LEATHER_DUCK_ARMOR);
        simpleItem(PPItems.GOLDEN_DUCK_ARMOR);
        simpleItem(PPItems.CHAINMAIL_DUCK_ARMOR);
        simpleItem(PPItems.IRON_DUCK_ARMOR);
        simpleItem(PPItems.DIAMOND_DUCK_ARMOR);
        simpleItem(PPItems.NETHERITE_DUCK_ARMOR);
        simpleItem(PPItems.PET_BOWTIE);
        simpleItem(PPItems.ANNIVERSARY_PET_HAT_0);
        simpleItem(PPItems.RUBBER_DUCKY_PET_HAT);
        simpleItem(PPItems.DIAMOND_NUGGET);
        simpleItem(PPItems.CHICKEN_NUGGET);
        simpleItem(PPItems.PET_CHEF_HAT);
        simpleItem(PPItems.RATATOUILLE);
        //simpleItem(PPItems.PET_BACKPACK);
        //simpleItem(ModItems.END_ROD_DUCK_ARMOR);
    }

    private ItemModelBuilder simpleItem(RegistryObject<Item> item) {
        return withExistingParent(
                item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(PracticalPets.MODID,"item/" + item.getId().getPath())
        );
    }
}