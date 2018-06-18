package no.ntnu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;

/**
 * An input handler that will play a sound on some keys and mouse presses. Most
 * of the methods are not used.
 *
 * The tutorial for this example is found here:
 * https://github.com/strazdinsg/gamelab/wiki/Playing-audio-on-mouse-button-click
 *
 */
public class SoundKeyHandler implements InputProcessor {

    private Sound jumpSound;
    private Sound shootSound;

    /**
     * This method must be called to initialize the input handler
     */
    public void create() {
        // Load the sounds from files into memory
        jumpSound = Gdx.audio.newSound(new FileHandle("jump.ogg"));
        shootSound = Gdx.audio.newSound(new FileHandle("shoot.ogg"));
    }

    /**
     * Call this method to release all the resources loaded into memory
     */
    public void dispose() {
        if (jumpSound != null) {
            jumpSound.dispose();
        }
        if (shootSound != null) {
            shootSound.dispose();
        }
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.W) {
            jumpSound.play();
        }
        return true;
    }

    /**
     * This method is called when we press somewhere on the screen with either
     * mouse or touch
     *
     * @param screenX
     * @param screenY
     * @param pointer
     * @param button
     * @return
     */
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        shootSound.play();
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return true;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return true;
    }

    @Override
    public boolean scrolled(int amount) {
        return true;
    }

}
