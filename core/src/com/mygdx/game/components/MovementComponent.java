package com.mygdx.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class MovementComponent implements Component {
    public final Vector2 velocity = new Vector2();
    public final Vector2 accel = new Vector2();
    public boolean jumping;
    public boolean headhit;
    public final Vector2 maxVelocity = new Vector2(60, 60);
}
