package billeyzambie.practicalpets.jade;


import billeyzambie.practicalpets.entity.fish.base.PracticalFish;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import snownee.jade.api.EntityAccessor;
import snownee.jade.api.IEntityComponentProvider;
import snownee.jade.api.IServerDataProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.theme.IThemeHelper;

public enum LaunchedFishDespawnProvider implements IEntityComponentProvider, IServerDataProvider<EntityAccessor> {
    INSTANCE;

    @Override
    public void appendTooltip(ITooltip tooltip, EntityAccessor accessor, IPluginConfig config) {
        if (accessor.getServerData().contains("LaunchTime", CompoundTag.TAG_INT)) {
            int time = accessor.getServerData().getInt("LaunchTime");
            if (time > 0) {
                tooltip.add(Component.translatable("ui.jade.plugin_practicalpets.launched_fish_despawn", IThemeHelper.get().seconds(time)));
            }

        }
    }

    @Override
    public void appendServerData(CompoundTag tag, EntityAccessor accessor) {
        int time = -1;
        Entity entity = accessor.getEntity();
        if (entity instanceof PracticalFish fish && fish.isLaunched()) {
            time = PracticalFish.MAX_LAUNCHED_LIFESPAN - fish.getLaunchTime();
        }

        if (time > 0) {
            tag.putInt("LaunchTime", time);
        }

    }

    @Override
    public ResourceLocation getUid() {
        return PracticalPetsWailaPlugin.LAUNCHED_FISH_DESPAWN_PROVIDER;
    }
}

