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

        float accelerationX = 500;
        float dx=0f;

        if ( controller.left )
        {
            dx = -accelerationX;
            if(movement.velocity.x > 0){
                dx = -700;
            }
        }
        else if ( controller.right ) {
            dx = accelerationX;
            if (movement.velocity.x < 0) {
                dx = 700;
            }
        }
        else{
            float sign = Math.signum(movement.velocity.x);
            if ( Math.abs(movement.velocity.x) > 30) {
                dx = sign * -550;
            }
            else
                movement.velocity.x = 0;
        }

        if ( movement.jumping )
        {
            controller.jumpTime += deltaTime;
        }

        if ( controller.up ) {
            if ( !movement.jumping ) {
                movement.velocity.y = 200;
                movement.jumping = true;
                controller.jumpTime = 0;
            }
            else {
                if(controller.jumpTime < 0.6f){
                    movement.velocity.y = 200;
                }

            }
        }

        movement.accel.x=dx;
    }
}
