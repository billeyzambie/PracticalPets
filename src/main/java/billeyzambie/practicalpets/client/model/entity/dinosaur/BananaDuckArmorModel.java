package billeyzambie.practicalpets.client.model.entity.dinosaur;// Made with Blockbench 4.12.4
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import billeyzambie.practicalpets.entity.dinosaur.BananaDuck;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class BananaDuckArmorModel extends BananaDuckModel {

	public BananaDuckArmorModel(ModelPart root) {
		this.ooo = root.getChild("ooo");
		this.pop = this.ooo.getChild("pop");
		this.shake = this.pop.getChild("shake");
		this.body = this.shake.getChild("body");
		this.leg0 = this.body.getChild("leg0");
		this.bone5 = this.leg0.getChild("bone5");
		this.leg1 = this.body.getChild("leg1");
		this.bone4 = this.leg1.getChild("bone4");
		this.bone6 = this.body.getChild("bone6");
		this.bodynolegs = this.bone6.getChild("bodynolegs");
		this.bodylol = this.bodynolegs.getChild("bodylol");
		this.head = this.bodylol.getChild("head");
		this.lol = this.head.getChild("lol");
		this.beak = this.lol.getChild("beak");
		this.bone = this.beak.getChild("bone");
		this.bone3 = this.beak.getChild("bone3");
		this.bone7 = this.lol.getChild("bone7");
		this.hat = this.bone7.getChild("hat");
		this.bone11 = this.bone7.getChild("bone11");
		this.bone10 = this.bone11.getChild("bone10");
		this.bone9 = this.bone11.getChild("bone9");
		this.bone2 = this.bodynolegs.getChild("bone2");
		this.bone8 = this.bone2.getChild("bone8");
		this.wing1 = this.bone2.getChild("wing1");
		this.wing2 = this.wing1.getChild("wing2");
		this.duck_tail = this.bone2.getChild("duck_tail");
		this.wing5 = this.duck_tail.getChild("wing5");
		this.wing6 = this.bone2.getChild("wing6");
		this.wing3 = this.wing6.getChild("wing3");
		this.wing0 = this.bone2.getChild("wing0");
		this.wing4 = this.wing0.getChild("wing4");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition ooo = partdefinition.addOrReplaceChild("ooo", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition pop = ooo.addOrReplaceChild("pop", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition shake = pop.addOrReplaceChild("shake", CubeListBuilder.create(), PartPose.offset(0.0F, -5.0F, 0.0F));

		PartDefinition body = shake.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 0.075F, 0.0F));

		PartDefinition leg0 = body.addOrReplaceChild("leg0", CubeListBuilder.create(), PartPose.offsetAndRotation(-1.55F, -1.75F, 0.0F, 0.0F, 0.0F, 0.3927F));

		PartDefinition bone5 = leg0.addOrReplaceChild("bone5", CubeListBuilder.create(), PartPose.offsetAndRotation(0.4677F, 6.9914F, 0.0F, 0.0F, 0.0F, -0.3927F));

		PartDefinition leg1 = body.addOrReplaceChild("leg1", CubeListBuilder.create(), PartPose.offsetAndRotation(1.55F, -1.75F, 0.0F, 0.0F, 0.0F, -0.3927F));

		PartDefinition bone4 = leg1.addOrReplaceChild("bone4", CubeListBuilder.create(), PartPose.offsetAndRotation(-0.4677F, 6.9914F, 0.0F, 0.0F, 0.0F, 0.3927F));

		PartDefinition bone6 = body.addOrReplaceChild("bone6", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, -3.0F));

		PartDefinition bodynolegs = bone6.addOrReplaceChild("bodynolegs", CubeListBuilder.create(), PartPose.offset(0.0F, -1.075F, 3.0F));

		PartDefinition bodylol = bodynolegs.addOrReplaceChild("bodylol", CubeListBuilder.create(), PartPose.offset(0.0F, 2.075F, 1.75F));

		PartDefinition head = bodylol.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 15).addBox(-1.5F, -1.8F, -1.05F, 3.0F, 1.8F, 2.0F, new CubeDeformation(0.1F))
				.texOffs(0, 18).addBox(-1.5F, 0.2F, -1.05F, 3.0F, 0.8F, 2.0F, new CubeDeformation(0.1F)), PartPose.offset(0.0F, -6.075F, -0.9F));


		PartDefinition lol = head.addOrReplaceChild("lol", CubeListBuilder.create(), PartPose.offset(0.0F, -2.5F, 0.0F));

		PartDefinition beak = lol.addOrReplaceChild("beak", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, -2.02F));

		PartDefinition bone = beak.addOrReplaceChild("bone", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition bone3 = beak.addOrReplaceChild("bone3", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition bone7 = lol.addOrReplaceChild("bone7", CubeListBuilder.create().texOffs(0, 9).addBox(-1.5F, -1.5206F, -1.5506F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.11F)), PartPose.offset(0.0F, -0.9794F, -0.4994F));

		PartDefinition hat = bone7.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.offset(0.0F, -1.5206F, -0.0006F));

		PartDefinition bone11 = bone7.addOrReplaceChild("bone11", CubeListBuilder.create(), PartPose.offset(0.0F, -0.2F, 0.4F));

		PartDefinition bone10 = bone11.addOrReplaceChild("bone10", CubeListBuilder.create(), PartPose.offset(0.0F, 3.6794F, -0.3006F));

		PartDefinition bone9 = bone11.addOrReplaceChild("bone9", CubeListBuilder.create(), PartPose.offset(0.0F, -1.3206F, -0.3006F));

		PartDefinition bone2 = bodynolegs.addOrReplaceChild("bone2", CubeListBuilder.create(), PartPose.offset(0.0F, -3.0F, 1.75F));

		PartDefinition bone8 = bone2.addOrReplaceChild("bone8", CubeListBuilder.create(), PartPose.offsetAndRotation(0.5F, 1.0F, -0.25F, -0.3927F, 0.0F, 0.0F));

		PartDefinition bone8_r1 = bone8.addOrReplaceChild("bone8_r1", CubeListBuilder.create().texOffs(0, 15).addBox(-2.0F, -2.0F, 0.0F, 3.0F, 1.8F, 2.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.0F, 6.0F, -2.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition bone8_r2 = bone8.addOrReplaceChild("bone8_r2", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -2.0F, -2.0F, 3.0F, 2.0F, 7.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.0F, 4.0F, 0.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition wing1 = bone2.addOrReplaceChild("wing1", CubeListBuilder.create().texOffs(0, 15).addBox(-1.025F, 0.0F, -1.0F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.11F)), PartPose.offsetAndRotation(1.525F, -1.0F, -1.0F, 0.0F, 0.0F, -0.3927F));

		PartDefinition wing2 = wing1.addOrReplaceChild("wing2", CubeListBuilder.create().texOffs(0, 20).addBox(-1.0F, 0.2F, -1.0F, 1.0F, 5.0F, 2.0F, new CubeDeformation(0.11F)), PartPose.offset(1.975F, 1.0F, 0.0F));

		PartDefinition duck_tail = bone2.addOrReplaceChild("duck_tail", CubeListBuilder.create().texOffs(12, 9).addBox(-0.5F, 0.0F, -1.5F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.11F)), PartPose.offsetAndRotation(0.0F, -1.0F, 0.1F, 1.5708F, -1.1781F, -1.5708F));

		PartDefinition wing5 = duck_tail.addOrReplaceChild("wing5", CubeListBuilder.create().texOffs(12, 13).addBox(-1.0F, 0.2F, -3.5F, 1.0F, 5.0F, 3.0F, new CubeDeformation(0.11F)), PartPose.offset(2.5F, 1.0F, 2.0F));

		PartDefinition wing6 = bone2.addOrReplaceChild("wing6", CubeListBuilder.create().texOffs(12, 9).addBox(-0.5F, 0.0F, -1.5F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.11F)), PartPose.offsetAndRotation(0.0F, -1.0F, -2.0F, -1.5708F, 1.1345F, -1.5708F));

		PartDefinition wing3 = wing6.addOrReplaceChild("wing3", CubeListBuilder.create().texOffs(12, 13).addBox(-1.0F, 0.2F, -1.5F, 1.0F, 5.0F, 3.0F, new CubeDeformation(0.11F)), PartPose.offset(2.5F, 1.0F, 0.0F));

		PartDefinition wing0 = bone2.addOrReplaceChild("wing0", CubeListBuilder.create().texOffs(0, 15).addBox(-1.975F, 0.0F, -1.0F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.11F)), PartPose.offsetAndRotation(-1.525F, -1.0F, -1.0F, 0.0F, 0.0F, 0.3927F));

		PartDefinition wing4 = wing0.addOrReplaceChild("wing4", CubeListBuilder.create().texOffs(0, 20).addBox(0.0F, 0.2F, -1.0F, 1.0F, 5.0F, 2.0F, new CubeDeformation(0.11F)), PartPose.offset(-1.975F, 1.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void renderToBuffer(@NotNull PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		ooo.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

}