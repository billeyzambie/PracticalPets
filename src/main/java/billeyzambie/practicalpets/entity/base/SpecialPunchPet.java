package billeyzambie.practicalpets.entity.base;

import net.minecraft.world.entity.player.Player;

/** Simply overriding hurt makes you have to sneak when
 * Domestication Innovation is installed */
public interface SpecialPunchPet {
    /** @return {@code true} if the special hurt was performed,
     * unlike hurt which returns false when canceled. */
    boolean specialPunched(Player player);

    /** only run in the client in the domestication innovation mixin */
    boolean shouldDisableDIPunchThrough(Player player);
}
