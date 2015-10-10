package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public class MainMenuScreen extends ScreenAdapter {

    private final OrthographicCamera guiCam;
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    private Rectangle startButton = new Rectangle(70,70,80,80);


    public MainMenuScreen(SpriteBatch batch) {
        this.batch = batch;
        shapeRenderer = new ShapeRenderer();

        guiCam = new OrthographicCamera(640, 480);
        guiCam.position.set(640 / 2, 480 / 2, 0);
    }

    @Override
    public void render (float delta) {
        update(delta);
        draw();
    }

    public void update(float delta)
    {

    }

    public void draw () {
        GL20 gl = Gdx.gl;
        gl.glClearColor(1, 0, 0, 1);
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        guiCam.update();
        batch.setProjectionMatrix(guiCam.combined);
        batch.begin();
        batch.end();

        shapeRenderer.setProjectionMatrix(guiCam.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        //draw the background

        shapeRenderer.setColor(0,1,0,1);
        shapeRenderer.rect(startButton.x, startButton.y, startButton.width, startButton.height);
        shapeRenderer.end();

    }

}
