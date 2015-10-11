package com.mygdx.game;

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
import com.mygdx.game.systems.AnimationSystem;
import com.mygdx.game.systems.RenderingSystem;
import com.mygdx.loader.AssetLoader;


public class GameScreen extends ScreenAdapter {
    SpriteBatch batch;
    private PooledEngine engine;
    private World world;
    private MariskaGame game;
    private AssetLoader loader;

    // touchpad
    private OrthographicCamera camera;
    private Skin touchpadSkin;
    private Touchpad.TouchpadStyle touchpadStyle;
    private Drawable touchBackground;
    private Touchpad touchpad;
    private FitViewport viewport;
    private Stage stage;


    public GameScreen(MariskaGame game) {
        this.game = game;
        this.batch = game.getBatcher();
        this.loader = game.getLoader();

        engine = new PooledEngine();
        world = new World(engine,game.getLoader());
        world.create();

        engine.addSystem(new AnimationSystem());
        engine.addSystem(new RenderingSystem(batch));

        create();
    }

    private void create() {
        //Create camera
        float aspectRatio = (float) Gdx.graphics.getWidth() / (float) Gdx.graphics.getHeight();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 10f*aspectRatio, 10f);

        //Create a touchpad skin
        touchpadSkin = new Skin();
        //Set background image
        touchpadSkin.add("touchBackground", loader.getTextureRegion("TouchBackground"));

        //Set knob image
        touchpadSkin.add("touchKnob", loader.getTextureRegion("TouchKnob"));
        //Create TouchPad Style
        touchpadStyle = new Touchpad.TouchpadStyle();
        //Create Drawable's from TouchPad skin
        touchBackground = touchpadSkin.getDrawable("touchBackground");
        Drawable touchKnob = touchpadSkin.getDrawable("touchKnob");
        //Apply the Drawables to the TouchPad Style
        touchpadStyle.background = touchBackground;
        touchpadStyle.knob = touchKnob;
        //Create new TouchPad with the created style
        touchpad = new Touchpad(10, touchpadStyle);
        //setBounds(x,y,width,height)
        touchpad.setBounds(15, 15, 180, 180);

        //Create a Stage and add TouchPad
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage = new Stage(viewport, batch);
        stage.addActor(touchpad);
        Gdx.input.setInputProcessor(stage);


    }

    private void update(float delta) {
        engine.update(delta);
    }

    private void drawUI(float delta){
        camera.update();

        //Draw
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void render (float delta) {
        update(delta);
        drawUI(delta);
    }
}

