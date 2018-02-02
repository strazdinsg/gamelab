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

public class DialogExample extends Game {

    // Viewport and camera fields.
    private Viewport viewport;
    private Camera camera;

    // The virtual screen width and height.
    private final static int HEIGHT = 600;
    private final static int WIDTH = 800;

    // Uses a background texture and a batch to show differences between viewports.
    private SpriteBatch batch;

    private Skin skin;
    private Dialog dialog;
    private Stage stage;

    @Override
    public void create() {


        useFitViewport();

        batch = new SpriteBatch();

        stage = new Stage(viewport, batch);

        skin = new Skin(Gdx.files.internal("uiskin.json"));

        Label label = new Label("This is my dialog text.", skin);
        label.setWrap(true);
        label.setFontScale(.8f);
        label.setAlignment(Align.center);

        dialog = new Dialog("Dialog Box", skin, "dialog") {
            protected void result(Object object) {
                System.out.println("Chosen: " + object);
            }
        };

        dialog.setSize(200 ,200);
        dialog.setPosition(WIDTH/2-100, HEIGHT/2-100);

        dialog.text(label);

        TextButton button1 = new TextButton("Yes", skin);
        dialog.button(button1, true);

        TextButton button2 = new TextButton("No", skin);
        dialog.button(button2, false);

        stage.addActor(dialog);

        Button btn = new Button(skin);
        stage.addActor(btn);


        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void render() {
        // Avoid flickering.

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
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
     * FitViewport maintains the aspect-ratio of the original width and height.
     * This may result in black bars.
     */
    private void useFitViewport() {
        // A viewport manages a cameras viewportWidth and viewportHeight.
        // Thus it needs a camera to be supplied the constructor.
        camera = new OrthographicCamera();
        viewport = new FitViewport(WIDTH, HEIGHT, camera);

        // Applies every render that happens, to the viewport.
        viewport.apply();
    }

}
