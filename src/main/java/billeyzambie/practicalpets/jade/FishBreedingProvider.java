package billeyzambie.practicalpets.jade;

import billeyzambie.practicalpets.entity.fish.base.BreedableFish;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import snownee.jade.api.*;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.theme.IThemeHelper;

public enum FishBreedingProvider implements IEntityComponentProvider, IServerDataProvider<EntityAccessor> {
    INSTANCE;

    @Override
    public void appendTooltip(ITooltip tooltip, EntityAccessor accessor, IPluginConfig config) {
        if (accessor.getServerData().contains("BreedingCD", 3)) {
            int time = accessor.getServerData().getInt("BreedingCD");
            if (time > 0) {
                tooltip.add(Component.translatable("jade.mobbreeding.time", IThemeHelper.get().seconds(time)));
            }

        }
    }

    @Override
    public void appendServerData(CompoundTag tag, EntityAccessor accessor) {
        int time = 0;
        Entity entity = accessor.getEntity();
        time = ((BreedableFish) entity).getAge();

        if (time > 0) {
            tag.putInt("BreedingCD", time);
        }

    }

    @Override
    public ResourceLocation getUid() {
        return PracticalPetsWailaPlugin.FISH_BREEDING_PROVIDER;
    }
}
