package com.mygdx.game;

import com.badlogic.gdx.Game;

/**
 * Wiki-page link: https://github.com/strazdinsg/gamelab/wiki/Switching-between-screens
 */

public class ScreenExample extends Game {

	private MenuScreen menuScreen;

	@Override
	public void create () {
		menuScreen = new MenuScreen(this);
		setScreen(menuScreen);
	}

	@Override
	public void render () {
		super.render(); // Very important to call this.
	}

	@Override
	public void dispose () {
	}
}
