package billeyzambie.animationcontrollers;

import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.world.entity.Entity;

public class KeyframeAnimationReference implements Animatable {
    //This has just a string as a property instead of an AnimationDefinition so that animation controllers can be reused across different mobs
    String name;

    public KeyframeAnimationReference(String name) {
        this.name = name;
    }

    @Override
    public <T extends Entity> void play(
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
        AnimationDefinition animationDefinition = model.getKeyframeAnimationHashMap().get(name);
        KeyframeAnimations.animate(
                model,
                animationDefinition,
                (long) (50f * ageInTicks),
                blendWeight,
                ANIMATION_VECTOR_CACHE
        );
    }
}
