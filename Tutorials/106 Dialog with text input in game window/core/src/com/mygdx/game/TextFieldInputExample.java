package com.mygdx.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * This example shows how to create a text input
 * field to enter username in.
 */
public class TextFieldInputExample extends Game{

    // The virtual screen width and height.
    public final static int HEIGHT = 600;
    public final static int WIDTH = 800;

    // Username key for game state. (See Tutorial #17)
    public final static String K_USERNAME = "username";

    // Sprite batch for the stage.
    private SpriteBatch batch;

    // Makes us able to get text input from user.
    private UsernameInput usernameInput;

    // Game state instance (See tutorial #17)
    private GameState gameState;

    @Override
    public void create() {

        gameState = GameState.getInstance();
        gameState.set(K_USERNAME, null);

        // SpriteBatch to be drawn on
        batch = new SpriteBatch();

        // Creates a field to enter username.
        usernameInput = new UsernameInput(this);
    }

    @Override
    public void render() {
        // Avoid flickering.
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Render input field
        usernameInput.render();

        // Prints current game state.
        System.out.println("Current game state: " + gameState.toJson());
    }

    @Override
    public void resize(int width, int height) {
        usernameInput.resize(width, height);
    }

    @Override
    public void dispose() {
        // Dispose of components when we are done with them.
        usernameInput.dispose();
        batch.dispose();
    }

    public SpriteBatch getBatch() {
        return batch;
    }
}
