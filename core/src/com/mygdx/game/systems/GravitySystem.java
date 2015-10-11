package com.mygdx.game.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.components.*;

public class GravitySystem extends IteratingSystem {
    private Vector2 gravity;
    private ComponentMapper<MovementComponent> mm;

    public GravitySystem() {
        super(Family.all(GravityComponent.class, MovementComponent.class).get());
        gravity = new Vector2(0f,-400.81f);

        mm = ComponentMapper.getFor(MovementComponent.class);
    }

    @Override
    public void processEntity(Entity entity, float deltaTime) {
        MovementComponent mov = mm.get(entity);
        mov.velocity.add(gravity.x * deltaTime, gravity.y * deltaTime);
    }
}
