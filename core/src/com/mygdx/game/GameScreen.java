package com.mygdx.game;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.systems.*;
import com.mygdx.loader.AssetLoader;


public class GameScreen extends ScreenAdapter {
    SpriteBatch batch;
    private PooledEngine engine;
    private World world;
    private AssetLoader loader;


    public GameScreen(MariskaGame game) {
        this.batch = game.getBatcher();
        this.loader = game.getLoader();

        engine = new PooledEngine();
        world = new World(engine,game.getLoader());
        world.create();

        UIRenderingSystem renderingSystem = new UIRenderingSystem(batch);
        engine.addSystem(renderingSystem);
        engine.addSystem(new TouchpadSystem(loader, renderingSystem));
        engine.addSystem(new AnimationSystem());
        engine.addSystem(new RenderingSystem(batch));
        engine.addSystem(new ControllerSystem());
    }

    private void update(float delta) {
        engine.update(delta);
    }

    @Override
    public void render (float delta) {
        update(delta);
    }
}

