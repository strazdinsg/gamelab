package com.mygdx.game;

import com.badlogic.gdx.Input;

public class TextInputListener implements Input.TextInputListener {
    @Override
    public void input(String text) {
        System.out.println("Username: " + text);
    }

    @Override
    public void canceled() {
        System.out.println("Username not entered.");
    }
}
