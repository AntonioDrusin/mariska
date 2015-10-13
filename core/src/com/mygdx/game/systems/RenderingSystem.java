package com.mygdx.game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.components.TextureComponent;
import com.mygdx.game.components.TransformComponent;
import com.mygdx.game.systems.support.UIRenderingSupport;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class RenderingSystem extends IteratingSystem {

    static final float FRUSTUM_WIDTH = 640;
    static final float FRUSTUM_HEIGHT = 480;

    private final Array<Entity> renderQueue;
    private final List<Rectangle> debugList = new ArrayList<>();
    private final Comparator<Entity> comparator;
    private final ComponentMapper<TextureComponent> textureMapper;
    private final ComponentMapper<TransformComponent> transformMapper;
    private final ShapeRenderer shapeRenderer;
    private SpriteBatch batch;
    private UIRenderingSupport uiRenderer;
    private TiledMap map;
    private OrthographicCamera camera;
    private MapRenderer mapRenderer;
    private int foregroundLayersIndex[] = new int[1];
    private int backgroundLayersIndex[] = new int[2];

    public RenderingSystem(SpriteBatch batch, UIRenderingSupport uiRenderer) {
        super(Family.all(TransformComponent.class, TextureComponent.class).get());
        this.batch = batch;
        this.uiRenderer = uiRenderer;

        camera = new OrthographicCamera(FRUSTUM_WIDTH, FRUSTUM_HEIGHT);
        camera.position.set(FRUSTUM_WIDTH / 2, FRUSTUM_HEIGHT / 2, 0);

        textureMapper = ComponentMapper.getFor(TextureComponent.class);
        transformMapper = ComponentMapper.getFor(TransformComponent.class);

        renderQueue = new Array<>();

        comparator = new Comparator<Entity>() {
            @Override
            public int compare(Entity entityA, Entity entityB) {
                return (int) Math.signum(transformMapper.get(entityB).pos.z -
                        transformMapper.get(entityA).pos.z);
            }
        };

        shapeRenderer = new ShapeRenderer();
    }

    public void setMap(TiledMap map) {
        this.map = map;
        mapRenderer = new OrthogonalTiledMapRenderer(map, 1f, batch);
        mapRenderer.setView(camera);



        for ( int i = 0; i<map.getLayers().getCount(); i ++ ){
            String layerName = map.getLayers().get(i).getName();
            if (layerName.equals( "Foreground") )
                foregroundLayersIndex[0] = i;
            if (layerName.equals( "Main" ) )
                backgroundLayersIndex[1] = i;
            if (layerName.equals( "Background") )
                backgroundLayersIndex[0] = i;

        }
    }

    public void addDebugRectangle( Rectangle debug) {
        debugList.add(new Rectangle(debug));
    }

    public void resize(int width, int height) {
        uiRenderer.resize(width, height);
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        GL20 gl = Gdx.gl;
        gl.glClearColor(1, 0.7f, 0, 1);
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        mapRenderer.setView(camera);
        mapRenderer.render(backgroundLayersIndex);
        renderSprites();
        mapRenderer.render(foregroundLayersIndex);
        uiRenderer.update(deltaTime);


        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

        shapeRenderer.setColor(1.0f, 0.0f, 0.0f, 1);
        for ( Rectangle r : debugList ){
            shapeRenderer.rect(r.x, r.y, r.width, r.height);
        }
        shapeRenderer.end();
        debugList.clear();

        renderQueue.clear();
        
    }

    private void renderSprites() {
        renderQueue.sort(comparator);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        for (Entity entity : renderQueue) {
            TextureComponent tex = textureMapper.get(entity);

            if (tex.region == null) {
                continue;
            }

            TransformComponent t = transformMapper.get(entity);

            float width = tex.region.getRegionWidth();
            float height = tex.region.getRegionHeight();
            float originX = width * 0.5f;
            float originY = height * 0.5f;

            batch.draw(tex.region,
                    t.pos.x - originX, t.pos.y - originY,
                    originX, originY,
                    width, height,
                    1f, 1f, 0f); // scale x, scale y, rotation
        }
        batch.end();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

        renderQueue.add(entity);
    }
}
