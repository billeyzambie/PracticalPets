package billeyzambie.practicalpets.client.animation;// Save this class in your mod and generate all required imports

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;

/**
 * Made with Blockbench 4.12.5
 * Exported for Minecraft version 1.19 or later with Mojang mappings
 * @author billeyzambie
 */
public class PracticalPetAnimation {
	public static final AnimationDefinition HatSquish = AnimationDefinition.Builder.withLength(0.3704F)
		.addAnimation("hat", new AnimationChannel(AnimationChannel.Targets.SCALE,
			new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.0926F, KeyframeAnimations.scaleVec(1.1F, 1.0F, 0.8F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.1852F, KeyframeAnimations.scaleVec(1.2F, 0.8F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.2778F, KeyframeAnimations.scaleVec(0.8F, 1.1F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.3704F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.build();
}