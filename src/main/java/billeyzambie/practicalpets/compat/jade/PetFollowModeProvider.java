package billeyzambie.practicalpets.compat.jade;

import billeyzambie.practicalpets.entity.base.practicalpet.GuardingOwnerFollowingPet;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import snownee.jade.api.EntityAccessor;
import snownee.jade.api.IEntityComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

public enum PetFollowModeProvider implements IEntityComponentProvider {
    INSTANCE;

    @Override
    public void appendTooltip(ITooltip tooltip, EntityAccessor accessor, IPluginConfig config) {
        if (!(accessor.getEntity() instanceof GuardingOwnerFollowingPet pet) || !pet.isTame())
            return;

        GuardingOwnerFollowingPet.FollowMode followMode =
                GuardingOwnerFollowingPet.FollowMode.values()[pet.getDisplayFollowModeId()];

        tooltip.add(
                Component.translatable(followMode.getJadeString(pet))
                        .withStyle(ChatFormatting.GREEN)
        );
    }

    @Override
    public ResourceLocation getUid() {
        return PracticalPetsWailaPlugin.PET_FOLLOW_MODE_PROVIDER;
    }
}
