package billeyzambie.practicalpets.blocks;

import billeyzambie.practicalpets.advancements.MiscTrigger;
import billeyzambie.practicalpets.misc.PPAdvancementTriggers;
import billeyzambie.practicalpets.util.DelayedTaskManager;
import billeyzambie.practicalpets.misc.PPSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BananaPeel extends Block {
    public BananaPeel() {
        super(BlockBehaviour.Properties.of()
                .instabreak()
                .noOcclusion()
                .sound(SoundType.SLIME_BLOCK)
        );
    }

    protected static final VoxelShape COLLISION_SHAPE = Block.box(0, 0, 0, 16.0D, 9.0D, 16.0D);
    protected static final VoxelShape SELECTION_SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 4.0D, 14.0D);

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SELECTION_SHAPE;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return COLLISION_SHAPE;
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader levelReader, BlockPos pos) {
        BlockPos belowPos = pos.below();
        BlockState blockState = levelReader.getBlockState(pos);
        BlockState belowState = levelReader.getBlockState(belowPos);

        return belowState.isFaceSturdy(levelReader, belowPos, Direction.UP) && blockState.getFluidState().isEmpty();
    }

    @Override
    public void neighborChanged(BlockState state, Level world, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
        if (!this.canSurvive(state, world, pos)) {
            world.destroyBlock(pos, true);
        }
    }

    @Override
    public void stepOn(Level world, BlockPos pos, BlockState state, Entity entity) {
        if (
                !world.isClientSide
                && entity instanceof LivingEntity livingEntity
                && !entity.getPersistentData().getBoolean("practicalpets_just_slipped")
                && !livingEntity.isCrouching()
                && !(livingEntity instanceof TamableAnimal pet && pet.isTame())
        ) {
            world.destroyBlock(pos, false, entity);
            livingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 40, 5));
            livingEntity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 240, 5));
            world.playSound(null, pos, PPSounds.BANANA_SLIP_SOUND.get(), SoundSource.BLOCKS);
            entity.getPersistentData().putBoolean("practicalpets_just_slipped", true);
            DelayedTaskManager.schedule(() -> {
                if (entity.isAlive()) {
                    entity.causeFallDamage(16f, 1f, world.damageSources().fall());
                    if (entity.getBbHeight() > 1)
                        entity.teleportRelative(0, -0.75, 0);
                    entity.getPersistentData().remove("practicalpets_just_slipped");
                    if (!entity.isAlive()) {
                        Player player = entity.level().getNearestPlayer(entity, 8);
                        if (player != null)
                            PPAdvancementTriggers.MISC.trigger((ServerPlayer) player, MiscTrigger.Advancement.BANANA_DUCK1);
                    }
                }
            }, 10);
        }
    }
}
