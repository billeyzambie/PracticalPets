package billeyzambie.practicalpets.misc;

import billeyzambie.practicalpets.entity.dinosaur.BananaDuck;
import billeyzambie.practicalpets.entity.dinosaur.Duck;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = PracticalPets.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class CommonListener {
//    @SubscribeEvent
//    public static void registerEntityAttributes(EntityAttributeCreationEvent event) {
//        event.put(
//                ModEntities.BANANA_DUCK.get(),
//                BananaDuck.createMobAttributes()
//                        .add(Attributes.MAX_HEALTH, 6)
//                        .add(Attributes.MOVEMENT_SPEED, 0.25)
//                        .add(Attributes.ATTACK_DAMAGE, 2)
//                        .build()
//        );
//        event.put(
//                ModEntities.DUCK.get(),
//                Duck.createMobAttributes()
//                        .add(Attributes.MAX_HEALTH, 6)
//                        .add(Attributes.MOVEMENT_SPEED, Duck.MOVEMENT_SPEED)
//                        .add(Attributes.ATTACK_DAMAGE, 2)
//                        .build()
//        );
//    }

}