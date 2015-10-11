package com.mygdx.game;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.systems.AnimationSystem;
import com.mygdx.game.systems.RenderingSystem;
import com.mygdx.loader.SpriteSheetLoader;

public class GameScreen extends ScreenAdapter {
    SpriteBatch batch;
    private PooledEngine engine;
    private World world;

    public void GameScreen(SpriteBatch batch,  SpriteSheetLoader loader) {
        this.batch = batch;

        engine = new PooledEngine();
        world = new World(engine,loader);

        world.create();

        engine.addSystem(new AnimationSystem());
        engine.addSystem(new RenderingSystem(batch));

    }

    private void update(float delta) {
        engine.update(delta);
    }

    private void drawUI(){
    }

    @Override
    public void render (float delta) {
        update(delta);
        drawUI();
    }
}
