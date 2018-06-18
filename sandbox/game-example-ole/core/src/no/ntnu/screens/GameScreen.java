package no.ntnu.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.TimeUtils;
import no.ntnu.ShooterGame;
import no.ntnu.entities.Enemy;
import no.ntnu.entities.Entity;
import no.ntnu.entities.Player;
import no.ntnu.scenes.Hud;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GameScreen implements Screen{

    private ShooterGame instance;

    private static final float SPAWN_PROBABILITY = 0.3f;

    // Max number of enemies
    private static final int MAX_ENEMIES = 10;

    // Current number of enemies
    private int enemyCount = 0;

    // Time when the last enemy was spawned
    private long lastSpawnTime = 0;

    // How many milliseconds must pass between two spawns of enemies
    private static final long SPAWN_COOLDOWN_TIME = 3000;

    // Coordinates for enemy
    private final List<Enemy> enemies = new ArrayList<Enemy>();

    // Enemy speed, pixels per second
    public static final float ENEMY_SPEED_X = 100;
    public static final float ENEMY_SPEED_Y = -100 - 100 * MathUtils.random();

    // Coordinates for enemy
    private Enemy enemy;

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

        createEnemy();
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

        // Call one render/update loop for the enemy. This call will do all
        // the logic and also drawing of the enemy
        trySpawnEnemies();

        // Call one render/update loop for each enemy. This call will do all
        // the logic and also drawing of the enemies
        for (Enemy enemy : enemies) {
            enemy.updateAndRender(batch);
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

        // Release the reserved memory resources
        for (Enemy enemy : enemies) {
            enemy.dispose();
        }
    }

    /**
     * Creates an enemy
     */
    private void createEnemy() {
        // Spawn it at the top of the screen, at a random horizontal position
        final int BOUNDARY = 200;
        int enemyX = MathUtils.random(0, ShooterGame.SCREEN_WIDTH - BOUNDARY);
        int enemyY = MathUtils.random(0,ShooterGame.SCREEN_HEIGHT - BOUNDARY);
        enemy = new Enemy("enemy.png", enemyX, enemyY, ENEMY_SPEED_X, ENEMY_SPEED_Y);
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

    /**
     * Spawn enemies with a certain probability
     */
    private void trySpawnEnemies() {
        // Check if enough time passed since last spawn
        long currentTime = TimeUtils.millis();
        if (currentTime - lastSpawnTime < SPAWN_COOLDOWN_TIME) {
            return;
        }

        // Throw a die - if it is withing the allowed probability range,
        // spawn an enemy of given type
        float die = MathUtils.random(1.0f);
        // Adjust the probability to the framerate, to get 1/second unit
        die /= Gdx.graphics.getDeltaTime();

        // We should start with the smallest probability first, otherwise the
        // most rare enemy will never be spawned
        if (die <= SPAWN_PROBABILITY
                && enemyCount < MAX_ENEMIES) {
            spawnEnemy("enemy.png");
            ++enemyCount;
            lastSpawnTime = TimeUtils.millis();
        }
    }

    /**
     * Spawn enemy with given texture and movement patter
     *
     * @param texture
     * @param movesStraight when true, spawn a straight-moving enemy; a
     * jittery one otherwise
     */
    private void spawnEnemy(String texture) {
        // Spawn enemies at random position, with random speed
        final int BOUNDARY = 200;
        int enemyX = MathUtils.random(0, ShooterGame.SCREEN_WIDTH - BOUNDARY);
        int enemyY = MathUtils.random(0,ShooterGame.SCREEN_HEIGHT - BOUNDARY);
        Enemy e = new Enemy(texture, enemyX, enemyY, ENEMY_SPEED_X, ENEMY_SPEED_Y);
        enemies.add(e);
    }

}
