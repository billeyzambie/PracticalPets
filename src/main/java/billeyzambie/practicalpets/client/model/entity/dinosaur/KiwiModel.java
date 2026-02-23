package billeyzambie.practicalpets.client.model.entity.dinosaur;// Made with Blockbench 5.0.7
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import billeyzambie.animationcontrollers.Animatable;
import billeyzambie.animationcontrollers.PracticalPetModel;
import billeyzambie.practicalpets.client.animation.dinosaur.KiwiAnimations;
import billeyzambie.practicalpets.client.animation.dinosaur.PigeonAnimations;
import billeyzambie.practicalpets.entity.dinosaur.Kiwi;
import billeyzambie.practicalpets.misc.PPAnimationControllers;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class KiwiModel extends PracticalPetModel<Kiwi> {
	HashMap<String, AnimationDefinition> keyframeAnimationHashMap = new HashMap<>() {{
		put("sit", KiwiAnimations.sit);
		put("bite_floor", KiwiAnimations.bite_floor);
		put("attack", KiwiAnimations.attack);
	}};

	@Override
	public HashMap<String, AnimationDefinition> getKeyframeAnimationHashMap() {
		return keyframeAnimationHashMap;
	}

	@Override
	public HashMap<String, Animatable> getOtherAnimationHashMap() {
		return null;
	}

	List<ModelPart> pathToBowtie;
	@Override
	public List<ModelPart> pathToBowtie() {
		return pathToBowtie;
	}

	List<ModelPart> pathToHat;
	@Override
	public List<ModelPart> pathToHat() {
		return pathToHat;
	}

	List<ModelPart> pathToBackpack;
	@Override
	public List<ModelPart> pathToBackpack() {
		return pathToBackpack;
	}

	@Override
	public @Nullable ModelPart head() {
		return head;
	}


	private final ModelPart ooo;
	private final ModelPart bone6;
	private final ModelPart death;
	private final ModelPart body;
	private final ModelPart leg0;
	private final ModelPart bone0;
	private final ModelPart leg1;
	private final ModelPart bone1;
	private final ModelPart body2;
	private final ModelPart fluff1;
	private final ModelPart head;
	private final ModelPart fluff2;
	private final ModelPart hat;
	private final ModelPart beak;
	private final ModelPart bone5;
	private final ModelPart bone4;
	private final ModelPart bowtie;
	private final ModelPart backpack;

	public KiwiModel(ModelPart root) {
		//it doesn't actually need all 4 of the root bones (ooo, bone6, death, body) but too late now
		this.ooo = root.getChild("ooo");
		this.bone6 = this.ooo.getChild("bone6");
		this.death = this.bone6.getChild("death");
		this.body = this.death.getChild("body");
		this.leg0 = this.body.getChild("leg0");
		this.bone0 = this.leg0.getChild("bone0");
		this.leg1 = this.body.getChild("leg1");
		this.bone1 = this.leg1.getChild("bone1");
		this.body2 = this.body.getChild("body2");
		this.fluff1 = this.body2.getChild("fluff1");
		this.head = this.body2.getChild("head");
		this.fluff2 = this.head.getChild("fluff2");
		this.hat = this.head.getChild("hat");
		this.beak = this.head.getChild("beak");
		this.bone5 = this.beak.getChild("bone5");
		this.bone4 = this.beak.getChild("bone4");
		this.bowtie = this.head.getChild("bowtie");
		this.backpack = this.body2.getChild("backpack");

		this.pathToHat = List.of(ooo, bone6, death, body, body2, head, hat);
		this.pathToBowtie = List.of(ooo, bone6, death, body, body2, head, bowtie);
		this.pathToBackpack = List.of(ooo, bone6, death, body, body2, backpack);
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition ooo = partdefinition.addOrReplaceChild("ooo", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition bone6 = ooo.addOrReplaceChild("bone6", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition death = bone6.addOrReplaceChild("death", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition body = death.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 2.0F));

		PartDefinition leg0 = body.addOrReplaceChild("leg0", CubeListBuilder.create().texOffs(2, 0).addBox(-0.5F, -1.0F, 0.0F, 1.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, -4.0F, 1.0F));

		PartDefinition bone0 = leg0.addOrReplaceChild("bone0", CubeListBuilder.create().texOffs(21, 5).addBox(-1.5F, 0.0F, -3.0F, 3.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 4.0F, 0.0F));

		PartDefinition leg1 = body.addOrReplaceChild("leg1", CubeListBuilder.create().texOffs(2, 0).mirror().addBox(-0.5F, -1.0F, 0.0F, 1.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-2.0F, -4.0F, 1.0F));

		PartDefinition bone1 = leg1.addOrReplaceChild("bone1", CubeListBuilder.create().texOffs(21, 5).mirror().addBox(-1.5F, 0.0F, -3.0F, 3.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 4.0F, 0.0F));

		PartDefinition body2 = body.addOrReplaceChild("body2", CubeListBuilder.create().texOffs(0, 0).addBox(-2.5F, -7.0F, -5.0F, 5.0F, 7.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.0F, 1.0F));

		PartDefinition fluff1 = body2.addOrReplaceChild("fluff1", CubeListBuilder.create().texOffs(0, 14).addBox(-2.5F, -3.0F, -4.0F, 5.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, -1.0F));

		PartDefinition head = body2.addOrReplaceChild("head", CubeListBuilder.create().texOffs(20, 10).addBox(-1.5F, -3.0F, -3.0F, 3.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -6.0F, -5.0F));

		PartDefinition fluff2 = head.addOrReplaceChild("fluff2", CubeListBuilder.create().texOffs(17, 0).addBox(-1.5F, -6.0F, -7.0F, 3.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 9.0F, 4.0F));

		PartDefinition hat = head.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.offset(0.0F, -3.0F, -1.0F));

		PartDefinition beak = head.addOrReplaceChild("beak", CubeListBuilder.create(), PartPose.offset(0.0F, -1.5F, -3.0F));

		PartDefinition bone5 = beak.addOrReplaceChild("bone5", CubeListBuilder.create().texOffs(0, 22).addBox(-0.5F, -0.5F, -4.0F, 1.0F, 0.5F, 4.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition bone4 = beak.addOrReplaceChild("bone4", CubeListBuilder.create().texOffs(10, 22).addBox(-0.5F, 0.0F, -4.0F, 1.0F, 0.5F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition bowtie = head.addOrReplaceChild("bowtie", CubeListBuilder.create(), PartPose.offset(0.0F, 0.5F, -3.0F));

		PartDefinition backpack = body2.addOrReplaceChild("backpack", CubeListBuilder.create(), PartPose.offset(0.0F, -7.0F, -0.5F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(Kiwi entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

		if (entity.isBaby()) {
			head.xScale *= 1.5f;
			head.yScale *= 1.5f;
			head.zScale *= 1.5f;
		}

		if (!entity.isInSittingPose()) {
			float freqMulti = 3f;
			float ampMulti = 4f;
			this.animateWalk(KiwiAnimations.walk, limbSwing, limbSwingAmount, freqMulti, ampMulti);

			this.animate(entity.randomIdle1AnimationState, PigeonAnimations.eatfloor, ageInTicks);

			PPAnimationControllers.SIMPLE_ATTACK.play(this, entity, limbSwing, limbSwingAmount, ageInTicks, 0, netHeadYaw, headPitch, 1);
		}

		PPAnimationControllers.SIMPLE_SIT.play(this, entity, limbSwing, limbSwingAmount, ageInTicks, 0, netHeadYaw, headPitch, 1);

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		ooo.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart root() {
		return ooo;
	}
}