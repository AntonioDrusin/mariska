package com.mygdx.game;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.systems.AnimationSystem;
import com.mygdx.game.systems.RenderingSystem;
import com.mygdx.loader.SpriteSheetLoader;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	TextureRegion robotRegion;
	private PooledEngine engine;
	private World world;

	@Override
	public void create () {
		SpriteSheetLoader loader = new SpriteSheetLoader("spritesheet.json","spritesheet.png");
		loader.Load();
		batch = new SpriteBatch();

		engine = new PooledEngine();
		world = new World(engine,loader);

		world.create();

		engine.addSystem(new AnimationSystem());
		engine.addSystem(new RenderingSystem(batch));

	}

	private void update(float delta) {
		engine.update(delta);
	}

	private void drawUI(){
	}

	@Override
	public void render () {
		update(Gdx.graphics.getDeltaTime());
		drawUI();
	}
}

