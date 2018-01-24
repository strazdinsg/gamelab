package no.ntnu;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;

public class PlaySoundExample extends ApplicationAdapter {
    
    private PlayAudio playAudio;

	@Override
        public void create () {
                Gdx.input.setInputProcessor(new InputHandler());

                playAudio = new PlayAudio();
        }

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
                
                // Play sound if W is pressed.
                if (Gdx.input.isKeyJustPressed(Input.Keys.W)){
                   playAudio.playSound("nine.ogg");
                }
	}
	
	@Override
	public void dispose () {
	}
}
