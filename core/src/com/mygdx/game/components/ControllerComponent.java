package com.mygdx.game.components;

import com.badlogic.ashley.core.Component;

/**
 * Created by antonio on 10/11/15.
 */
public class ControllerComponent implements Component {

    public boolean left;
    public boolean right;
    public boolean up;
    public boolean down;
    public float jumpTime;
}
