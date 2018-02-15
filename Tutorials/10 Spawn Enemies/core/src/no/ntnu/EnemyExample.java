package no.ntnu;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.TimeUtils;
import java.util.ArrayList;
import java.util.List;

/**
 * This example shows how to spawn enemies in random locations, with random AI
 * and at random time intervals
 */
public class EnemyExample extends ApplicationAdapter {

    // Screen size, in pixels
    public static final int SCREEN_WIDTH = 800;
    public static final int SCREEN_HEIGHT = 600;

    // Enemy max speed, pixels per second
    public static final int MAX_SPEED_X = 300;
    public static final int MAX_SPEED_Y = 300;

    // How many pixels from the sides the enemy is not allowed to step into
    private static final float SAFE_BOUNDARY = 80;

    // Allowed boundaries for enemy movement
    private static final float MIN_X = SAFE_BOUNDARY;
    private static final float MIN_Y = SAFE_BOUNDARY;
    private static final float MAX_X = SCREEN_WIDTH - SAFE_BOUNDARY;
    private static final float MAX_Y = SCREEN_HEIGHT - SAFE_BOUNDARY;

    // Probability of spawning each type of enemy: odds to spawn it once per second
    // Each second we have X% chance to spawn a straight-moving enemy 
    // and Y% chance to spawn a jittering enemy
    private static final float STRAIGHT_SPAWN_PROBABILITY = 0.3f;
    private static final float JITTER_SPAWN_PROBABILITY = 0.1f;

    // Max number of enemies of each type
    private static final int MAX_STRAIGHT_ENEMIES = 10;
    private static final int MAX_JITTERING_ENEMIES = 6;

    // Current number of enemies
    private int straightEnemyCount = 0;
    private int jitteringEnemyCount = 0;

    // Time when the last enemy was spawned
    private long lastSpawnTime = 0;

    // How many milliseconds must pass between two spawns of enemies
    private static final long SPAWN_COOLDOWN_TIME = 3000;

    // Used for a 2D projection of the game world
    private OrthographicCamera camera;

    // Used to draw 2D textures (sprites) in the world
    private SpriteBatch batch;

    // Coordinates for enemy
    private final List<Enemy> enemies = new ArrayList<Enemy>();

    @Override
    public void create() {
        createCamera();
        // Prepare to draw 2D sprites
        batch = new SpriteBatch();
    }

    @Override
    public void render() {
        // Clear the screen
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Start our sprite drawing
        batch.begin();

        trySpawnEnemies();

        // Call one render/update loop for each enemy. This call will do all 
        // the logic and also drawing of the enemies
        for (Enemy enemy : enemies) {
            enemy.updateAndRender(batch);
        }

        // We are done with sprite drawing, allow the system to put them on screen
        batch.end();
    }

    @Override
    public void dispose() {
        // Release the reserved memory resources
        for (Enemy enemy : enemies) {
            enemy.dispose();
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
        if (die <= JITTER_SPAWN_PROBABILITY
                && jitteringEnemyCount < MAX_JITTERING_ENEMIES) {
            spawnEnemy("zombie.png", false);
            ++jitteringEnemyCount;
            lastSpawnTime = TimeUtils.millis();
        } else if (die <= STRAIGHT_SPAWN_PROBABILITY
                && straightEnemyCount < MAX_STRAIGHT_ENEMIES) {
            spawnEnemy("enemy.png", true);
            ++straightEnemyCount;
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
    private void spawnEnemy(String texture, boolean movesStraight) {
        // Spawn enemies at random position, with random speed
        float enemyX = MathUtils.random(MIN_X, MAX_X);
        float enemyY = MathUtils.random(MIN_Y, MAX_Y);
        // Movement allowed both ways - also in negative direction
        float speedX = MathUtils.random(-MAX_SPEED_X, MAX_SPEED_X);
        float speedY = MathUtils.random(-MAX_SPEED_Y, MAX_SPEED_Y);
        Enemy e = new Enemy(texture, enemyX, enemyY);
        MovementAI ai;
        if (movesStraight) {
            ai = new StraightMovementAI(enemyX, enemyY, MIN_X, MAX_X, MIN_Y,
                    MAX_Y, speedX, speedY);
        } else {
            ai = new JitteringMovementAI(enemyX, enemyY, MIN_X, MAX_X, MIN_Y,
                    MAX_Y, MAX_SPEED_X, MAX_SPEED_Y);
        }
        e.setMovementAI(ai);
        enemies.add(e);
    }
}
