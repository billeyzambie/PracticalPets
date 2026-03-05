package billeyzambie.practicalpets.client.model.entity.otherpet;// Made with Blockbench 5.0.7
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import billeyzambie.animationcontrollers.Animatable;
import billeyzambie.animationcontrollers.PracticalPetModel;
import billeyzambie.practicalpets.entity.otherpet.GiraffeCat;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;

public class GiraffeCatNeckPieceModel extends PracticalPetModel<GiraffeCat> {
	public static final float INFLATE_VALUE = 0.02F;
	private final ModelPart neck;

	public GiraffeCatNeckPieceModel(ModelPart root) {
		this.neck = root.getChild("neck");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition neck = partdefinition.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(23, 3).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 3.0F, new CubeDeformation(INFLATE_VALUE))
				.texOffs(53, 0).addBox(-0.01F, 3.0F, 1.0F, 0.0F, 9.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(53, 3).addBox(-0.01F, 0.0F, 1.0F, 0.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 32);
	}

	@Override
	public ModelPart root() {
		return neck;
	}

	@Override
	public HashMap<String, AnimationDefinition> getKeyframeAnimationHashMap() {
		return null;
	}

	@Override
	public HashMap<String, Animatable> getOtherAnimationHashMap() {
		return null;
	}

	@Override
	public List<ModelPart> pathToBowtie() {
		return List.of();
	}

	@Override
	public List<ModelPart> pathToHat() {
		return List.of();
	}

	@Override
	public List<ModelPart> pathToBackpack() {
		return List.of();
	}

	@Override
	public @Nullable ModelPart head() {
		return null;
	}

	@Override
	public void setupAnim(GiraffeCat p_102618_, float p_102619_, float p_102620_, float p_102621_, float p_102622_, float p_102623_) {

	}
}