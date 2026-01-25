package billeyzambie.practicalpets.client.animation.dinosaur;// Save this class in your mod and generate all required imports

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;

/**
 * Made with Blockbench 5.0.7
 * Exported for Minecraft version 1.19 or later with Mojang mappings
 * @author Author
 */
public class PigeonAnimations {
	public static final AnimationDefinition walk = AnimationDefinition.Builder.withLength(0.5F).looping()
			.addAnimation("body_walking", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.125F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 5.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.25F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.375F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -5.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("body_walking", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -0.575F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.0833F, KeyframeAnimations.posVec(0.0F, 0.23F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.125F, KeyframeAnimations.posVec(0.0F, 0.375F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.1667F, KeyframeAnimations.posVec(0.0F, 0.23F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.25F, KeyframeAnimations.posVec(0.0F, -0.575F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.3333F, KeyframeAnimations.posVec(0.0F, 0.23F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.375F, KeyframeAnimations.posVec(0.0F, 0.375F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.4167F, KeyframeAnimations.posVec(0.0F, 0.23F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, -0.575F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("necc", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, -0.25F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.25F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.25F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 0.0F, -0.25F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("head", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, -0.25F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.25F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.25F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 0.0F, -0.25F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("leg0", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(45.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.125F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.25F, KeyframeAnimations.degreeVec(-45.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.375F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(45.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("leg0", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.125F, KeyframeAnimations.posVec(0.0F, 1.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.25F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("foot0", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-60.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.125F, KeyframeAnimations.degreeVec(-45.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.25F, KeyframeAnimations.degreeVec(-12.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.3333F, KeyframeAnimations.degreeVec(15.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.375F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.4167F, KeyframeAnimations.degreeVec(-20.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(-60.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("leg1", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-45.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.125F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.25F, KeyframeAnimations.degreeVec(45.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.375F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(-45.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("leg1", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.25F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.375F, KeyframeAnimations.posVec(0.0F, 1.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("foot1", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-12.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.0833F, KeyframeAnimations.degreeVec(15.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.125F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.1667F, KeyframeAnimations.degreeVec(-20.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.25F, KeyframeAnimations.degreeVec(-60.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.375F, KeyframeAnimations.degreeVec(-45.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(-12.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.build();

	public static final AnimationDefinition sleep_particle = AnimationDefinition.Builder.withLength(0.5F).looping()
		
		.build();

	public static final AnimationDefinition fall = AnimationDefinition.Builder.withLength(0.0F).looping()
		.addAnimation("wing0", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("wing1", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
		))
		.build();

	public static final AnimationDefinition fly = AnimationDefinition.Builder.withLength(0.0F).looping()
		.addAnimation("body_flying", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
		))
		.build();

	public static final AnimationDefinition sit = AnimationDefinition.Builder.withLength(0.0F).looping()
		.addAnimation("bodynolegs", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -2.99F, 0.0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("wing1", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("wing1", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("tail", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(-10.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("wing0", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("wing0", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("leg0", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 2.0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("leg1", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 2.0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("body_walking", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("body1", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(50.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("body1", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("necc", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -2.0F, -1.0F), AnimationChannel.Interpolations.LINEAR)
		))
		.build();

	public static final AnimationDefinition sleep = AnimationDefinition.Builder.withLength(0.0F).looping()
		.addAnimation("bodynolegs", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -2.99F, 0.0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("wing1", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("wing1", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("tail", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(-10.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("wing0", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("wing0", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("leg0", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 2.0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("leg1", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 2.0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("body_walking", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("body1", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(50.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("body1", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("necc", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(15.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("necc", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -2.0F, -1.0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("lower_eyelids", new AnimationChannel(AnimationChannel.Targets.SCALE, 
			new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0F, 1.45F, 1.1F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("upper_eyelids", new AnimationChannel(AnimationChannel.Targets.SCALE, 
			new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0F, 1.375F, 1.1F), AnimationChannel.Interpolations.LINEAR)
		))
		.build();

	public static final AnimationDefinition eatfloor = AnimationDefinition.Builder.withLength(0.7F)
		.addAnimation("bodynolegs", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.1833F, KeyframeAnimations.degreeVec(30.15F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.2667F, KeyframeAnimations.degreeVec(45.12F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.3F, KeyframeAnimations.degreeVec(35.12F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.3833F, KeyframeAnimations.degreeVec(45.12F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.4667F, KeyframeAnimations.degreeVec(35.12F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.7F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("necc", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.1167F, KeyframeAnimations.degreeVec(35.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.3833F, KeyframeAnimations.degreeVec(60.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.7F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("necc", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0.2167F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.3833F, KeyframeAnimations.posVec(0.0F, 0.0F, 1.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.7F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("bone", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.3333F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.4167F, KeyframeAnimations.degreeVec(-20.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.7F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("bone2", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.3333F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.4167F, KeyframeAnimations.degreeVec(5.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.7F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.01F, 0.01F, 0.01F), AnimationChannel.Interpolations.LINEAR)
		))
		.build();

	public static final AnimationDefinition attack = AnimationDefinition.Builder.withLength(0.56F)
		.addAnimation("bodynolegs", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.1467F, KeyframeAnimations.degreeVec(30.15F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.2133F, KeyframeAnimations.degreeVec(45.12F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.24F, KeyframeAnimations.degreeVec(35.12F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.3067F, KeyframeAnimations.degreeVec(45.12F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.3733F, KeyframeAnimations.degreeVec(35.12F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.56F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("necc", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.0933F, KeyframeAnimations.degreeVec(35.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.3067F, KeyframeAnimations.degreeVec(60.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.56F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("necc", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0.1733F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.3067F, KeyframeAnimations.posVec(0.0F, 0.0F, 1.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.56F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("bone", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.2667F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.3333F, KeyframeAnimations.degreeVec(-20.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.56F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("bone2", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.2667F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.3333F, KeyframeAnimations.degreeVec(5.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.56F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.01F, 0.01F, 0.01F), AnimationChannel.Interpolations.LINEAR)
		))
		.build();

	public static final AnimationDefinition eat = AnimationDefinition.Builder.withLength(0.0F).looping()
		.addAnimation("bone", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(-5.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("bone2", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(12.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
		))
		.build();

	public static final AnimationDefinition hurt = AnimationDefinition.Builder.withLength(0.0F).looping()
		.addAnimation("necc", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(15.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("necc", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("body_walking", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
		))
		.build();

	public static final AnimationDefinition idle = AnimationDefinition.Builder.withLength(0.0F).looping()
		.addAnimation("wing1", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -3.4142F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("wing0", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 3.4142F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("necc", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.1F, 0.0F), AnimationChannel.Interpolations.LINEAR)
		))
		.build();

	public static final AnimationDefinition look = AnimationDefinition.Builder.withLength(0.0F).looping()
		.addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
		))
		.build();

	public static final AnimationDefinition riding = AnimationDefinition.Builder.withLength(0.0F).looping()
		.addAnimation("bodynolegs", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -1.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
		))
		.build();

}