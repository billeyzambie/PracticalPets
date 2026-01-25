package billeyzambie.practicalpets.client.model.entity.dinosaur;// Made with Blockbench 4.12.4
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import billeyzambie.animationcontrollers.Animatable;
import billeyzambie.animationcontrollers.PracticalPetModel;
import billeyzambie.practicalpets.misc.PPAnimationControllers;
import billeyzambie.practicalpets.client.animation.dinosaur.DuckAnimations;
import billeyzambie.practicalpets.entity.dinosaur.Duck;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;

public class DuckModel extends PracticalPetModel<Duck> {
    protected ModelPart ooo;
    protected ModelPart body;
    protected ModelPart leg0;
    protected ModelPart bone5;
    protected ModelPart leg1;
    protected ModelPart bone4;
    protected ModelPart bone6;
    protected ModelPart bodynolegs;
    protected ModelPart head;
    protected ModelPart dontshowonbaby;
    protected ModelPart lol;
    protected ModelPart beak;
    protected ModelPart bone;
    protected ModelPart bone3;
    protected ModelPart bone7;
    protected ModelPart hat;
    protected ModelPart bone11;
    protected ModelPart bone10;
    protected ModelPart bone9;
    protected ModelPart bowtie;
    protected ModelPart bodylol;
    protected ModelPart backpack;
    protected ModelPart duck_tail;
    protected ModelPart duck_tail_flesh;
    protected ModelPart tcheste;
    protected ModelPart endrod;
    protected ModelPart wing0;
    protected ModelPart wing1;

    public DuckModel(ModelPart root) {
        this.ooo = root.getChild("ooo");
        this.body = this.ooo.getChild("body");
        this.leg0 = this.body.getChild("leg0");
        this.bone5 = this.leg0.getChild("bone5");
        this.leg1 = this.body.getChild("leg1");
        this.bone4 = this.leg1.getChild("bone4");
        this.bone6 = this.body.getChild("bone6");
        this.bodynolegs = this.bone6.getChild("bodynolegs");
        this.head = this.bodynolegs.getChild("head");
        this.dontshowonbaby = this.head.getChild("dontshowonbaby");
        this.lol = this.head.getChild("lol");
        this.beak = this.lol.getChild("beak");
        this.bone = this.beak.getChild("bone");
        this.bone3 = this.beak.getChild("bone3");
        this.bone7 = this.lol.getChild("bone7");
        this.hat = this.bone7.getChild("hat");
        this.bone11 = this.bone7.getChild("bone11");
        this.bone10 = this.bone11.getChild("bone10");
        this.bone9 = this.bone11.getChild("bone9");
        this.bowtie = this.head.getChild("bowtie");
        this.bodylol = this.bodynolegs.getChild("bodylol");
        this.backpack = this.bodylol.getChild("backpack");
        this.duck_tail = this.bodylol.getChild("duck_tail");
        this.duck_tail_flesh = this.duck_tail.getChild("duck_tail_flesh");
        this.tcheste = this.bodylol.getChild("tcheste");
        this.endrod = this.bodylol.getChild("endrod");
        this.wing0 = this.bodynolegs.getChild("wing0");
        this.wing1 = this.bodynolegs.getChild("wing1");

        pathToBowtie = List.of(ooo, body, bone6, bodynolegs, head, bowtie);
        pathToHat = List.of(ooo, body, bone6, bodynolegs, head, lol, bone7, hat);
        pathToBackpack = List.of(ooo, body, bone6, bodynolegs, bodylol, backpack);
    }

    protected DuckModel() {

    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition ooo = partdefinition.addOrReplaceChild("ooo", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition body = ooo.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, -4.925F, 1.0F));

        PartDefinition leg0 = body.addOrReplaceChild("leg0", CubeListBuilder.create().texOffs(28, 14).addBox(-0.5F, -1.9F, 0.0F, 1.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.5F, 1.8F, 0.0F));

        PartDefinition bone5 = leg0.addOrReplaceChild("bone5", CubeListBuilder.create().texOffs(24, 10).addBox(-1.5F, 0.0F, -2.0F, 3.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.1F, 0.0F));

