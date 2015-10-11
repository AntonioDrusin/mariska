package com.mygdx.game.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.mygdx.game.components.*;

public class CollisionSystem extends EntitySystem {
    private final ComponentMapper<MovementComponent> movementMapper;
    private final ComponentMapper<BoundsComponent> boundsMapper;
    private final ComponentMapper<TransformComponent> transformMapper;
    private Engine engine;
    private ImmutableArray<Entity> background;
    private ImmutableArray<Entity> foreground;

    public CollisionSystem() {

        boundsMapper = ComponentMapper.getFor(BoundsComponent.class);
        transformMapper= ComponentMapper.getFor(TransformComponent.class);
        movementMapper = ComponentMapper.getFor(MovementComponent.class);
    }

    @Override
    public void addedToEngine(Engine engine){
        this.engine = engine;
        this.background = engine.getEntitiesFor(Family.all(BackgroundComponent.class, BoundsComponent.class).get());
        this.foreground = engine.getEntitiesFor(Family.all(BoundsComponent.class, TransformComponent.class, MovementComponent.class).get());
    }

    @Override
    public void update ( float deltaTime ) {
        // slow?
        for ( Entity foregroundEntity : foreground ){
            BoundsComponent fore = boundsMapper.get(foregroundEntity);
            for( Entity backgroundEntity : background ){
                BoundsComponent back = boundsMapper.get(backgroundEntity);

                if (fore.bounds.overlaps(back.bounds)) {
                    float lowerY =  back.bounds.y + back.bounds.height;
                    if ( fore.bounds.y < lowerY) {
                        TransformComponent foreLocation = transformMapper.get(foregroundEntity);
                        foreLocation.pos.y = lowerY + fore.bounds.height/2;
                        MovementComponent movement = movementMapper.get(foregroundEntity);
                        movement.velocity.y=0f;
                    }
                }

            }
        }
    }
}
