package com.mygdx.game;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.systems.AnimationSystem;
import com.mygdx.game.systems.RenderingSystem;

public class GameScreen extends ScreenAdapter {
    SpriteBatch batch;
    private PooledEngine engine;
    private World world;
    private MariskaGame game;

    public GameScreen(MariskaGame game) {
        this.game = game;
        this.batch = game.getBatcher();

        engine = new PooledEngine();

        world = new World(engine,game.getLoader());

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
