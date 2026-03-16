package billeyzambie.practicalpets.client.animation.fish;// Save this class in your mod and generate all required imports

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;

/**
 * Made with Blockbench 5.0.7
 * Exported for Minecraft version 1.19 or later with Mojang mappings
 * @author billeyzambie
 */
public class PiranhaAnimations {
	public static final AnimationDefinition flop = AnimationDefinition.Builder.withLength(5.2F).looping()
			.addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -90.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.6F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -90.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.72F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 90.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(5.08F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 90.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(5.2F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -90.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(-0.25F, -0.25F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.6F, KeyframeAnimations.posVec(-0.25F, -0.25F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.64F, KeyframeAnimations.posVec(-0.08F, 0.75F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.72F, KeyframeAnimations.posVec(0.25F, -0.25F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(5.08F, KeyframeAnimations.posVec(0.25F, -0.25F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(5.12F, KeyframeAnimations.posVec(0.08F, 0.75F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(5.2F, KeyframeAnimations.posVec(-0.25F, -0.25F, 0.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_fin", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -37.5F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_fin", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 37.5F), AnimationChannel.Interpolations.LINEAR)
			))
			.build();
}