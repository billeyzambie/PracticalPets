package billeyzambie.animationcontrollers;

import net.minecraft.world.entity.Entity;
import org.checkerframework.checker.units.qual.A;

import java.util.List;

public class AnimationController implements Animatable {

    @FunctionalInterface
    public interface TransitionPredicate<T extends Entity & ACEntity> {
        TransitionPredicate<?> NEVER = (model, entity, limbSwing, limbSwingAmount, ageInTicks, animTime, netHeadYaw, headPitch, blendWeight) -> false;

        boolean test(
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

        default TransitionPredicate<T> negate() {
            return (model, entity, limbSwing, limbSwingAmount, ageInTicks, animTime, netHeadYaw, headPitch, blendWeight)
                    -> !this.test(model, entity, limbSwing, limbSwingAmount, ageInTicks, animTime, netHeadYaw, headPitch, blendWeight);
        }
    }

    @FunctionalInterface
    public interface TransitionAction<T extends Entity & ACEntity> {
        void run(
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

    public static class State {
        List<Animatable> animations;
        List<TransitionPredicate<?>> transitions;
        TransitionAction onEntry;
        TransitionAction onExit;
        float blendOutTime;

        public State(List<Animatable> animations, List<TransitionPredicate<?>> transitions,
                     float blendOutTime, TransitionAction<?> onEntry, TransitionAction<?> onExit) {
            this.animations = animations;
            this.transitions = transitions;
            this.blendOutTime = blendOutTime;
            this.onEntry = onEntry;
            this.onExit = onExit;
        }

        public State(List<Animatable> animations, List<TransitionPredicate<?>> transitions, float blendOutTime) {
            this(animations, transitions, blendOutTime, null, null);
        }

        public State(List<Animatable> animations, List<TransitionPredicate<?>> transitions) {
            this(animations, transitions, 0);
        }
    }

    List<State> states;
    String name;
    private final boolean dontTransitionAt0animTime;

    public AnimationController(String name, List<State> states, boolean dontTransitionAt0animTime) {
        this.name = name;
        this.states = states;
        this.dontTransitionAt0animTime = dontTransitionAt0animTime;
    }

    public AnimationController(String name, List<State> states) {
        this(name, states, false);
    }

    @Override
    public <T extends Entity & ACEntity> void play(
            PracticalPetModel<T> modelT,
            T entity,
            float limbSwing,
            float limbSwingAmount,
            float ageInTicks,
            float animTime,
            float netHeadYaw,
            float headPitch,
            float blendWeight
    ) {
            ACData acData;
            ACData entityACData = entity.getACData().get(name);
            boolean firstTime = false;
            if (entityACData == null) {
                entity.getACData().put(name, acData = new ACData(ageInTicks));
                firstTime = true;
            } else {
                acData = entityACData;
            }

            State state = states.get(acData.getStateIndex());
            State previousState = states.get(acData.getPreviousStateIndex());

            if (firstTime && state.onEntry != null) {
                state.onEntry.run(
                        modelT, entity,
                        limbSwing, limbSwingAmount,
                        ageInTicks,
                        0.0F,
                        netHeadYaw, headPitch,
                        blendWeight
                );
            }

            float stateTime = ageInTicks - acData.getTimeStateStarted();
            float previousStateTime = ageInTicks - acData.getTimePreviousStateStarted();

            float transitionBlendFactor =
                    previousState.blendOutTime == 0 ? 1.0F :
                            Math.min(1.0F, (stateTime) / (20.0F * previousState.blendOutTime));

            if (transitionBlendFactor != 0.0F) {
                for (Animatable a : state.animations) {
                    a.play(
                            modelT,
                            entity,
                            limbSwing,
                            limbSwingAmount,
                            ageInTicks,
                            stateTime,
                            netHeadYaw,
                            headPitch,
                            blendWeight * transitionBlendFactor
                    );
                }
            }

            if (transitionBlendFactor != 1.0F) {
                for (Animatable a : previousState.animations) {
                    a.play(
                            modelT,
                            entity,
                            limbSwing,
                            limbSwingAmount,
                            ageInTicks,
                            previousStateTime,
                            netHeadYaw,
                            headPitch,
                            blendWeight * (1.0F - transitionBlendFactor)
                    );
                }
            }

            List<TransitionPredicate<?>> transitions = state.transitions;
            for (int i = 0; i < transitions.size(); i++) {
                TransitionPredicate predicate = transitions.get(i);
                if (predicate != null && predicate.test(
                        modelT,
                        entity,
                        limbSwing,
                        limbSwingAmount,
                        ageInTicks,
                        stateTime,
                        netHeadYaw,
                        headPitch,
                        blendWeight
                )) {
                    if (state.onExit != null) {
                        state.onExit.run(
                                modelT, entity,
                                limbSwing, limbSwingAmount,
                                ageInTicks,
                                stateTime,
                                netHeadYaw, headPitch,
                                blendWeight
                        );
                    }

                    State nextState = states.get(i);
                    if (nextState.onEntry != null) {
                        nextState.onEntry.run(
                                modelT, entity,
                                limbSwing, limbSwingAmount,
                                ageInTicks,
                                0.0F,
                                netHeadYaw, headPitch,
                                blendWeight
                        );
                    }


                    acData.setStateIndex(
                            i,
                            ageInTicks,
                            this.dontTransitionAt0animTime ? nextState.blendOutTime * 20 : 0
                    );


                    break;
                }
            }
    }
}