package billeyzambie.practicalpets.entity.base.practicalpet;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import org.jetbrains.annotations.NotNull;

public interface IPracticalPet extends PetEquipmentWearer, OwnerFollowingPet, LevelablePet {

    default boolean shouldDefendOwner(@NotNull LivingEntity target) {
        return this.anyEquipmentIsBrave() || (asMob().getMaxHealth() >= 20 && asMob().getAttributeValue(Attributes.ATTACK_DAMAGE) >= 3);
    }

    default boolean shouldDefendSelf() {
        return this.anyEquipmentIsBrave() || (asMob().getMaxHealth() >= 20 && asMob().getAttributeValue(Attributes.ATTACK_DAMAGE) >= 3);
    }

    default boolean shouldPanic() {
        return !this.shouldDefendSelf();
    }
}
