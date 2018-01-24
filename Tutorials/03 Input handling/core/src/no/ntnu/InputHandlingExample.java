package no.ntnu;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;

public class InputHandlingExample extends ApplicationAdapter {
    
    
	@Override
        public void create () {
                // We create a new instance if InputHandler, and register it with GDX
            Gdx.input.setInputProcessor(new InputHandler());
        }

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
                
                // Testing if the W key just went from unpressed to pressed
                if (Gdx.input.isKeyJustPressed(Input.Keys.W)){
                    System.out.println("W was just pressed!");
                }
                // Testing if the W key is currently down
                if (Gdx.input.isKeyPressed(Input.Keys.W)){
                    System.out.println("W is still down!");
                }
                
                // Testing if the left mousebutton is pressed
                if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
                    System.out.println("The left mousebutton is pressed!");
                }
                // Testing if the right mousebutton is pressed
                if (Gdx.input.isButtonPressed(Input.Buttons.RIGHT)){
                    System.out.println("The right mousebutton is pressed!");
                }
                
                // We print out the current mouse position
                System.out.println("Mouse position: " + Gdx.input.getX() + ":" + Gdx.input.getY());
	}
	
	@Override
	public void dispose () {
	}
}
