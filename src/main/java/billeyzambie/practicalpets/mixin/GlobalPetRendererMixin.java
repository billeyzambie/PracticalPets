package billeyzambie.practicalpets.mixin;

import billeyzambie.practicalpets.client.layer.PetEquipmentLayer;
import billeyzambie.practicalpets.client.model.entity.base.PetEquipmentWearerModel;
import billeyzambie.practicalpets.entity.base.practicalpet.PetEquipmentWearer;
import com.github.alexthe666.alexsmobs.client.render.RenderCrow;
import com.github.alexthe666.alexsmobs.client.render.RenderRaccoon;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.world.entity.Mob;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = {
        CatRenderer.class,
        WolfRenderer.class,
        ParrotRenderer.class,
        RenderRaccoon.class,
        RenderCrow.class
})
public abstract class GlobalPetRendererMixin<T extends Mob & PetEquipmentWearer, M extends EntityModel<T> & PetEquipmentWearerModel> extends MobRenderer<T, M> {

    private GlobalPetRendererMixin(EntityRendererProvider.Context p_174304_, M p_174305_, float p_174306_) {
        super(p_174304_, p_174305_, p_174306_);
    }

    @Inject(
            method = "<init>",
            at = @At("TAIL")
    )
    private void onConstructor(EntityRendererProvider.Context context, CallbackInfo ci) {
        this.addLayer(new PetEquipmentLayer<>(this, context));
    }
}