        PartDefinition leg1 = body.addOrReplaceChild("leg1", CubeListBuilder.create().texOffs(28, 19).addBox(-0.5F, -1.9F, 0.0F, 1.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(1.5F, 1.8F, 0.0F));

        PartDefinition bone4 = leg1.addOrReplaceChild("bone4", CubeListBuilder.create().texOffs(24, 12).addBox(-1.5F, 0.0F, -2.0F, 3.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.1F, 0.0F));

        PartDefinition bone6 = body.addOrReplaceChild("bone6", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, -3.0F));

        PartDefinition bodynolegs = bone6.addOrReplaceChild("bodynolegs", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 3.0F));

        PartDefinition head = bodynolegs.addOrReplaceChild("head", CubeListBuilder.create().texOffs(24, 0).addBox(-2.0F, -2.0F, -1.0F, 4.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.075F, -5.0F));

        PartDefinition dontshowonbaby = head.addOrReplaceChild("dontshowonbaby", CubeListBuilder.create(), PartPose.offset(0.0F, 8.0F, 4.0F));

        PartDefinition lol = head.addOrReplaceChild("lol", CubeListBuilder.create(), PartPose.offset(0.0F, -3.5F, -0.5F));

        PartDefinition beak = lol.addOrReplaceChild("beak", CubeListBuilder.create(), PartPose.offset(0.0F, 1.0F, -1.51F));

        PartDefinition bone = beak.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(14, 26).addBox(-1.0F, -0.5F, -1.99F, 2.0F, 0.5F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition bone3 = beak.addOrReplaceChild("bone3", CubeListBuilder.create().texOffs(22, 26).addBox(-1.0F, 0.0F, -1.99F, 2.0F, 0.5F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition bone7 = lol.addOrReplaceChild("bone7", CubeListBuilder.create().texOffs(0, 24).addBox(-2.0F, -1.3206F, -1.8006F, 4.0F, 3.0F, 3.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, -0.1794F, 0.3006F));

        PartDefinition hat = bone7.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.offset(0.0F, -1.3306F, -0.3006F));

        PartDefinition bone11 = bone7.addOrReplaceChild("bone11", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition bone10 = bone11.addOrReplaceChild("bone10", CubeListBuilder.create().texOffs(14, 24).addBox(-2.02F, -3.0F, -0.5F, 4.04F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.6794F, -0.3006F));

        PartDefinition bone9 = bone11.addOrReplaceChild("bone9", CubeListBuilder.create().texOffs(24, 24).addBox(-2.02F, 0.0F, -0.5F, 4.04F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.3206F, -0.3006F));

        PartDefinition bowtie = head.addOrReplaceChild("bowtie", CubeListBuilder.create(), PartPose.offset(0.0F, 0.5F, -1.0F));

        PartDefinition bodylol = bodynolegs.addOrReplaceChild("bodylol", CubeListBuilder.create(), PartPose.offset(0.0F, -1.0F, -1.0F));

        PartDefinition backpack = bodylol.addOrReplaceChild("backpack", CubeListBuilder.create(), PartPose.offset(0.0F, -3.075F, 0.0F));

        PartDefinition bodylol_r1 = bodylol.addOrReplaceChild("bodylol_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -4.0F, -3.0F, 6.0F, 8.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.075F, 0.0F, 1.5708F, 0.0F, 0.0F));

        PartDefinition duck_tail = bodylol.addOrReplaceChild("duck_tail", CubeListBuilder.create(), PartPose.offset(0.0F, -2.075F, 4.0F));

        PartDefinition duck_tail_r1 = duck_tail.addOrReplaceChild("duck_tail_r1", CubeListBuilder.create().texOffs(14, 28).addBox(-1.0F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.05F)), PartPose.offsetAndRotation(0.0F, -1.05F, -0.2F, 0.7854F, 0.0F, 0.0F));

        PartDefinition duck_tail_flesh = duck_tail.addOrReplaceChild("duck_tail_flesh", CubeListBuilder.create().texOffs(24, 5).addBox(-2.0F, -2.0F, -1.0F, 4.0F, 3.0F, 2.0F, new CubeDeformation(0.02F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition tcheste = bodylol.addOrReplaceChild("tcheste", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition endrod = bodylol.addOrReplaceChild("endrod", CubeListBuilder.create(), PartPose.offset(0.0F, -3.075F, 0.5F));

        PartDefinition wing0 = bodynolegs.addOrReplaceChild("wing0", CubeListBuilder.create().texOffs(0, 14).addBox(-1.0F, 0.0F, -3.0F, 1.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, -4.075F, -1.0F));

        PartDefinition wing1 = bodynolegs.addOrReplaceChild("wing1", CubeListBuilder.create().texOffs(14, 14).addBox(-0.025F, 0.0F, -3.0F, 1.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(3.025F, -4.075F, -1.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(@NotNull Duck entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

        //this.dontshowonbaby is null for the armor
        if (this.dontshowonbaby != null)
            this.dontshowonbaby.visible = !entity.isBaby();
        if (entity.isBaby()) {
            KeyframeAnimations.animate(this, DuckAnimations.ling, 0, 1, ANIMATION_VECTOR_CACHE);
        }

        this.animate(entity.biteFloorAnimationState, DuckAnimations.pickupbread, ageInTicks);
        this.animate(entity.quackAnimationState, DuckAnimations.quack, ageInTicks);

        PPAnimationControllers.SIMPLE_SIT.play(this, entity, limbSwing, limbSwingAmount, ageInTicks, 0, netHeadYaw, headPitch, 1);
        PPAnimationControllers.SIMPLE_ANGRY.play(this, entity, limbSwing, limbSwingAmount, ageInTicks, 0, netHeadYaw, headPitch, 1);
        PPAnimationControllers.DUCK_IDLE_FLAP.play(this, entity, limbSwing, limbSwingAmount, ageInTicks, 0, netHeadYaw, headPitch, 1);
        PPAnimationControllers.DUCK_WATER_WAVE.play(this, entity, limbSwing, limbSwingAmount, ageInTicks, 0, netHeadYaw, headPitch, Mth.clamp(1 - 2 * limbSwingAmount, 0, 1));

        if (!entity.isInSittingPose()) {
            this.animateWalk(DuckAnimations.walk32, limbSwing, limbSwingAmount, 3f, 4f);
        }

        float partialTick = ageInTicks % 1f;
        float f = Mth.lerp(partialTick, entity.oFlap, entity.flap);
        float f1 = Mth.lerp(partialTick, entity.oFlapSpeed, entity.flapSpeed);
        float flap = (Mth.sin(f) + 1.0F) * f1;

        this.wing0.zRot += flap;
        this.wing1.zRot -= flap;
    }

    @Override
    protected void applyHeadRotation(float pNetHeadYaw, float pHeadPitch) {
        this.head.yRot += pNetHeadYaw * ((float) Math.PI / 180F);
        this.head.xRot += pHeadPitch * ((float) Math.PI / 180F) / 2;
        this.lol.xRot += pHeadPitch * ((float) Math.PI / 180F) / 2;
    }

    @Override
    public @NotNull ModelPart root() {
        return ooo;
    }

    private final HashMap<String, AnimationDefinition> keyframeAnimationHashMap = new HashMap<>() {{
        put("sit", DuckAnimations.sit);
        put("angry", DuckAnimations.angry);
        put("idle_flap", DuckAnimations.idle_flap);
    }};
    private final HashMap<String, Animatable> mathAnimationHashMap = new HashMap<>() {{
        put("water_wave", DuckAnimations.water_wave);
    }};

    @Override
    public HashMap<String, AnimationDefinition> getKeyframeAnimationHashMap() {
        return keyframeAnimationHashMap;
    }

    @Override
    public HashMap<String, Animatable> getOtherAnimationHashMap() {
        return mathAnimationHashMap;
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

    @Override
    public ModelPart head() {
        return head;
    }

    List<ModelPart> pathToBackpack;

    @Override
    public List<ModelPart> pathToBackpack() {
        return pathToBackpack;
    }
}