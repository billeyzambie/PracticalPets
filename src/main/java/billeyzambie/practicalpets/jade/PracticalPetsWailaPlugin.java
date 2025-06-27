package billeyzambie.practicalpets.jade;

import billeyzambie.practicalpets.misc.PracticalPets;
import billeyzambie.practicalpets.entity.PracticalPet;
import net.minecraft.resources.ResourceLocation;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaCommonRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;

@WailaPlugin
public class PracticalPetsWailaPlugin implements IWailaPlugin {

    public static final ResourceLocation PET_LEVEL_PROVIDER = new ResourceLocation(PracticalPets.MODID, "pet_level_provider");

    @Override
    public void register(IWailaCommonRegistration registration) {
        //TODO register data providers
    }

    @Override
    public void registerClient(IWailaClientRegistration registration) {
        registration.registerEntityComponent(PetLevelComponentProvider.INSTANCE, PracticalPet.class);
    }
}