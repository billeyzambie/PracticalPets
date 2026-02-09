package billeyzambie.practicalpets.client.model.entity.otherpet;// Made with Blockbench 5.0.7
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import billeyzambie.animationcontrollers.Animatable;
import billeyzambie.animationcontrollers.PracticalPetModel;
import billeyzambie.practicalpets.client.animation.otherpet.GiraffeCatAnimations;
import billeyzambie.practicalpets.entity.otherpet.GiraffeCat;
import billeyzambie.practicalpets.misc.PPAnimationControllers;
import billeyzambie.practicalpets.util.PPUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;

public class GiraffeCatModel extends PracticalPetModel<GiraffeCat> {

    private final HashMap<String, AnimationDefinition> keyframeAnimationHashMap = new HashMap<>() {{
        put("walk_pose", GiraffeCatAnimations.walk_pose);
        put("sneak_pose", GiraffeCatAnimations.sneak_pose);
        put("sit", GiraffeCatAnimations.sit);
        put("yeet", GiraffeCatAnimations.yeet);
        put("ladder", GiraffeCatAnimations.ladder);
        put("dig", GiraffeCatAnimations.dig);
        put("bend_over", GiraffeCatAnimations.bend_over);
    }};

    @Override
    public HashMap<String, AnimationDefinition> getKeyframeAnimationHashMap() {
        return keyframeAnimationHashMap;
    }

    private final HashMap<String, Animatable> otherAnimationHashMap = new HashMap<>() {{
        put("controller.giraffe_cat_sneak_or_sit", PPAnimationControllers.GIRAFFE_CAT_SNEAK_OR_SIT);
        put("controller.giraffe_cat_bend_over", PPAnimationControllers.GIRAFFE_CAT_BEND_OVER);
    }};

