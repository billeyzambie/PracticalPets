package billeyzambie.animationcontrollers;

import net.minecraft.world.entity.Entity;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

public interface Animatable {
    public static final Vector3f ANIMATION_VECTOR_CACHE = new Vector3f();

    public static List<Animatable> NO_ANIMATIONS = new ArrayList<>();

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
    );
}