package billeyzambie.practicalpets.client.model.entity.dinosaur;// Made with Blockbench 5.0.7
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import billeyzambie.animationcontrollers.Animatable;
import billeyzambie.animationcontrollers.PracticalPetModel;
import billeyzambie.practicalpets.client.animation.dinosaur.PigeonAnimations;
import billeyzambie.practicalpets.client.model.entity.ItemHoldingEntityModel;
import billeyzambie.practicalpets.entity.dinosaur.Pigeon;
import billeyzambie.practicalpets.misc.PPAnimationControllers;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;

public class PigeonModel extends PracticalPetModel<Pigeon> implements ItemHoldingEntityModel {
    @Override
    public ModelPart root() {
        return ooo;
    }

    @Override
    public ModelPart head() {
        return head;
    }

    private final HashMap<String, AnimationDefinition> keyframeAnimationHashMap = new HashMap<>() {{
        put("sit", PigeonAnimations.sit);
        put("attack", PigeonAnimations.attack);
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
    @Override
    public List<ModelPart> pathToItem() {
        return pathToItem;
    }

    private final ModelPart ooo;
    private final ModelPart body_walking;
    private final ModelPart bodynolegs;
    private final ModelPart body1;
    private final ModelPart wing1;
    private final ModelPart wing0;
    private final ModelPart tail;
    private final ModelPart backpack;
    private final ModelPart necc;
    private final ModelPart head;
    private final ModelPart nose;
    private final ModelPart item;
    private final ModelPart bone;
    private final ModelPart bone2;
    private final ModelPart hat;
    private final ModelPart upper_eyelids;
    private final ModelPart lower_eyelids;
    private final ModelPart bowtie;
    private final ModelPart leg0;
    private final ModelPart foot0;
    private final ModelPart leg1;
    private final ModelPart foot1;

    public PigeonModel(ModelPart root) {
        this.ooo = root.getChild("ooo");
        this.body_walking = this.ooo.getChild("body_walking");
        this.bodynolegs = this.body_walking.getChild("bodynolegs");
        this.body1 = this.bodynolegs.getChild("body1");
        this.wing1 = this.body1.getChild("wing1");
        this.wing0 = this.body1.getChild("wing0");
        this.tail = this.body1.getChild("tail");
        this.backpack = this.body1.getChild("backpack");
        this.necc = this.bodynolegs.getChild("necc");
        this.head = this.necc.getChild("head");
        this.nose = this.head.getChild("nose");
        this.item = this.nose.getChild("item");
        this.bone = this.nose.getChild("bone");
        this.bone2 = this.nose.getChild("bone2");
        this.hat = this.head.getChild("hat");
        this.upper_eyelids = this.head.getChild("upper_eyelids");
        this.lower_eyelids = this.head.getChild("lower_eyelids");
        this.bowtie = this.necc.getChild("bowtie");
        this.leg0 = this.body_walking.getChild("leg0");
        this.foot0 = this.leg0.getChild("foot0");
        this.leg1 = this.body_walking.getChild("leg1");
        this.foot1 = this.leg1.getChild("foot1");

        this.pathToHat = List.of(ooo, body_walking, bodynolegs, necc, head, hat);
        this.pathToBowtie = List.of(ooo, body_walking, bodynolegs, necc, bowtie);
        this.pathToBackpack = List.of(ooo, body_walking, bodynolegs, body1, backpack);
        this.pathToItem = List.of(ooo, body_walking, bodynolegs, necc, head, nose, item);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition ooo = partdefinition.addOrReplaceChild("ooo", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition body_walking = ooo.addOrReplaceChild("body_walking", CubeListBuilder.create(), PartPose.offset(0.0F, -3.0F, 1.0F));

        PartDefinition bodynolegs = body_walking.addOrReplaceChild("bodynolegs", CubeListBuilder.create(), PartPose.offset(0.0F, 1.0F, -1.0F));

        PartDefinition body1 = bodynolegs.addOrReplaceChild("body1", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -4.0F, -2.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.0F, 0.0F, 0.6981F, 0.0F, 0.0F));

        PartDefinition wing1 = body1.addOrReplaceChild("wing1", CubeListBuilder.create().texOffs(11, 15).addBox(0.0F, 0.0F, -2.0F, 1.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, -3.0F, 1.0F));

        PartDefinition wing0 = body1.addOrReplaceChild("wing0", CubeListBuilder.create().texOffs(11, 15).mirror().addBox(-1.0F, 0.0F, -3.0F, 1.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-2.0F, -3.0F, 2.0F));

        PartDefinition tail = body1.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 12).addBox(-2.0F, 0.0F, 0.0F, 4.0F, 1.0F, 3.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(0.0F, 3.0F, 2.0F, -0.3054F, 0.0F, 0.0F));

        PartDefinition backpack = body1.addOrReplaceChild("backpack", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -0.5F, 2.0F, -1.5708F, 0.0F, 0.0F));

        PartDefinition necc = bodynolegs.addOrReplaceChild("necc", CubeListBuilder.create().texOffs(12, 0).addBox(-1.5F, -1.0F, -0.5F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -6.0F, -3.0F));

        PartDefinition head = necc.addOrReplaceChild("head", CubeListBuilder.create().texOffs(13, 9).mirror().addBox(-1.5F, -2.0F, -2.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.01F)).mirror(false)
                .texOffs(20, 2).addBox(-1.5F, -2.0F, -2.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(-0.09F)), PartPose.offset(0.0F, -2.0F, 0.5F));

