package com.mygdx.game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.components.CameraComponent;
import com.mygdx.game.components.TransformComponent;

public class CameraSystem extends IteratingSystem {
    private ComponentMapper<TransformComponent> tm;
    private ComponentMapper<CameraComponent> cm;

    private final Vector2 distance = new Vector2();

    public CameraSystem() {
        super(Family.all(CameraComponent.class).get());

        tm = ComponentMapper.getFor(TransformComponent.class);
        cm = ComponentMapper.getFor(CameraComponent.class);
    }

    @Override
    public void processEntity(Entity entity, float deltaTime) {
        CameraComponent cam = cm.get(entity);

        if (cam.target == null) {
            return;
        }

        TransformComponent target = tm.get(cam.target);

        if (target == null) {
            return;
        }

        distance.set(cam.camera.position.x - target.pos.x, cam.camera.position.y - target.pos.y);
        cam.velocity.set(distance.scl(-1.5f));

        if ( distance.len() > 4) {
            cam.camera.position.y += cam.velocity.y * deltaTime;
            cam.camera.position.x += cam.velocity.x * deltaTime;
        }


    }
}

