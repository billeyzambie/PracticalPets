package billeyzambie.practicalpets.misc;

import billeyzambie.practicalpets.client.ModModelLayers;
import billeyzambie.practicalpets.client.model.entity.dinosaur.BananaDuckArmorModel;
import billeyzambie.practicalpets.client.model.entity.dinosaur.BananaDuckModel;
import billeyzambie.practicalpets.client.model.entity.dinosaur.DuckArmorModel;
import billeyzambie.practicalpets.client.model.entity.dinosaur.DuckModel;
import billeyzambie.practicalpets.client.model.entity.pet_equipment.AnniversaryPetHatModel;
import billeyzambie.practicalpets.client.model.entity.pet_equipment.PetBowtieModel;
import billeyzambie.practicalpets.client.model.entity.pet_equipment.RubberDuckyPetHatModel;
import billeyzambie.practicalpets.client.renderer.dinosaur.BananaDuckRenderer;
import billeyzambie.practicalpets.client.renderer.dinosaur.DuckRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = PracticalPets.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class ClientListener {
}