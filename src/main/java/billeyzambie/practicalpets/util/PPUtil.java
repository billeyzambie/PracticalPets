package billeyzambie.practicalpets.util;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.items.IItemHandler;

public class PPUtil {

    public static ResourceLocation CHICKEN_LOOT = new ResourceLocation("minecraft", "entities/chicken");

    public static void clear(IItemHandler h) {
        for (int i = 0; i < h.getSlots(); i++) {
            ItemStack s = h.getStackInSlot(i);
            if (s.isEmpty()) continue;
            h.extractItem(i, Integer.MAX_VALUE, false);
        }
    }

    public static void dump(IItemHandler h, Level level, Vec3 pos) {
        for (int i = 0; i < h.getSlots(); i++) {
            ItemStack s = h.extractItem(i, Integer.MAX_VALUE, false);
            if (!s.isEmpty())
                level.addFreshEntity(new ItemEntity(level, pos.x, pos.y, pos.z, s));
        }
    }

    public static void dump(IItemHandler h, Entity entity) {
        dump(h, entity.level(), entity.position());
    }

    public static int countItems(IItemHandler h) {
        int total = 0;
        for (int i = 0; i < h.getSlots(); i++) {
            total += h.getStackInSlot(i).getCount(); // empty stacks count as 0
        }
        return total;
    }

    public static boolean isFull(IItemHandler h) {
        for (int i = 0; i < h.getSlots(); i++) {
            ItemStack s = h.getStackInSlot(i);
            if (s.isEmpty()) return false;
            int cap = Math.min(h.getSlotLimit(i), s.getMaxStackSize());
            if (s.getCount() < cap) return false;
        }
        return true;
    }

    public static void dropLootTableAtEntity(Entity entity, ResourceLocation lootTableId) {
        if (!(entity.level() instanceof ServerLevel serverLevel)) return;

        LootTable lootTable = serverLevel.getServer().getLootData().getLootTable(lootTableId);

        final Holder<DamageType> mobAttackDamageType = serverLevel.registryAccess()
                .registryOrThrow(Registries.DAMAGE_TYPE)
                .getHolderOrThrow(DamageTypes.MOB_ATTACK);

        LootParams lootParams = new LootParams.Builder(serverLevel)
                .withParameter(LootContextParams.ORIGIN, entity.position())
                .withParameter(LootContextParams.THIS_ENTITY, entity)
                .withParameter(LootContextParams.DAMAGE_SOURCE, new DamageSource(mobAttackDamageType, entity))
                .create(LootContextParamSets.ENTITY);

        for (ItemStack stack : lootTable.getRandomItems(lootParams)) {
            ItemEntity itemEntity = new ItemEntity(serverLevel, entity.getX(), entity.getY(), entity.getZ(), stack);
            serverLevel.addFreshEntity(itemEntity);
        }


    }

    /** Computes sine in degrees and then multiplies the result by Mth.DEG_TO_RAD.
     * Used to port math animations from bedrock without constantly having to write * Mth.DEG_TO_RAD.
     * Also converts the time from ticks to seconds, with the / 20f. */
    public static float bedrockSin(float value) {
        return Mth.sin(value / 20f * Mth.DEG_TO_RAD) * Mth.DEG_TO_RAD;
    }

    /** Computes cosine in degrees and then multiplies the result by Mth.DEG_TO_RAD.
     * Used to port math animations from bedrock without constantly having to write * Mth.DEG_TO_RAD.
     * Also converts the time from ticks to seconds, with the / 20f. */
    public static float bedrockCos(float value) {
        return Mth.cos(value / 20f * Mth.DEG_TO_RAD) * Mth.DEG_TO_RAD;
    }
}
