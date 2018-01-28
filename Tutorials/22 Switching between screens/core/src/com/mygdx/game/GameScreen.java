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

    // Loading a tiled map. Tutorial #21
    private LoadingTiledMap tiledMap;

    /**
     * Game screen
     * @param gameClass The main game class
     */
    public GameScreen(ScreenExample gameClass) {
        this.gameClass = gameClass;

        // Creating a tiled map object. T#21
        tiledMap = new LoadingTiledMap("level1.tmx");
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        // Clearing the screen.
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Render map
        tiledMap.render();

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
        tiledMap.dispose();
    }


}
