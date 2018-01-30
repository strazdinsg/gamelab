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
    private Texture texture;
    private SpriteBatch batch;

    @Override
    public void create() {

        /*
        There is a use-method for each of the viewport options.
		How these work are described in the methods themselves.
         */
        useFitViewport();
        //useStretchViewport();
        //useFillViewport();
        //useExtendViewport();
        //useScreenViewport();

        // A background texture and a batch, in order to see the black bars.
        texture = new Texture("sky1.png");
        batch = new SpriteBatch();
    }

    @Override
    public void render() {
        // Avoid flickering.
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Draws the background, in order to spot black bars.
        batch.begin();
        batch.draw(texture, 0, 0);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        // When the game window gets resized, the viewport must me told.
        viewport.update(width, height);
    }

    @Override
    public void dispose() {
        // Dispose of components when we are done with them.
        texture.dispose();
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

    /**
     * Stretch viewport maintains the original width and height, but not the
     * aspect-ratio.
     * This will result in textures looking stretched on some screens.
     */
    private void useStretchViewport() {
        // A viewport manages a cameras viewportWidth and viewportHeight.
        // Thus it needs a camera to be supplied the constructor.
        camera = new OrthographicCamera();
        viewport = new StretchViewport(WIDTH, HEIGHT, camera);

        // Applies every render that happens, to the viewport.
        viewport.apply();
    }

    /**
     * Fill viewport maintains the original aspect-ratio, but fills the screen.
     * This results
     * in some of the viewport might not show on screen, depending on it's size.
     */
    private void useFillViewport() {
        // A viewport manages a cameras viewportWidth and viewportHeight.
        // Thus it needs a camera to be supplied the constructor.
        camera = new OrthographicCamera();
        viewport = new FillViewport(WIDTH, HEIGHT, camera);

        // Applies every render that happens, to the viewport.
        viewport.apply();
    }

    /**
     * Extend viewport maintains aspect ratio without black bars, by extending
     * the world in one direction.
     */
    private void useExtendViewport() {
        // A viewport manages a cameras viewportWidth and viewportHeight.
        // Thus it needs a camera to be supplied the constructor.
        camera = new OrthographicCamera();
        viewport = new ExtendViewport(WIDTH, HEIGHT, camera);

        // Applies every render that happens, to the viewport.
        viewport.apply();
    }

    /**
     * Screen viewport doesn't have virtual width or height. Therefore, no
     * scaling or black bars appear.
     * This results in the game looking entirely different, on different
     * screens.
     */
    private void useScreenViewport() {
        // A viewport manages a cameras viewportWidth and viewportHeight.
        // Thus it needs a camera to be supplied the constructor.
        camera = new OrthographicCamera();
        viewport = new ScreenViewport(camera);

        // Applies every render that happens, to the viewport.
        viewport.apply();
    }

}
