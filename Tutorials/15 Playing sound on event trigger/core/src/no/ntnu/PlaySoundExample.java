package no.ntnu;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

/**
 * Plays a sound on key press and mouse button click.
 * To test it, press W on keyboard or click left mouse button somewhere on game
 * screen
 */
public class PlaySoundExample extends ApplicationAdapter {
    private SoundKeyHandler keyHandler;
    
    @Override
    public void create() {
        // we will handle key and mouse presses by the InputHandler
        keyHandler = new SoundKeyHandler();
        keyHandler.create();
        Gdx.input.setInputProcessor(keyHandler);
    }

    @Override
    public void render() {
        // Clear screen to avoid flickering
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // The key.mouse handling happens in the InputHandler
    }

    @Override
    public void dispose() {
        keyHandler.dispose();
    }
}
