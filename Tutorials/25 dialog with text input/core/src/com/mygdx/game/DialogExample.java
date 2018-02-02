package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

/**
 * This example shows how to create a dialog box with text input in LibGDX
 * with the help of scene2d.
 */
public class DialogExample extends Game {
    @Override
    public void create() {
        TextInputListener listener = new TextInputListener();
        Gdx.input.getTextInput(listener, "Enter Username", "", "username");
    }

    @Override
    public void render() {
        // Avoid flickering.
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void dispose() {
    }
}
