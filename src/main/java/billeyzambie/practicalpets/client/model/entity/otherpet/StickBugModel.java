package billeyzambie.practicalpets.client.model.entity.otherpet;// Made with Blockbench 5.0.7
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import billeyzambie.animationcontrollers.Animatable;
import billeyzambie.animationcontrollers.PracticalPetModel;
import billeyzambie.practicalpets.client.animation.otherpet.StickBugAnimations;
import billeyzambie.practicalpets.entity.otherpet.StickBug;
import billeyzambie.practicalpets.misc.PPAnimationControllers;
import billeyzambie.practicalpets.util.PPUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

import java.util.HashMap;
import java.util.List;

public class StickBugModel extends PracticalPetModel<StickBug> {
    @Override
    public ModelPart root() {
        return ooo;
    }

    @Override
    public ModelPart head() {
        return null;
    }

    private final HashMap<String, AnimationDefinition> keyframeAnimationHashMap = new HashMap<>() {{
        put("sit", StickBugAnimations.sit);
        put("dance", StickBugAnimations.dance);
        put("on_ground", StickBugAnimations.hide_wings);
    }};

    @Override
    public HashMap<String, AnimationDefinition> getKeyframeAnimationHashMap() {
        return keyframeAnimationHashMap;
    }

    @Override
    public HashMap<String, Animatable> getOtherAnimationHashMap() {
        return null;
    }

    final List<ModelPart> pathToBowtie;

    @Override
    public List<ModelPart> pathToBowtie() {
        return pathToBowtie;
    }

    final List<ModelPart> pathToHat;

    @Override
    public List<ModelPart> pathToHat() {
        return pathToHat;
    }

    final List<ModelPart> pathToBackpack;

    @Override
    public List<ModelPart> pathToBackpack() {
        return pathToBackpack;
    }

    private final ModelPart ooo;
    private final ModelPart body;
    private final ModelPart bowtie;
    private final ModelPart antennas;
    private final ModelPart left_antena;
    private final ModelPart right_antena;
    private final ModelPart left_leg;
    private final ModelPart left_leg1;
    private final ModelPart right_leg;
    private final ModelPart right_leg1;
    private final ModelPart left_leg2;
    private final ModelPart left_leg3;
    private final ModelPart right_leg2;
    private final ModelPart right_leg3;
    private final ModelPart left_leg4;
    private final ModelPart left_leg5;
    private final ModelPart right_leg4;
    private final ModelPart right_leg5;
    private final ModelPart tail;
    private final ModelPart left_wing;
    private final ModelPart right_wing;
    private final ModelPart hat;
    private final ModelPart backpack;

    public StickBugModel(ModelPart root) {
        this.ooo = root.getChild("ooo");
        this.body = this.ooo.getChild("body");
        this.bowtie = this.body.getChild("bowtie");
        this.antennas = this.body.getChild("antennas");
        this.left_antena = this.antennas.getChild("left_antena");
        this.right_antena = this.antennas.getChild("right_antena");
        this.left_leg = this.body.getChild("left_leg");
        this.left_leg1 = this.left_leg.getChild("left_leg1");
        this.right_leg = this.body.getChild("right_leg");
        this.right_leg1 = this.right_leg.getChild("right_leg1");
        this.left_leg2 = this.body.getChild("left_leg2");
        this.left_leg3 = this.left_leg2.getChild("left_leg3");
        this.right_leg2 = this.body.getChild("right_leg2");
        this.right_leg3 = this.right_leg2.getChild("right_leg3");
        this.left_leg4 = this.body.getChild("left_leg4");
        this.left_leg5 = this.left_leg4.getChild("left_leg5");
        this.right_leg4 = this.body.getChild("right_leg4");
        this.right_leg5 = this.right_leg4.getChild("right_leg5");
        this.tail = this.body.getChild("tail");
        this.left_wing = this.body.getChild("left_wing");
        this.right_wing = this.body.getChild("right_wing");
        this.hat = this.body.getChild("hat");
        this.backpack = this.body.getChild("backpack");

        this.pathToHat = List.of(ooo, body, hat);
        this.pathToBowtie = List.of(ooo, body, bowtie);
        this.pathToBackpack = List.of(ooo, body, backpack);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition ooo = partdefinition.addOrReplaceChild("ooo", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition body = ooo.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, -5.5F, 1.0F));

