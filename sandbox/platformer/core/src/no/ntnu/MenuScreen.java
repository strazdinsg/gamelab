package no.ntnu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 *
 * @author Thomas S. Mjåland
 */
public class MenuScreen implements Screen {

    private Viewport viewport;
    private Stage stage;
    private final Skin skin;
    private final Camera camera;
    private final SpriteBatch batch;
    
    private final Texture menuBackground;

    private static final int WIDTH = 1280;
    private static final int HEIGHT = 720;

    public MenuScreen() {
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        menuBackground = new Texture("Textures/background_menu.png");
        camera = MainClass.camera;
        batch = MainClass.batch;
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
        menuBackground.dispose();
    }
    
    private void createStage() {
        createFitViewport();
        stage = new Stage(viewport, batch);
    }

    private void createButtons() {
        TextButton startButton = new TextButton("START", skin);
        TextButton loadButton = new TextButton("LOAD", skin);
        Image image = new Image();
        
        startButton.setBounds(WIDTH/2-100-5, HEIGHT/2+100, 100, 25);
        startButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
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
        
        image.setBounds(0, 0, WIDTH, HEIGHT);
        image.setDrawable(new TextureRegionDrawable(new TextureRegion(menuBackground)));
        
        stage.addActor(image);
        stage.addActor(startButton);
        stage.addActor(loadButton);
    }

    private void createFitViewport() {
        viewport = new FitViewport(WIDTH, HEIGHT, camera);
        viewport.apply();
    }
}
