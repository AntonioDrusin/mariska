package com.mygdx.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.utils.IntMap;

public class AnimationComponent implements Component{
    private IntMap<Animation> animations = new IntMap<>();
    public void add( int key, Animation animation ) { animations.put(key,animation);}
    public Animation get ( int key ) {return animations.get(key);}
}
