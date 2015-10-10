package com.mygdx.game;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.mygdx.game.components.*;
import com.mygdx.loader.SpriteSheetLoader;

/**
 * Created by antonio on 10/10/15.
 */
public class World {
    private PooledEngine engine;
    private SpriteSheetLoader assets;

    public World( PooledEngine engine, SpriteSheetLoader assets) {
        this.engine = engine;
        this.assets = assets;
    }

    public void create() {
        Entity robot = createRobot();
    }

    private Entity createRobot() {
        Entity entity = engine.createEntity();

        AnimationComponent animation = engine.createComponent(AnimationComponent.class);
        TextureComponent texture = engine.createComponent(TextureComponent.class);
        TransformComponent transform = engine.createComponent(TransformComponent.class);
        StateComponent state = engine.createComponent(StateComponent.class);

        animation.add(RobotComponent.STATE_WALKING, assets.getAnimation("Robot"));
        animation.add(RobotComponent.STATE_ENTERING, assets.getAnimation("RobotEnter"));

        transform.pos.set(50f,50f,0f);

        state.set(RobotComponent.STATE_ENTERING);

        entity.add(animation);
        entity.add(texture);
        entity.add(transform);
        entity.add(state);

        engine.addEntity(entity);
        return entity;
    }
}

