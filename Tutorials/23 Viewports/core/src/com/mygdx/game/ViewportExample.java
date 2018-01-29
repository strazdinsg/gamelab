package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


public class ViewportExample extends Game {

	// Viewport and camera fields.
	private Viewport viewport;
	private Camera camera;

	// The original screen width and height.
	private final static int HEIGHT = 600;
	private final static int WIDTH = 800;

	// Uses a background texture and a batch to show blackbars.
	private Texture texture;
	private SpriteBatch batch;

	@Override
	public void create () {

		// A viewport manages a cameras viewportWidth and viewportHeight.
		// Thus it needs a camera to be supplied the constructor.
		camera = new OrthographicCamera();

		// Creates a FitViewport. (maintains aspect ratio)
		viewport = new FitViewport(WIDTH, HEIGHT, camera);

		// Applies every render that happens, to the viewport.
		// (prevents rendering outside its limits == nothing will be rendered on the black bars)
		viewport.apply();

		// A background texture and a batch, in order to see the black bars.
		texture = new Texture("sky1.png");
		batch = new SpriteBatch();
	}

	@Override
	public void render () {
		// Avoid flickering.
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// Draws the background, in order to spot black bars.
		batch.begin();
		batch.draw(texture, 0, 0);
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
		// When the game window gets resized, the viewport must me told.
		viewport.update(width, height);
	}

	@Override
	public void dispose () {
	}

}
