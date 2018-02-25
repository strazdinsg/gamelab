package no.ntnu;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * An example showing how to spawn bullets in the direction of the mouse, and
 * how to dispose them when they go out of the screen.
 * Textures source: https://gamedevelopment.tutsplus.com/tutorials/make-a-neon-vector-shooter-in-jmonkeyengine-the-basics--gamedev-11616
 */
public class BulletExample extends ApplicationAdapter {

    // Screen size, in pixels
    public static final int SCREEN_WIDTH = 800;
    public static final int SCREEN_HEIGHT = 600;

    private SpriteBatch batch;
    private Texture playerTexture;
    private Texture bulletTexture;
    private Player player;

    // List of all renderable entities
    private List<Entity> entities;

    // Used for a 2D projection of the game world
    private OrthographicCamera camera;

    // Implement the singleton pattern
    private static final BulletExample instance = new BulletExample();

    // Private constructor - singleton needed
    private BulletExample() {
    }

    public static BulletExample getInstance() {
        return instance;
    }

    @Override
    public void create() {
        createCamera();
        entities = new ArrayList<Entity>();
        loadTextures();
        batch = new SpriteBatch();
        player = new Player(playerTexture, bulletTexture);
        registerEntity(player);
    }

    @Override
    public void render() {
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

        disposeDeadEntities();
    }

    @Override
    public void dispose() {
        batch.dispose();
        playerTexture.dispose();
        bulletTexture.dispose();
    }

    void loadTextures() {
        playerTexture = new Texture("player.png");
        bulletTexture = new Texture("bullet.png");
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

    /**
     * Set our camera to display a 2D world, 800x600 pixels large
     * (or whatever the screen size is set to currently)
     */
    private void createCamera() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT);
        camera.update();
    }
}
