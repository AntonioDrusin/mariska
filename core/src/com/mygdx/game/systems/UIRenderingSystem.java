package com.mygdx.game.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.components.UIComponent;
import com.mygdx.game.systems.support.IUIStage;


public class UIRenderingSystem extends IteratingSystem implements IUIStage {

    private Stage stage;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private FitViewport viewport;

    public UIRenderingSystem(SpriteBatch batch) {
        super(Family. all(UIComponent.class).get());
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
    @Override
    public void update (float deltaTime) {
        super.update(deltaTime);
        camera.update();
        stage.act(deltaTime);
        stage.draw();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

    }

    @Override
    public Stage getStage() {
        return stage;
    }
}
