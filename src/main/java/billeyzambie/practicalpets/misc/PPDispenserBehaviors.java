package billeyzambie.practicalpets.misc;

import billeyzambie.practicalpets.items.PiranhaLauncher;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.DispensibleContainerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class PPDispenserBehaviors {
    public static void register() {
        DispenserBlock.registerBehavior(PPItems.PIRANHA_LAUNCHER.get(), new DefaultDispenseItemBehavior() {
            @Override
            public ItemStack execute(BlockSource blockSource, @NotNull ItemStack stack) {
                Level level = blockSource.getLevel();
                Position position = DispenserBlock.getDispensePosition(blockSource);
                Direction direction = blockSource.getBlockState().getValue(DispenserBlock.FACING);

                if (!(stack.getItem() instanceof PiranhaLauncher launcher))
                    return stack;

                Projectile projectile = launcher.shoot(stack, stack.getOrCreateTag(), level, null, new Vec3(position.x(), position.y(), position.z()));

                if (projectile == null)
                    return stack;

                projectile.shoot(direction.getStepX(), (float)direction.getStepY() + 0.1F, direction.getStepZ(), this.getPower(), this.getUncertainty());
                level.addFreshEntity(projectile);


                return stack;
            }

            @Override
            protected void playSound(BlockSource p_123364_) {
                p_123364_.getLevel().levelEvent(1002, p_123364_.getPos(), 0);
            }

            private float getUncertainty() {
                return 0f;
            }

            private float getPower() {
                return 1.1f;
            }
        });

        DispenseItemBehavior bucketDispenseBehavior = new DefaultDispenseItemBehavior() {
            private final DefaultDispenseItemBehavior defaultDispenseItemBehavior = new DefaultDispenseItemBehavior();

            @Override
            public ItemStack execute(BlockSource p_123561_, ItemStack p_123562_) {
                DispensibleContainerItem dispensiblecontaineritem = (DispensibleContainerItem)p_123562_.getItem();
                BlockPos blockpos = p_123561_.getPos().relative(p_123561_.getBlockState().getValue(DispenserBlock.FACING));
                Level level = p_123561_.getLevel();
                if (dispensiblecontaineritem.emptyContents((Player)null, level, blockpos, (BlockHitResult)null, p_123562_)) {
                    dispensiblecontaineritem.checkExtraContent((Player)null, level, p_123562_, blockpos);
                    return new ItemStack(Items.BUCKET);
                } else {
                    return this.defaultDispenseItemBehavior.dispense(p_123561_, p_123562_);
                }
            }
        };

        DispenserBlock.registerBehavior(PPItems.PIRANHA_BUCKET.get(), bucketDispenseBehavior);

    }
}
