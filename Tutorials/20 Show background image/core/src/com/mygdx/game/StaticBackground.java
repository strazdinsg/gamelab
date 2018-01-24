package com.mygdx.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class StaticBackground {

    private Texture backgroundImg;
    private Stage stage;
    private MyGdxGame game;
    private SpriteBatch batch;

    /**
     * Creates a static background.
     * @param game The main class.
     * @param filepath Filepath of image.
     * @throws NullPointerException if filepath not found.
     */
    public StaticBackground(MyGdxGame game, String filepath) throws NullPointerException {
        this.game = game;
        batch = game.getBatch();
        backgroundImg = new Texture(filepath);
        createStage();
    }

    /**
     * Draws the image.
     */
    public void renderImg() {
        stage.getBatch().begin();
        stage.getBatch().draw(backgroundImg, 0, 0);
        stage.getBatch().end();
    }

    /**
     * Disposes of resources
     */
    public void dispose() {
        backgroundImg.dispose();
        stage.dispose();
    }

    /**
     * Creates the stage to be drawn on.
     */
    private void createStage() {
        OrthographicCamera camera;
        Viewport viewport;

        // Set up camera
        camera = new OrthographicCamera();

        // Create viewport (can use other viewports)
        // More about that in other tutorials
        viewport = new FitViewport(game.WIDTH, game.HEIGHT, camera);

        // Sets up a stage on the SpriteBatch
        stage = new Stage(viewport, batch);
    }
}
