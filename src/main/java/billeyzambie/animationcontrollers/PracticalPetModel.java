package billeyzambie.animationcontrollers;

import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

import java.util.HashMap;
import java.util.List;

public abstract class PracticalPetModel<T extends Entity> extends HierarchicalModel<T> {
    protected static final Vector3f ANIMATION_VECTOR_CACHE = new Vector3f();

    public abstract HashMap<String, AnimationDefinition> getKeyframeAnimationHashMap();
    public abstract HashMap<String, MathAnimationDefinition> getMathAnimationHashMap();

    public abstract List<ModelPart> pathToBowtie();
    public abstract List<ModelPart> pathToHat();
    public abstract ModelPart head();

    @Override
    public void setupAnim(@NotNull T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);

        if (entity instanceof LivingEntity living && living.getHealth() < living.getMaxHealth())
            hurtAnimation(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

        this.applyHeadRotation(netHeadYaw, headPitch);
    }

    protected void applyHeadRotation(float pNetHeadYaw, float pHeadPitch) {
        this.head().yRot += pNetHeadYaw * ((float) Math.PI / 180F);
        this.head().xRot += pHeadPitch * ((float) Math.PI / 180F);
    }

    protected void hurtAnimation(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.head().xRot += 9 * ((float) Math.PI / 180F);
        this.root().x += (float) (Math.sin(ageInTicks * 100 * ((float) Math.PI / 180F)) / 15);
    }
}