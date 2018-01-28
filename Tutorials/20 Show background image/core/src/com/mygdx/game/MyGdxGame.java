package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MyGdxGame extends ApplicationAdapter {

    private SpriteBatch batch;
    private Texture backgroundImg;

    @Override
    public void create() {
        // Sprite batch is needed for rendering of graphical elements
        batch = new SpriteBatch();

        // Load texture for the background image
        backgroundImg = new Texture("sky1.png");
    }

    @Override
    public void render() {
        // Open sprite batch for rendering. This should be called before any 
        // draw() methods for the batch
        batch.begin();

        // Draws the background
        batch.draw(backgroundImg, 0, 0);

        // Finish sprite batch rendering. This should be called at the end of 
        // all sprite drawing
        batch.end();
    }

    @Override
    public void dispose() {
        // Dispose the components
        backgroundImg.dispose();
        batch.dispose();
    }
}