        PartDefinition body_r1 = body.addOrReplaceChild("body_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, -17.5F, -0.495F, 1.0F, 18.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -7.5F, -1.5708F, 0.0F, 0.0F));

        PartDefinition bowtie = body.addOrReplaceChild("bowtie", CubeListBuilder.create(), PartPose.offset(0.0F, 0.5F, -4.5F));

        PartDefinition antennas = body.addOrReplaceChild("antennas", CubeListBuilder.create(), PartPose.offset(0.0F, -0.5F, -8.0F));

        PartDefinition left_antena = antennas.addOrReplaceChild("left_antena", CubeListBuilder.create(), PartPose.offset(0.5F, 0.0F, 0.0F));

        PartDefinition left_antena_r1 = left_antena.addOrReplaceChild("left_antena_r1", CubeListBuilder.create().texOffs(5, 0).addBox(0.5F, -5.0F, 0.0F, 6.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, 0.0F, 0.0F, 0.6545F, 0.0F, 0.0F));

        PartDefinition right_antena = antennas.addOrReplaceChild("right_antena", CubeListBuilder.create(), PartPose.offset(-0.5F, 0.0F, 0.0F));

        PartDefinition right_antena_r1 = right_antena.addOrReplaceChild("right_antena_r1", CubeListBuilder.create().texOffs(5, 0).mirror().addBox(-6.5F, -5.0F, 0.0F, 6.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.5F, 0.0F, 0.0F, 0.6545F, 0.0F, 0.0F));

        PartDefinition left_leg = body.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(5, 16).addBox(0.0F, 0.0F, -1.0F, 5.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 0.5F, -5.0F, 0.0F, 0.5236F, 0.0F));

        PartDefinition left_leg1 = left_leg.addOrReplaceChild("left_leg1", CubeListBuilder.create().texOffs(5, 14).addBox(0.0F, 0.0F, -1.0F, 5.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.4835F));

