package billeyzambie.practicalpets.util;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

public class PPUtil {

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
}
