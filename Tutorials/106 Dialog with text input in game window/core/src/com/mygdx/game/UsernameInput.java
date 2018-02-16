package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * This class creates a text field to enter
 * username in, and can handle the username.
 */
public class UsernameInput {

    // Viewport for the stage.
    private Viewport viewport;

    // Our game object.
    private TextFieldInputExample game;

    // Skin is what determines how the dialog will look.
    private Skin skin;

    private TextField textField;

    // Stage the dialog box will be added to.
    private Stage stage;

    private GameState gameState;

    public UsernameInput(TextFieldInputExample game) {
        this.game = game;

        gameState = GameState.getInstance();

        // Fetches the user interface skin.
        skin = new Skin(Gdx.files.internal("uiskin.json"));

        // Stage we will attach dialog to
        createStage();
        // Text field used to enter username.
        createTextField();

        // Sets the stage as input. See InputHandler tutorial.
        Gdx.input.setInputProcessor(stage);
    }

    public void render() {
        // Activate the stage and draw it.
        stage.act();
        stage.draw();

        // Handles text input
        handleInput();
    }

    public void resize(int width, int height) {
        // When the game window gets resized, the viewport must me told.
        viewport.update(width, height);
    }

    public void dispose() {
        // Dispose of components when we are done with them.
        stage.dispose();
    }

    /**
     * Creates a stage the dialog can be attached to
     */
    private void createStage() {
        // Creates viewport (see Tutorial #23.)
        createFitViewport();
        // New Stage.
        stage = new Stage(viewport, game.getBatch());
    }

    /**
     * FitViewport maintains the aspect-ratio of the original width and height.
     * This may result in black bars.
     */
    private void createFitViewport() {
        // A viewport manages a cameras viewportWidth and viewportHeight.
        // Thus it needs a camera to be supplied the constructor.
        Camera camera = new OrthographicCamera();
        viewport = new FitViewport(game.WIDTH, game.HEIGHT, camera);

        // Applies every render that happens, to the viewport.
        viewport.apply();
    }

    /**
     * Creates a text field to enter username in. Gets
     * added to stage.
     */
    private void createTextField() {
        textField = new TextField("", skin);
        textField.setMessageText("Enter username and press enter");

        // Sets position and size of the text field.
        textField.setPosition(game.WIDTH/2 - 150, game.HEIGHT/2);
        textField.setSize(300, 50);

        // We add the text field to the stage.
        stage.addActor(textField);
    }

    /**
     * Handles input from user, to add username.
     */
    private void handleInput() {

        // If ENTER key is pressed (see tutorial #3)
        if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) {

            String textInput = textField.getText();

            // If input is not empty
            if (!textInput.isEmpty()) {
                // Sets username in game state instance.
                gameState.set(game.K_USERNAME, textInput);
                stage.dispose();
            }
        }
    }
}
