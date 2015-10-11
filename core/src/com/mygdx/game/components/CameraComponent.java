package com.mygdx.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;

public class CameraComponent implements Component {
    public Entity target;
    public OrthographicCamera camera;
    public final Vector2 velocity = new Vector2();
}