    @Override
    public HashMap<String, Animatable> getOtherAnimationHashMap() {
        return otherAnimationHashMap;
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

    final List<ModelPart> pathToNeck2;
    public List<ModelPart> pathToNeck2() {
        return pathToNeck2;
    }


    private final ModelPart ooo;
    private final ModelPart bone;
    private final ModelPart body;
    private final ModelPart bodynohead;
    private final ModelPart bodyxd;
    private final ModelPart uhhhh;
    private final ModelPart backpack;
    private final ModelPart neck2;
    private final ModelPart neck;
    private final ModelPart bowtie;
    private final ModelPart head2;
    private final ModelPart head;
    private final ModelPart snout;
    private final ModelPart ear;
    private final ModelPart ear2;
    private final ModelPart hat;
    private final ModelPart backLegL;
    private final ModelPart backLegR;
    private final ModelPart frontLegL;
    private final ModelPart frontLegR;
    private final ModelPart tail3;
    private final ModelPart tail4;

    public GiraffeCatModel(ModelPart root) {
        this.ooo = root.getChild("ooo");
        this.bone = this.ooo.getChild("bone");
        this.body = this.bone.getChild("body");
        this.bodynohead = this.body.getChild("bodynohead");
        this.bodyxd = this.bodynohead.getChild("bodyxd");
        this.uhhhh = this.bodyxd.getChild("uhhhh");
        this.backpack = this.bodyxd.getChild("backpack");
        this.neck2 = this.bodynohead.getChild("neck2");
        this.neck = this.neck2.getChild("neck");
        this.bowtie = this.neck.getChild("bowtie");
        this.head2 = this.neck.getChild("head2");
        this.head = this.head2.getChild("head");
        this.snout = this.head.getChild("snout");
        this.ear = this.head.getChild("ear");
        this.ear2 = this.head.getChild("ear2");
        this.hat = this.head.getChild("hat");
        this.backLegL = this.bodynohead.getChild("backLegL");
        this.backLegR = this.bodynohead.getChild("backLegR");
        this.frontLegL = this.bodynohead.getChild("frontLegL");
        this.frontLegR = this.bodynohead.getChild("frontLegR");
        this.tail3 = this.bodynohead.getChild("tail3");
        this.tail4 = this.tail3.getChild("tail4");

		this.pathToHat = List.of(ooo, bone, body, bodynohead, neck2, neck, head2, head, hat);
		this.pathToBowtie = List.of(ooo, bone, body, bodynohead, neck2, neck, bowtie);
		this.pathToBackpack = List.of(ooo, bone, body, bodynohead, bodyxd, backpack);
        this.pathToNeck2 = List.of(ooo, bone, body, bodynohead, neck2);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition ooo = partdefinition.addOrReplaceChild("ooo", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition bone = ooo.addOrReplaceChild("bone", CubeListBuilder.create(), PartPose.offset(0.0F, -10.0F, -7.0F));

        PartDefinition body = bone.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 8.0F));

        PartDefinition bodynohead = body.addOrReplaceChild("bodynohead", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition bodyxd = bodynohead.addOrReplaceChild("bodyxd", CubeListBuilder.create().texOffs(54, 25).addBox(-2.0F, -3.0F, 0.0F, 4.0F, 6.0F, 1.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition bodyxd_r1 = bodyxd.addOrReplaceChild("bodyxd_r1", CubeListBuilder.create().texOffs(20, 0).addBox(-2.0F, -8.0F, -3.0F, 4.0F, 16.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.5708F, 0.0F, 0.0F));

        PartDefinition uhhhh = bodyxd.addOrReplaceChild("uhhhh", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition backpack = bodyxd.addOrReplaceChild("backpack", CubeListBuilder.create(), PartPose.offset(0.0F, -3.0F, 0.5F));

        PartDefinition neck2 = bodynohead.addOrReplaceChild("neck2", CubeListBuilder.create(), PartPose.offset(0.0F, 0.01F, -8.0F));

        PartDefinition neck = neck2.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(23, 3).addBox(-2.0F, -10.0F, -1.5F, 4.0F, 12.0F, 3.0F, new CubeDeformation(0.01F))
                .texOffs(53, 0).addBox(0.0F, -10.0F, 1.5F, 0.0F, 9.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.01F, -0.3F, 0.0873F, 0.0F, 0.0F));

        PartDefinition bowtie = neck.addOrReplaceChild("bowtie", CubeListBuilder.create(), PartPose.offset(0.0F, -1.0F, -1.6F));

        PartDefinition head2 = neck.addOrReplaceChild("head2", CubeListBuilder.create(), PartPose.offset(0.0F, -10.0F, 0.5F));

        PartDefinition head = head2.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-2.5F, -2.0F, -3.0F, 5.0F, 4.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(53, 0).addBox(0.0F, -2.0F, 2.0F, 0.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.0F, -1.0F, -0.0873F, 0.0F, 0.0F));

        PartDefinition snout = head.addOrReplaceChild("snout", CubeListBuilder.create().texOffs(0, 24).addBox(-1.5F, -1.0F, -1.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, 0.9844F, -3.0F));

        PartDefinition ear = head.addOrReplaceChild("ear", CubeListBuilder.create().texOffs(0, 10).addBox(-0.5F, -0.5F, -2.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.01F)), PartPose.offset(-1.5F, -2.5F, 2.0F));

        PartDefinition ear2 = head.addOrReplaceChild("ear2", CubeListBuilder.create().texOffs(6, 10).addBox(-0.5F, -0.5F, -2.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.01F)), PartPose.offset(1.5F, -2.5F, 2.0F));

        PartDefinition hat = head.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.offset(0.0F, -2.0F, -0.5F));

        PartDefinition backLegL = bodynohead.addOrReplaceChild("backLegL", CubeListBuilder.create().texOffs(40, 0).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 9.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(1.1F, 1.0F, 6.0F));

        PartDefinition backLegR = bodynohead.addOrReplaceChild("backLegR", CubeListBuilder.create().texOffs(40, 0).mirror().addBox(-1.0F, 0.0F, -1.0F, 2.0F, 9.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-1.1F, 1.0F, 6.0F));

        PartDefinition frontLegL = bodynohead.addOrReplaceChild("frontLegL", CubeListBuilder.create().texOffs(40, 0).mirror().addBox(-1.0F, -0.2F, -1.0F, 2.0F, 11.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(1.2F, -0.8F, -5.0F));

        PartDefinition frontLegR = bodynohead.addOrReplaceChild("frontLegR", CubeListBuilder.create().texOffs(40, 0).addBox(-1.0F, -0.2F, -1.0F, 2.0F, 11.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.2F, -0.8F, -5.0F));

        PartDefinition tail3 = bodynohead.addOrReplaceChild("tail3", CubeListBuilder.create().texOffs(0, 15).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 8.0F, 1.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(0.0F, -2.0F, 7.0F, 1.5708F, 0.0F, 0.0F));

        PartDefinition tail4 = tail3.addOrReplaceChild("tail4", CubeListBuilder.create().texOffs(4, 15).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 8.0F, 1.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, 8.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 32);
    }

