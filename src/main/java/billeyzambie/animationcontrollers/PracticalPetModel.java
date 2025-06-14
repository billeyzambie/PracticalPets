package billeyzambie.animationcontrollers;

import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.Entity;

import java.util.HashMap;
import java.util.List;

public abstract class PracticalPetModel<T extends Entity> extends HierarchicalModel<T> {
    public abstract HashMap<String, AnimationDefinition> getKeyframeAnimationHashMap();
    public abstract HashMap<String, MathAnimationDefinition> getMathAnimationHashMap();

    public abstract List<ModelPart> pathToBowtie();
    public abstract List<ModelPart> pathToHat();
}