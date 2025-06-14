package billeyzambie.practicalpets.client.model.entity.pet_equipment;// Made with Blockbench 4.12.4
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import billeyzambie.animationcontrollers.ACEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class AnniversaryPetHatModel<T extends Entity> extends HierarchicalModel<T> {
	private final ModelPart hat;
	private final ModelPart hat_body;
	private final ModelPart hat_number;

	public AnniversaryPetHatModel(ModelPart root) {
		this.hat = root.getChild("hat");
		this.hat_body = this.hat.getChild("hat_body");
		this.hat_number = this.hat_body.getChild("hat_number");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition hat = partdefinition.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.ZERO);

		PartDefinition hat_body = hat.addOrReplaceChild("hat_body", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.02F))
		.texOffs(0, 6).addBox(-1.0F, -4.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.03F))
		.texOffs(0, 10).addBox(-0.5F, -5.0F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition hat_number = hat_body.addOrReplaceChild("hat_number", CubeListBuilder.create().texOffs(8, 11).addBox(-2.0F, -5.0F, 0.0F, 4.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -5.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 16, 16);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		if (entity instanceof ACEntity mob) {
			float headSizeX = mob.headSizeX();
            float headSizeZ = mob.headSizeZ();

			if (headSizeX < 5 && headSizeZ < 5) {
				this.root().y += 1; //moves it 1 pixel down (somehow)
				this.root().xScale *= (headSizeX + 1) / 4;
				this.root().zScale *= (headSizeZ + 1) / 4;
			}

        }
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		hat.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart root() {
		return hat;
	}
}