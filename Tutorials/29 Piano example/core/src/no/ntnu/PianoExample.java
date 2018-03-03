package no.ntnu;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PianoExample extends ApplicationAdapter {

    private Piano piano;

    @Override
    public void create() {
        piano = new Piano();
    }

    @Override
    public void resize(int width, int height) {
        piano.resize(width, height);
    }

    @Override
    public void render() {
        //We clear the screen
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //We draw our entity
        piano.render();
    }



    @Override
    public void dispose() {
        //We clean up
        piano.dispose();
    }


}
