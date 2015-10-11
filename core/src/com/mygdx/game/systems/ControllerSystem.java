package com.mygdx.game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.mygdx.game.components.ControllerComponent;
import com.mygdx.game.components.TransformComponent;

public class ControllerSystem extends IteratingSystem {

    private ComponentMapper<ControllerComponent> controllerMapper;
    private ComponentMapper<TransformComponent> transformMapper;

    public ControllerSystem() {
        super(Family.all(ControllerComponent.class, TransformComponent.class).get());
        controllerMapper = ComponentMapper.getFor(ControllerComponent.class);
        transformMapper = ComponentMapper.getFor(TransformComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        TransformComponent position = transformMapper.get(entity);
        ControllerComponent controller = controllerMapper.get(entity);

        float velocity = 50;
        float x = deltaTime * velocity;
        float y = deltaTime * velocity;
        float dx=0f;
        float dy=0f;

        if ( controller.left )
            dx = -x;
        if ( controller.right )
            dx = x;
        if ( controller.up)
            dy = y;
        if ( controller.down)
            dy = -y;

        position.pos.add(dx,dy,0f);
    }
}
