package billeyzambie.practicalpets.client.renderer.dinosaur;

import billeyzambie.practicalpets.client.PPRenders;
import billeyzambie.practicalpets.client.layer.PetBackStrapLayer;
import billeyzambie.practicalpets.client.model.entity.dinosaur.PigeonModel;
import billeyzambie.practicalpets.client.renderer.ItemHoldingEntityRenderer;
import billeyzambie.practicalpets.client.renderer.PracticalPetRenderer;
import billeyzambie.practicalpets.entity.dinosaur.Pigeon;
import billeyzambie.practicalpets.misc.PracticalPets;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class PigeonRenderer extends PracticalPetRenderer<Pigeon, PigeonModel> implements ItemHoldingEntityRenderer {
    private final ItemInHandRenderer itemInHandRenderer;

    public PigeonRenderer(EntityRendererProvider.Context context) {
        super(context, new PigeonModel(context.bakeLayer(PPRenders.PIGEON)), 0.25f);
        this.addLayer(new PetBackStrapLayer<>(this, BACK_STRAP_TEXTURE));
        this.itemInHandRenderer = context.getItemInHandRenderer();
    }

    private static final ResourceLocation BACK_STRAP_TEXTURE = new ResourceLocation(PracticalPets.MODID, "textures/entity/pigeon/back_strap.png");

    private static final ResourceLocation[] TEXTURES = {
            new ResourceLocation(PracticalPets.MODID, "textures/entity/pigeon/pigeon.png"),
            new ResourceLocation(PracticalPets.MODID, "textures/entity/pigeon/pigeon1.png"),
            new ResourceLocation(PracticalPets.MODID, "textures/entity/pigeon/pigeon2.png"),
            new ResourceLocation(PracticalPets.MODID, "textures/entity/pigeon/pigeon3.png"),
            new ResourceLocation(PracticalPets.MODID, "textures/entity/pigeon/pigeon4.png"),
            new ResourceLocation(PracticalPets.MODID, "textures/entity/pigeon/pigeon5.png"),
            new ResourceLocation(PracticalPets.MODID, "textures/entity/pigeon/pigeon6.png")
    };

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Pigeon pigeon) {
        return TEXTURES[pigeon.getVariant() % TEXTURES.length];
    }

    @Override
    public void render(Pigeon entity, float entityYaw, float partialticks, PoseStack poseStack, @NotNull MultiBufferSource buffer, int packedLight) {
        super.render(entity, entityYaw, partialticks, poseStack, buffer, packedLight);
        this.renderItem(this, entity, partialticks, poseStack, buffer, packedLight);
    }

    @Override
    public ItemInHandRenderer getItemInHandRenderer() {
        return this.itemInHandRenderer;
    }
}
