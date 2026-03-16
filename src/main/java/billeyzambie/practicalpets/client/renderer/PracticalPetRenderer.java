package billeyzambie.practicalpets.client.renderer;

import billeyzambie.animationcontrollers.ACEntity;
import billeyzambie.animationcontrollers.PracticalPetModel;
import billeyzambie.practicalpets.client.layer.PetEquipmentLayer;
import billeyzambie.practicalpets.entity.base.practicalpet.PetEquipmentWearer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.world.entity.Mob;
import org.jetbrains.annotations.NotNull;

public abstract class PracticalPetRenderer<T extends Mob & PetEquipmentWearer & ACEntity, M extends PracticalPetModel<T>> extends MobRenderer<T, M> {

    public PracticalPetRenderer(EntityRendererProvider.Context context, M model, float shadowRadius) {
        super(context, model, shadowRadius);
        this.addLayer(new PetEquipmentLayer<>(this, context));
    }

    @Override
    public void render(T entity, float entityYaw, float partialticks, PoseStack poseStack,
                       @NotNull MultiBufferSource buffer, int packedLight) {
        float scale = entity.getScale();
        poseStack.scale(scale, scale, scale);

        super.render(entity, entityYaw, partialticks, poseStack, buffer, packedLight);

    }
}
