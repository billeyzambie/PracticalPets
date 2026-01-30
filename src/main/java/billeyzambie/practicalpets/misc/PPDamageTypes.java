package billeyzambie.practicalpets.misc;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;

public class PPDamageTypes {
    public static final ResourceKey<DamageType> STICK_BUGGED =
            ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(PracticalPets.MODID,
                    "stick_bugged"));

    public static DamageSource stickBuggedDamage(ServerLevel level) {
        return new DamageSource(level.registryAccess().lookupOrThrow(Registries.DAMAGE_TYPE).getOrThrow(STICK_BUGGED));
    }
}
