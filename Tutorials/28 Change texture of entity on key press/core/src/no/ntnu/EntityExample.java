package no.ntnu;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * In this example we show how to change the texture/color
 * of an entity in libGDX.
 * Wiki-page: https://github.com/strazdinsg/gamelab/wiki/Changing-texture-of-entity-on-key-press
 */
public class EntityExample extends ApplicationAdapter {

    private SpriteBatch batch;
    private Texture entityTexture;
    private MyEntity testEntity;

    @Override
    public void create() {
        //We load our textures
        loadTextures();
        //We create the SpriteBatch and our entity
        batch = new SpriteBatch();
        testEntity = new MyEntity(entityTexture);
    }

    @Override
    public void render() {
        //We clear the screen
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //We draw our entity
        batch.begin();
        testEntity.render(batch);
        batch.end();

        handleInput();
    }

    @Override
    public void dispose() {
        //We clean up
        batch.dispose();
        entityTexture.dispose();
    }

    /**
     * Loads initial texture of entity.
     */
    private void loadTextures() {
        //We load our textures so they're ready to be drawn
        entityTexture = new Texture("TestTexture.png");
    }

    /**
     *  Handles input from player
     */
    private void handleInput() {

        // If "A" is pressed, the texture will change.
        if (Gdx.input.isKeyJustPressed(Input.Keys.A)) {
            // Our new texture
            Texture newTex = new Texture("TestTexture2.png");
            // Set the new texture.
            testEntity.changeEntityTexture(newTex);
        }
    }
}
