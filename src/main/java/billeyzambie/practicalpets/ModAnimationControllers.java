package billeyzambie.practicalpets;

import billeyzambie.animationcontrollers.AnimationController;
import billeyzambie.animationcontrollers.BinaryAnimationControllerBuilder;
import billeyzambie.animationcontrollers.KeyframeAnimationReference;
import billeyzambie.animationcontrollers.MathAnimationReference;
import billeyzambie.practicalpets.entity.LandPracticalPet;
import net.minecraft.world.entity.Mob;
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

    public static final AnimationController FLAP_AND_IF_ANGRY = BinaryAnimationControllerBuilder.start("simple_flap")
            .otherStateAnimations(new MathAnimationReference("flap"))
            .blendTime(0.1f)
            .transitionPredicate(
                    (model, entity, limbSwing, limbSwingAmount, ageInTicks, animTime, netHeadYaw, headPitch, deltaTime)
                            -> !entity.onGround() || ((LandPracticalPet)entity).hasTarget()
            )
            .build();

    public static final AnimationController SIMPLE_ANGRY = BinaryAnimationControllerBuilder.start("simple_angry")
            .otherStateAnimations(new KeyframeAnimationReference("angry"))
            .blendTime(0.1f)
            .transitionPredicate(
                    (model, entity, limbSwing, limbSwingAmount, ageInTicks, animTime, netHeadYaw, headPitch, deltaTime)
                            -> ((LandPracticalPet)entity).hasTarget()
            )
            .build();

}