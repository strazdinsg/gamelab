package com.mygdx.testgamev1;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.testgamev1.Screens.PlayScreen;

/**
 * This game was made following the video tutorials made by Brent Aureli on YouTube.
 * I've made changes to the structure of the game, for low coupling and OOD.
 * Link to the tutorial playlist: https://www.youtube.com/playlist?list=PLZm85UZQLd2SXQzsF-a0-pPF6IWDDdrXt
 *
 * The game class, that holds the SpriteBatch
 * and the virtual height and width of the screen.
 *
 * @author Ole-martin Steinnes
 * @version 1
 */
public class MarioBros extends Game {

	private static final int V_WIDTH = 400;
	private static final int V_HEIGHT = 208;
	private static final float pixelsPerMeter = 100;
	private SpriteBatch batch;


	@Override
	public void create () {
		batch = new SpriteBatch();
		setScreen(new PlayScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}

	/**
	 * Returns the SpriteBatch, so that all screens can access the batch.
	 * @return SpriteBatch batch
	 */
	public SpriteBatch getBatch() {
		return this.batch;
	}

	/**
	 * Returns the virtual width of the game.
	 * @return Virtual Width
	 */
	public int getvWidth() {
		return this.V_WIDTH;
	}

	/**
	 * Returns the virtual height of the game.
	 * @return Virtual Height
	 */
	public int getvHeight() {
		return this.V_HEIGHT;
	}

	/**
	 * Returns the pixels per meter value of the game
	 * @return Pixels per meter
	 */
	public float getPixelsPerMeter() {
		return this.pixelsPerMeter;
	}
}
