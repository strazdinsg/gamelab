package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

/**
 * This example shows how to create a dialog box with text input in LibGDX.
 */
public class TextDialogExample extends Game {
    @Override
    public void create() {
        // Creates a text input listener.
        TextInputListener listener = new TextInputListener();
        // Gets the text input from listener, sets title and hint of dialog.
        Gdx.input.getTextInput(listener, "Enter Username", "", "username");
    }

    @Override
    public void render() {
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void dispose() {
    }
}
