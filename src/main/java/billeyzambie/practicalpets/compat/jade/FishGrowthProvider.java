package billeyzambie.practicalpets.compat.jade;

import billeyzambie.practicalpets.entity.fish.base.BreedableFish;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import snownee.jade.api.*;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.theme.IThemeHelper;

public enum FishGrowthProvider implements IEntityComponentProvider, IServerDataProvider<EntityAccessor> {
    INSTANCE;

    private FishGrowthProvider() {
    }

    @Override
    public void appendTooltip(ITooltip tooltip, EntityAccessor accessor, IPluginConfig config) {
        if (accessor.getServerData().contains("GrowingTime", 3)) {
            int time = accessor.getServerData().getInt("GrowingTime");
            if (time > 0) {
                tooltip.add(Component.translatable("jade.mobgrowth.time", new Object[]{IThemeHelper.get().seconds(time)}));
            }

        }
    }

    @Override
    public void appendServerData(CompoundTag tag, EntityAccessor accessor) {
        int time = -1;
        Entity entity = accessor.getEntity();
        if (entity instanceof BreedableFish ageable) {
            time = -ageable.getAge();
        }

        if (time > 0) {
            tag.putInt("GrowingTime", time);
        }

    }

    @Override
    public ResourceLocation getUid() {
        return PracticalPetsWailaPlugin.FISH_GROWTH_PROVIDER;
    }
}
