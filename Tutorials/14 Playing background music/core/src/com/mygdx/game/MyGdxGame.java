package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;

/**
 * You can find the wiki-page for this example here:
 * https://github.com/strazdinsg/gamelab/wiki/Playing-background-music
 */

public class MyGdxGame extends ApplicationAdapter {
	private PlayAudio playAudio;
	
	@Override
	public void create () {
		// Creates audio object
		playAudio = new PlayAudio();
		playAudio.playMusic(true, "nine.ogg");
	}

	@Override
	public void render () {
	}
	
	@Override
	public void dispose () {
		playAudio.stopLoopingMusic();
	}
}
