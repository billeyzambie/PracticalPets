package billeyzambie.animationcontrollers;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.Entity;
import org.joml.Vector3f;

import java.util.HashMap;
import java.util.Map;

public class MathAnimationDefinition implements Animatable {

    public enum AnimationChannel {
        POSITION,
        ROTATION,
        SCALE
    }

    @FunctionalInterface
    public interface BoneFunction {
        Vector3f apply(
                PracticalPetModel<Entity> model,
                Entity entity,
                float limbSwing,
                float limbSwingAmount,
                float ageInTicks,
                float animTime,
                float netHeadYaw,
                float headPitch,
                float blendWeight,
                Vector3f previousValue
        );
    }

    final HashMap<String, HashMap<AnimationChannel, BoneFunction>> boneFunctions;

    public MathAnimationDefinition(HashMap<String, HashMap<AnimationChannel, BoneFunction>> boneFunctions) {
        this.boneFunctions = boneFunctions;
    }

    public static float fastPow(float base, float exponent) {
        if (exponent == 0.0f) return 1f;
        if (exponent == 1.0f) return base;
        if (exponent == 2.0f) return base * base;
        return (float) Math.pow(base, exponent);
    }

    @Override
    public <T extends Entity> void animate(
            PracticalPetModel<T> model,
            T entity,
            float limbSwing,
            float limbSwingAmount,
            float ageInTicks,
            float animTime,
            float netHeadYaw,
            float headPitch,
            float blendWeight
    ) {
        for (Map.Entry<String, HashMap<AnimationChannel, BoneFunction>> entry : boneFunctions.entrySet()) {
            String boneName = entry.getKey();
            Map<AnimationChannel, BoneFunction> channelMap = entry.getValue();

            ModelPart bone = model.getAnyDescendantWithName(boneName).orElse(null);
            if (bone == null) continue;

            for (Map.Entry<AnimationChannel, BoneFunction> channelEntry : channelMap.entrySet()) {
                AnimationChannel channel = channelEntry.getKey();
                BoneFunction func = channelEntry.getValue();

                Vector3f previousValue = switch (channel) {
                    case POSITION -> new Vector3f(bone.x, bone.y, bone.z);
                    case ROTATION -> new Vector3f((float)Math.toDegrees(bone.xRot), (float)Math.toDegrees(bone.yRot), (float)Math.toDegrees(bone.zRot));
                    case SCALE -> new Vector3f(bone.xScale, bone.yScale, bone.zScale);
                };

                Vector3f result = func.apply(
                        (PracticalPetModel<Entity>) model,
                        entity,
                        limbSwing,
                        limbSwingAmount,
                        ageInTicks,
                        animTime / 20,
                        netHeadYaw,
                        headPitch,
                        blendWeight,
                        previousValue
                );

                switch (channel) {
                    case POSITION -> {
                        bone.offsetPos(result.mul(blendWeight));
                    }
                    case ROTATION -> {
                        bone.offsetRotation(result.mul((float)Math.PI / 180 * blendWeight ));
                    }
                    case SCALE -> {
                        bone.offsetScale(
                                new Vector3f(
                                        fastPow(result.x, blendWeight),
                                        fastPow(result.y, blendWeight),
                                        fastPow(result.z, blendWeight)
                                )
                        );
                    }
                }
            }
        }
    }
}