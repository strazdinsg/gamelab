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


	private static final short DEFAULT_BIT = 1;
	private static final short MARIO_BIT = 2;
	private static final short BRICK_BIT = 4;
	private static final short COIN_BIT = 8;
	private static final short DESTROYED_BIT = 16;

	private PlayScreen playScreen;

	private SpriteBatch batch;


	@Override
	public void create () {
		batch = new SpriteBatch();
		playScreen = new PlayScreen(this);

		setScreen(playScreen);
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
		return V_WIDTH;
	}

	/**
	 * Returns the virtual height of the game.
	 * @return Virtual Height
	 */
	public int getvHeight() {
		return V_HEIGHT;
	}

	/**
	 * Returns the pixels per meter value of the game
	 * @return Pixels per meter
	 */
	public float getPixelsPerMeter() {
		return pixelsPerMeter;
	}

	/**
	 * Returns the default bit constant.
	 * @return the default bit constant.
	 */
	public static short getDefaultBit() {
		return DEFAULT_BIT;
	}

	/**
	 * Returns the Mario bit constant.
	 * @return the Mario bit constant.
	 */
	public static short getMarioBit() {
		return MARIO_BIT;
	}

	/**
	 * Returns the Brick bit constant.
	 * @return the Brick bit constant.
	 */
	public static short getBrickBit() {
		return BRICK_BIT;
	}

	/**
	 * Returns the Coin bit constant
	 * @return the Coin bit constant
	 */
	public static short getCoinBit() {
		return COIN_BIT;
	}

	/**
	 * Returns the Destroyed bit constant.
	 * @return the Destroyed bit constant.
	 */
	public static short getDestroyedBit() {
		return DESTROYED_BIT;
	}

	/**
	 * Returns the play screen of the game
	 * @return the play screen of the game
	 */
	public PlayScreen getPlayScreen() {
		return playScreen;
	}
}
