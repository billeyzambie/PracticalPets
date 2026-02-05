package billeyzambie.practicalpets.misc;

import billeyzambie.animationcontrollers.*;
import billeyzambie.practicalpets.entity.DancingEntity;
import billeyzambie.practicalpets.entity.PracticalPet;
import billeyzambie.practicalpets.entity.dinosaur.AbstractDuck;
import billeyzambie.practicalpets.entity.dinosaur.BananaDuck;
import billeyzambie.practicalpets.entity.otherpet.GiraffeCat;
import billeyzambie.practicalpets.entity.otherpet.Rat;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;

import java.util.List;

public class PPAnimationControllers {

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

    public static final float MIN_LIMB_SWING_MOVING = 0.01f;

    public static final AnimationController SIMPLE_SIT = BinaryAnimationControllerBuilder.start("simple_sit")
            .otherStateAnimations(new KeyframeAnimationReference("sit"))
            .blendTime(0.1f)
            .transitionPredicate(
                    (model, entity, limbSwing, limbSwingAmount, ageInTicks, animTime, netHeadYaw, headPitch, deltaTime)
                            -> ((TamableAnimal) entity).isInSittingPose()
            )
            .build();

    public static final AnimationController SIMPLE_DANCE = BinaryAnimationControllerBuilder.start("simple_dance")
            .otherStateAnimations(new KeyframeAnimationReference("dance"))
            .blendTime(0.2f)
            .transitionPredicate(
                    (model, entity, limbSwing, limbSwingAmount, ageInTicks, animTime, netHeadYaw, headPitch, deltaTime)
                            -> entity instanceof DancingEntity dancing && dancing.isDancing()
            )
            .build();

    public static final AnimationController SIMPLE_ATTACK = new AnimationController("simple_attack", List.of(
            new AnimationController.State(
                    Animatable.NO_ANIMATIONS,
                    List.of(
                            AnimationController.TransitionPredicate.NEVER,
                            (model, entity, limbSwing, limbSwingAmount, ageInTicks, animTime, netHeadYaw, headPitch, deltaTime)
                                    -> ((LivingEntity) entity).attackAnim > 0
                    ),
                    0
            ),
            new AnimationController.State(
                    List.of(new KeyframeAnimationReference(
                            "attack"
                    )),
                    List.of(
                            (model, entity, limbSwing, limbSwingAmount, ageInTicks, animTime, netHeadYaw, headPitch, deltaTime)
                                    -> ((LivingEntity) entity).attackAnim == 0
                    ),
                    0.1f
            )
    ));

    public static final AnimationController BANANA_DUCK_FLAP_AND_IF_ANGRY = BinaryAnimationControllerBuilder.start("banana_duck_flap_and_if_not_angry")
            .otherStateAnimations(new OtherAnimationReference("flap"))
            .blendTime(0.1f)
            .transitionPredicate(
                    (model, entity, limbSwing, limbSwingAmount, ageInTicks, animTime, netHeadYaw, headPitch, deltaTime)
                            -> entity instanceof BananaDuck bananaDuck && (bananaDuck.duckIsFlapping() || bananaDuck.hasTarget())
            )
            .build();

    public static final AnimationController ON_GROUND_AND_NOT_ANGRY = BinaryAnimationControllerBuilder.start("on_ground_and_not_angry")
            .otherStateAnimations(new KeyframeAnimationReference("on_ground"))
            .blendTime(0.2f)
            .transitionPredicate(
                    (model, entity, limbSwing, limbSwingAmount, ageInTicks, animTime, netHeadYaw, headPitch, deltaTime)
                            -> entity instanceof PracticalPet pet && pet.onGround() && !pet.hasTarget()
            )
            .build();

    public static final AnimationController SIMPLE_ANGRY = BinaryAnimationControllerBuilder.start("simple_angry")
            .otherStateAnimations(new KeyframeAnimationReference("angry"))
            .blendTime(0.1f)
            .transitionPredicate(
                    (model, entity, limbSwing, limbSwingAmount, ageInTicks, animTime, netHeadYaw, headPitch, deltaTime)
                            -> ((PracticalPet) entity).hasTarget()
            )
            .build();

