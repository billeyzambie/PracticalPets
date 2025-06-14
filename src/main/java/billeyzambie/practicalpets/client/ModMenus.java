package billeyzambie.practicalpets.client;

import billeyzambie.practicalpets.client.menus.BananaDuckMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMenus {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, "practicalpets");

    public static final RegistryObject<MenuType<BananaDuckMenu>> BANANA_DUCK_MENU =
            MENUS.register("banana_duck_menu", () -> IForgeMenuType.create((id, inv, buf) -> new BananaDuckMenu(id, inv, null)));
}
