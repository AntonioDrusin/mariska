package com.mygdx.game.systems.support;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;


public class UIRenderingSupport {
    static final float FRUSTUM_WIDTH = 640f;
    static final float FRUSTUM_HEIGHT = 480f;
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
        camera = new OrthographicCamera();
        camera.position.set(FRUSTUM_WIDTH / 2, FRUSTUM_HEIGHT / 2, 0);
        viewport = new FitViewport(FRUSTUM_WIDTH, FRUSTUM_HEIGHT, camera);
    }

    private void createStage() {
        stage = new Stage(viewport, batch);
        Gdx.input.setInputProcessor(stage);
    }


    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
    }

    public void update(float deltaTime) {
        camera.update();
        stage.act(deltaTime);
        stage.draw();
        batch.setColor(1f, 1f, 1f, 1f);
    }

    public Stage getStage() {
        return stage;
    }
}
