package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * @author Ole-martin Steinnes
 */
public class ScreenExample extends Game {

	private SpriteBatch batch;
	private MenuScreen menuScreen;

	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;

	@Override
	public void create () {
		batch = new SpriteBatch();
		menuScreen = new MenuScreen(this);
		setScreen(menuScreen);
	}

	@Override
	public void render () {
		super.render(); // Very important to call this.
	}

	@Override
	public void dispose () {
		batch.dispose();
	}

	public SpriteBatch getBatch() {
		return batch;
	}

}
