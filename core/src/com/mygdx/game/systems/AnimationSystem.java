package com.mygdx.game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.mygdx.game.components.AnimationComponent;
import com.mygdx.game.components.StateComponent;
import com.mygdx.game.components.TextureComponent;

public class AnimationSystem extends IteratingSystem{
    private ComponentMapper<TextureComponent> textureMapper;
    private ComponentMapper<AnimationComponent> animationMapper;
    private ComponentMapper<StateComponent> stateMapper;

    public AnimationSystem() {
        super(Family.all(TextureComponent.class,
                AnimationComponent.class,
                StateComponent.class).get());

        textureMapper = ComponentMapper.getFor(TextureComponent.class);
        animationMapper = ComponentMapper.getFor(AnimationComponent.class);
        stateMapper = ComponentMapper.getFor(StateComponent.class);
    }

    @Override
    public void processEntity(Entity entity, float deltaTime) {
        TextureComponent texture = textureMapper.get(entity);
        AnimationComponent animationComponent = animationMapper.get(entity);
        StateComponent state = stateMapper.get(entity);

        Animation animation = animationComponent.get(state.get());
        if ( animation != null ) {
            texture.region = animation.getKeyFrame(state.time,true);
        }

        state.time += deltaTime;
    }
}
