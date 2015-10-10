package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.loader.SpriteSheetLoader;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	float stateTime;
	Animation robot;
	TextureRegion robotRegion;

	@Override
	public void create () {
		SpriteSheetLoader loader = new SpriteSheetLoader("spritesheet.json","spritesheet.png");
		loader.Load();

		robot = loader.getAnimation("Robot");

		batch = new SpriteBatch();
		stateTime = 0f;
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		stateTime += Gdx.graphics.getDeltaTime();
		robotRegion = robot.getKeyFrame(stateTime, true);

		batch.begin();
		batch.draw(robotRegion, 50, 50);
		batch.end();

	}
}
