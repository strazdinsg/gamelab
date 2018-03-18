package no.ntnu;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 *
 * @author Thomas S. Mjåland
 */
public class MainClass extends Game {
    private static MainClass instance;
    
    public static SpriteBatch batch;
    public static MenuScreen menu;
    public static GameScreen game;
    public static Camera camera;

    public static MainClass getInstance(){
        return instance;
    }
    
    @Override
    public void create() {
        instance = this;
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        menu = new MenuScreen();
        game = new GameScreen();
        setScreen(menu);
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
