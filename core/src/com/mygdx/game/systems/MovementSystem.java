package com.mygdx.game.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.components.*;

public class MovementSystem extends IteratingSystem {
    private final Vector2 tmp = new Vector2();

    private final ComponentMapper<BoundsComponent> boundsMapper;
    private final ComponentMapper<TransformComponent> transformMapper;
    private final ComponentMapper<MovementComponent> movementMapper;
    private final Rectangle cellRectangle = new Rectangle();
    private final Rectangle boundsRectangle = new Rectangle();

    private RenderingSystem renderer;

    private TiledMapTileLayer mapLayer;

    private float mapCellWidth;
    private float mapCellHeight;
    private boolean blockers[][];
    private int heightCells;
    private int widthCells;


    public MovementSystem() {
        super(Family.all(TransformComponent.class, MovementComponent.class).get());

        transformMapper = ComponentMapper.getFor(TransformComponent.class);
        movementMapper = ComponentMapper.getFor(MovementComponent.class);
        boundsMapper = ComponentMapper.getFor(BoundsComponent.class);
    }

    @Override
    public void processEntity(Entity entity, float deltaTime) {
        renderer = getEngine().getSystem(RenderingSystem.class);


        TransformComponent pos = transformMapper.get(entity);
        MovementComponent mov = movementMapper.get(entity);

        tmp.set(mov.accel).scl(deltaTime);
        mov.velocity.add(tmp);

        if (mov.velocity.x > mov.maxVelocity.x) {
            mov.velocity.x = mov.maxVelocity.x;
        }

        if (mov.velocity.x < -mov.maxVelocity.x) {
            mov.velocity.x = -mov.maxVelocity.x;
        }

        // pretend set
        tmp.set(mov.velocity).scl(deltaTime);
        testCollisions(entity, mov.velocity, pos.pos.x + tmp.x, pos.pos.y + tmp.y);

        tmp.set(mov.velocity).scl(deltaTime);
        pos.pos.add(tmp.x, tmp.y, 0.0f);
    }

    private void setBoundsRectanglePosition( Rectangle bounds,  float x, float y) {
        boundsRectangle.x = x - bounds.width / 2;
        boundsRectangle.y = y - bounds.height / 2;
    }

    private void testCollisions(Entity entity, Vector2 velocity, float newX, float newY) {
        BoundsComponent bounds = boundsMapper.get(entity);
        MovementComponent mov = movementMapper.get(entity);
        TransformComponent pos = transformMapper.get(entity);

        boundsRectangle.set(bounds.bounds);

        int x = (int) (newX / mapCellWidth);
        int y = (int) (newY / mapCellHeight);

        if (velocity.y < 0) {
            setBoundsRectanglePosition(bounds.bounds, pos.pos.x, newY);
            if (isCellBlocked(x, y - 1) || isCellBlocked(x - 1, y - 1) || isCellBlocked(x + 1, y - 1)) {
                mov.jumping = false;
                mov.headhit= false;
                mov.velocity.y = 0;
                float clipPosY =mapCellHeight * y + bounds.bounds.height / 2;
                pos.pos.y = Math.min(pos.pos.y, clipPosY);
                boundsRectangle.y = pos.pos.y - bounds.bounds.height / 2;
            }
        }

        if (velocity.y > 0) {
            setBoundsRectanglePosition(bounds.bounds, pos.pos.x, newY);
            if (isCellBlocked(x, y + 1) || isCellBlocked(x - 1, y + 1) || isCellBlocked(x + 1, y + 1)) {
                mov.velocity.y = 0;
                mov.headhit= true;
                boundsRectangle.y = mapCellHeight * y - bounds.bounds.height / 2;
            }
        }

        if (velocity.x < 0) {
            setBoundsRectanglePosition(bounds.bounds, newX, pos.pos.y);
            if (isCellBlocked(x - 1, y) || isCellBlocked(x - 1, y - 1) || isCellBlocked(x - 1, y + 1)) {
                mov.velocity.x = 0;
                pos.pos.x = mapCellWidth * x + bounds.bounds.width / 2;
            }
        }

        if (velocity.x > 0) {
            setBoundsRectanglePosition(bounds.bounds, newX, pos.pos.y);
            if (isCellBlocked(x + 1, y) || isCellBlocked(x + 1, y - 1) || isCellBlocked(x + 1, y + 1)) {
                mov.velocity.x = 0;
                pos.pos.x = mapCellWidth * (x+1) - bounds.bounds.width / 2;
            }
        }
    }

    public boolean isCellBlocked(int x, int y) {
        cellRectangle.set(mapCellWidth * x, mapCellHeight * y, 32, 32);
        return isBlockingTile(x, y) && cellRectangle.overlaps(boundsRectangle);
    }

    public boolean isBlockingTile(int x, int y) {
        if (x < 0 || y < 0 || x >= widthCells || y >= heightCells) return true; // blocks at the end of the level
        return blockers[x][y];
    }

    public void setMap(TiledMap map) {
        this.mapLayer = (TiledMapTileLayer) map.getLayers().get("Main");
        this.mapCellWidth = mapLayer.getTileWidth();
        this.mapCellHeight = mapLayer.getTileHeight();
        this.widthCells = mapLayer.getWidth();
        this.heightCells = mapLayer.getHeight();
        blockers = new boolean[widthCells][heightCells];

        for (int x = 0; x < widthCells; x++) {
            for (int y = 0; y < heightCells; y++) {

                TiledMapTileLayer.Cell cell = mapLayer.getCell(x, y);
                if (cell != null) {
                    TiledMapTile tile = cell.getTile();
                    MapProperties properties = tile.getProperties();
                    blockers[x][y] = properties != null && properties.containsKey("Block");
                } else
                    blockers[x][y] = false;
            }
        }
    }
}
