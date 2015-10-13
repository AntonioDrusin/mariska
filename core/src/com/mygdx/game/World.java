package com.mygdx.game;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.mygdx.game.components.*;
import com.mygdx.game.systems.*;
import com.mygdx.loader.AssetLoader;

public class World {
    private PooledEngine engine;
    private AssetLoader assets;
    private TiledMap map;

    public World(PooledEngine engine, AssetLoader assets) {
        this.engine = engine;
        this.assets = assets;
    }

    public void create() {
        map = createMap();

        Entity robot = createRobot();
        createCamera(robot);
    }

    private TiledMap createMap() {
        map = new TmxMapLoader().load("levels/Level1.tmx");
        return map;
    }


    public TiledMap getMap() {
        return map;
    }

    private Entity createRobot() {
        Entity entity = engine.createEntity();

        AnimationComponent animation = engine.createComponent(AnimationComponent.class);
        TextureComponent texture = engine.createComponent(TextureComponent.class);
        TransformComponent transform = engine.createComponent(TransformComponent.class);
        StateComponent state = engine.createComponent(StateComponent.class);
        ControllerComponent controller = engine.createComponent(ControllerComponent.class);
        BoundsComponent bounds = engine.createComponent(BoundsComponent.class);
        MovementComponent movement = engine.createComponent(MovementComponent.class);

        movement.maxVelocity.set(180.3f,200);
        animation.add(RobotComponent.STATE_WALKING, assets.getAnimation("Robot"), false);
        animation.add(RobotComponent.STATE_ENTERING, assets.getAnimation("RobotEnter"), true);

        bounds.bounds.width = 20;
        bounds.bounds.height = 32;

        setLocation(transform);

        state.set(RobotComponent.STATE_ENTERING);

        entity.add(animation);
        entity.add(texture);
        entity.add(transform);
        entity.add(state);
        entity.add(controller);
        entity.add(bounds);
        entity.add(movement);
        entity.add(engine.createComponent(GravityComponent.class));

        engine.addEntity(entity);
        return entity;
    }

    private void setLocation(TransformComponent transform) {
        MapProperties props = map.getLayers().get("Actors").getObjects().get("Player").getProperties();
        float x = props.get("x", float.class);
        float y = props.get("y", float.class);
        transform.pos.set(x+16, y+16, 0f);
    }

    private void createCamera(Entity target) {
        Entity entity = engine.createEntity();

        CameraComponent camera = new CameraComponent();
        camera.camera = engine.getSystem(RenderingSystem.class).getCamera();
        camera.target = target;
        TransformComponent robotLocation = ComponentMapper.getFor(TransformComponent.class).get(target);
        camera.camera.position.x = robotLocation.pos.x;
        camera.camera.position.y = robotLocation.pos.y;

        entity.add(camera);

        engine.addEntity(entity);
    }
}



