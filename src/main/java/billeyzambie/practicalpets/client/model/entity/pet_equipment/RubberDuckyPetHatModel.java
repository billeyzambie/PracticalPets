package billeyzambie.practicalpets.client.model.entity.pet_equipment;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

public class RubberDuckyPetHatModel<T extends Entity> extends HierarchicalModel<T> {
	private final ModelPart hat;
	private final ModelPart plushie_whole;

	public RubberDuckyPetHatModel(ModelPart root) {
		this.hat = root.getChild("hat");
		this.plushie_whole = this.hat.getChild("plushie_whole");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition hat = partdefinition.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition plushie_whole = hat.addOrReplaceChild("plushie_whole", CubeListBuilder.create().texOffs(22, 7).addBox(-1.5F, -5.0F, -3.5F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-2.0F, -3.0F, -2.5F, 4.0F, 3.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(0, 13).addBox(-1.0F, -3.0F, 2.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 8).addBox(2.0F, -3.0F, -1.5F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(0, 8).mirror().addBox(-3.0F, -3.0F, -1.5F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(8, 12).addBox(-1.0F, -4.0F, -5.5F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -24.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		hat.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

    @Override
    public @NotNull ModelPart root() {
        return hat;
    }
}