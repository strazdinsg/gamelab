package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.*;

public class ViewportExample extends Game {

    // Viewport and camera fields.
    private Viewport viewport;
    private Camera camera;

    // The virtual screen width and height.
    private final static int HEIGHT = 208;
    private final static int WIDTH = 400;

    private LoadingTiledMaps map;

    @Override
    public void create() {
        map = new LoadingTiledMaps("level1.tmx");
        useFitViewport();
    }

    @Override
    public void render() {
        // Avoid flickering.
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        map.render();

        handleInput();
        camera.update();
    }

    @Override
    public void resize(int width, int height) {
        // When the game window gets resized, the viewport must me told.
        viewport.update(width,height);
    }

    @Override
    public void dispose() {
        // Dispose of components when we are done with them.
        map.dispose();
    }

    /**
     * FitViewport maintains the aspect-ratio of the original width and height.
     * This may result in black bars.
     */
    private void useFitViewport() {
        // A viewport manages a cameras viewportWidth and viewportHeight.
        // Thus it needs a camera to be supplied the constructor.
        camera = map.getGameCamera();
        viewport = new FitViewport(WIDTH * map.unitScale, HEIGHT * map.unitScale, camera);

        // Applies every render that happens, to the viewport.
        viewport.apply();
    }

    /**
     * When Right or Left key is pressed, camera is moved in the
     * corresponding direction.
     */
    private void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            camera.position.x += 0.1;
        } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            camera.position.x -= 0.4;
        }
    }
}
