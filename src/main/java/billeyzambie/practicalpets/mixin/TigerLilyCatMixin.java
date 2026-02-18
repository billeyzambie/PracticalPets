package billeyzambie.practicalpets.mixin;

import billeyzambie.practicalpets.misc.PracticalPets;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Cat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Cat.class)
public class TigerLilyCatMixin {
    @Unique
    private static final ResourceLocation TIGERLILY_TEXTURE = new ResourceLocation(
            PracticalPets.MODID,
            "textures/entity/cat/tigerlily.png"
    );

    @Inject(
            method = "getResourceLocation",
            at = @At("HEAD"),
            cancellable = true
    )
    private void tigerLilyNameTagTexture(CallbackInfoReturnable<ResourceLocation> cir) {
        Cat self = (Cat)(Object)this;
        if (self.getName().getString().equals("Tigerlily")) {
            cir.setReturnValue(TIGERLILY_TEXTURE);
        }
    }
}