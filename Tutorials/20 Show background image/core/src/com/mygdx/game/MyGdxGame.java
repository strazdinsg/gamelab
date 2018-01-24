package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MyGdxGame extends ApplicationAdapter {

	private SpriteBatch batch;

	// Class that sets up a static background
	private StaticBackground staticBackground;

	// Game width and height.
	static final int WIDTH = 800;
	static final int HEIGHT = 600;
	
	@Override
	public void create () {
		batch = new SpriteBatch();

		staticBackground = new StaticBackground(this, "sky1.png");
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// Draws the background
		staticBackground.renderImg();
	}
	
	@Override
	public void dispose () {
		staticBackground.dispose();
		batch.dispose();
	}

	public SpriteBatch getBatch() {
		return batch;
	}
}
