package billeyzambie.practicalpets.client.model.entity.dinosaur;// Made with Blockbench 4.12.4
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import billeyzambie.animationcontrollers.Animatable;
import billeyzambie.animationcontrollers.PracticalPetModel;
import billeyzambie.animationcontrollers.MathAnimationDefinition;
import billeyzambie.practicalpets.misc.PPAnimationControllers;
import billeyzambie.practicalpets.client.animation.dinosaur.BananaDuckAnimation;
import billeyzambie.practicalpets.client.animation.dinosaur.DuckAnimation;
import billeyzambie.practicalpets.entity.dinosaur.BananaDuck;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;


public class BananaDuckModel extends PracticalPetModel<BananaDuck> {

    private final HashMap<String, AnimationDefinition> keyframeAnimationHashMap = new HashMap<>() {{
        put("sit", BananaDuckAnimation.sit);
        put("angry", BananaDuckAnimation.angry);
        put("idle_flap", DuckAnimation.idle_flap);
    }};
    private final HashMap<String, Animatable> mathAnimationHashMap = new HashMap<>() {{
        put("flap", BananaDuckAnimation.flap);
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

    List<ModelPart> pathToBackpack;

    @Override
    public List<ModelPart> pathToBackpack() {
        return pathToBackpack;
    }

    @Override
    public ModelPart head() {
        return head;
    }

    protected ModelPart ooo;
    protected ModelPart pop;
    protected ModelPart shake;
    protected ModelPart body;
    protected ModelPart leg0;
    protected ModelPart bone5;
    protected ModelPart leg1;
    protected ModelPart bone4;
    protected ModelPart bone6;
    protected ModelPart bodynolegs;
    protected ModelPart bodylol;
    protected ModelPart head;
    protected ModelPart backpack;
    protected ModelPart lol;
    protected ModelPart beak;
    protected ModelPart bone;
    protected ModelPart bone3;
    protected ModelPart bone7;
    protected ModelPart hat;
    protected ModelPart bone11;
    protected ModelPart bone10;
    protected ModelPart bone9;
    protected ModelPart endrod;
    protected ModelPart bone2;
    protected ModelPart bone8;
    protected ModelPart wing1;
    protected ModelPart wing2;
    protected ModelPart duck_tail;
    protected ModelPart wing5;
    protected ModelPart wing6;
    protected ModelPart wing3;
    protected ModelPart bowtie;
    protected ModelPart wing0;
    protected ModelPart wing4;

    protected BananaDuckModel() {
    }

    public BananaDuckModel(ModelPart root) {
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
        this.backpack = this.head.getChild("backpack");
        this.lol = this.head.getChild("lol");
        this.beak = this.lol.getChild("beak");
        this.bone = this.beak.getChild("bone");
        this.bone3 = this.beak.getChild("bone3");
        this.bone7 = this.lol.getChild("bone7");
        this.hat = this.bone7.getChild("hat");
        this.bone11 = this.bone7.getChild("bone11");
        this.bone10 = this.bone11.getChild("bone10");
        this.bone9 = this.bone11.getChild("bone9");
        this.endrod = this.head.getChild("endrod");
        this.bone2 = this.bodynolegs.getChild("bone2");
        this.bone8 = this.bone2.getChild("bone8");
        this.wing1 = this.bone2.getChild("wing1");
        this.wing2 = this.wing1.getChild("wing2");
        this.duck_tail = this.bone2.getChild("duck_tail");
        this.wing5 = this.duck_tail.getChild("wing5");
        this.wing6 = this.bone2.getChild("wing6");
        this.wing3 = this.wing6.getChild("wing3");
        this.bowtie = this.wing3.getChild("bowtie");
        this.wing0 = this.bone2.getChild("wing0");
        this.wing4 = this.wing0.getChild("wing4");

        pathToBowtie = List.of(ooo, pop, shake, body, bone6, bodynolegs, bone2, wing6, wing3, bowtie);
        pathToHat = List.of(ooo, pop, shake, body, bone6, bodynolegs, bodylol, head, lol, bone7, hat);
        pathToBackpack = List.of(ooo, pop, shake, body, bone6, bodynolegs, bodylol, head, backpack);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition ooo = partdefinition.addOrReplaceChild("ooo", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition pop = ooo.addOrReplaceChild("pop", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition shake = pop.addOrReplaceChild("shake", CubeListBuilder.create(), PartPose.offset(0.0F, -5.0F, 0.0F));

        PartDefinition body = shake.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 0.075F, 0.0F));

        PartDefinition leg0 = body.addOrReplaceChild("leg0", CubeListBuilder.create().texOffs(14, 25).addBox(-0.0323F, -0.7086F, 0.0F, 1.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.55F, -1.75F, 0.0F, 0.0F, 0.0F, 0.3927F));

        PartDefinition bone5 = leg0.addOrReplaceChild("bone5", CubeListBuilder.create().texOffs(24, 8).addBox(-1.5F, 0.0F, -2.0F, 3.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.4677F, 6.9914F, 0.0F, 0.0F, 0.0F, -0.3927F));

        PartDefinition leg1 = body.addOrReplaceChild("leg1", CubeListBuilder.create().texOffs(16, 25).addBox(-0.9677F, -0.7086F, 0.0F, 1.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.55F, -1.75F, 0.0F, 0.0F, 0.0F, -0.3927F));

        PartDefinition bone4 = leg1.addOrReplaceChild("bone4", CubeListBuilder.create().texOffs(24, 10).addBox(-1.5F, 0.0F, -2.0F, 3.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.4677F, 6.9914F, 0.0F, 0.0F, 0.0F, 0.3927F));

        PartDefinition bone6 = body.addOrReplaceChild("bone6", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, -3.0F));

        PartDefinition bodynolegs = bone6.addOrReplaceChild("bodynolegs", CubeListBuilder.create(), PartPose.offset(0.0F, -1.075F, 3.0F));

        PartDefinition bodylol = bodynolegs.addOrReplaceChild("bodylol", CubeListBuilder.create(), PartPose.offset(0.0F, 2.075F, 1.75F));

        PartDefinition head = bodylol.addOrReplaceChild("head", CubeListBuilder.create().texOffs(20, 0).addBox(-1.5F, -2.0F, -1.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(20, 13).addBox(-1.5F, 0.0F, -1.0F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(20, 16).addBox(-1.5F, -3.0F, -1.0F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -6.075F, -0.9F));

        PartDefinition backpack = head.addOrReplaceChild("backpack", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -2.0F, 1.15F, -1.5708F, 0.0F, 0.0F));

        PartDefinition lol = head.addOrReplaceChild("lol", CubeListBuilder.create(), PartPose.offset(0.0F, -2.5F, 0.0F));

        PartDefinition beak = lol.addOrReplaceChild("beak", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, -2.02F));

        PartDefinition bone = beak.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(0, 27).addBox(-1.0F, -0.5F, -1.98F, 2.0F, 0.5F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition bone3 = beak.addOrReplaceChild("bone3", CubeListBuilder.create().texOffs(0, 30).addBox(-1.0F, 0.0F, -1.98F, 2.0F, 0.5F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition bone7 = lol.addOrReplaceChild("bone7", CubeListBuilder.create().texOffs(0, 9).addBox(-1.5F, -1.5206F, -1.5006F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.02F)), PartPose.offset(0.0F, -0.9794F, -0.4994F));

        PartDefinition hat = bone7.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.offset(0.0F, -1.5206F, -0.0006F));

        PartDefinition bone11 = bone7.addOrReplaceChild("bone11", CubeListBuilder.create(), PartPose.offset(0.0F, -0.2F, 0.4F));

        PartDefinition bone10 = bone11.addOrReplaceChild("bone10", CubeListBuilder.create().texOffs(18, 29).addBox(-1.53F, -3.0F, -0.6F, 3.06F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.6794F, -0.3006F));

        PartDefinition bone9 = bone11.addOrReplaceChild("bone9", CubeListBuilder.create().texOffs(26, 29).addBox(-1.53F, 0.0F, -0.6F, 3.06F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.3206F, -0.3006F));

        PartDefinition endrod = head.addOrReplaceChild("endrod", CubeListBuilder.create(), PartPose.offset(0.0F, -1.5F, 1.0F));

        PartDefinition bone2 = bodynolegs.addOrReplaceChild("bone2", CubeListBuilder.create(), PartPose.offset(0.0F, -3.0F, 1.75F));

        PartDefinition bone8 = bone2.addOrReplaceChild("bone8", CubeListBuilder.create(), PartPose.offsetAndRotation(0.5F, 1.0F, -0.25F, -0.3927F, 0.0F, 0.0F));

        PartDefinition bone8_r1 = bone8.addOrReplaceChild("bone8_r1", CubeListBuilder.create().texOffs(20, 4).addBox(-2.0F, -2.0F, 0.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 6.0F, -2.0F, 1.5708F, 0.0F, 0.0F));

        PartDefinition bone8_r2 = bone8.addOrReplaceChild("bone8_r2", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -2.0F, -2.0F, 3.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 4.0F, 0.0F, 1.5708F, 0.0F, 0.0F));

        PartDefinition wing1 = bone2.addOrReplaceChild("wing1", CubeListBuilder.create().texOffs(20, 19).addBox(-1.025F, 0.0F, -1.0F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(1.525F, -1.0F, -1.0F, 0.0F, 0.0F, -0.3927F));

        PartDefinition wing2 = wing1.addOrReplaceChild("wing2", CubeListBuilder.create().texOffs(8, 21).addBox(-1.0F, 0.0F, -1.0F, 1.0F, 5.0F, 2.0F, new CubeDeformation(0.01F)), PartPose.offset(1.975F, 1.0F, 0.0F));

        PartDefinition duck_tail = bone2.addOrReplaceChild("duck_tail", CubeListBuilder.create().texOffs(12, 9).addBox(-0.5F, 0.0F, -1.5F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(0.0F, -1.0F, 0.1F, 1.5708F, -1.1781F, -1.5708F));

        PartDefinition wing5 = duck_tail.addOrReplaceChild("wing5", CubeListBuilder.create().texOffs(12, 13).addBox(-1.0F, 0.0F, -1.5F, 1.0F, 5.0F, 3.0F, new CubeDeformation(0.01F)), PartPose.offset(2.5F, 1.0F, 0.0F));

        PartDefinition wing6 = bone2.addOrReplaceChild("wing6", CubeListBuilder.create().texOffs(0, 15).addBox(-0.5F, 0.0F, -1.5F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(0.0F, -1.0F, -2.0F, -1.5708F, 1.1345F, -1.5708F));

        PartDefinition wing3 = wing6.addOrReplaceChild("wing3", CubeListBuilder.create().texOffs(0, 19).addBox(-1.0F, 0.0F, -1.5F, 1.0F, 5.0F, 3.0F, new CubeDeformation(0.01F)), PartPose.offset(2.5F, 1.0F, 0.0F));

        PartDefinition bowtie = wing3.addOrReplaceChild("bowtie", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition wing0 = bone2.addOrReplaceChild("wing0", CubeListBuilder.create().texOffs(14, 22).addBox(-1.975F, 0.0F, -1.0F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(-1.525F, -1.0F, -1.0F, 0.0F, 0.0F, 0.3927F));

        PartDefinition wing4 = wing0.addOrReplaceChild("wing4", CubeListBuilder.create().texOffs(24, 22).addBox(0.0F, 0.0F, -1.0F, 1.0F, 5.0F, 2.0F, new CubeDeformation(0.01F)), PartPose.offset(-1.975F, 1.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }


    @Override
    public void setupAnim(@NotNull BananaDuck entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

        if (entity.isBaby()) {
            KeyframeAnimations.animate(this, DuckAnimation.ling, 0, 1, ANIMATION_VECTOR_CACHE);
        }

        boolean isMakingBanana =
                entity.makingBananaAnimationState.isStarted()
                        && entity.makingBananaAnimationState.getAccumulatedTime() / 1000f
                        < BananaDuckAnimation.make_banana.lengthInSeconds();

        if (isMakingBanana)
            bananaMakingShake(entity.makingBananaAnimationState.getAccumulatedTime() / 1000f);
        else
            this.animate(entity.quackAnimationState, DuckAnimation.quack, ageInTicks);

        this.animate(entity.makingBananaAnimationState, BananaDuckAnimation.make_banana, ageInTicks);

        PPAnimationControllers.SIMPLE_SIT.play(this, entity, limbSwing, limbSwingAmount, ageInTicks, 0, netHeadYaw, headPitch, 1);
        PPAnimationControllers.BANANA_DUCK_FLAP_AND_IF_ANGRY.play(this, entity, limbSwing, limbSwingAmount, ageInTicks, 0, netHeadYaw, headPitch, 1);
        PPAnimationControllers.SIMPLE_ANGRY.play(this, entity, limbSwing, limbSwingAmount, ageInTicks, 0, netHeadYaw, headPitch, 1);
        PPAnimationControllers.DUCK_IDLE_FLAP.play(this, entity, limbSwing, limbSwingAmount, ageInTicks, 0, netHeadYaw, headPitch, 1);

        if (!entity.isInSittingPose())
            this.animateWalk(BananaDuckAnimation.walk, limbSwing, limbSwingAmount, 3f, 2f);

    }

    @Override
    protected void applyHeadRotation(float pNetHeadYaw, float pHeadPitch) {
        this.head.yRot += pNetHeadYaw * ((float) Math.PI / 180F);
        this.head.xRot += pHeadPitch * ((float) Math.PI / 180F) / 2;
        this.lol.xRot += pHeadPitch * ((float) Math.PI / 180F) / 2;
    }

    private void bananaMakingShake(float animTimeInSeconds) {
        double xOffset;

        double sine1 = Math.sin((Math.PI / 180) * animTimeInSeconds * 600) / 4.0;
        double sine2 = Math.sin((Math.PI / 180) * animTimeInSeconds * 1200 + 60);

        if (animTimeInSeconds < 0.64f) {
            float localTime = animTimeInSeconds / 0.64f;
            double target = sine1;
            xOffset = Mth.lerp(localTime, 0, target);
        } else if (animTimeInSeconds < 1.12f) {
            float localTime = (animTimeInSeconds - 0.64f) / (1.12f - 0.64f);
            double start = sine1;
            double end = sine2;
            xOffset = Mth.lerp(localTime, start, end);
        } else if (animTimeInSeconds < 1.6f) {
            float localTime = (animTimeInSeconds - 1.12f) / (1.6f - 1.12f);
            double start = sine2;
            double end = sine1;
            xOffset = Mth.lerp(localTime, start, end);
        } else {
            xOffset = 0;
        }

        this.root().x += (float) xOffset;
    }

    @Override
    public @NotNull ModelPart root() {
        return ooo;
    }

    @Override
    public void copyPropertiesTo(EntityModel<BananaDuck> otherModel) {
        super.copyPropertiesTo(otherModel);
        //WIthout this armor wasn't shaking unsynced to the banana duck itself when making bananas
        ((PracticalPetModel<BananaDuck>) otherModel).root().copyFrom(this.root());
    }
}