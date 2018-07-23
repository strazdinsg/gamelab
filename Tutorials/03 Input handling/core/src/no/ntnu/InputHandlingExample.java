package no.ntnu;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

/**
 * Example showing how to handle keyboard and mouse input using both approaches: 
 * polling and event handling. For details see Wiki-page: 
 * https://github.com/strazdinsg/gamelab/wiki/Input-handling
 */
public class InputHandlingExample extends ApplicationAdapter {

    @Override
    public void create() {
        // We create a new instance if InputHandler, and register it with GDX
        // This is the event-driven approach
        Gdx.input.setInputProcessor(new InputHandler());
    }

    @Override
    public void render() {
        
        //-----------------
        // Polling approach
        //-----------------
        // Testing if the W key just went from unpressed to pressed
        if (Gdx.input.isKeyJustPressed(Input.Keys.W)) {
            System.out.println("W was just pressed! (Polling)");
        }
        // Testing if the W key is currently down
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            System.out.println("W is still down! (Polling)");
        }

        // Testing if the left mousebutton is pressed
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            System.out.println("The left mousebutton is pressed! (Polling)");
        }
        // Testing if the right mousebutton is pressed
        if (Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) {
            System.out.println("The right mousebutton is pressed! (Polling)");
        }

        // We print out the current mouse position
        System.out.println("Mouse position: " + Gdx.input.getX() + ":" 
                + Gdx.input.getY() + " (Polling)");

        // Sleep a while to avoid overload of messages
        // Warning: normally won't do this in a real game, this is only for
        // demo purpose so that we can see the debug messages in the Output log
        try {
            Thread.sleep(300);
        } catch (InterruptedException ex) {
            System.out.println("Someone interrupted the sleep: " + ex.getMessage());
        }
    }

    @Override
    public void dispose() {
    }
}
