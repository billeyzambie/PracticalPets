package billeyzambie.practicalpets.client.model.entity.pet_equipment;// Made with Blockbench 5.0.7
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import billeyzambie.animationcontrollers.ACEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.Entity;

public class PlainPetHatModel<T extends Entity> extends HierarchicalModel<T> {
	private final ModelPart hat;
	private final ModelPart hat_bone2;
	private final ModelPart hat_bone;

	@Override
	public ModelPart root() {
		return hat;
	}

	public PlainPetHatModel(ModelPart root) {
		this.hat = root.getChild("hat");
		this.hat_bone2 = this.hat.getChild("hat_bone2");
		this.hat_bone = this.hat.getChild("hat_bone");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition hat = partdefinition.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition hat_bone2 = hat.addOrReplaceChild("hat_bone2", CubeListBuilder.create().texOffs(17, 0).addBox(-1.0F, -3.05F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, 0.0F));

		PartDefinition hat_bone = hat.addOrReplaceChild("hat_bone", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -0.05F, -2.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.root().y -= 24f;
		if (entity instanceof ACEntity mob) {
			float headSizeX = mob.headSizeX();
			float headSizeY = mob.headSizeY();
			float headSizeZ = mob.headSizeZ();

			if (headSizeY > 2) {
				this.root().y += 1; //moves it 1 pixel down (somehow)
				this.hat_bone.xScale *= headSizeX / 4f + 0.5f;
				this.hat_bone.zScale *= headSizeZ / 4f + 0.5f;
				this.hat_bone2.xScale *= headSizeX / 4f + 0.5f;
				this.hat_bone2.zScale *= headSizeZ / 4f + 0.5f;
				this.hat_bone2.yScale *= (headSizeX + headSizeZ) / 8f + 0.5f;
				this.hat_bone2.y += 0.02f;
			}

		}
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		hat.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}