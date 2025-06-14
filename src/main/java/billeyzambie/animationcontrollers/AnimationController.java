package billeyzambie.animationcontrollers;

import net.minecraft.world.entity.Entity;

import java.util.List;

public class AnimationController implements Animatable {

    @FunctionalInterface
    public interface TransitionPredicate {
        TransitionPredicate ALWAYS_FALSE = (model, entity, limbSwing, limbSwingAmount, ageInTicks, animTime, netHeadYaw, headPitch, deltaTime) -> false;

        boolean test(
                PracticalPetModel<Entity> model,
                Entity entity,
                float limbSwing,
                float limbSwingAmount,
                float ageInTicks,
                float animTime,
                float netHeadYaw,
                float headPitch,
                float blendWeight
        );

        default TransitionPredicate negate() {
            return (model, entity, limbSwing, limbSwingAmount, ageInTicks, animTime, netHeadYaw, headPitch, blendWeight)
                    -> !this.test(model, entity, limbSwing, limbSwingAmount, ageInTicks, animTime, netHeadYaw, headPitch, blendWeight);
        }
    }

    public static class State {
        List<Animatable> animations;
        List<TransitionPredicate> transitions;
        float blendOutTime;

        public State(List<Animatable> animations, List<TransitionPredicate> transitions, float blendOutTime) {
            this.animations = animations;
            this.transitions = transitions;
            this.blendOutTime = blendOutTime;
        }

        public State(List<Animatable> animations, List<TransitionPredicate> transitions) {
            this(animations, transitions, 0);
        }
    }

    List<State> states;
    String name;

    public AnimationController(String name, List<State> states) {
        this.name = name;
        this.states = states;
    }

    @Override
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
    ) {
        if (entity instanceof ACEntity acEntity) {
            ACData acData;
            ACData entityACData = acEntity.getACData().get(name);
            if (entityACData == null)
                acEntity.getACData().put(name, acData = new ACData(ageInTicks));
            else
                acData = entityACData;
            State state = states.get(acData.getStateIndex());
            State previousState = states.get(acData.getPreviousStateIndex());

            float stateTime = ageInTicks - acData.getTimeStateStarted();
            float previousStateTime = ageInTicks - acData.getTimePreviousStateStarted();

            float transitionBlendFactor =
                    previousState.blendOutTime == 0 ? 1 :
                            (Math.min(1, (stateTime) / (20 * previousState.blendOutTime)));

            if (transitionBlendFactor != 0)
                state.animations.forEach(a -> a.animate(
                        (PracticalPetModel<Entity>) model,
                        entity,
                        limbSwing,
                        limbSwingAmount,
                        ageInTicks,
                        stateTime,
                        netHeadYaw,
                        headPitch,
                        blendWeight * transitionBlendFactor
                ));

            if (transitionBlendFactor != 1)
                previousState.animations.forEach(a -> a.animate(
                        (PracticalPetModel<Entity>) model,
                        entity,
                        limbSwing,
                        limbSwingAmount,
                        ageInTicks,
                        previousStateTime,
                        netHeadYaw,
                        headPitch,
                        blendWeight * (1 - transitionBlendFactor)
                ));


            List<TransitionPredicate> transitions = state.transitions;
            for (int i = 0; i < transitions.size(); i++) {
                TransitionPredicate transitionPredicate = transitions.get(i);
                if (
                        transitionPredicate != null
                                && transitionPredicate.test(
                                (PracticalPetModel<Entity>) model,
                                entity,
                                limbSwing,
                                limbSwingAmount,
                                ageInTicks,
                                stateTime,
                                netHeadYaw,
                                headPitch,
                                blendWeight
                        )
                ) {
                    acData.setStateIndex(i, ageInTicks);
                }

            }

        }


    }
}
