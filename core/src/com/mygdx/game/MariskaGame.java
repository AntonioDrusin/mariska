package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.loader.AssetLoader;

public class MariskaGame extends Game {

	private SpriteBatch batcher;
	private AssetLoader loader;

	public SpriteBatch getBatcher() { return batcher;}
	public AssetLoader getLoader() {return loader;}


	@Override
	public void create () {
		batcher = new SpriteBatch();
		loader = new AssetLoader("spritesheet.json","spritesheet.png","sounds/");
		loader.load();
		setScreen(new MainMenuScreen(this));
	}


	@Override
	public void render() {
		GL20 gl = Gdx.gl;
		gl.glClearColor(1.0f, 1.0f, 1.0f, 0.0f);
		gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		super.render();
	}

}

