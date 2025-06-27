package billeyzambie.animationcontrollers;

import java.util.HashMap;

public class MathAnimationBuilder {

    private MathAnimationBuilder() {};

    public static MathAnimationBuilder start() {
        return new MathAnimationBuilder();
    }

    private final HashMap<String, HashMap<MathAnimationDefinition.AnimationChannel, MathAnimationDefinition.BoneFunction>> boneFunctions = new HashMap<>();

    public MathAnimationBuilder addBoneFunction(
            String boneName,
            MathAnimationDefinition.AnimationChannel channel,
            MathAnimationDefinition.BoneFunction function
    ) {
        boneFunctions
                .computeIfAbsent(boneName, k -> new HashMap<>())
                .put(channel, function);
        return this;
    }

    public MathAnimationDefinition build() {
        return new MathAnimationDefinition(boneFunctions);
    }
}
