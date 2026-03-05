package billeyzambie.animationcontrollers;

import net.minecraft.world.entity.Entity;
import org.checkerframework.checker.units.qual.A;

public class BlendValueController {
    private final String name;
    private final float blendInTime;
    private final float blendOutTime;
    private final AnimationController.TransitionPredicate predicate;

    public BlendValueController(String name, float blendInTime, float blendOutTime, AnimationController.TransitionPredicate predicate) {
        this.name = name;
        this.blendInTime = blendInTime;
        this.blendOutTime = blendOutTime;
        this.predicate = predicate;
    }

    public BlendValueController(String name, float blendTime, AnimationController.TransitionPredicate predicate) {
        this(name, blendTime, blendTime, predicate);
    }

    public <T extends Entity & ACEntity> float calculate(PracticalPetModel<T> model, T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float animTime, float netHeadYaw, float headPitch, float blendWeight) {

            BVCData bvcData;
            BVCData entityBVCData = entity.getBVCData().get(name);
            if (entityBVCData == null) {
                entity.getBVCData().put(name, bvcData = new BVCData(ageInTicks));
            } else {
                bvcData = entityBVCData;
            }

            float stateTime = ageInTicks - bvcData.getTimeStateStarted();

            float result;
            if (bvcData.getIsInOtherState()) {
                result = blendInTime == 0 ? 1.0F :
                        Math.min(1.0F, (stateTime) / (20.0F * blendInTime));
            }
            else {
                result = blendInTime == 0 ? 0.0F :
                        1 - Math.min(1.0F, (stateTime) / (20.0F * blendOutTime));
            }

            if (!bvcData.getIsInOtherState() && predicate.test(
                    model,
                    entity,
                    limbSwing,
                    limbSwingAmount,
                    ageInTicks,
                    stateTime,
                    netHeadYaw,
                    headPitch,
                    blendWeight
            )) {
                bvcData.setState(true, ageInTicks);
            }
            else if (bvcData.getIsInOtherState() && predicate.negate().test(
                    model,
                    entity,
                    limbSwing,
                    limbSwingAmount,
                    ageInTicks,
                    stateTime,
                    netHeadYaw,
                    headPitch,
                    blendWeight
            )) {
                bvcData.setState(false, ageInTicks);
            }
            return result;

    }
}
