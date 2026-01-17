package billeyzambie.animationcontrollers;

import billeyzambie.practicalpets.client.animation.PracticalPetAnimation;
import billeyzambie.practicalpets.entity.PracticalPet;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

import java.util.HashMap;
import java.util.List;

public abstract class PracticalPetModel<T extends Entity> extends HierarchicalModel<T> {
    protected static final Vector3f ANIMATION_VECTOR_CACHE = new Vector3f();

    public abstract HashMap<String, AnimationDefinition> getKeyframeAnimationHashMap();
    public abstract HashMap<String, Animatable> getOtherAnimationHashMap();

    public abstract List<ModelPart> pathToBowtie();
    public abstract List<ModelPart> pathToHat();
    public abstract List<ModelPart> pathToBackpack();
    public abstract ModelPart head();

    @Override
    public void setupAnim(@NotNull T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        redMultiplier = greenMultiplier = blueMultiplier = 1;

        if (entity instanceof LivingEntity living && living.getHealth() < living.getMaxHealth())
            hurtAnimation(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

        if (entity instanceof PracticalPet pet) {
            this.animate(pet.squishHatAnimationState, PracticalPetAnimation.HatSquish, ageInTicks);

            if (pet.isRainbow()) {
                redMultiplier *= 0.5f + 0.5f * Mth.sin(pet.getVariant() + Mth.PI * ageInTicks / 20f);
                greenMultiplier *= 0.5f + 0.5f * Mth.sin(pet.getVariant() + Mth.PI * ageInTicks / 20f + Mth.TWO_PI / 3f);
                blueMultiplier *= 0.5f + 0.5f * Mth.sin(pet.getVariant() + Mth.PI * ageInTicks / 20f - Mth.TWO_PI / 3f);
            }
        }

        this.applyHeadRotation(netHeadYaw, headPitch);
    }

    protected void applyHeadRotation(float pNetHeadYaw, float pHeadPitch) {
        this.head().yRot += pNetHeadYaw * ((float) Math.PI / 180F);
        this.head().xRot += pHeadPitch * ((float) Math.PI / 180F);
    }

    protected void hurtAnimation(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.head().xRot += 9 * ((float) Math.PI / 180F);
        this.root().x += (float) (Math.sin(ageInTicks * 100 * Mth.PI / 180f) / 15f);
    }

    public float redMultiplier = 1;
    public float greenMultiplier = 1;
    public float blueMultiplier = 1;

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {

        super.renderToBuffer(poseStack, vertexConsumer, packedLight, packedOverlay, red * redMultiplier, green * greenMultiplier, blue * blueMultiplier, alpha);
    }

}