package no.ntnu.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import no.ntnu.ShooterGame;
import no.ntnu.entities.Entity;
import no.ntnu.entities.Player;
import no.ntnu.scenes.Hud;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GameScreen implements Screen{

    private ShooterGame instance;

    private Texture playerTexture;
    private Texture bulletTexture;
    private Player player;
    private Hud hud;

    private SpriteBatch batch;
    private OrthographicCamera camera;

    // List of all renderable entities
    private List<Entity> entities;

    public GameScreen() {
        instance = ShooterGame.getInstance();
        instance.startEventTimer();
        entities = new ArrayList<Entity>();
        createCamera();
        batch = new SpriteBatch();
        loadTextures();
        createHud();
        player = new Player(playerTexture, bulletTexture, this);
        registerEntity(player);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        clearScreen();

        // Render all the entities
        batch.begin();
        int i = 0;
        System.out.println(entities.size() + " entities");
        while (i < entities.size()) {
            Entity e = entities.get(i);
            if (e.isAlive()) {
                e.render(batch);
            }
            ++i;
        }
        batch.end();

        hud.render();

        disposeDeadEntities();

    }

    @Override
    public void resize(int width, int height) {

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
        batch.dispose();
        playerTexture.dispose();
        bulletTexture.dispose();
    }

    /**
     * Creates the HUD of the game.
     */
    private void createHud() {
        // Creates the HUD
        hud = new Hud(this);
    }


    void loadTextures() {
        playerTexture = new Texture("player.png");
        bulletTexture = new Texture("bullet.png");
    }

    /**
     * Set our camera to display a 2D world, 800x600 pixels large
     * (or whatever the screen size is set to currently)
     */
    private void createCamera() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, ShooterGame.SCREEN_WIDTH, ShooterGame.SCREEN_HEIGHT);
        camera.update();
    }

    /**
     * Fill the screen with black color
     */
    private void clearScreen() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    /**
     * Register a new entity which will be rendered in all future game loops
     *
     * @param entity
     */
    public void registerEntity(Entity entity) {
        if (!entities.contains(entity)) {
            entities.add(entity);
        }
    }

    /**
     * Remove all dead entities from the game loop
     */
    private void disposeDeadEntities() {
        Iterator it = entities.iterator();
        while (it.hasNext()) {
            Entity e = (Entity) it.next();
            if (!e.isAlive()) {
                System.out.println("Disposing " + e.getClass().getSimpleName());
                it.remove();
            }
        }
    }

}
