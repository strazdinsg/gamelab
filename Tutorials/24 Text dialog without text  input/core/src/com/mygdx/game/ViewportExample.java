package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
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
    private final static int HEIGHT = 600;
    private final static int WIDTH = 800;

    // Uses a background texture and a batch to show differences between viewports.
    private SpriteBatch batch;

    @Override
    public void create() {
        useFitViewport();
    }

    @Override
    public void render() {
        // Avoid flickering.
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void resize(int width, int height) {
        // When the game window gets resized, the viewport must me told.
        viewport.update(width, height);
    }

    @Override
    public void dispose() {
        // Dispose of components when we are done with them.
        batch.dispose();
    }

    /**
     * FitViewport maintains the aspect-ratio of the original width and height.
     * This may result in black bars.
     */
    private void useFitViewport() {
        // A viewport manages a cameras viewportWidth and viewportHeight.
        // Thus it needs a camera to be supplied the constructor.
        camera = new OrthographicCamera();
        viewport = new FitViewport(WIDTH, HEIGHT, camera);

        // Applies every render that happens, to the viewport.
        viewport.apply();
    }

}
