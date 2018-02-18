package no.ntnu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;

public class PianoTabListener implements InputProcessor {

    private Piano piano;
    private int windowWidth;
    private int windowHeight;

    public PianoTabListener(Piano piano) {
        this.piano = piano;

        windowWidth = Gdx.graphics.getWidth();
        windowHeight = Gdx.graphics.getHeight();
    }
    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        float pointerX = InputTransform.getCursorToModelX(windowWidth, screenX);
        float pointerY = InputTransform.getCursorToModelY(windowHeight, screenY);

        piano.touchDown(pointerX, pointerY);

        System.out.println("x: " + pointerX + ", y: " + pointerY);
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        float pointerX = InputTransform.getCursorToModelX(windowWidth, screenX);
        float pointerY = InputTransform.getCursorToModelY(windowHeight, screenY);
        piano.touchUp(pointerX, pointerY);
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        float pointerX = InputTransform.getCursorToModelX(windowWidth, screenX);
        float pointerY = InputTransform.getCursorToModelY(windowHeight, screenY);
        piano.touchUp(pointerX, pointerY);
        return true;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}


