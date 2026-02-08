package billeyzambie.practicalpets.items;

import billeyzambie.practicalpets.misc.PracticalPets;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AnniversaryPetHat extends PetHat {
    public AnniversaryPetHat(String modelTextureName, float xpMultiplier, Properties properties) {
        super(modelTextureName, xpMultiplier, properties);
        this.modelEmissiveTexture = new ResourceLocation(
                PracticalPets.MODID,
                "textures/entity/pet_equipment/" + modelTextureName + "_emissive.png"
        );
    }

    final ResourceLocation modelEmissiveTexture;

    @Override
    public @Nullable ResourceLocation getModelEmissiveTexture() {
        return modelEmissiveTexture;
    }

    public static void putPlayerName(ItemStack stack, String playerName) {
        stack.getOrCreateTag().putString("PracticalPetsPlayerName", playerName);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        super.appendHoverText(stack, p_41422_, components, p_41424_);
        var tag = stack.getTag();
        if (tag == null)
            return;
        String playerName = tag.getString("PracticalPetsPlayerName");
        if (playerName.isEmpty())
            return;
        components.add(Component.literal(playerName).withStyle(ChatFormatting.AQUA));
    }
}
