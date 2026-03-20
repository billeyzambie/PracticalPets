package billeyzambie.practicalpets.entity.base;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

/** Allow mob interfaces to use mob methods without constantly casting */
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
    float distanceTo(Entity entity);
    float getEyeHeight();
    boolean hasCustomName();
    Component getDisplayName();
    default Component mobInterfaceGetTypeName() {
        return asMob().getType().getDescription();
    }
    ItemEntity spawnAtLocation(ItemStack stack);
    double getX();
    double getY();
    double getZ();
    @Nullable AttributeInstance getAttribute(Attribute attribute);
    BlockPos blockPosition();
    Component getName();
    void setHealth(float value);
    float getMaxHealth();
}
