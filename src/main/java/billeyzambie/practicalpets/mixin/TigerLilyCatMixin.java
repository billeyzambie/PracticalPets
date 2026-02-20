package billeyzambie.practicalpets.mixin;

import billeyzambie.practicalpets.misc.PracticalPets;
import net.minecraft.client.renderer.entity.CatRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Cat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

//Inject it right before More Mob Variants's weirdness so that this still works when it's installed
@Mixin(value = CatRenderer.class, priority = 999)
public class TigerLilyCatMixin {
    @Unique
    private static final ResourceLocation TIGERLILY_TEXTURE = new ResourceLocation(
            PracticalPets.MODID,
            "textures/entity/cat/tigerlily.png"
    );

    @Inject(
            method = "getTextureLocation(Lnet/minecraft/world/entity/animal/Cat;)Lnet/minecraft/resources/ResourceLocation;",
            at = @At("HEAD"),
            cancellable = true
    )
    private void tigerLilyNameTagTexture(Cat cat, CallbackInfoReturnable<ResourceLocation> cir) {
        if (cat.getName().getString().equals("Tigerlily")) {
            cir.setReturnValue(TIGERLILY_TEXTURE);
        }
    }
}