        PartDefinition nose = head.addOrReplaceChild("nose", CubeListBuilder.create(), PartPose.offset(0.0F, 0.5F, -2.0F));

        PartDefinition item = nose.addOrReplaceChild("item", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.5708F, 0.0F, 0.0F));

        PartDefinition bone = nose.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(0, 16).addBox(-0.5F, -0.5F, -2.0F, 1.0F, 0.5F, 2.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition bone2 = nose.addOrReplaceChild("bone2", CubeListBuilder.create().texOffs(0, 19).addBox(-0.5F, 0.0F, -2.0F, 1.0F, 0.5F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition hat = head.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.offset(0.0F, -2.0F, -0.5F));

        PartDefinition upper_eyelids = head.addOrReplaceChild("upper_eyelids", CubeListBuilder.create().texOffs(14, 13).addBox(-1.52F, 0.0F, -0.6F, 3.04F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, -0.4F));

        PartDefinition lower_eyelids = head.addOrReplaceChild("lower_eyelids", CubeListBuilder.create().texOffs(20, 15).mirror().addBox(-1.52F, -1.0F, -0.6F, 3.04F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 1.0F, -0.4F));

        PartDefinition bowtie = necc.addOrReplaceChild("bowtie", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, -0.5F));

        PartDefinition leg0 = body_walking.addOrReplaceChild("leg0", CubeListBuilder.create().texOffs(0, 1).mirror().addBox(-0.5F, 0.0F, 0.0F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-1.5F, 1.0F, -1.0F));

        PartDefinition foot0 = leg0.addOrReplaceChild("foot0", CubeListBuilder.create().texOffs(14, 4).mirror().addBox(-1.5F, 0.0F, -2.0F, 3.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 2.0F, 0.0F));

        PartDefinition leg1 = body_walking.addOrReplaceChild("leg1", CubeListBuilder.create().texOffs(0, 1).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(1.5F, 1.0F, -1.0F));

        PartDefinition foot1 = leg1.addOrReplaceChild("foot1", CubeListBuilder.create().texOffs(14, 4).addBox(-1.5F, 0.0F, -2.0F, 3.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 32, 32);
    }

    @Override
    public void setupAnim(@NotNull Pigeon entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        if (entity.isBaby()) {
            head.xScale *= 1.5f;
            head.yScale *= 1.5f;
            head.zScale *= 1.5f;
        }

        if (!entity.isInSittingPose()) {
            float freqMulti = 3f;
            float ampMulti = 4f;
            ampMulti *= PPAnimationControllers.ON_GROUND_BLEND.calculate(this, entity, limbSwing, limbSwingAmount, ageInTicks, 0, netHeadYaw, headPitch, 1);
            this.animateWalk(PigeonAnimations.walk, limbSwing, limbSwingAmount, freqMulti, ampMulti);

            if (!entity.hasTarget())
                this.animate(entity.randomIdle1AnimationState, PigeonAnimations.eatfloor, ageInTicks);
            PPAnimationControllers.SIMPLE_ATTACK.play(this, entity, limbSwing, limbSwingAmount, ageInTicks, 0, netHeadYaw, headPitch, 1);
        }

        PPAnimationControllers.SIMPLE_SIT.play(this, entity, limbSwing, limbSwingAmount, ageInTicks, 0, netHeadYaw, headPitch, 1);

        float partialTick = ageInTicks % 1f;
        float f = Mth.lerp(partialTick, entity.oFlap, entity.flap);
        float f1 = Mth.lerp(partialTick, entity.oFlapSpeed, entity.flapSpeed);
        float flap = (Mth.sin(f) + 1.0F) * f1;

        this.wing0.zRot += flap;
        this.wing1.zRot -= flap;

        if (!entity.getMainHandItem().isEmpty()) {
            //Open mouth
            this.bone.xRot = -5 * Mth.DEG_TO_RAD;
            this.bone2.xRot = -12.5f * Mth.DEG_TO_RAD;
        }
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        ooo.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}