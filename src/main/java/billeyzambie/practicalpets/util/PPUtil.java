package billeyzambie.practicalpets.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.items.IItemHandler;

import java.util.UUID;

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
    public static float bedrockSinAngle(float value) {
        return Mth.sin(value / 20f * Mth.DEG_TO_RAD) * Mth.DEG_TO_RAD;
    }

    /** Computes cosine in degrees and then multiplies the result by Mth.DEG_TO_RAD.
     * Used to port math animations from bedrock without constantly having to write * Mth.DEG_TO_RAD.
     * Also converts the time from ticks to seconds, with the / 20f. */
    public static float bedrockCosAngle(float value) {
        return Mth.cos(value / 20f * Mth.DEG_TO_RAD) * Mth.DEG_TO_RAD;
    }

    public static float bedrockSin(float value) {
        return Mth.sin(value / 20f * Mth.DEG_TO_RAD);
    }

    public static float bedrockCos(float value) {
        return Mth.cos(value / 20f * Mth.DEG_TO_RAD);
    }

    public static Vec3 modelToWorldPosition(LivingEntity entity, Vec3 positionInModel) {
        return positionInModel
                .scale(1 / 16f)
                .yRot(-entity.yBodyRot * Mth.DEG_TO_RAD + Mth.PI)
                .scale(entity.getScale())
                .add(entity.position());
    }

    public static BlockPos getBlockPosInFront(LivingEntity entity) {
        return entity.blockPosition().relative(entity.getDirection());
    }

    public static boolean isSolid(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
        return !blockState.getCollisionShape(blockGetter, blockPos).isEmpty();
    }

    public static boolean isSolid(LivingEntity entity, BlockPos blockPos) {
        return !entity.level().getBlockState(blockPos).getCollisionShape(entity.level(), blockPos).isEmpty();
    }

    public static double distanceXZSqr(Vec3 a, Vec3 b) {
        double dx = b.x - a.x;
        double dz = b.z - a.z;
        return dx * dx + dz * dz;
    }

    public static boolean petsShareOwner(TamableAnimal pet1, TamableAnimal pet2) {
        UUID ownerId = pet1.getOwnerUUID();
        return ownerId != null
                && ownerId.equals(pet2.getOwnerUUID());
    }

    public static void playBoneMealEffectsAt(Entity entity, int count) {
        Level level = entity.level();
        RandomSource randomsource = level.getRandom();
        double d1 = entity.getBbHeight();
        double d0 = 0.5;
        BlockPos blockPos = entity.blockPosition();
        for(int i = 0; i < count; ++i) {
            double d2 = randomsource.nextGaussian() * 0.02D;
            double d3 = randomsource.nextGaussian() * 0.02D;
            double d4 = randomsource.nextGaussian() * 0.02D;
            double d5 = -d0;
            double d6 = entity.getX() + d5 + randomsource.nextDouble() * d0 * 2.0D;
            double d7 = entity.getY() + randomsource.nextDouble() * d1;
            double d8 = entity.getZ() + d5 + randomsource.nextDouble() * d0 * 2.0D;
            level.addParticle(ParticleTypes.HAPPY_VILLAGER, d6, d7, d8, d2, d3, d4);
        }
        level.playLocalSound(blockPos, SoundEvents.BONE_MEAL_USE, SoundSource.NEUTRAL, 1.0F, 1.0F, false);
    }

}
