package billeyzambie.practicalpets.jade;

import billeyzambie.practicalpets.entity.fish.base.BreedableFish;
import billeyzambie.practicalpets.entity.fish.base.PracticalFish;
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
    public static final ResourceLocation FISH_BREEDING_PROVIDER = new ResourceLocation(PracticalPets.MODID, "fish_breeding_provider");
    public static final ResourceLocation FISH_GROWTH_PROVIDER = new ResourceLocation(PracticalPets.MODID, "fish_growth_provider");
    public static final ResourceLocation LAUNCHED_FISH_DESPAWN_PROVIDER = new ResourceLocation(PracticalPets.MODID, "launched_fish_despawn_provider");

    @Override
    public void register(IWailaCommonRegistration registration) {
        registration.registerEntityDataProvider(FishGrowthProvider.INSTANCE, BreedableFish.class);
        registration.registerEntityDataProvider(FishBreedingProvider.INSTANCE, BreedableFish.class);
        registration.registerEntityDataProvider(LaunchedFishDespawnProvider.INSTANCE, PracticalFish.class);
    }

    @Override
    public void registerClient(IWailaClientRegistration registration) {
        registration.registerEntityComponent(PetLevelProvider.INSTANCE, PracticalPet.class);
        registration.registerEntityComponent(FishGrowthProvider.INSTANCE, BreedableFish.class);
        registration.registerEntityComponent(FishBreedingProvider.INSTANCE, BreedableFish.class);
        registration.registerEntityComponent(LaunchedFishDespawnProvider.INSTANCE, PracticalFish.class);
    }
}