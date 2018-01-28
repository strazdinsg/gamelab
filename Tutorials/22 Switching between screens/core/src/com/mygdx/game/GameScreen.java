package com.mygdx.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;

/**
 * @author Ole-martin Steinnes
 */
public class GameScreen implements Screen {

    // Our main game class
    private ScreenExample gameClass;

    /**
     * Game screen
     * @param gameClass The main game class
     */
    public GameScreen(ScreenExample gameClass) {
        this.gameClass = gameClass;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        // Clearing the screen to the color green.
        Gdx.gl.glClearColor(0, 1, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Change screen if ESCAPE is pressed.
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            gameClass.setScreen(new MenuScreen(gameClass));
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
