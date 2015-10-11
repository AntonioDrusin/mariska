package com.mygdx.game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.components.TextureComponent;
import com.mygdx.game.components.TransformComponent;
import com.mygdx.game.systems.support.UIRenderingSupport;

import java.util.Comparator;


public class RenderingSystem  extends IteratingSystem {

    static final float FRUSTUM_WIDTH = 640;
    static final float FRUSTUM_HEIGHT = 480;

    private final Array<Entity> renderQueue;
    private final Comparator<Entity> comparator;
    private final ComponentMapper<TextureComponent> textureMapper;
    private final ComponentMapper<TransformComponent> transformMapper;
    private SpriteBatch batch;
    private UIRenderingSupport uiRenderer;
    private TiledMap map;
    private OrthographicCamera camera;

    public RenderingSystem(SpriteBatch batch, UIRenderingSupport uiRenderer) {
        super(Family.all(TransformComponent.class, TextureComponent.class).get());
        this.batch = batch;
        this.uiRenderer = uiRenderer;

        camera = new OrthographicCamera(FRUSTUM_WIDTH, FRUSTUM_HEIGHT);
        camera.position.set(FRUSTUM_WIDTH/2, FRUSTUM_HEIGHT/2,0);

        textureMapper = ComponentMapper.getFor(TextureComponent.class);
        transformMapper = ComponentMapper.getFor(TransformComponent.class);

        renderQueue = new Array<>();

        comparator = new Comparator<Entity>() {
            @Override
            public int compare(Entity entityA, Entity entityB) {
                return (int)Math.signum(transformMapper.get(entityB).pos.z -
                        transformMapper.get(entityA).pos.z);
            }
        };
    }
    
    public void setMap( TiledMap map) {
        this.map = map;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        camera.update();

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
                    1f,1f,0f); // scale x, scale y, rotation
        }
        batch.end();

        uiRenderer.update(deltaTime);
        renderQueue.clear();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

        renderQueue.add(entity);
    }
}
