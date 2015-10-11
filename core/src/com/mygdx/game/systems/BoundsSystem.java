package com.mygdx.game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.mygdx.game.components.BoundsComponent;
import com.mygdx.game.components.TransformComponent;

public class BoundsSystem extends IteratingSystem {
    private ComponentMapper<TransformComponent> transformMapper;
    private ComponentMapper<BoundsComponent> boundsMapper;

    public BoundsSystem() {
        super(Family.all(BoundsComponent.class, TransformComponent.class).get());

        transformMapper = ComponentMapper.getFor(TransformComponent.class);
        boundsMapper = ComponentMapper.getFor(BoundsComponent.class);
    }

    @Override
    public void processEntity(Entity entity, float deltaTime) {
        TransformComponent pos = transformMapper.get(entity);
        BoundsComponent bounds = boundsMapper.get(entity);

        bounds.bounds.x = pos.pos.x - bounds.bounds.width * 0.5f;
        bounds.bounds.y = pos.pos.y - bounds.bounds.height * 0.5f;
    }
}
