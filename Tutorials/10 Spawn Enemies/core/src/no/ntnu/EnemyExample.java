package no.ntnu;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
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

    // Number of enemies of each type
    public static final int STRAIGHT_ENEMY_COUNT = 3;
    public static final int JITTERING_ENEMY_COUNT = 2;

    // Used for a 2D projection of the game world
    private OrthographicCamera camera;

    // Used to draw 2D textures (sprites) in the world
    private SpriteBatch batch;

    // Coordinates for enemy
    private List<Enemy> enemies;

    @Override
    public void create() {
        createCamera();
        // Prepare to draw 2D sprites
        batch = new SpriteBatch();
        createEnemies();
    }

    @Override
    public void render() {
        // Clear the screen
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Start our sprite drawing
        batch.begin();

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
     * Creates the enemies
     */
    private void createEnemies() {
        enemies = new ArrayList<Enemy>();

        // Let's create some straight-moving enemies
        for (int i = 1; i <= STRAIGHT_ENEMY_COUNT; ++i) {
            // Spawn enemies at random position, with random speed
            float enemyX = MathUtils.random(MIN_X, MAX_X);
            float enemyY = MathUtils.random(MIN_Y, MAX_Y);
            // Movement allowed both ways - also in negative direction
            float speedX = MathUtils.random(-MAX_SPEED_X, MAX_SPEED_X);
            float speedY = MathUtils.random(-MAX_SPEED_Y, MAX_SPEED_Y);
            Enemy e = new Enemy("enemy.png", enemyX, enemyY);
            e.setMovementAI(new StraightMovementAI(enemyX, enemyY,
                    MIN_X, MAX_X, MIN_Y, MAX_Y, speedX, speedY));
            enemies.add(e);
        }

        // Let's create some jittering enemies
        for (int i = 1; i <= JITTERING_ENEMY_COUNT; ++i) {
            // Spawn enemies at random position, with random speed
            float enemyX = MathUtils.random(MIN_X, MAX_X);
            float enemyY = MathUtils.random(MIN_Y, MAX_Y);
            // Movement allowed both ways - also in negative direction
            Enemy e = new Enemy("zombie.png", enemyX, enemyY);
            e.setMovementAI(new JitteringMovementAI(enemyX, enemyY,
                    MIN_X, MAX_X, MIN_Y, MAX_Y, MAX_SPEED_X, MAX_SPEED_Y));
            enemies.add(e);
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
