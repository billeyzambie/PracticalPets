package billeyzambie.practicalpets.client;

import billeyzambie.practicalpets.client.ui.PracticalPetMenu;
import billeyzambie.practicalpets.entity.PracticalPet;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMenus {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, "practicalpets");

    public static final RegistryObject<MenuType<PracticalPetMenu>> PRACTICAL_PET_MENU =
            MENUS.register("practical_pet_menu",
                    () -> IForgeMenuType.create((id, inv, buf) -> {
                        int entityId = buf.readVarInt();
                        Entity entity = inv.player.level().getEntity(entityId);
                        return new PracticalPetMenu(id, inv, (PracticalPet) entity);
                    }));
}
