package com.mygdx.game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.mygdx.game.components.*;


public class RobotSystem extends IteratingSystem {
    private final ComponentMapper<MovementComponent> movementMapper;
    private final ComponentMapper<StateComponent> stateMapper;
    private final ComponentMapper<AnimationComponent> animationMapper;

    public RobotSystem() {
        super(Family.all(MovementComponent.class, RobotComponent.class, StateComponent.class, AnimationComponent.class).get());
        stateMapper = ComponentMapper.getFor(StateComponent.class);
        movementMapper  = ComponentMapper.getFor(MovementComponent.class);
        animationMapper  = ComponentMapper.getFor(AnimationComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        AnimationComponent animation = animationMapper.get(entity);
        StateComponent state = stateMapper.get(entity);
        MovementComponent movement = movementMapper.get(entity);

        if ( animation.completed ) {
            if ( Math.abs(movement.velocity.x) > 5f) {
                state.transition(RobotComponent.STATE_WALKING);
            } else {
                state.transition(RobotComponent.STATE_IDLE);
            }
        }
    }

}
