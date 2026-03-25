package billeyzambie.practicalpets.compat;

import billeyzambie.practicalpets.entity.base.practicalpet.PracticalPetEvents;
import com.github.alexthe666.alexsmobs.entity.ai.CrowAIMelee;

public class AlexsMobsHelper {
    public static void registerAlexsMobsAttackGoals() {
        var goalMap = PracticalPetEvents.PetEquipmentWearerEvents.goalMap;
        PracticalPetEvents.PetEquipmentWearerEvents.registerGoalReturnSelf(CrowAIMelee.class);
    }
}
