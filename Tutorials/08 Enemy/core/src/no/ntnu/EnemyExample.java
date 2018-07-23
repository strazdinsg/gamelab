package no.ntnu;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

/**
 * This example shows how to create an enemy that roams in the game world
 * autonomously.
 * Wiki-page link: https://github.com/strazdinsg/gamelab/wiki/Creating-enemies
 */
public class EnemyExample extends ApplicationAdapter {

    // Screen size, in pixels
    public static final int SCREEN_WIDTH = 800;
    public static final int SCREEN_HEIGHT = 600;

    // Enemy speed, pixels per second
    public static final float ENEMY_SPEED_X = 300;
    public static final float ENEMY_SPEED_Y = -100 - 300 * MathUtils.random();

    // Used for a 2D projection of the game world
    private OrthographicCamera camera;

    // Used to draw 2D textures (sprites) in the world
    private SpriteBatch batch;

    // Coordinates for enemy
    private Enemy enemy;

    @Override
    public void create() {
        createCamera();

        // Prepare to draw 2D sprites
        batch = new SpriteBatch();

        createEnemy();
    }

    @Override
    public void render() {
        // Clear the screen
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Start our sprite drawing
        batch.begin();

        // Call one render/update loop for the enemy. This call will do all 
        // the logic and also drawing of the enemy
        enemy.updateAndRender(batch);

        // We are done with sprite drawing, allow the system to put them on screen
        batch.end();
    }

    @Override
    public void dispose() {
        // Release the reserved memory resources
        enemy.dispose();
    }

    /**
     * Creates an enemy
     */
    private void createEnemy() {
        // Spawn it at the top of the screen, at a random horizontal position
        final int BOUNDARY = 100;
        int enemyX = MathUtils.random(0, SCREEN_WIDTH - BOUNDARY);
        int enemyY = SCREEN_HEIGHT - BOUNDARY;
        enemy = new Enemy("enemy.png", enemyX, enemyY, ENEMY_SPEED_X, ENEMY_SPEED_Y);
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
