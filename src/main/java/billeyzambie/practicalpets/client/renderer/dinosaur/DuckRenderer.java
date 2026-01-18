
package billeyzambie.practicalpets.client.renderer.dinosaur;

import billeyzambie.practicalpets.client.PPRenders;
import billeyzambie.practicalpets.client.layer.PetBackStrapLayer;
import billeyzambie.practicalpets.misc.PracticalPets;
import billeyzambie.practicalpets.client.layer.DuckArmorLayer;
import billeyzambie.practicalpets.client.model.entity.dinosaur.DuckModel;
import billeyzambie.practicalpets.client.renderer.PracticalPetRenderer;
import billeyzambie.practicalpets.entity.dinosaur.Duck;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class DuckRenderer extends PracticalPetRenderer<Duck, DuckModel> {
    public DuckRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new DuckModel(pContext.bakeLayer(PPRenders.DUCK)), 0.35f);
        this.addLayer(new DuckArmorLayer(this, pContext.getModelSet()));
        this.addLayer(new PetBackStrapLayer<>(this, BACK_STRAP_TEXTURE));
    }

    private static final ResourceLocation PEKIN_TEXTURE = new ResourceLocation(PracticalPets.MODID, "textures/entity/duck/pekin.png");
    private static final ResourceLocation MALLARD_TEXTURE = new ResourceLocation(PracticalPets.MODID, "textures/entity/duck/mallard.png");
    private static final ResourceLocation GOLDEN_TEXTURE = new ResourceLocation(PracticalPets.MODID, "textures/entity/duck/golden.png");
    private static final ResourceLocation MANDARIN_TEXTURE = new ResourceLocation(PracticalPets.MODID, "textures/entity/duck/mandarin.png");
    private static final ResourceLocation RUDDY_TEXTURE = new ResourceLocation(PracticalPets.MODID, "textures/entity/duck/ruddy.png");
    private static final ResourceLocation BUTCHY_TEXTURE = new ResourceLocation(PracticalPets.MODID, "textures/entity/duck/butchy.png");
    private static final ResourceLocation PARADISE_TEXTURE = new ResourceLocation(PracticalPets.MODID, "textures/entity/duck/paradise.png");
    private static final ResourceLocation CAYUGA_TEXTURE = new ResourceLocation(PracticalPets.MODID, "textures/entity/duck/cayuga.png");
    private static final ResourceLocation BABY_TEXTURE = new ResourceLocation(PracticalPets.MODID, "textures/entity/duck/baby.png");
    private static final ResourceLocation DARK_BABY_TEXTURE = new ResourceLocation(PracticalPets.MODID, "textures/entity/duck/dark_baby.png");

    private static final ResourceLocation BACK_STRAP_TEXTURE = new ResourceLocation(PracticalPets.MODID, "textures/entity/duck/back_strap.png");

    private static final ResourceLocation[] ADULT_TEXTURES = {
            PEKIN_TEXTURE,
            MALLARD_TEXTURE,
            GOLDEN_TEXTURE,
            MANDARIN_TEXTURE,
            RUDDY_TEXTURE,
            BUTCHY_TEXTURE,
            PARADISE_TEXTURE,
            CAYUGA_TEXTURE
    };

    private static final ResourceLocation[] BABY_TEXTURES = {
            BABY_TEXTURE,
            DARK_BABY_TEXTURE,
            GOLDEN_TEXTURE,
            DARK_BABY_TEXTURE,
            DARK_BABY_TEXTURE,
            BABY_TEXTURE,
            BABY_TEXTURE,
            CAYUGA_TEXTURE
    };

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Duck duck) {
        int variant = duck.getVariant();
        return duck.isBaby() ? BABY_TEXTURES[variant % BABY_TEXTURES.length] : ADULT_TEXTURES[variant % ADULT_TEXTURES.length];
    }
}
