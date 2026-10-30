package billeyzambie.practicalpets.client.layer;

import billeyzambie.practicalpets.client.model.entity.fish.AnglerfishModel;
import billeyzambie.practicalpets.entity.fish.Anglerfish;
import billeyzambie.practicalpets.misc.PracticalPets;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.resources.ResourceLocation;

public class AnglerfishLightLayer extends EyesLayer<Anglerfish, AnglerfishModel> {
    public AnglerfishLightLayer(RenderLayerParent<Anglerfish, AnglerfishModel> p_117346_) {
        super(p_117346_);
    }

    private static final RenderType ANGLERFISH_LIGHT = RenderType.eyes(
            new ResourceLocation(PracticalPets.MODID, "textures/entity/anglerfish/light.png")
    );

    @Override
    public RenderType renderType() {
        return ANGLERFISH_LIGHT;
    }
}
