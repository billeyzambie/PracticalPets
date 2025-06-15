package billeyzambie.practicalpets.client.model.entity.dinosaur;// Made with Blockbench 4.12.4
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import billeyzambie.practicalpets.entity.dinosaur.Duck;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;

public class DuckArmorModel extends DuckModel {

	public DuckArmorModel(ModelPart root) {
		this.ooo = root.getChild("ooo");
		this.body = this.ooo.getChild("body");
		this.leg0 = this.body.getChild("leg0");
		this.bone5 = this.leg0.getChild("bone5");
		this.leg1 = this.body.getChild("leg1");
		this.bone4 = this.leg1.getChild("bone4");
		this.bone6 = this.body.getChild("bone6");
		this.bodynolegs = this.bone6.getChild("bodynolegs");
		this.bodylol = this.bodynolegs.getChild("bodylol");
		this.duck_tail = this.bodylol.getChild("duck_tail");
		this.duck_tail_flesh = this.duck_tail.getChild("duck_tail_flesh");
		this.tcheste = this.bodylol.getChild("tcheste");
		this.wing0 = this.bodynolegs.getChild("wing0");
		this.wing1 = this.bodynolegs.getChild("wing1");
		this.head = this.bodynolegs.getChild("head");
		this.lol = this.head.getChild("lol");
		this.beak = this.lol.getChild("beak");
		this.bone = this.beak.getChild("bone");
		this.bone3 = this.beak.getChild("bone3");
		this.bone7 = this.lol.getChild("bone7");
		this.bone11 = this.bone7.getChild("bone11");
		this.bone10 = this.bone11.getChild("bone10");
		this.bone9 = this.bone11.getChild("bone9");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition ooo = partdefinition.addOrReplaceChild("ooo", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition body = ooo.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, -4.925F, 1.0F));

		PartDefinition leg0 = body.addOrReplaceChild("leg0", CubeListBuilder.create(), PartPose.offset(-1.5F, 1.8F, 0.0F));

		PartDefinition bone5 = leg0.addOrReplaceChild("bone5", CubeListBuilder.create(), PartPose.offset(0.0F, 3.1F, 0.0F));

		PartDefinition leg1 = body.addOrReplaceChild("leg1", CubeListBuilder.create(), PartPose.offset(1.5F, 1.8F, 0.0F));

		PartDefinition bone4 = leg1.addOrReplaceChild("bone4", CubeListBuilder.create(), PartPose.offset(0.0F, 3.1F, 0.0F));

		PartDefinition bone6 = body.addOrReplaceChild("bone6", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, -3.0F));

		PartDefinition bodynolegs = bone6.addOrReplaceChild("bodynolegs", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 3.0F));

		PartDefinition bodylol = bodynolegs.addOrReplaceChild("bodylol", CubeListBuilder.create(), PartPose.offset(0.0F, -1.0F, -1.0F));

		PartDefinition bodylol_r1 = bodylol.addOrReplaceChild("bodylol_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -4.0F, -3.0F, 6.0F, 8.0F, 6.0F, new CubeDeformation(0.155F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition duck_tail = bodylol.addOrReplaceChild("duck_tail", CubeListBuilder.create(), PartPose.offset(0.0F, -3.125F, 3.8F));

		PartDefinition duck_tail_r1 = duck_tail.addOrReplaceChild("duck_tail_r1", CubeListBuilder.create().texOffs(24, 9).addBox(-1.0F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.05F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));

		PartDefinition duck_tail_flesh = duck_tail.addOrReplaceChild("duck_tail_flesh", CubeListBuilder.create(), PartPose.offset(0.0F, 1.15F, 0.55F));

		PartDefinition tcheste = bodylol.addOrReplaceChild("tcheste", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition wing0 = bodynolegs.addOrReplaceChild("wing0", CubeListBuilder.create().texOffs(0, 14).addBox(-1.0F, 0.0F, -3.0F, 1.0F, 4.0F, 6.0F, new CubeDeformation(0.15F)), PartPose.offset(-3.0F, -4.0F, -1.0F));

		PartDefinition wing1 = bodynolegs.addOrReplaceChild("wing1", CubeListBuilder.create().texOffs(14, 14).addBox(-0.025F, 0.0F, -3.0F, 1.0F, 4.0F, 6.0F, new CubeDeformation(0.15F)), PartPose.offset(3.025F, -4.0F, -1.0F));

		PartDefinition head = bodynolegs.addOrReplaceChild("head", CubeListBuilder.create().texOffs(24, 0).addBox(-2.0F, -2.0F, -1.0F, 4.0F, 3.0F, 2.0F, new CubeDeformation(0.11F)), PartPose.offset(0.0F, -3.075F, -5.0F));

		PartDefinition lol = head.addOrReplaceChild("lol", CubeListBuilder.create(), PartPose.offset(0.0F, -3.5F, -0.5F));

		PartDefinition beak = lol.addOrReplaceChild("beak", CubeListBuilder.create(), PartPose.offset(0.0F, 1.0F, -1.5F));

		PartDefinition bone = beak.addOrReplaceChild("bone", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition bone3 = beak.addOrReplaceChild("bone3", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition bone7 = lol.addOrReplaceChild("bone7", CubeListBuilder.create().texOffs(0, 24).addBox(-2.0F, -1.3206F, -1.8006F, 4.0F, 3.0F, 3.0F, new CubeDeformation(0.12F)), PartPose.offset(0.0F, -0.1794F, 0.3006F));

		PartDefinition bone11 = bone7.addOrReplaceChild("bone11", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition bone10 = bone11.addOrReplaceChild("bone10", CubeListBuilder.create().texOffs(24, 5).addBox(-2.02F, -3.0F, -0.5F, 4.04F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.6794F, -0.3006F));

		PartDefinition bone9 = bone11.addOrReplaceChild("bone9", CubeListBuilder.create().texOffs(24, 7).addBox(-2.02F, 0.0F, -0.5F, 4.04F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.3206F, -0.3006F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		ooo.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}