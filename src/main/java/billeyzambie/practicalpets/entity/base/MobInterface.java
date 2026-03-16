package billeyzambie.practicalpets.entity.base;

import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

/** Allow mob interfaces to use mob methods */
public interface MobInterface {
    default Mob asMob() {
        return (Mob)this;
    }
    void playSound(SoundEvent soundEvent);
    void playSound(SoundEvent soundEvent, float volume, float pitch);
    Level level();
    Vec3 position();
    float getHealth();
    RandomSource getRandom();
    boolean isAlive();
    boolean hasCustomName();
    Component getDisplayName();
    default Component mobInterfaceGetTypeName() {
        return asMob().getType().getDescription();
    }
    ItemEntity spawnAtLocation(ItemStack stack);
}
