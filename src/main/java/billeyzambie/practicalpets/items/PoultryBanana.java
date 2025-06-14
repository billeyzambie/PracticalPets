package billeyzambie.practicalpets.items;

import billeyzambie.practicalpets.ModItems;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;

import java.util.List;
import java.util.Random;

public class PoultryBanana extends Item {

    public PoultryBanana() {
        super(new Item.Properties()
                .food(new FoodProperties.Builder()
                        .nutrition(3)
                        .saturationMod(1.2f)
                        .alwaysEat()
                        .build()
                )
        );
    }
    private static final List<MobEffect> BENEFICIAL_EFFECTS = List.of(
            MobEffects.MOVEMENT_SPEED,
            MobEffects.DIG_SPEED,
            MobEffects.DAMAGE_BOOST,
            MobEffects.HEAL,
            MobEffects.JUMP,
            MobEffects.REGENERATION,
            MobEffects.DAMAGE_RESISTANCE,
            MobEffects.FIRE_RESISTANCE,
            MobEffects.WATER_BREATHING,
            MobEffects.INVISIBILITY,
            MobEffects.NIGHT_VISION,
            MobEffects.HEALTH_BOOST,
            MobEffects.ABSORPTION,
            MobEffects.SATURATION
    );

    private static final Random random = new Random();

    @Override
    public ItemStack finishUsingItem(ItemStack itemStack, Level level, LivingEntity entity) {
        if (!level.isClientSide) {
            MobEffect randomEffect = BENEFICIAL_EFFECTS.get(random.nextInt(BENEFICIAL_EFFECTS.size()));
            entity.addEffect(new MobEffectInstance(randomEffect, 200, 1));
            ItemStack peel = new ItemStack(ModItems.BANANA_PEEL.get());
            if (entity instanceof Player player) {
                if (!player.addItem(peel)) {
                    player.drop(peel, false);
                } else {
                    entity.spawnAtLocation(peel, 0.5F);
                }
            }
        }
            return super.finishUsingItem(itemStack, level, entity);
        }

        // Example: When right-clicked, the item will play a sound and display a message
    /*@Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        if (!world.isClientSide) {
            // Play a sound
            world.playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.PLAYERS, 1.0F, 1.0F);

            // Send a message to the player
            player.displayClientMessage(Component.translatable("action.practicalpets.use.poultry_banana"), true);
        }
        return super.use(world, player, hand);
    }
    @Override
    public float getDestroySpeed(ItemStack itemStack, BlockState blockState) {
        return 10.0F;
    }*/
}