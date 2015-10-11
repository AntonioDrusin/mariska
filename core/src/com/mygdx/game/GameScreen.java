package com.mygdx.game;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.systems.*;
import com.mygdx.game.systems.support.UIRenderingSupport;
import com.mygdx.loader.AssetLoader;


public class GameScreen extends ScreenAdapter {
    private final RenderingSystem gameRenderingSystem;
    SpriteBatch batch;
    private PooledEngine engine;
    private World world;
    private AssetLoader loader;


    public GameScreen(MariskaGame game) {
        this.batch = game.getBatcher();
        this.loader = game.getLoader();

        engine = new PooledEngine();
        world = new World(engine,game.getLoader());

        engine.addSystem(new ControllerSystem());
        engine.addSystem(new AnimationSystem());
        engine.addSystem(new CameraSystem());
        engine.addSystem(new BoundsSystem());
        engine.addSystem(new CollisionSystem());

        UIRenderingSupport uiRenderingSupport = new UIRenderingSupport(batch);
        engine.addSystem(new TouchpadSystem(loader, uiRenderingSupport));
        gameRenderingSystem = new RenderingSystem(batch, uiRenderingSupport);
        engine.addSystem(gameRenderingSystem);

        world.create();
        gameRenderingSystem.setMap(world.getMap());



    }

    private void update(float delta) {
        engine.update(delta);
    }

    @Override
    public void render (float delta) {
        update(delta);
    }

    @Override
    public void resize (int width, int height) {
        gameRenderingSystem.resize(width, height);
    }
}

