package billeyzambie.practicalpets.misc;

import billeyzambie.practicalpets.items.PiranhaLauncher;
import net.minecraft.core.BlockSource;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
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
                return 6.0F;
            }

            private float getPower() {
                return 1.1F;
            }
        });
    }
}