    public static final AnimationController DUCK_IDLE_FLAP = BinaryAnimationControllerBuilder.start("duck_idle_flap")
            .otherStateAnimations(new KeyframeAnimationReference("idle_flap"))
            .blendTime(0.2f)
            .transitionPredicate(
                    (model, entity, limbSwing, limbSwingAmount, ageInTicks, animTime, netHeadYaw, headPitch, deltaTime)
                            -> ((AbstractDuck) entity).isIdleFlapping()
            )
            .build();

    public static final AnimationController DUCK_WATER_WAVE = BinaryAnimationControllerBuilder.start("duck_water_wave")
            .otherStateAnimations(new OtherAnimationReference("water_wave"))
            .blendTime(0.2f)
            .transitionPredicate(
                    (model, entity, limbSwing, limbSwingAmount, ageInTicks, animTime, netHeadYaw, headPitch, deltaTime)
                            -> entity.isInWater()
            )
            .build();


    public static final AnimationController RAT_POSE = new AnimationController("rat_pose", List.of(
            //default
            new AnimationController.State(
                    Animatable.NO_ANIMATIONS,
                    List.of(
                            AnimationController.TransitionPredicate.NEVER,
                            (model, entity, limbSwing, limbSwingAmount, ageInTicks, animTime, netHeadYaw, headPitch, deltaTime)
                                    -> entity instanceof Rat rat
                                    && rat.isInterested()
                                    && limbSwingAmount > MIN_LIMB_SWING_MOVING,
                            AnimationController.TransitionPredicate.NEVER,
                            (model, entity, limbSwing, limbSwingAmount, ageInTicks, animTime, netHeadYaw, headPitch, deltaTime)
                                    -> entity instanceof Rat rat
                                    && rat.isInSittingPose()
                                    && rat.getRandom().nextBoolean(),
                            (model, entity, limbSwing, limbSwingAmount, ageInTicks, animTime, netHeadYaw, headPitch, deltaTime)
                                    -> entity instanceof Rat rat
                                    && rat.isInSittingPose(),
                            (model, entity, limbSwing, limbSwingAmount, ageInTicks, animTime, netHeadYaw, headPitch, deltaTime)
                                    -> entity instanceof Rat rat
                                    && rat.isCooking()
                    ),
                    0.1f
            ),
            //waiting to use sit2 after walking tempted
            new AnimationController.State(
                    Animatable.NO_ANIMATIONS,
                    List.of(
                            (model, entity, limbSwing, limbSwingAmount, ageInTicks, animTime, netHeadYaw, headPitch, deltaTime)
                                    -> entity instanceof Rat rat
                                    && !rat.isInterested(),
                            AnimationController.TransitionPredicate.NEVER,
                            (model, entity, limbSwing, limbSwingAmount, ageInTicks, animTime, netHeadYaw, headPitch, deltaTime)
                                    -> entity instanceof Rat rat
                                    && limbSwingAmount <= MIN_LIMB_SWING_MOVING,
                            (model, entity, limbSwing, limbSwingAmount, ageInTicks, animTime, netHeadYaw, headPitch, deltaTime)
                                    -> entity instanceof Rat rat
                                    && rat.isInSittingPose()
                                    && rat.getRandom().nextBoolean(),
                            (model, entity, limbSwing, limbSwingAmount, ageInTicks, animTime, netHeadYaw, headPitch, deltaTime)
                                    -> entity instanceof Rat rat
                                    && rat.isInSittingPose(),
                            (model, entity, limbSwing, limbSwingAmount, ageInTicks, animTime, netHeadYaw, headPitch, deltaTime)
                                    -> entity instanceof Rat rat
                                    && rat.isCooking()
                    ),
                    0.1f
            ),
            //using sit2 after walking tempted
            new AnimationController.State(
                    List.of(new KeyframeAnimationReference(
                            "sit2"
                    )),
                    List.of(
                            (model, entity, limbSwing, limbSwingAmount, ageInTicks, animTime, netHeadYaw, headPitch, deltaTime)
                                    -> entity instanceof Rat rat
                                    && limbSwingAmount > MIN_LIMB_SWING_MOVING,
                            AnimationController.TransitionPredicate.NEVER,
                            AnimationController.TransitionPredicate.NEVER,
                            (model, entity, limbSwing, limbSwingAmount, ageInTicks, animTime, netHeadYaw, headPitch, deltaTime)
                                    -> entity instanceof Rat rat
                                    && rat.isInSittingPose(),
                            AnimationController.TransitionPredicate.NEVER,
                            (model, entity, limbSwing, limbSwingAmount, ageInTicks, animTime, netHeadYaw, headPitch, deltaTime)
                                    -> entity instanceof Rat rat
                                    && rat.isCooking()
                    ),
                    0.2f
            ),
            //commanded to sit
            new AnimationController.State(
                    List.of(new KeyframeAnimationReference(
                            "sit"
                    )),
                    List.of(
                            (model, entity, limbSwing, limbSwingAmount, ageInTicks, animTime, netHeadYaw, headPitch, deltaTime)
                                    -> entity instanceof Rat rat
                                    && !rat.isInSittingPose(),
                            AnimationController.TransitionPredicate.NEVER,
                            AnimationController.TransitionPredicate.NEVER,
                            AnimationController.TransitionPredicate.NEVER,
                            AnimationController.TransitionPredicate.NEVER,
                            (model, entity, limbSwing, limbSwingAmount, ageInTicks, animTime, netHeadYaw, headPitch, deltaTime)
                                    -> entity instanceof Rat rat
                                    && rat.isCooking()
                    ),
                    0.1f
            ),
            //commanded to sit2
            new AnimationController.State(
                    List.of(new KeyframeAnimationReference(
                            "sit2"
                    )),
                    List.of(
                            (model, entity, limbSwing, limbSwingAmount, ageInTicks, animTime, netHeadYaw, headPitch, deltaTime)
                                    -> entity instanceof Rat rat
                                    && !rat.isInSittingPose(),
                            AnimationController.TransitionPredicate.NEVER,
                            AnimationController.TransitionPredicate.NEVER,
                            AnimationController.TransitionPredicate.NEVER,
                            AnimationController.TransitionPredicate.NEVER,
                            (model, entity, limbSwing, limbSwingAmount, ageInTicks, animTime, netHeadYaw, headPitch, deltaTime)
                                    -> entity instanceof Rat rat
                                    && rat.isCooking()
                    ),
                    0.2f
            ),
            //cooking
            new AnimationController.State(
                    List.of(new KeyframeAnimationReference(
                            "cook"
                    )),
                    List.of(
                            (model, entity, limbSwing, limbSwingAmount, ageInTicks, animTime, netHeadYaw, headPitch, deltaTime)
                                    -> entity instanceof Rat rat
                                    && !rat.isCooking()
                                    && !rat.isInSittingPose(),
                            AnimationController.TransitionPredicate.NEVER,
                            AnimationController.TransitionPredicate.NEVER,
                            (model, entity, limbSwing, limbSwingAmount, ageInTicks, animTime, netHeadYaw, headPitch, deltaTime)
                                    -> entity instanceof Rat rat
                                    && !rat.isCooking()
                                    && rat.getRandom().nextBoolean(),
                            (model, entity, limbSwing, limbSwingAmount, ageInTicks, animTime, netHeadYaw, headPitch, deltaTime)
                                    -> entity instanceof Rat rat
                                    && !rat.isCooking(),
                            AnimationController.TransitionPredicate.NEVER
                    ),
                    0.2f
            )
    ));

