package billeyzambie.practicalpets.client.renderer.otherpet;

import billeyzambie.practicalpets.client.PPRenders;
import billeyzambie.practicalpets.client.layer.PetBackStrapLayer;
import billeyzambie.practicalpets.client.layer.RatPatternLayer;
import billeyzambie.practicalpets.client.model.entity.otherpet.RatModel;
import billeyzambie.practicalpets.client.renderer.ItemHoldingEntityRenderer;
import billeyzambie.practicalpets.client.renderer.PracticalPetRenderer;
import billeyzambie.practicalpets.entity.otherpet.Rat;
import billeyzambie.practicalpets.misc.PracticalPets;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class RatRenderer extends PracticalPetRenderer<Rat, RatModel> implements ItemHoldingEntityRenderer {
    private final ItemInHandRenderer itemInHandRenderer;

    public RatRenderer(EntityRendererProvider.Context context) {
        super(context, new RatModel(context.bakeLayer(PPRenders.RAT)), 0.4f);
        this.addLayer(new RatPatternLayer(this));
        this.addLayer(new PetBackStrapLayer<>(this, BACK_STRAP_TEXTURE));
        itemInHandRenderer = context.getItemInHandRenderer();
    }

    private static final ResourceLocation ALBINO_TEXTURE =
            new ResourceLocation(PracticalPets.MODID, "textures/entity/rat/albino.png");

    private static final ResourceLocation[] BASE_TEXTURES = {
            new ResourceLocation(PracticalPets.MODID, "textures/entity/rat/rat.png"),
            new ResourceLocation(PracticalPets.MODID, "textures/entity/rat/rat1.png"),
            new ResourceLocation(PracticalPets.MODID, "textures/entity/rat/rat2.png"),
            new ResourceLocation(PracticalPets.MODID, "textures/entity/rat/rat3.png"),
            new ResourceLocation(PracticalPets.MODID, "textures/entity/rat/rat4.png"),
    };

    private static final ResourceLocation BACK_STRAP_TEXTURE =
            new ResourceLocation(PracticalPets.MODID, "textures/entity/rat/back_strap.png");

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Rat rat) {
        if (rat.isAlbino())
            return ALBINO_TEXTURE;

        return BASE_TEXTURES[rat.getVariant() % BASE_TEXTURES.length];
    }

    @Override
    public ItemInHandRenderer getItemInHandRenderer() {
        return this.itemInHandRenderer;
    }

    @Override
    public void render(@NotNull Rat entity, float entityYaw, float partialticks, @NotNull PoseStack poseStack, @NotNull MultiBufferSource buffer, int packedLight) {
        super.render(entity, entityYaw, partialticks, poseStack, buffer, packedLight);
        this.renderItem(this, entity, partialticks, poseStack, buffer, packedLight);
    }

}
