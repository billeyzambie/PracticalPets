package billeyzambie.animationcontrollers;

import net.minecraft.world.entity.Entity;

public class MathAnimationReference implements Animatable {
    //This has just a string as a property instead of an AnimationDefinition so that animation controllers can be reused across different mobs
    String name;

    public MathAnimationReference(String name) {
        this.name = name;
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
        MathAnimationDefinition animationDefinition = model.getMathAnimationHashMap().get(name);
        animationDefinition.animate(model, entity, limbSwing, limbSwingAmount, ageInTicks, animTime, netHeadYaw, headPitch, blendWeight);
    }
}
