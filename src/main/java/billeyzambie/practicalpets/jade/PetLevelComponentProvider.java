package billeyzambie.practicalpets.jade;

import billeyzambie.practicalpets.entity.PracticalPet;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import snownee.jade.api.EntityAccessor;
import snownee.jade.api.IEntityComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

public enum PetLevelComponentProvider implements IEntityComponentProvider {
    INSTANCE;

    @Override
    public void appendTooltip(
            ITooltip tooltip,
            EntityAccessor accessor,
            IPluginConfig config
    ) {
        if (accessor.getEntity() instanceof PracticalPet pet && pet.isLevelable()) {
            int level = pet.petLevel();
            int xpForNextLevel = (int) (pet.getTotalPetXPNeededForLevel(level + 1) - pet.getTotalPetXPNeededForLevel(level));
            int currentXp = (int) (pet.petXP() - pet.getTotalPetXPNeededForLevel(level));
            currentXp = Math.min(currentXp, xpForNextLevel - 1);
            tooltip.add(
                    Component.translatable(
                            "ui.jade.plugin_practicalpets.pet_level",
                            Component.literal(
                                    Integer.toString(level)
                            ).withStyle(ChatFormatting.BLUE)
                    ).withStyle(ChatFormatting.LIGHT_PURPLE)
            );
            tooltip.add(
                    Component.translatable(
                            "ui.jade.plugin_practicalpets.pet_xp",
                            Component.literal(
                                    Integer.toString(currentXp)
                            ).withStyle(ChatFormatting.LIGHT_PURPLE),
                            Component.literal(
                                    Integer.toString(xpForNextLevel)
                            ).withStyle(ChatFormatting.BLUE)
                    )
            );
        }
    }

    @Override
    public ResourceLocation getUid() {
        return PracticalPetsWailaPlugin.PET_LEVEL_PROVIDER;
    }
}