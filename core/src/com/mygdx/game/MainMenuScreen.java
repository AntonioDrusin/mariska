package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.loader.AssetLoader;

public class MainMenuScreen extends ScreenAdapter {

    private final OrthographicCamera guiCam;
    private final Viewport viewport;
    private TextureRegion texture;
    private TextureRegion textureOnion;
    private TextureRegion textureStartButton;

    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    private Rectangle startButton = new Rectangle(80,380,150,50);
    private BitmapFont font;
    private float position;
    private float campos;
    private float time;

    private Animation robot;
    private Animation onion;

    Vector3 touchPoint;
    private MariskaGame game;

    Sound click;


    public MainMenuScreen(MariskaGame game) {
        this.game = game;
        this.batch = game.getBatcher();
        AssetLoader loader = game.getLoader();

        shapeRenderer = new ShapeRenderer();

        font = new BitmapFont(Gdx.files.internal("menu.fnt"),Gdx.files.internal("menu.png"),false);

        robot=loader.getAnimation("Robot");
        onion=loader.getAnimation("Onion");
        textureStartButton=loader.getTextureRegion("Button");
        click=loader.getSound("click");

        guiCam = new OrthographicCamera(640, 480);
        viewport = new FitViewport(640,480, guiCam);
        resetCamera();

        touchPoint = new Vector3();
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
        viewport.update(width, height);
    }

    public void update(float delta)
    {
        if (Gdx.input.justTouched()) {
            guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
            if ( startButton.contains(touchPoint.x, touchPoint.y)) {
                game.setScreen(new GameScreen(this.game));
                click.play();
            }
        }

        time += delta;

        float speed = 80;
        position=position + speed*delta;
        if (position > 0)
        {
            position = position -160;
        }

        campos = campos+1*delta;

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
        shapeRenderer.setColor(1.0f, 0.8f, 0.05f, 1);
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

        shapeRenderer.setColor(1f,1.0f,0.3f,.75f);
        shapeRenderer.circle(550,400,60+10*sinus);
        shapeRenderer.setColor(1f,1.0f,0.6f,.75f);
        shapeRenderer.circle(550,400,36);

        DrawWatermelon();
        shapeRenderer.end();

        batch.setProjectionMatrix(guiCam.combined);
        batch.begin();
        batch.enableBlending();
        batch.setColor(1f,1f,1f,1f);
        font.draw(batch, "Start", startButton.x+20, startButton.y+40);


        batch.draw(texture,32f,120f,125f,0f, 250f, 250f, 1f,1f, 15.9f + (float)Math.sin(campos * 10)*4);
        batch.draw(textureOnion,300f + sinus * 80,90f,16f,16f, 332, 332, 1f,1f, 0);

        batch.setColor(1f,1f,1f,0.5f);
        batch.draw(textureStartButton, startButton.x, startButton.y);
        batch.end();

    }

    private void DrawWatermelon() {
        shapeRenderer.setColor(0,0.3f,0,.75f);
        shapeRenderer.circle(320,-600,800);
        shapeRenderer.setColor(0,0.6f,0,.75f);
        shapeRenderer.circle(320,-600,770);
        int slices=20;
        float x = 320;
        float y = -600;
        float r = 775;
        float angle = -campos/1.5f;
        float angleOffset = ((float)Math.PI * 2)/slices/2;
        shapeRenderer.setColor(0,0.3f,0,.75f);
        float circAngle = angle;
        for ( int i=0; i<slices; i++ ) {
            float lx = r * (float)Math.sin(circAngle) + x;
            float ly = r * (float)Math.cos(circAngle) + y;
            float qx = r * (float)Math.sin(circAngle+angleOffset) + x;
            float qy = r * (float)Math.cos(circAngle+angleOffset) + y;

            shapeRenderer.triangle(x,y,lx,ly,qx,qy);

            circAngle+=angleOffset*2;
        }
    }

}