    public static final AnimationController SIT_OR_DANCE = new AnimationController("sit_or_dance", List.of(
            new AnimationController.State(
                    Animatable.NO_ANIMATIONS,
                    List.of(
                            AnimationController.TransitionPredicate.NEVER,
                            (model, entity, limbSwing, limbSwingAmount, ageInTicks, animTime, netHeadYaw, headPitch, deltaTime)
                                    -> entity instanceof DancingEntity dancingEntity && dancingEntity.isDancing(),
                            (model, entity, limbSwing, limbSwingAmount, ageInTicks, animTime, netHeadYaw, headPitch, deltaTime)
                                    -> entity instanceof TamableAnimal tamableAnimal && tamableAnimal.isInSittingPose()
                    ),
                    0.1f
            ),
            new AnimationController.State(
                    List.of(new KeyframeAnimationReference(
                            "dance"
                    )),
                    List.of(
                            (model, entity, limbSwing, limbSwingAmount, ageInTicks, animTime, netHeadYaw, headPitch, deltaTime)
                                    -> entity instanceof DancingEntity dancingEntity && !dancingEntity.isDancing(),
                            AnimationController.TransitionPredicate.NEVER,
                            AnimationController.TransitionPredicate.NEVER
                    ),
                    0.2f
            ),
            new AnimationController.State(
                    List.of(new KeyframeAnimationReference(
                            "sit"
                    )),
                    List.of(
                            (model, entity, limbSwing, limbSwingAmount, ageInTicks, animTime, netHeadYaw, headPitch, deltaTime)
                                    -> entity instanceof TamableAnimal tamableAnimal && !tamableAnimal.isInSittingPose(),
                            (model, entity, limbSwing, limbSwingAmount, ageInTicks, animTime, netHeadYaw, headPitch, deltaTime)
                                    -> entity instanceof DancingEntity dancingEntity && dancingEntity.isDancing(),
                            AnimationController.TransitionPredicate.NEVER
                    ),
                    0.1f
            )
    ));

