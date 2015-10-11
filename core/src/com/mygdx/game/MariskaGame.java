package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.loader.SpriteSheetLoader;

public class MariskaGame extends Game {

	private SpriteBatch batcher;
	private SpriteSheetLoader loader;


	@Override
	public void create () {
		batcher = new SpriteBatch();
		loader = new SpriteSheetLoader("spritesheet.json","spritesheet.png");
		loader.load();
		setScreen(new MainMenuScreen(batcher,loader));
	}


	@Override
	public void render() {
		GL20 gl = Gdx.gl;
		gl.glClearColor(1.0f, 1.0f, 1.0f, 0.0f);
		gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		super.render();
	}

}

