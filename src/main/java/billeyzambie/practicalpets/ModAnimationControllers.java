package billeyzambie.practicalpets;

import billeyzambie.animationcontrollers.AnimationController;
import billeyzambie.animationcontrollers.BinaryAnimationControllerBuilder;
import billeyzambie.animationcontrollers.KeyframeAnimationReference;
import billeyzambie.animationcontrollers.MathAnimationReference;
import billeyzambie.practicalpets.entity.PracticalPet;
import billeyzambie.practicalpets.entity.dinosaur.AbstractDuck;
import billeyzambie.practicalpets.entity.dinosaur.BananaDuck;
import net.minecraft.world.entity.TamableAnimal;

public class ModAnimationControllers {

    /*
    public static final AnimationController SIMPLE_SIT = new AnimationController("sit", List.of(
            new AnimationController.State(
                    Animatable.NO_ANIMATIONS,
                    List.of(
                            AnimationController.TransitionPredicate.ALWAYS_FALSE,
                            (model, entity, limbSwing, limbSwingAmount, ageInTicks, animTime, netHeadYaw, headPitch, deltaTime)
                                    -> ((TamableAnimal) entity).isInSittingPose()
                    ),
                    0.1f
            ),
            new AnimationController.State(
                    List.of(new KeyframeAnimation(
                            "sit"
                    )),
                    List.of(
                            (model, entity, limbSwing, limbSwingAmount, ageInTicks, animTime, netHeadYaw, headPitch, deltaTime)
                                    -> !((TamableAnimal) entity).isInSittingPose()
                    ),
                    0.1f
            )
    ));
    */

    public static final AnimationController SIMPLE_SIT = BinaryAnimationControllerBuilder.start("simple_sit")
            .otherStateAnimations(new KeyframeAnimationReference("sit"))
            .blendTime(0.1f)
            .transitionPredicate(
                    (model, entity, limbSwing, limbSwingAmount, ageInTicks, animTime, netHeadYaw, headPitch, deltaTime)
                            -> ((TamableAnimal) entity).isInSittingPose()
            )
            .build();

    public static final AnimationController BANANA_DUCK_FLAP_AND_IF_ANGRY = BinaryAnimationControllerBuilder.start("simple_flap")
            .otherStateAnimations(new MathAnimationReference("flap"))
            .blendTime(0.1f)
            .transitionPredicate(
                    (model, entity, limbSwing, limbSwingAmount, ageInTicks, animTime, netHeadYaw, headPitch, deltaTime)
                            -> entity instanceof BananaDuck bananaDuck && (bananaDuck.duckIsFlapping() || bananaDuck.hasTarget())
            )
            .build();

    public static final AnimationController SIMPLE_ANGRY = BinaryAnimationControllerBuilder.start("simple_angry")
            .otherStateAnimations(new KeyframeAnimationReference("angry"))
            .blendTime(0.1f)
            .transitionPredicate(
                    (model, entity, limbSwing, limbSwingAmount, ageInTicks, animTime, netHeadYaw, headPitch, deltaTime)
                            -> ((PracticalPet)entity).hasTarget()
            )
            .build();

    public static final AnimationController DUCK_IDLE_FLAP = BinaryAnimationControllerBuilder.start("duck_idle_flap")
            .otherStateAnimations(new KeyframeAnimationReference("idle_flap"))
            .blendTime(0.2f)
            .transitionPredicate(
                    (model, entity, limbSwing, limbSwingAmount, ageInTicks, animTime, netHeadYaw, headPitch, deltaTime)
                            -> ((AbstractDuck)entity).isIdleFlapping()
            )
            .build();

}