        PartDefinition right_leg = body.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(5, 16).mirror().addBox(-5.0F, 0.0F, -1.0F, 5.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-0.5F, 0.5F, -5.0F, 0.0F, -0.5236F, 0.0F));

        PartDefinition right_leg1 = right_leg.addOrReplaceChild("right_leg1", CubeListBuilder.create().texOffs(5, 14).mirror().addBox(-5.0F, 0.0F, -1.0F, 5.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-5.0F, 0.0F, 0.0F, 0.0F, 0.0F, -1.4835F));

        PartDefinition left_leg2 = body.addOrReplaceChild("left_leg2", CubeListBuilder.create().texOffs(5, 12).addBox(0.0F, 0.0F, -1.0F, 5.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 0.5F, -1.0F, 0.0F, 0.1745F, 0.0F));

        PartDefinition left_leg3 = left_leg2.addOrReplaceChild("left_leg3", CubeListBuilder.create().texOffs(5, 10).addBox(0.0F, 0.0F, -1.0F, 5.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.4835F));

        PartDefinition right_leg2 = body.addOrReplaceChild("right_leg2", CubeListBuilder.create().texOffs(5, 12).mirror().addBox(-5.0F, 0.0F, -1.0F, 5.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-0.5F, 0.5F, -1.0F, 0.0F, -0.1745F, 0.0F));

        PartDefinition right_leg3 = right_leg2.addOrReplaceChild("right_leg3", CubeListBuilder.create().texOffs(5, 10).mirror().addBox(-5.0F, 0.0F, -1.0F, 5.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-5.0F, 0.0F, 0.0F, 0.0F, 0.0F, -1.4835F));

        PartDefinition left_leg4 = body.addOrReplaceChild("left_leg4", CubeListBuilder.create().texOffs(5, 8).addBox(0.0F, 0.0F, 0.0F, 5.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 0.5F, 2.0F, 0.0F, -0.2618F, 0.0F));

        PartDefinition left_leg5 = left_leg4.addOrReplaceChild("left_leg5", CubeListBuilder.create().texOffs(5, 6).addBox(0.0F, 0.0F, -1.0F, 5.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.0F, 0.0F, 1.0F, 0.0F, 0.0F, 1.4835F));

        PartDefinition right_leg4 = body.addOrReplaceChild("right_leg4", CubeListBuilder.create().texOffs(5, 8).mirror().addBox(-5.0F, 0.0F, 0.0F, 5.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-0.5F, 0.5F, 2.0F, 0.0F, 0.2618F, 0.0F));

        PartDefinition right_leg5 = right_leg4.addOrReplaceChild("right_leg5", CubeListBuilder.create().texOffs(5, 6).mirror().addBox(-5.0F, 0.0F, -1.0F, 5.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-5.0F, 0.0F, 1.0F, 0.0F, 0.0F, -1.4835F));

        PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(4, 18).addBox(-1.5F, 0.0F, 0.0F, 3.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.5F, 10.0F, -0.4363F, 0.0F, 0.0F));

        PartDefinition left_wing = body.addOrReplaceChild("left_wing", CubeListBuilder.create().texOffs(2, 17).addBox(0.0F, 0.0F, 0.0F, 5.0F, 0.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, -0.495F, -5.0F));

        PartDefinition right_wing = body.addOrReplaceChild("right_wing", CubeListBuilder.create().texOffs(2, 17).mirror().addBox(-5.0F, 0.0F, 0.0F, 5.0F, 0.0F, 9.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-0.5F, -0.495F, -5.0F));

        PartDefinition hat = body.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.offset(0.0F, -0.5F, -6.0F));

        PartDefinition backpack = body.addOrReplaceChild("backpack", CubeListBuilder.create(), PartPose.offset(0.0F, -0.5F, -0.5F));

        return LayerDefinition.create(meshdefinition, 32, 32);
    }

    @Override
    public void setupAnim(StickBug entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        float partialTick = ageInTicks % 1f;

        PPAnimationControllers.ON_GROUND_AND_NOT_ANGRY.play(this, entity, limbSwing, limbSwingAmount, ageInTicks, 0, netHeadYaw, headPitch, 1);
        PPAnimationControllers.SIT_OR_DANCE.play(this, entity, limbSwing, limbSwingAmount, ageInTicks, 0, netHeadYaw, headPitch, 1);

        float f = Mth.lerp(partialTick, entity.oFlap, entity.flap);
        float f1 = Mth.lerp(partialTick, entity.oFlapSpeed, entity.flapSpeed);
        float flap = (Mth.sin(f) + 1.0F) * f1;

        this.right_wing.zRot += flap;
        this.left_wing.zRot -= flap;

        if (!entity.isInSittingPose()) {
            this.animateWalk(limbSwing, limbSwingAmount);
        }
    }

    private static final float WALK_FREQ_MULTI = 4;
    private static final float WALK_AMP_MULTI = 2;

    public void animateWalk(float limbSwing, float limbSwingAmount) {
        //The animation looked backwards for some reason so I put the -
        float time = -limbSwing * WALK_FREQ_MULTI;
        float amp = limbSwingAmount * WALK_AMP_MULTI;

        float sin = amp * PPUtil.bedrockSinAngle(time * 720);
        float cos = amp * PPUtil.bedrockCosAngle(time * 720);

        body.yRot += -sin;
        body.zRot += sin * 3;
        body.y += Mth.abs(cos * 0.5f * Mth.RAD_TO_DEG);

        left_leg.yRot += -sin * 20;
        left_leg.zRot += -cos * 10;
        left_leg2.yRot += sin * 20;
        left_leg2.zRot += cos * 10;
        left_leg4.yRot += -sin * 20;
        left_leg4.zRot += -cos * 10;

        right_leg.yRot += -sin * 20;
        right_leg.zRot += -cos * 10;
        right_leg2.yRot += sin * 20;
        right_leg2.zRot += cos * 10;
        right_leg4.yRot += -sin * 20;
        right_leg4.zRot += -cos * 10;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        ooo.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}