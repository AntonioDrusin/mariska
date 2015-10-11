package com.mygdx.game;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.mygdx.game.components.*;
import com.mygdx.loader.AssetLoader;

public class World {
    private PooledEngine engine;
    private AssetLoader assets;

    public World( PooledEngine engine, AssetLoader assets) {
        this.engine = engine;
        this.assets = assets;
    }

    public void create() {
        createRobot();
    }

    private void  createRobot() {
        Entity entity = engine.createEntity();

        AnimationComponent animation = engine.createComponent(AnimationComponent.class);
        TextureComponent texture = engine.createComponent(TextureComponent.class);
        TransformComponent transform = engine.createComponent(TransformComponent.class);
        StateComponent state = engine.createComponent(StateComponent.class);
        ControllerComponent controller = engine.createComponent(ControllerComponent.class);

        animation.add(RobotComponent.STATE_WALKING, assets.getAnimation("Robot"),false);
        animation.add(RobotComponent.STATE_ENTERING, assets.getAnimation("RobotEnter"),true);

        transform.pos.set(50f,50f,0f);

        state.set(RobotComponent.STATE_ENTERING);

        entity.add(animation);
        entity.add(texture);
        entity.add(transform);
        entity.add(state);
        entity.add(controller);

        engine.addEntity(entity);
    }
}

