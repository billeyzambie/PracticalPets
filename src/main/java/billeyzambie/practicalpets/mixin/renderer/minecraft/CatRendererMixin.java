package billeyzambie.practicalpets.mixin.renderer.minecraft;

import billeyzambie.practicalpets.client.layer.PetEquipmentLayer;
import billeyzambie.practicalpets.misc.PracticalPets;
import net.minecraft.client.model.CatModel;
import net.minecraft.client.renderer.entity.CatRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Cat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

//Inject it right before More Mob Variants's weirdness so that this still works when it's installed
@Mixin(value = CatRenderer.class, priority = 999)
public class CatRendererMixin extends MobRenderer<Cat, CatModel<Cat>>  {
    @Unique
    private static final ResourceLocation TIGERLILY_TEXTURE = new ResourceLocation(
            PracticalPets.MODID,
            "textures/entity/cat/tigerlily.png"
    );

    @Unique
    private static final ResourceLocation BUDDER_TEXTURE = new ResourceLocation(
            PracticalPets.MODID,
            "textures/entity/cat/budder.png"
    );

    @Inject(
            method = "getTextureLocation(Lnet/minecraft/world/entity/animal/Cat;)Lnet/minecraft/resources/ResourceLocation;",
            at = @At("HEAD"),
            cancellable = true
    )
    private void onGetTexture(Cat cat, CallbackInfoReturnable<ResourceLocation> cir) {
        switch (cat.getName().getString()) {
            case "Tigerlily" -> cir.setReturnValue(TIGERLILY_TEXTURE);
            case "Budder" -> cir.setReturnValue(BUDDER_TEXTURE);
        }
    }

    @Inject(
            method = "<init>",
            at = @At("RETURN")
    )
    private void onConstructor(EntityRendererProvider.Context context, CallbackInfo ci) {
        this.addLayer(new PetEquipmentLayer(this, context));
    }

    @Unique
    @Override
    public ResourceLocation getTextureLocation(Cat p_114482_) {
        return null;
    }

    public CatRendererMixin(EntityRendererProvider.Context p_174304_, CatModel<Cat> p_174305_, float p_174306_) {
        super(p_174304_, p_174305_, p_174306_);
    }
}