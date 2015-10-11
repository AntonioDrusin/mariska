package com.mygdx.game;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.components.ControllerComponent;
import com.mygdx.game.systems.AnimationSystem;
import com.mygdx.game.systems.RenderingSystem;
import com.mygdx.game.systems.TouchpadSystem;
import com.mygdx.game.systems.UIRenderingSystem;
import com.mygdx.loader.AssetLoader;


public class GameScreen extends ScreenAdapter {
    SpriteBatch batch;
    private PooledEngine engine;
    private World world;
    private MariskaGame game;
    private AssetLoader loader;

    // touchpad
    private Skin touchpadSkin;
    private Touchpad.TouchpadStyle touchpadStyle;
    private Drawable touchBackground;
    private Touchpad touchpad;


    public GameScreen(MariskaGame game) {
        this.game = game;
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
    }

    private void update(float delta) {
        engine.update(delta);
    }

    @Override
    public void render (float delta) {
        update(delta);
    }
}

