package billeyzambie.practicalpets.client.model.entity.otherpet;// Made with Blockbench 4.12.6
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import billeyzambie.animationcontrollers.Animatable;
import billeyzambie.animationcontrollers.PracticalPetModel;
import billeyzambie.practicalpets.client.animation.otherpet.RatAnimation;
import billeyzambie.practicalpets.entity.otherpet.Rat;
import billeyzambie.practicalpets.misc.PPAnimationControllers;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;

public class RatModel extends PracticalPetModel<Rat> {
	@Override
	public ModelPart root() {
		return ooo;
	}

	private final HashMap<String, AnimationDefinition> keyframeAnimationHashMap = new HashMap<>() {{
		put("sit", RatAnimation.sit);
		put("sit2", RatAnimation.sit2);
		put("cook", RatAnimation.cook);
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

	List<ModelPart> pathToItem;
	public List<ModelPart> pathToItem() {
		return pathToItem;
	}

	@Override
	public ModelPart head() {
		return head;
	}

    private final ModelPart ooo;
	private final ModelPart death;
	private final ModelPart body;
	private final ModelPart leftbackleg;
	private final ModelPart leftbackleg2;
	private final ModelPart leftfoot;
	private final ModelPart rightbackleg;
	private final ModelPart rightbackleg2;
	private final ModelPart rightfoot;
	private final ModelPart bodynolegs;
	private final ModelPart backpack;
	private final ModelPart frontbody;
	private final ModelPart bowtie;
	private final ModelPart rightflap;
	private final ModelPart frontrightpaw;
	private final ModelPart leftflap;
	private final ModelPart frontleftpaw;
	private final ModelPart head;
	private final ModelPart rightear;
	private final ModelPart leftear;
	private final ModelPart snout;
	private final ModelPart item;
	private final ModelPart hat;
	private final ModelPart tail;

	public RatModel(ModelPart root) {
		this.ooo = root.getChild("ooo");
		this.death = this.ooo.getChild("death");
		this.body = this.death.getChild("body");
		this.leftbackleg = this.body.getChild("leftbackleg");
		this.leftbackleg2 = this.leftbackleg.getChild("leftbackleg2");
		this.leftfoot = this.leftbackleg2.getChild("leftfoot");
		this.rightbackleg = this.body.getChild("rightbackleg");
		this.rightbackleg2 = this.rightbackleg.getChild("rightbackleg2");
		this.rightfoot = this.rightbackleg2.getChild("rightfoot");
		this.bodynolegs = this.body.getChild("bodynolegs");
		this.backpack = this.bodynolegs.getChild("backpack");
		this.frontbody = this.bodynolegs.getChild("frontbody");
		this.bowtie = this.frontbody.getChild("bowtie");
		this.rightflap = this.frontbody.getChild("rightflap");
		this.frontrightpaw = this.rightflap.getChild("frontrightpaw");
		this.leftflap = this.frontbody.getChild("leftflap");
		this.frontleftpaw = this.leftflap.getChild("frontleftpaw");
		this.head = this.frontbody.getChild("head");
		this.rightear = this.head.getChild("rightear");
		this.leftear = this.head.getChild("leftear");
		this.snout = this.head.getChild("snout");
		this.item = this.snout.getChild("item");
		this.hat = this.head.getChild("hat");
		this.tail = this.bodynolegs.getChild("tail");

		pathToBowtie = List.of(ooo, death, body, bodynolegs, frontbody, bowtie);
		pathToHat = List.of(ooo, death, body, bodynolegs, frontbody, head, hat);
		pathToBackpack = List.of(ooo, death, body, bodynolegs, backpack);
		pathToItem = List.of(ooo, death, body, bodynolegs, frontbody, head, snout, item);
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition ooo = partdefinition.addOrReplaceChild("ooo", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition death = ooo.addOrReplaceChild("death", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition body = death.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, -3.5F, -3.0F));

		PartDefinition leftbackleg = body.addOrReplaceChild("leftbackleg", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-1.0F, -1.0F, -1.0F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-2.0F, 0.5F, 5.0F));

		PartDefinition leftbackleg2 = leftbackleg.addOrReplaceChild("leftbackleg2", CubeListBuilder.create().texOffs(7, 7).mirror().addBox(-1.0F, 0.0F, 0.0F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 2.0F, 2.0F));

		PartDefinition leftfoot = leftbackleg2.addOrReplaceChild("leftfoot", CubeListBuilder.create().texOffs(0, 7).mirror().addBox(-1.0F, 0.0F, -2.0F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 1.0F, 0.0F));

		PartDefinition rightbackleg = body.addOrReplaceChild("rightbackleg", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 0.5F, 5.0F));

		PartDefinition rightbackleg2 = rightbackleg.addOrReplaceChild("rightbackleg2", CubeListBuilder.create().texOffs(7, 7).addBox(-1.0F, 0.0F, 0.0F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, 2.0F));

		PartDefinition rightfoot = rightbackleg2.addOrReplaceChild("rightfoot", CubeListBuilder.create().texOffs(0, 7).addBox(-1.0F, 0.0F, -2.0F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.0F, 0.0F));

		PartDefinition bodynolegs = body.addOrReplaceChild("bodynolegs", CubeListBuilder.create().texOffs(0, 14).addBox(-2.5F, -3.5F, -4.5F, 5.0F, 5.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.0F, 5.5F));

		PartDefinition backpack = bodynolegs.addOrReplaceChild("backpack", CubeListBuilder.create(), PartPose.offset(0.0F, -3.5F, -2.0F));

		PartDefinition frontbody = bodynolegs.addOrReplaceChild("frontbody", CubeListBuilder.create().texOffs(15, 0).addBox(-2.0F, -2.0F, -3.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, -4.5F));

		PartDefinition bowtie = frontbody.addOrReplaceChild("bowtie", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 2.0F, -3.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition rightflap = frontbody.addOrReplaceChild("rightflap", CubeListBuilder.create(), PartPose.offset(1.5F, 2.0F, -1.0F));

		PartDefinition frontrightpaw = rightflap.addOrReplaceChild("frontrightpaw", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.2618F, 0.0F, 0.0F));

		PartDefinition frontrightpaw_r1 = frontrightpaw.addOrReplaceChild("frontrightpaw_r1", CubeListBuilder.create().texOffs(0, 14).addBox(0.0F, 0.0F, 0.0F, 1.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, 0.0F, 0.0F, -0.7854F, 0.0F, 0.0F));

		PartDefinition leftflap = frontbody.addOrReplaceChild("leftflap", CubeListBuilder.create(), PartPose.offset(-1.5F, 2.0F, -1.0F));

		PartDefinition frontleftpaw = leftflap.addOrReplaceChild("frontleftpaw", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.2618F, 0.0F, 0.0F));

