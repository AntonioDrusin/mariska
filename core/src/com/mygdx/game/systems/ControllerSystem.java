package com.mygdx.game.systems;

import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.core.*;
import com.mygdx.game.components.*;


public class ControllerSystem extends IteratingSystem {

    private ComponentMapper<ControllerComponent> controllerMapper;
    private ComponentMapper<MovementComponent> movementMapper;

    public ControllerSystem() {
        super(Family.all(ControllerComponent.class, TransformComponent.class).get());
        controllerMapper = ComponentMapper.getFor(ControllerComponent.class);
        movementMapper = ComponentMapper.getFor(MovementComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        MovementComponent movement = movementMapper.get(entity);
        ControllerComponent controller = controllerMapper.get(entity);

        float velocity = 50;
        float x = velocity;
        float dx=0f;

        if ( controller.left )
            dx = -x;
        if ( controller.right )
            dx = x;
        if ( controller.up)
            movement.velocity.y = 60f;

        movement.velocity.x=dx;
    }
}
