package billeyzambie.practicalpets.datagen;

import billeyzambie.practicalpets.ModItems;
import billeyzambie.practicalpets.PracticalPets;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.function.Consumer;

import static billeyzambie.practicalpets.ModItems.*;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {

    public ModRecipeProvider(PackOutput output) {
        super(output);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        duckArmorRecipe(consumer, "leather", LEATHER_DUCK_ARMOR.getId().getPath(), Items.LEATHER);
        duckArmorRecipe(consumer, "iron", IRON_DUCK_ARMOR.getId().getPath(), Items.IRON_INGOT);
        duckArmorRecipe(consumer, "golden", GOLDEN_DUCK_ARMOR.getId().getPath(), Items.GOLD_INGOT);
        duckArmorRecipe(consumer, "diamond", DIAMOND_DUCK_ARMOR.getId().getPath(), Items.DIAMOND);
        duckArmorRecipe(consumer, "chainmail", CHAINMAIL_DUCK_ARMOR.getId().getPath(), Items.IRON_NUGGET);
        SmithingTransformRecipeBuilder
                .smithing(
                        Ingredient.of(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE),
                        Ingredient.of(ModItems.DIAMOND_DUCK_ARMOR.get()),
                        Ingredient.of(Items.NETHERITE_INGOT),
                        RecipeCategory.COMBAT,
                        ModItems.NETHERITE_DUCK_ARMOR.get()
                )
                .unlocks("has_netherite_ingot", has(Items.NETHERITE_INGOT))
                .save(consumer, new ResourceLocation(PracticalPets.MODID, NETHERITE_DUCK_ARMOR.getId().getPath()));
    }

    private void duckArmorRecipe(Consumer<FinishedRecipe> consumer, String materialName, String resultId, Item ingredient) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, getDuckArmorItem(materialName))
                .define('M', ingredient)
                .define('N', materialName.equals("chainmail") ? Items.IRON_INGOT : ingredient)
                .pattern(" M ")
                .pattern("MN ")
                .pattern(" MM")
                .unlockedBy("has_material", has(ingredient))
                .save(consumer, new ResourceLocation(PracticalPets.MODID, resultId));
    }

    private net.minecraft.world.item.Item getDuckArmorItem(String materialName) {
        return switch (materialName) {
            case "leather" -> LEATHER_DUCK_ARMOR.get();
            case "iron" -> IRON_DUCK_ARMOR.get();
            case "golden" -> GOLDEN_DUCK_ARMOR.get();
            case "diamond" -> DIAMOND_DUCK_ARMOR.get();
            case "netherite" -> NETHERITE_DUCK_ARMOR.get();
            case "chainmail" -> CHAINMAIL_DUCK_ARMOR.get();
            default -> throw new IllegalArgumentException("Unknown material: " + materialName);
        };
    }
}
