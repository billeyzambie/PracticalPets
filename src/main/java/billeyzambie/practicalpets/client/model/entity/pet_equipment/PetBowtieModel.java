package billeyzambie.practicalpets.client.model.entity.pet_equipment;// Made with Blockbench 4.12.4
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.Entity;

public class PetBowtieModel<T extends Entity> extends HierarchicalModel<T> {
    private final ModelPart bowtie;

    public PetBowtieModel(ModelPart root) {
        this.bowtie = root.getChild("bowtie");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        partdefinition.addOrReplaceChild("bowtie",
                CubeListBuilder.create()
                        .texOffs(0, 4).addBox(-1.0F, -0.5F, -1.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 0).addBox(-2.5F, -1.5F, -0.5F, 5.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.ZERO);

        return LayerDefinition.create(meshdefinition, 16, 16);
    }

    @Override
    public ModelPart root() {
        return bowtie;
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }
}