    public static final AnimationController GIRAFFE_CAT_SNEAK_OR_SIT = new AnimationController("giraffe_cat_sneak_or_sit", List.of(
            new AnimationController.State(
                    List.of(new KeyframeAnimationReference(
                            "walk_pose"
                    )),
                    List.of(
                            AnimationController.TransitionPredicate.NEVER,
                            (model, entity, limbSwing, limbSwingAmount, ageInTicks, animTime, netHeadYaw, headPitch, deltaTime)
                                    -> entity instanceof TamableAnimal tamableAnimal
                                    && tamableAnimal.isInSittingPose(),
                            (model, entity, limbSwing, limbSwingAmount, ageInTicks, animTime, netHeadYaw, headPitch, deltaTime)
                                    -> entity.isCrouching()
                    ),
                    0.1f
            ),
            new AnimationController.State(
                    List.of(new KeyframeAnimationReference(
                            "sit"
                    )),
                    List.of(
                            (model, entity, limbSwing, limbSwingAmount, ageInTicks, animTime, netHeadYaw, headPitch, deltaTime)
                                    -> entity instanceof TamableAnimal tamableAnimal
                                    && !tamableAnimal.isInSittingPose() && !tamableAnimal.isCrouching(),
                            AnimationController.TransitionPredicate.NEVER,
                            (model, entity, limbSwing, limbSwingAmount, ageInTicks, animTime, netHeadYaw, headPitch, deltaTime)
                                    -> entity instanceof TamableAnimal tamableAnimal
                                    && !tamableAnimal.isInSittingPose()
                    ),
                    0.1f
            ),
            new AnimationController.State(
                    List.of(new KeyframeAnimationReference(
                            "sneak_pose"
                    )),
                    List.of(
                            (model, entity, limbSwing, limbSwingAmount, ageInTicks, animTime, netHeadYaw, headPitch, deltaTime)
                                    -> entity instanceof TamableAnimal tamableAnimal
                                    && !tamableAnimal.isInSittingPose() && !tamableAnimal.isCrouching(),
                            (model, entity, limbSwing, limbSwingAmount, ageInTicks, animTime, netHeadYaw, headPitch, deltaTime)
                                    -> !entity.isCrouching(),
                            AnimationController.TransitionPredicate.NEVER
                    ),
                    0.1f
            )
    ));

