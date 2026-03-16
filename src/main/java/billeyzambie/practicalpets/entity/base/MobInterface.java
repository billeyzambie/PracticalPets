package billeyzambie.practicalpets.entity.base;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

/** Allow mob interfaces to use mob methods */
public interface MobInterface {
    void playSound(SoundEvent soundEvent);
    void playSound(SoundEvent soundEvent, float volume, float pitch);
    Level level();
    Vec3 position();
    float getHealth();
    RandomSource getRandom();
    boolean isAlive();
}
