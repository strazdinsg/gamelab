package no.ntnu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 *
 * @author Thomas S. Mjåland
 */
public class MenuScreen implements Screen {

    private Viewport viewport;
    private Stage stage;
    private Skin skin;
    private final Camera camera;

    private static final int WIDTH = 600;
    private static final int HEIGHT = 480;

    public MenuScreen() {
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        camera = MainClass.camera;
        stage = new Stage();
        createStage();
        createButtons();
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
    }
    
    private void createStage() {
        SpriteBatch batch = MainClass.batch;
        createFitViewport();
        stage = new Stage(viewport, batch);
    }

    private void createButtons() {
        TextButton startButton = new TextButton("START", skin);
        TextButton loadButton = new TextButton("LOAD", skin);
        
        startButton.setBounds(WIDTH/2-100-5, HEIGHT/2+100, 100, 25);
        startButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                System.out.println("Starting!");
                MainClass.getInstance().setScreen(MainClass.game);
            } 
        });
        
        loadButton.setBounds(WIDTH/2+5, HEIGHT/2+100, 100, 25);
        loadButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                throw new UnsupportedOperationException("The load feature has yet to be implemented");
            } 
        });
        
        stage.addActor(startButton);
        stage.addActor(loadButton);
    }

    private void createFitViewport() {
        viewport = new FitViewport(WIDTH, HEIGHT, camera);
        viewport.apply();
    }
}
