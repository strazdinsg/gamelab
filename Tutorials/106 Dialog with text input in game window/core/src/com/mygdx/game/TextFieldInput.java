package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.*;

/**
 * This example shows how to create a dialog box in LibGDX
 * with the help of scene2d.
 */
public class TextFieldInput extends Game {

    // Viewport for the stage.
    private Viewport viewport;

    // The virtual screen width and height.
    private final static int HEIGHT = 600;
    private final static int WIDTH = 800;

    // Sprite batch for the stage.
    private SpriteBatch batch;

    // Skin is what determines how the dialog will look.
    private Skin skin;

    private TextField textField;

    // Stage the dialog box will be added to.
    private Stage stage;

    // The text of our dialog box.
    private Label label;

    @Override
    public void create() {

        // Fetches the user interface skin.
        skin = new Skin(Gdx.files.internal("uiskin.json"));

        // Stage we will attach dialog to
        createStage();
        // Label we will add to dialog.
        createLabel();
        createTextField();

        // Sets the stage as input. See InputHandler tutorial.
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render() {
        // Avoid flickering.
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Activate the stage and draw it.
        stage.act();
        stage.draw();
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
        stage.dispose();
    }

    /**
     * Creates a stage the dialog can be attached to
     */
    private void createStage() {
        // Creates viewport (see Tutorial #23.)
        createFitViewport();
        // SpriteBatch for the stage.
        batch = new SpriteBatch();
        // New Stage.
        stage = new Stage(viewport, batch);
    }

    /**
     * FitViewport maintains the aspect-ratio of the original width and height.
     * This may result in black bars.
     */
    private void createFitViewport() {
        // A viewport manages a cameras viewportWidth and viewportHeight.
        // Thus it needs a camera to be supplied the constructor.
        Camera camera = new OrthographicCamera();
        viewport = new FitViewport(WIDTH, HEIGHT, camera);

        // Applies every render that happens, to the viewport.
        viewport.apply();
    }

    /**
     * Creates label for the dialog box.
     */
    private void createLabel() {
        label = new Label("This is my dialog text.", skin);
        label.setWrap(true);
        label.setFontScale(.8f);
        label.setAlignment(Align.center);
    }

    private void createTextField() {
        textField = new TextField("", skin);
        textField.setMessageText("test");

        textField.setPosition(WIDTH/2 - 100, HEIGHT/2);
        textField.setSize(200, 50);



        stage.addActor(textField);
    }


}
