package billeyzambie.practicalpets.items;

import billeyzambie.practicalpets.misc.PPItems;
import billeyzambie.practicalpets.misc.PracticalPets;
import billeyzambie.practicalpets.ui.infobook.InfoBookCategory;
import billeyzambie.practicalpets.ui.infobook.InfoBookEntry;
import billeyzambie.practicalpets.ui.infobook.InfoBookScreen;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

@Mod.EventBusSubscriber
public class InfoBook extends Item {
    public InfoBook() {
        super(new Properties().stacksTo(1));
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(
            @NotNull Level level,
            @NotNull Player player,
            @NotNull InteractionHand hand
    ) {
        if (level.isClientSide()) {
            setInfoBookScreen();
        }
        return InteractionResultHolder.success(player.getItemInHand(hand));
    }

    @OnlyIn(Dist.CLIENT)
    private static void setInfoBookScreen() {
        Minecraft.getInstance().setScreen(new InfoBookScreen());
    }

    @Override
    public void appendHoverText(@NotNull ItemStack p_41421_, @Nullable Level p_41422_, @NotNull List<Component> tooltip, @NotNull TooltipFlag p_41424_) {
        super.appendHoverText(p_41421_, p_41422_, tooltip, p_41424_);
        tooltip.add(Component.translatable("book.byAuthor", "Bill").withStyle(ChatFormatting.GRAY));
    }

    @SubscribeEvent
    public static void onEntityInteract(PlayerInteractEvent.EntityInteract event) {
        ItemStack stack = event.getItemStack();
        Entity target = event.getTarget();

        if (!stack.is(PPItems.INFO_BOOK.get()) || !(target instanceof Mob))
            return;

        ResourceLocation mobId = ForgeRegistries.ENTITY_TYPES.getKey(target.getType());
        if (mobId != null && mobId.getNamespace().equals(PracticalPets.MODID)) {
            InfoBookCategory.PETS.stream().filter(
                    entry -> entry.name.equals(mobId.getPath())
            ).findFirst().ifPresent(entry -> {
                if (target.level().isClientSide())
                    InfoBookScreen.goToPageOf(entry);
                event.setCanceled(true);
                event.setCancellationResult(InteractionResult.SUCCESS);
            });
        }

    }

    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getEntity();
        CompoundTag persistentData = player.getPersistentData();
        CompoundTag persistedData = persistentData.getCompound(Player.PERSISTED_NBT_TAG);
        boolean gotInfoBook = persistedData.getBoolean("GotPracticalPetsInfoBook");
        if (!gotInfoBook) {
            player.spawnAtLocation(new ItemStack(PPItems.INFO_BOOK.get()));
        }
        persistedData.putBoolean("GotPracticalPetsInfoBook", true);
        persistentData.put(Player.PERSISTED_NBT_TAG, persistedData);
    }
}
