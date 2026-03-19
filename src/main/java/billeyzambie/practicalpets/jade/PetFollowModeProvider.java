package billeyzambie.practicalpets.jade;

import billeyzambie.practicalpets.entity.base.practicalpet.GuardingOwnerFollowingPet;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import snownee.jade.api.EntityAccessor;
import snownee.jade.api.IEntityComponentProvider;
import snownee.jade.api.IServerDataProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

public enum PetFollowModeProvider implements IEntityComponentProvider, IServerDataProvider<EntityAccessor> {
    INSTANCE;

    @Override
    public void appendTooltip(ITooltip tooltip, EntityAccessor accessor, IPluginConfig config) {
        if (accessor.getServerData().contains("PetFollowModeId", CompoundTag.TAG_INT)) {
            int followModeId = accessor.getServerData().getInt("PetFollowModeId");
            var followMode = GuardingOwnerFollowingPet.FollowMode.values()[followModeId];
            tooltip.add(
                    Component.translatable(followMode.jadeTranslationString)
                            .withStyle(ChatFormatting.GREEN)
            );

        }
    }

    @Override
    public void appendServerData(CompoundTag tag, EntityAccessor accessor) {
        Entity entity = accessor.getEntity();
        if (entity instanceof GuardingOwnerFollowingPet pet) {
            int followModeId = pet.getFollowMode().ordinal();
            tag.putInt("PetFollowModeId", followModeId);
        }

    }

    @Override
    public ResourceLocation getUid() {
        return PracticalPetsWailaPlugin.PET_FOLLOW_MODE_PROVIDER;
    }
}
