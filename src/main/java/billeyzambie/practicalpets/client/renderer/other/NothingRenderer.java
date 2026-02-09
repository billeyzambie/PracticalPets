package billeyzambie.practicalpets.client.renderer.other;

import billeyzambie.practicalpets.misc.PracticalPets;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

public class NothingRenderer<T extends Entity> extends EntityRenderer<T> {
    public NothingRenderer(EntityRendererProvider.Context p_174008_) {
        super(p_174008_);
    }

    private static final ResourceLocation BLACK_TEXTURE = new ResourceLocation(
            PracticalPets.MODID,
            "black.png"
    );

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull T p_114482_) {
        //never used but I had to put something
        return BLACK_TEXTURE;
    }

    @Override
    public boolean shouldRender(@NotNull T p_114491_, @NotNull Frustum p_114492_, double p_114493_, double p_114494_, double p_114495_) {
        return false;
    }
}