    public static final AnimationController GIRAFFE_CAT_BASE = new AnimationController("giraffe_cat_ability", List.of(
            new AnimationController.State(
                    List.of(
                            new OtherAnimationReference("controller.giraffe_cat_sneak_or_sit"),
                            new OtherAnimationReference("controller.giraffe_cat_bend_over")
                    ),
                    List.of(
                            AnimationController.TransitionPredicate.NEVER,
                            (model, entity, limbSwing, limbSwingAmount, ageInTicks, animTime, netHeadYaw, headPitch, deltaTime)
                                    -> entity instanceof GiraffeCat giraffeCat
                                    && giraffeCat.isYeeting(),
                            (model, entity, limbSwing, limbSwingAmount, ageInTicks, animTime, netHeadYaw, headPitch, deltaTime)
                                    -> entity instanceof GiraffeCat giraffeCat
                                    && giraffeCat.isDigging(),
                            (model, entity, limbSwing, limbSwingAmount, ageInTicks, animTime, netHeadYaw, headPitch, deltaTime)
                                    -> entity instanceof GiraffeCat giraffeCat
                                    && giraffeCat.isLadder()
                    ),
                    0.1f
            ),
            new AnimationController.State(
                    List.of(
                            new OtherAnimationReference("controller.giraffe_cat_sneak_or_sit"),
                            new KeyframeAnimationReference("yeet")
                    ),
                    List.of(
                            (model, entity, limbSwing, limbSwingAmount, ageInTicks, animTime, netHeadYaw, headPitch, deltaTime)
                                    -> entity instanceof GiraffeCat giraffeCat
                                    && !giraffeCat.isYeeting(),
                            AnimationController.TransitionPredicate.NEVER,
                            AnimationController.TransitionPredicate.NEVER,
                            AnimationController.TransitionPredicate.NEVER
                    ),
                    0.1f
            ),
            new AnimationController.State(
                    List.of(
                            new KeyframeAnimationReference("dig")
                    ),
                    List.of(
                            AnimationController.TransitionPredicate.NEVER,
                            (model, entity, limbSwing, limbSwingAmount, ageInTicks, animTime, netHeadYaw, headPitch, deltaTime)
                                    -> entity instanceof GiraffeCat giraffeCat
                                    && !giraffeCat.isDigging(),
                            AnimationController.TransitionPredicate.NEVER,
                            AnimationController.TransitionPredicate.NEVER
                    ),
                    0.1f
            ),
            new AnimationController.State(
                    List.of(
                            new KeyframeAnimationReference("ladder")
                    ),
                    List.of(
                            AnimationController.TransitionPredicate.NEVER,
                            AnimationController.TransitionPredicate.NEVER,
                            (model, entity, limbSwing, limbSwingAmount, ageInTicks, animTime, netHeadYaw, headPitch, deltaTime)
                                    -> entity instanceof GiraffeCat giraffeCat
                                    && !giraffeCat.isLadder(),
                            AnimationController.TransitionPredicate.NEVER
                    ),
                    0.1f
            )
    ));

    public static final AnimationController GIRAFFE_CAT_BEND_OVER = BinaryAnimationControllerBuilder
            .start("giraffe_cat_bend_over")
            .toOtherBlendTime(0.1f)
            .toDefaultBlendTime(0.2f)
            .transitionPredicate((model, entity, limbSwing, limbSwingAmount, ageInTicks, animTime, netHeadYaw, headPitch, deltaTime)
                    -> entity instanceof GiraffeCat giraffeCat
                    && giraffeCat.shouldBendOver()
            ).otherStateAnimations(new KeyframeAnimationReference("bend_over"))
            .build();

    public static final BlendValueController ON_GROUND_BLEND = new BlendValueController("on_ground", 0.2f, (model, entity, limbSwing, limbSwingAmount, ageInTicks, animTime, netHeadYaw, headPitch, deltaTime)
            -> entity.onGround()
    );

}