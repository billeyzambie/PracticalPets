package billeyzambie.practicalpets.compat;

import billeyzambie.practicalpets.entity.base.practicalpet.GuardingOwnerFollowingPet;
import com.github.alexthe666.citadel.server.entity.IComandableMob;
import net.minecraft.world.entity.TamableAnimal;

public class DomesticationInnovationHelper {
    public static GuardingOwnerFollowingPet.FollowMode getFollowModeForVanillaPet(TamableAnimal pet) {
        IComandableMob comandableMob = (IComandableMob) pet;
        int command = comandableMob.getCommand();
        return GuardingOwnerFollowingPet.FollowMode.values()[2 - command];
    }
    public static void setPetCommandFromFollowMode(TamableAnimal pet, GuardingOwnerFollowingPet.FollowMode followMode) {
        if (followMode == GuardingOwnerFollowingPet.FollowMode.GUARDING)
            followMode = GuardingOwnerFollowingPet.FollowMode.WANDERING;
        int command = 2 - followMode.ordinal();
        IComandableMob comandableMob = (IComandableMob) pet;
        comandableMob.setCommand(command);
    }
}
