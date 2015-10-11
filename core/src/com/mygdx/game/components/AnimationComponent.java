package com.mygdx.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.utils.IntMap;
import com.mygdx.game.components.support.ComponentAnimation;

public class AnimationComponent implements Component{
    private IntMap<ComponentAnimation> animations = new IntMap<>();
    public void add( int key, Animation animation, boolean completes ) {
        ComponentAnimation c = new ComponentAnimation();
        c.completes = completes;
        c.animation = animation;
        animations.put(key,c);
    }
    public ComponentAnimation get ( int key ) {return animations.get(key);}
}
