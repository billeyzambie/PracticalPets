package billeyzambie.practicalpets.client.model.entity.fish;// Made with Blockbench 5.1.4
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports

import billeyzambie.animationcontrollers.Animatable;
import billeyzambie.practicalpets.client.animation.fish.PiranhaAnimations;
import billeyzambie.practicalpets.entity.fish.Anglerfish;
import billeyzambie.practicalpets.entity.fish.Piranha;
import billeyzambie.practicalpets.misc.PPAnimationControllers;
import billeyzambie.practicalpets.util.PPUtil;
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

public class AnglerfishModel extends SwimmingEntityModel<Anglerfish> {
	private final ModelPart ooo;
	private final ModelPart body;
	private final ModelPart hat;
	private final ModelPart tail;
	private final ModelPart tail1;
	private final ModelPart jaw;
	private final ModelPart angler;
	private final ModelPart bone;
	private final ModelPart left_fin;
	private final ModelPart right_fin;
	private final ModelPart bowtie;

	public AnglerfishModel(ModelPart root) {
		this.ooo = root.getChild("ooo");
		this.body = this.ooo.getChild("body");
		this.hat = this.body.getChild("hat");
		this.tail = this.body.getChild("tail");
		this.tail1 = this.tail.getChild("tail1");
		this.jaw = this.body.getChild("jaw");
		this.angler = this.body.getChild("angler");
		this.bone = this.angler.getChild("bone");
		this.left_fin = this.body.getChild("left_fin");
		this.right_fin = this.body.getChild("right_fin");
		this.bowtie = this.body.getChild("bowtie");

		this.pathToHat = List.of(ooo, body, hat);
		this.pathToBowtie = List.of(ooo, body, bowtie);
		this.pathToBackpack = List.of(ooo, body, bowtie);
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition ooo = partdefinition.addOrReplaceChild("ooo", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition body = ooo.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-3.5F, -3.5F, -3.5F, 7.0F, 7.0F, 7.0F, new CubeDeformation(0.0F))
		.texOffs(22, 0).addBox(-3.5F, -3.5F, -4.5F, 7.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.5F, 0.5F));

		PartDefinition hat = body.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.offset(0.0F, -3.5F, 0.0F));

		PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 23).addBox(-1.5F, -1.5F, -1.0F, 3.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 3.5F));

		PartDefinition tail1 = tail.addOrReplaceChild("tail1", CubeListBuilder.create().texOffs(36, 34).addBox(0.0F, -2.5F, 0.0F, 0.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 4.0F));

		PartDefinition jaw = body.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(0, 15).addBox(-3.5F, -2.0F, -5.0F, 7.0F, 2.0F, 5.0F, new CubeDeformation(0.01F))
		.texOffs(20, 18).addBox(-3.5F, -3.0F, -5.0F, 7.0F, 1.0F, 5.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(0.0F, 3.5F, -3.5F, -0.3927F, 0.0F, 0.0F));

		PartDefinition angler = body.addOrReplaceChild("angler", CubeListBuilder.create(), PartPose.offset(0.0F, -3.5F, -2.5F));

		PartDefinition angler_r1 = angler.addOrReplaceChild("angler_r1", CubeListBuilder.create().texOffs(17, 25).addBox(0.0F, -5.0F, -6.0F, 0.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3054F, 0.0F, 0.0F));

		PartDefinition bone = angler.addOrReplaceChild("bone", CubeListBuilder.create(), PartPose.offset(0.0F, -1.7F, -6.25F));

		PartDefinition left_fin = body.addOrReplaceChild("left_fin", CubeListBuilder.create(), PartPose.offset(3.5F, 0.5F, 0.5F));

		PartDefinition left_fin_r1 = left_fin.addOrReplaceChild("left_fin_r1", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, -1.0F, 0.0F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.5672F, 0.0F));

		PartDefinition right_fin = body.addOrReplaceChild("right_fin", CubeListBuilder.create(), PartPose.offset(-3.5F, 0.5F, 0.5F));

		PartDefinition right_fin_r1 = right_fin.addOrReplaceChild("right_fin_r1", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-2.0F, -1.0F, 0.0F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.5672F, 0.0F));

		PartDefinition bowtie = body.addOrReplaceChild("bowtie", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 3.5F, -3.0F, 1.5708F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 48, 48);
	}

	@Override
	public void setupAnim(@NotNull Anglerfish entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

		PPAnimationControllers.FISH_FLOP.play(this, entity, limbSwing, limbSwingAmount, ageInTicks, 0, netHeadYaw, headPitch, 1);

		this.animateSwim(entity.getSwimSwing(), entity.getSwimSwingAmount());
	}


	private static final float SWIM_FREQ_MULTI = 3;
	private static final float SWIM_AMP_MULTI = 6;

	public static final float MIN_SWIM_SWING_DELTA = 0.02f * 6 / SWIM_FREQ_MULTI;
	public static final float MIN_SWIM_SWING_AMOUNT = 0.13f * 6 / SWIM_AMP_MULTI;

	public void animateSwim(float swimSwing, float swimSwingAmount) {
		//The animation looked backwards for some reason so I put the -
		float time = -swimSwing * SWIM_FREQ_MULTI;
		float amp = swimSwingAmount * SWIM_AMP_MULTI;

		float phase = time * 480;
		this.body.yRot += amp * PPUtil.bedrockSin(phase) * 2.5f;
		this.tail.yRot += amp * PPUtil.bedrockSin(phase + 30) * 10f;
		this.tail1.yRot += amp * PPUtil.bedrockSin(phase + 45) * 10f;
		this.left_fin.yRot += amp * PPUtil.bedrockSin(phase + 15) * 5f;
		this.right_fin.yRot += amp * PPUtil.bedrockSin(phase + 60) * 5f;
	}

	private final List<ModelPart> pathToBowtie;
	private final List<ModelPart> pathToHat;
	private final List<ModelPart> pathToBackpack;

	private final HashMap<String, AnimationDefinition> keyframeAnimationHashMap = new HashMap<>() {{
		put("flop", PiranhaAnimations.flop);
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
	public @Nullable ModelPart head() {
		return null;
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		ooo.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart root() {
		return ooo;
	}

	@Override
	protected @NotNull ModelPart body() {
		return body;
	}

	@Override
	public List<ModelPart> pathToPetBowtie() {
		return pathToBowtie;
	}

	@Override
	public List<ModelPart> pathToPetHat() {
		return pathToHat;
	}

	@Override
	public List<ModelPart> pathToPetBackpack() {
		return pathToBackpack;
	}

}