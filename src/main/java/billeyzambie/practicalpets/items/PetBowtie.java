package billeyzambie.practicalpets.items;

import billeyzambie.practicalpets.misc.PracticalPets;
import billeyzambie.practicalpets.entity.PracticalPet;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PetBowtie extends Item implements AttachablePetCosmetic, DyeableItem {
    public PetBowtie(String modelTextureName) {
        super(new Item.Properties().stacksTo(1));
        this.modelTexture = new ResourceLocation(
                PracticalPets.MODID,
                "textures/entity/pet_equipment/" + modelTextureName + ".png"
        );
    }

    ResourceLocation modelTexture;

    @Override
    public int getDefaultColor() {
        return 11546150;
    }

    @Override
    public ResourceLocation getModelTexture() {
        return modelTexture;
    }

    @Override
    public AttachBone getAttachBone() {
        return AttachBone.BOWTIE;
    }

    @Override
    public Slot slot() {
        return Slot.NECK;
    }

    @Override
    public boolean canBePutOn(PracticalPet pet) {
        return true;
    }

    @Override
    public boolean causesBravery(ItemStack stack) {
        return false;
    }

    public void putDeadPetInfo(ItemStack stack, Component component) {
        stack.getOrCreateTag().putString("DeadPracticalPetInfo", Component.Serializer.toJson(component));
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        super.appendHoverText(stack, p_41422_, components, p_41424_);
        CompoundTag tag = stack.getTag();
        if (tag != null ) {
            MutableComponent deadPracticalPetInfo = Component.Serializer.fromJson(tag.getString("DeadPracticalPetInfo"));
            if (deadPracticalPetInfo != null)
                components.add(deadPracticalPetInfo);
        }
    }
}
