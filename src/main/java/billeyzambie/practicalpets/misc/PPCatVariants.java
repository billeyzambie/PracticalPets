package billeyzambie.practicalpets.misc;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.CatVariant;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class PPCatVariants {
    public static final DeferredRegister<CatVariant> REGISTRY = DeferredRegister.create(Registries.CAT_VARIANT, PracticalPets.MODID);

    private static RegistryObject<CatVariant> register(String name) {
        return REGISTRY.register(name, () -> new CatVariant(
                new ResourceLocation(PracticalPets.MODID, "textures/entity/cat/" + name + ".png")
        ));
    }

    public static final RegistryObject<CatVariant> PERRY = register("perry");
    public static final RegistryObject<CatVariant> PIZZA = register("pizza");
    public static final RegistryObject<CatVariant> SUGAR = register("sugar");

    public static final RegistryObject<CatVariant> LILY = register("lily");
}
