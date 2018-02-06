package com.mygdx.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

/**
 * Wiki: https://github.com/strazdinsg/gamelab/wiki/Tile-maps
 */
public class LoadingTiledMaps {

    // This is used to load our map
    private TmxMapLoader mapLoader;

    // Used as a reference to our map
    private TiledMap map;

    // Used to render our orthogonal map to the screen.
    private OrthogonalTiledMapRenderer mapRenderer;

    // The camera that captures the map.
    private OrthographicCamera gameCamera;

    // Since each tile is 16px, and we want each tile to represent a unit we scale it down by 1/16f.
    public final static float UNIT_SCALE = 1 / 16f;

    /**
     * LoadingTiledMaps object
     *
     * @param filepath The map filepath
     * @throws NullPointerException if filepath is not found.
     */
    public LoadingTiledMaps(String filepath) throws NullPointerException {
        loadMap(filepath);
        createRenderer();
        createCamera();
    }

    /**
     * Renders map.
     */
    public void render() {
        mapRenderer.setView(gameCamera);
        mapRenderer.render();
    }

    /**
     * Disposes of map
     */
    public void dispose() {
        map.dispose();
    }

    /**
     * Loads map.
     *
     * @param filepath Filepath of the map.
     */
    private void loadMap(String filepath) {
        // Loads map
        mapLoader = new TmxMapLoader();
        map = mapLoader.load(filepath);
    }

    /**
     * Create new map renderer.
     */
    private void createRenderer() {
        // We will use this to render the map (in render method)
        mapRenderer = new OrthogonalTiledMapRenderer(map, UNIT_SCALE);
    }

    /**
     * Create new camera.
     */
    private void createCamera() {
        gameCamera = new OrthographicCamera();

        // Sets the camera up to 20 units with and 13 unit height. (Read Wiki)
        gameCamera.setToOrtho(false, 20, 13);
    }

    /**
     * @return game camera
     */
    public OrthographicCamera getGameCamera() {
        return gameCamera;
    }
}
