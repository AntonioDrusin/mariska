package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.loader.SpriteSheetLoader;

public class MainMenuScreen extends ScreenAdapter {

    private final OrthographicCamera guiCam;
    private final Viewport viewport;
    private TextureRegion texture;
    private TextureRegion textureOnion;
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    private Rectangle startButton = new Rectangle(500,70,80,80);
    private float position;
    private float campos;
    private float time;

    private Animation robot;
    private Animation onion;

    public MainMenuScreen(SpriteBatch batch, SpriteSheetLoader loader) {
        this.batch = batch;
        shapeRenderer = new ShapeRenderer();

        robot=loader.getAnimation("Robot");
        onion=loader.getAnimation("Onion");

        guiCam = new OrthographicCamera(640, 480);
        viewport = new FitViewport(640,480, guiCam);
        resetCamera();
    }

    private void resetCamera() {
        guiCam.position.set(640 / 2, 480 / 2, 0);
    }

    @Override
    public void render (float delta) {
        update(delta);
        draw();
    }

    @Override
    public void resize (int width, int height) {
        viewport.update(width,height);
    }

    public void update(float delta)
    {
        time += delta;

        float speed = 80;
        position=position + speed*delta;
        if (position > 0)
        {
            position = position -160;
        }

        campos = campos+1  * delta;

        texture = robot.getKeyFrame(time, true );
        textureOnion = onion.getKeyFrame(time, true);
    }

    public void draw () {
        GL20 gl = Gdx.gl;
        gl.glClearColor(1, 0.7f, 0, 1);
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        float sinus = (float)Math.sin(campos);
        guiCam.zoom = 1 + sinus / 8;
        guiCam.rotate(sinus*5,0,0,1);
        guiCam.update();

        shapeRenderer.setProjectionMatrix(guiCam.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        //draw the background
        shapeRenderer.setColor(0.9f, 0.9f, 0, 1);
        for (int i = 0; i < 7; i++)
        {
            shapeRenderer.rect(position + i * 160, -60, 80, 600);
        }
        shapeRenderer.end();


        // Not zoomed
        guiCam.zoom = 1;
        guiCam.rotate(-sinus*5,0,0,1);
        guiCam.update();
        shapeRenderer.setProjectionMatrix(guiCam.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0,0.3f,0,.75f);
        shapeRenderer.circle(320,-600,800);
        shapeRenderer.setColor(0,0.6f,0,.75f);
        shapeRenderer.circle(320,-600,770);

        shapeRenderer.setColor(0,1,0,.75f);
        shapeRenderer.rect(startButton.x, startButton.y, startButton.width, startButton.height);

        shapeRenderer.end();


        batch.setProjectionMatrix(guiCam.combined);
        batch.begin();
        batch.draw(texture,32f,50f,16f,16f, 250f, 250f, 1f,1f, 15.9f);
        batch.draw(textureOnion,160f + sinus * 60,90f,16f,16f, 332, 332, 1f,1f, 0);
        batch.end();

    }

}
