package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;

public class MenuScreen implements Screen {

    // Our game class
    private ScreenExample game;

    /**
     * Menu screen
     * @param game The main game class.
     */
    public MenuScreen(ScreenExample game) {
        this.game = game;

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        // Clear the screen
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        // Set a new screen if W is pressed.
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            game.setScreen(new GameScreen(game));

            // Manually disposes of the MenuScreen-object.
            // (if it changes to the menu screen again, it will be a new object.)
            dispose();
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
    }
}