		PartDefinition frontleftpaw_r1 = frontleftpaw.addOrReplaceChild("frontleftpaw_r1", CubeListBuilder.create().texOffs(0, 14).mirror().addBox(-1.0F, 0.0F, 0.0F, 1.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.5F, 0.0F, 0.0F, -0.7854F, 0.0F, 0.0F));

		PartDefinition head = frontbody.addOrReplaceChild("head", CubeListBuilder.create().texOffs(18, 14).addBox(-2.0F, -1.5F, -2.0F, 4.0F, 3.0F, 3.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, 0.0F, -4.0F));

		PartDefinition rightear = head.addOrReplaceChild("rightear", CubeListBuilder.create().texOffs(15, 9).addBox(-2.0F, -2.0F, 0.0F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, -0.5F, 0.5F));

		PartDefinition leftear = head.addOrReplaceChild("leftear", CubeListBuilder.create().texOffs(15, 9).mirror().addBox(0.0F, -2.0F, 0.0F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(1.0F, -0.5F, 0.5F));

		PartDefinition snout = head.addOrReplaceChild("snout", CubeListBuilder.create().texOffs(22, 25).addBox(-1.5F, -1.0F, -2.5F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(25, 21).addBox(-3.5F, -1.5F, -1.5F, 7.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.5F, -2.0F));

		PartDefinition item = snout.addOrReplaceChild("item", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 1.0F, -2.5F, 1.5708F, 0.0F, 0.0F));

		PartDefinition hat = head.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.offset(0.0F, -1.5F, -0.5F));

		PartDefinition tail = bodynolegs.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(0.0F, -1.0F, 2.5F));

		PartDefinition tail_r1 = tail.addOrReplaceChild("tail_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, -0.5F, -1.0F, 1.0F, 1.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.2793F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(@NotNull Rat entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

		PPAnimationControllers.RAT_POSE.play(this, entity, limbSwing, limbSwingAmount, ageInTicks, 0, netHeadYaw, headPitch, 1);

		if (!entity.isInSittingPose()) {
			float freqMulti = 3f;
			float ampMulti = 6f;

			if (entity.getDeltaMovement().horizontalDistance() >= 0.09) {
				entity.lastRunTime = ageInTicks;
			}
			else {
				entity.lastWalkTime = ageInTicks;
			}
			float diff = entity.lastRunTime - entity.lastWalkTime;

			//smoothen the transition from the walk to the run and vice versa
			if (diff > 0) {
				this.animateWalk(RatAnimation.walk, limbSwing, limbSwingAmount * Mth.clamp(1 - diff / 4, 0, 1), freqMulti, ampMulti);
				this.animateWalk(RatAnimation.run, limbSwing, limbSwingAmount * Mth.clamp(diff / 4, 0, 1), freqMulti, ampMulti);
			}
			else {
				this.animateWalk(RatAnimation.walk, limbSwing, limbSwingAmount * Mth.clamp(-diff / 4, 0, 1), freqMulti, ampMulti);
				this.animateWalk(RatAnimation.run, limbSwing, limbSwingAmount * Mth.clamp(1 + diff / 4, 0, 1), freqMulti, ampMulti);
			}

			tail.yRot += Mth.cos(limbSwing * freqMulti * Mth.PI * 5.33f / 20f) * limbSwingAmount * ampMulti * Mth.PI / 36f;
		}

	}

}