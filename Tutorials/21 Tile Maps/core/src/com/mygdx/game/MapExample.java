package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Wiki: https://github.com/strazdinsg/gamelab/wiki/Tile-maps
 * @author Ole-martin Steinnes
 */
public class MapExample extends ApplicationAdapter {

	private SpriteBatch batch;

	private LoadingTiledMaps map;

	
	@Override
	public void create () {
		batch = new SpriteBatch();

		map = new LoadingTiledMaps("level1.tmx");
	}

	@Override
	public void render () {

		// Clears screen
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// Renders map
		map.render();

	}
	
	@Override
	public void dispose () {
		map.dispose();
		batch.dispose();
	}
}
