package billeyzambie.practicalpets.misc;

import billeyzambie.practicalpets.advancements.MiscTrigger;
import billeyzambie.practicalpets.advancements.UsedPetAbilityTrigger;
import net.minecraft.advancements.CriteriaTriggers;

public class PPAdvancementTriggers {
    public static final UsedPetAbilityTrigger USED_PET_ABILITY = new UsedPetAbilityTrigger();
    public static final MiscTrigger MISC = new MiscTrigger();

    public static void init() {
        CriteriaTriggers.register(USED_PET_ABILITY);
        CriteriaTriggers.register(MISC);
    }
}
