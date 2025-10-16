package billeyzambie.practicalpets.items;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BowlFoodItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;

public class Ratatouille extends BowlFoodItem {
    public Ratatouille(Properties properties) {
        super(properties);
    }

    private static final HashMap<Integer, FoodProperties> LEVELED_RATATOUILLE_CACHE = new HashMap<>();

    private static int getRatLevel(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        if (tag == null) {
            return 1;
        }
        return tag.getInt("RatLevel");
    }

    @Override
    public Rarity getRarity(@NotNull ItemStack itemStack) {
        int ratLevel = getRatLevel(itemStack);
        if (ratLevel == 1)
            return Rarity.COMMON;
        if (ratLevel >= 10)
            return Rarity.EPIC;
        if (ratLevel >= 5)
            return  Rarity.RARE;
        return Rarity.UNCOMMON;
    }

    @Override
    public @Nullable FoodProperties getFoodProperties(ItemStack stack, @Nullable LivingEntity entity) {
        int ratLevel = getRatLevel(stack);
        return LEVELED_RATATOUILLE_CACHE.computeIfAbsent(ratLevel,i -> new FoodProperties.Builder()
                .nutrition(6 + i / 3).saturationMod(1.2f).alwaysEat()
                .effect(() -> new MobEffectInstance(MobEffects.NIGHT_VISION, 340 + i * 60, 0), 1f)
                .effect(() -> new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 240 + i * 60, Math.min(3, 1 + i / 5)), 1f)
                .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 240 + i * 60, i / 5), 1f)
                .build()
        );
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, List<Component> tooltip, @NotNull TooltipFlag flag) {
        int ratLevel = getRatLevel(stack);
        if (ratLevel > 1) tooltip.add(Component.translatable(
                "ui.practicalpets.pet_level",
                Component.literal(
                        Integer.toString(ratLevel)
                ).withStyle(ChatFormatting.BLUE)
        ).withStyle(ChatFormatting.LIGHT_PURPLE));
        super.appendHoverText(stack, level, tooltip, flag);
    }
}
