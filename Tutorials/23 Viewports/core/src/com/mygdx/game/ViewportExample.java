package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


public class ViewportExample extends Game {

	private SpriteBatch spriteBatch;

	private Viewport viewport;
	private Camera camera;
	private Texture texture;

	@Override
	public void create () {
		camera = new OrthographicCamera();
		viewport = new FitViewport(800,600, camera);
		spriteBatch = new SpriteBatch();
		texture = new Texture("sky1.png");

		viewport.apply();
	}

	@Override
	public void render () {

		// Clears screen
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		spriteBatch.begin();
		spriteBatch.draw(texture,0,0);
		spriteBatch.end();
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
	}

	@Override
	public void dispose () {
		spriteBatch.dispose();
	}

}
