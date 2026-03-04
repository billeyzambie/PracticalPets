package billeyzambie.practicalpets.client.model.entity.fish;// Made with Blockbench 5.0.7
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports

import billeyzambie.animationcontrollers.Animatable;
import billeyzambie.animationcontrollers.PracticalPetModel;
import billeyzambie.practicalpets.client.animation.fish.PiranhaAnimations;
import billeyzambie.practicalpets.client.animation.otherpet.GiraffeCatAnimations;
import billeyzambie.practicalpets.entity.fish.Piranha;
import billeyzambie.practicalpets.misc.PPAnimationControllers;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;

public class PiranhaModel extends PracticalPetModel<Piranha> {

	private final HashMap<String, AnimationDefinition> keyframeAnimationHashMap = new HashMap<>() {{
		put("flip", PiranhaAnimations.flip);
	}};

	@Override
	public HashMap<String, AnimationDefinition> getKeyframeAnimationHashMap() {
		return keyframeAnimationHashMap;
	}

	@Override
	public HashMap<String, Animatable> getOtherAnimationHashMap() {
		return null;
	}

	@Override
	public List<ModelPart> pathToBowtie() {
		return List.of();
	}

	@Override
	public List<ModelPart> pathToHat() {
		return List.of();
	}

	@Override
	public List<ModelPart> pathToBackpack() {
		return List.of();
	}

	@Override
	public @Nullable ModelPart head() {
		return null;
	}

	private final ModelPart ooo;
	private final ModelPart look;
	private final ModelPart body;
	private final ModelPart body2;
	private final ModelPart tail;
	private final ModelPart tail1;
	private final ModelPart xd;
	private final ModelPart jaw;
	private final ModelPart left_fin;
	private final ModelPart right_fin;
	private final ModelPart eyes;
	private final ModelPart hat;
	private final ModelPart bowtie;

	public PiranhaModel(ModelPart root) {
		this.ooo = root.getChild("ooo");
		this.look = this.ooo.getChild("look");
		this.body = this.look.getChild("body");
		this.body2 = this.body.getChild("body2");
		this.tail = this.body2.getChild("tail");
		this.tail1 = this.tail.getChild("tail1");
		this.xd = this.body.getChild("xd");
		this.jaw = this.xd.getChild("jaw");
		this.left_fin = this.body.getChild("left_fin");
		this.right_fin = this.body.getChild("right_fin");
		this.eyes = this.body.getChild("eyes");
		this.hat = this.body.getChild("hat");
		this.bowtie = this.body.getChild("bowtie");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition ooo = partdefinition.addOrReplaceChild("ooo", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition look = ooo.addOrReplaceChild("look", CubeListBuilder.create(), PartPose.offset(0.0F, -2.0F, 0.0F));

		PartDefinition body = look.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -2.0F, -3.0F, 2.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition body_r1 = body.addOrReplaceChild("body_r1", CubeListBuilder.create().texOffs(24, 9).addBox(-0.5F, -2.4627F, -2.1566F, 0.0F, 3.0F, 3.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(0.5F, 1.25F, -2.0F, -2.4871F, 0.0F, 0.0F));

		PartDefinition body_r2 = body.addOrReplaceChild("body_r2", CubeListBuilder.create().texOffs(23, 5).addBox(0.0F, -2.6895F, -2.995F, 0.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.0091F, 1.004F, 0.5672F, 0.0F, 0.0F));

		PartDefinition body2 = body.addOrReplaceChild("body2", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, -3.0F));

		PartDefinition tail = body2.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(4, 13).addBox(-0.5F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition tail1 = tail.addOrReplaceChild("tail1", CubeListBuilder.create().texOffs(1, 8).addBox(-0.5F, -2.0F, -2.0F, 0.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, 0.0F, -1.0F));

		PartDefinition xd = body.addOrReplaceChild("xd", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 2.0F));

		PartDefinition xd_r1 = xd.addOrReplaceChild("xd_r1", CubeListBuilder.create().texOffs(10, 2).addBox(-1.0F, 0.01F, 0.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(0.0F, -2.01F, 1.0F, -0.7854F, 0.0F, 0.0F));

		PartDefinition jaw = xd.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(10, 11).addBox(-1.0F, -1.01F, 0.0F, 2.0F, 1.01F, 2.0F, new CubeDeformation(-0.01F))
		.texOffs(0, 0).addBox(-1.0F, -2.0F, 1.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.0F, 1.0F, 0.1309F, 0.0F, 0.0F));

		PartDefinition left_fin = body.addOrReplaceChild("left_fin", CubeListBuilder.create().texOffs(8, 0).addBox(-2.0F, 0.0F, -1.0F, 3.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 1.0F, 1.0F, 0.0F, 0.0F, -0.6109F));

		PartDefinition right_fin = body.addOrReplaceChild("right_fin", CubeListBuilder.create().texOffs(8, 0).mirror().addBox(-1.0F, 0.0F, -1.0F, 3.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.6109F));

		PartDefinition eyes = body.addOrReplaceChild("eyes", CubeListBuilder.create(), PartPose.offset(0.5F, 2.0F, 0.0F));

		PartDefinition hat = body.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -2.0F, 2.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition bowtie = body.addOrReplaceChild("bowtie", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 2.0F, 2.5F, -1.5708F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void setupAnim(@NotNull Piranha entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		ooo.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public @NotNull ModelPart root() {
		return ooo;
	}
}