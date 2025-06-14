package billeyzambie.animationcontrollers;

import java.util.Arrays;
import java.util.List;

public class BinaryAnimationControllerBuilder {
    private final String name;

    private BinaryAnimationControllerBuilder(String name) {
        this.name = name;
    }

    public static BinaryAnimationControllerBuilder start(String name) {
        return new BinaryAnimationControllerBuilder(name);
    }

    private List<Animatable> defaultStateAnimations = Animatable.NO_ANIMATIONS;
    private List<Animatable> otherStateAnimations = Animatable.NO_ANIMATIONS;
    private float toOtherBlendTime = 0;
    private float toDefaultBlendTime = 0;
    private AnimationController.TransitionPredicate transitionPredicate;

    public BinaryAnimationControllerBuilder defaultStateAnimations(Animatable... anims) {
        defaultStateAnimations = Arrays.asList(anims);
        return this;
    }

    public BinaryAnimationControllerBuilder otherStateAnimations(Animatable... anims) {
        otherStateAnimations = Arrays.asList(anims);
        return this;
    }

    public BinaryAnimationControllerBuilder blendTime(float blendTime) {
        this.toDefaultBlendTime = this.toOtherBlendTime = blendTime;
        return this;
    }

    public BinaryAnimationControllerBuilder toOtherBlendTime(float toOtherBlendTime) {
        this.toOtherBlendTime = toOtherBlendTime;
        return this;
    }
    public BinaryAnimationControllerBuilder toDefaultBlendTime(float toDefaultBlendTime) {
        this.toDefaultBlendTime = toDefaultBlendTime;
        return this;
    }

    public BinaryAnimationControllerBuilder transitionPredicate(AnimationController.TransitionPredicate transitionPredicate) {
        this.transitionPredicate = transitionPredicate;
        return this;
    }

    public AnimationController build() {
        return new AnimationController(name, List.of(
                new AnimationController.State(
                        defaultStateAnimations,
                        List.of(
                                AnimationController.TransitionPredicate.ALWAYS_FALSE,
                                transitionPredicate
                        ),
                        toOtherBlendTime
                ),
                new AnimationController.State(
                        otherStateAnimations,
                        List.of(
                               transitionPredicate.negate()
                        ),
                        toDefaultBlendTime
                )
        ));
    }

}
