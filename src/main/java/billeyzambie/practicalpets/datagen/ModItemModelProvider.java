package billeyzambie.practicalpets.datagen;

import billeyzambie.practicalpets.ModItems;
import billeyzambie.practicalpets.PracticalPets;
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
        simpleItem(ModItems.LEATHER_DUCK_ARMOR);
        simpleItem(ModItems.GOLDEN_DUCK_ARMOR);
        simpleItem(ModItems.CHAINMAIL_DUCK_ARMOR);
        simpleItem(ModItems.IRON_DUCK_ARMOR);
        simpleItem(ModItems.DIAMOND_DUCK_ARMOR);
        simpleItem(ModItems.NETHERITE_DUCK_ARMOR);
        simpleItem(ModItems.PET_BOWTIE);
        simpleItem(ModItems.ANNIVERSARY_PET_HAT_0);
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