    @Override
    public void setupAnim(@NotNull GiraffeCat entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

        float partialTick = ageInTicks % 1f;

        if (entity.isBaby()) {
            head.xScale *= 1.5f;
            head.yScale *= 1.5f;
            head.zScale *= 1.5f;
        }

        snout.visible = !entity.isSnoutless();

        PPAnimationControllers.GIRAFFE_CAT_BASE.play(this, entity, limbSwing, limbSwingAmount, ageInTicks, 0, netHeadYaw, headPitch, 1);

        if (!entity.isInSittingPose()) {
            this.animateWalk(limbSwing, limbSwingAmount, entity);
        }

        this.neck.y -= (entity.getVisibleLadderHeight(partialTick) - GiraffeCat.NECK_HEIGHT - GiraffeCat.SITTING_NECK_BOTTOM) * 16;


        //Shake the neck while the giraffe cat is yeeting
        boolean yeeting = entity.isYeeting();
        if (yeeting) {
            if (!entity.wasClientYeeting) {
                entity.yeetStartClientTime = ageInTicks;
            }
            float yeetTime = ageInTicks - entity.yeetStartClientTime;
            if (yeetTime < 20f) {
                neck.xRot -= PPUtil.bedrockSinAngle(yeetTime * 3600);
            }
        }
        entity.wasClientYeeting = yeeting;
    }


    private static final float WALK_FREQ_MULTI = 1.5f;
    private static final float WALK_AMP_MULTI = 1.0f;

    public void animateWalk(float limbSwing, float limbSwingAmount, GiraffeCat giraffeCat) {
        float time = limbSwing * WALK_FREQ_MULTI;
        float amp = limbSwingAmount * WALK_AMP_MULTI;

        float backLegLPhase;
        float backLegRPhase;
        float frontLegLPhase;
        float frontLegRPhase;

        if (giraffeCat.isSprinting()) {
            backLegLPhase = time * 0.6662F;
            backLegRPhase = time * 0.6662F + 0.3F;
            frontLegLPhase = time * 0.6662F + Mth.PI + 0.3F;
            frontLegRPhase = time * 0.6662F + Mth.PI;
            this.tail4.xRot += (Mth.PI / 10F) * Mth.cos(time) * amp;
        } else {
            backLegLPhase = time * 0.6662F;
            backLegRPhase = time * 0.6662F + Mth.PI;
            frontLegLPhase = time * 0.6662F + Mth.PI;
            frontLegRPhase = time * 0.6662F;
            if (!giraffeCat.isCrouching()) {
                this.tail4.xRot += (Mth.PI / 4F) * Mth.cos(time) * amp;
            } else {
                this.tail4.xRot += 0.47123894F * Mth.cos(time) * amp;
            }
        }

        this.backLegL.xRot += Mth.cos(backLegLPhase) * amp;
        this.backLegR.xRot += Mth.cos(backLegRPhase) * amp;
        this.frontLegL.xRot += Mth.cos(frontLegLPhase) * amp;
        this.frontLegR.xRot += Mth.cos(frontLegRPhase) * amp;

        this.backLegL.y -= Mth.sin(backLegLPhase) * amp;
        this.backLegL.z -= Mth.cos(backLegLPhase) * amp;
        this.backLegR.y -= Mth.sin(backLegRPhase) * amp;
        this.backLegR.z -= Mth.cos(backLegRPhase) * amp;
        this.frontLegL.y -= Mth.sin(frontLegLPhase) * amp;
        this.frontLegL.z -= Mth.cos(frontLegLPhase) * amp;
        this.frontLegR.y -= Mth.sin(frontLegRPhase) * amp;
        this.frontLegR.z -= Mth.cos(frontLegRPhase) * amp;

        this.body.zRot += Mth.sin(time) * amp * 2 * Mth.DEG_TO_RAD;
        this.body.y += Mth.abs(Mth.sin(time) * amp);

    }

    @Override
    protected void applyHeadRotation(float pNetHeadYaw, float pHeadPitch, GiraffeCat giraffeCat) {
        if (giraffeCat.noCurrentAbility()) {
            neck.yRot += pNetHeadYaw * ((float) Math.PI / 180F);
            neck.xRot += pHeadPitch * ((float) Math.PI / 180F) / 2;
            head.xRot += pHeadPitch * ((float) Math.PI / 180F) / 2;
        }
    }

    @Override
    public @Nullable ModelPart head() {
        return head;
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