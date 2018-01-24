package com.mygdx.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * @author Ole-martin Steinnes
 */
public class Map {

    // This is used to load our map
    private TmxMapLoader mapLoader;

    // Used as a reference to our map
    private TiledMap map;

    // Used to render our orthogonal map to the screen.
    private OrthogonalTiledMapRenderer mapRenderer;

    // The camera that captures the map.
    private OrthographicCamera gameCamera;

    // The maps viewport.
    private Viewport viewport;

    /**
     * Map object
     * @param game The game class.
     * @param filepath The map filepath
     * @throws NullPointerException if filepath is not found.
     */
    public Map(MyGdxGame game, String filepath) throws NullPointerException {

        loadMap(filepath);
        createRenderer();
        createCamera();
        createViewport(game);
        adjustCamera();

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
        mapRenderer = new OrthogonalTiledMapRenderer(map);
    }

    /**
     * Create new camera.
     */
    private void createCamera() {
        gameCamera = new OrthographicCamera();
    }

    /**
     * Creates viewport
     * @param game The game class.
     */
    private void createViewport(MyGdxGame game) {
        // Default position is (0,0) which is the bottom left corner.
        viewport = new FitViewport(game.WIDTH/2
                ,game.HEIGHT/2 ,gameCamera);
        viewport.apply();
    }

    /**
     * Set up camera correctly.
     */
    private void adjustCamera() {
        // Sets the camera position correctly at the start of the game
        gameCamera.position.set(viewport.getWorldWidth()/2, viewport.getWorldHeight()/2, 0);

        // Updates the camera position
        gameCamera.update();
    }
}
