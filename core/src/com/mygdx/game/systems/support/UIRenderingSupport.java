package com.mygdx.game.systems.support;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;


public class UIRenderingSupport  {

    private Stage stage;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private FitViewport viewport;

    public UIRenderingSupport(SpriteBatch batch) {
        this.batch = batch;

        createCamera();
        createStage();
    }

    private void createCamera() {
        float aspectRatio = (float) Gdx.graphics.getWidth() / (float) Gdx.graphics.getHeight();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 10f*aspectRatio, 10f);
    }

    private void createStage() {
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage = new Stage(viewport, batch);
        Gdx.input.setInputProcessor(stage);
    }

    public void update (float deltaTime) {
        camera.update();
        stage.act(deltaTime);
        stage.draw();
    }

    public Stage getStage() {
        return stage;
    }
}
