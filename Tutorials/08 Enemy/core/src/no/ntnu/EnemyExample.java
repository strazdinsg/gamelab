package no.ntnu;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

/**
 * This example shows how to create an enemy that roams in the game world
 * autonomously.
 */
public class EnemyExample extends ApplicationAdapter {

    // Screen size, in pixels
    public static final int SCREEN_WIDTH = 800;
    public static final int SCREEN_HEIGHT = 600;

    // Enemy size, in pixels
    private static final int ENEMY_WIDTH = 64;
    private static final int ENEMY_HEIGHT = 54;

    // Used for a 2D projection of the game world
    private OrthographicCamera camera;

    // Used to draw 2D textures (sprites) in the world
    private SpriteBatch batch;

    // Coordinates for enemy
    Rectangle enemy; 
    
    // Texture for enemy
    Texture enemyImage;

    @Override
    public void create() {
        // Set our camera to display a 2D world, 800x600 pixels large 
        // (or whatever the screen size is set to curently)
        camera = new OrthographicCamera();
        camera.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT);
        camera.update();

        // Prepare to draw 2D sprites
        batch = new SpriteBatch();
        
        // Load texture for the enemy
        enemyImage = new Texture("enemy.png");

        spawnEnemy();
    }

    @Override
    public void render() {
        // Start our sprite drawing
        batch.begin();
        batch.draw(enemyImage, enemy.x, enemy.y);
        // We are done with sprite drawing, allow the system to put them on screen
        batch.end();
    }

    @Override
    public void dispose() {
    }

    /**
     * Creates an enemy
     */
    private void spawnEnemy() {
        enemy = new Rectangle();
        // Position the enemy in the top of the screen, randomly on the X axis
        enemy.x = MathUtils.random(0, SCREEN_WIDTH - ENEMY_WIDTH);
        enemy.y = SCREEN_HEIGHT - ENEMY_HEIGHT;
        enemy.width = ENEMY_WIDTH;
        enemy.height = ENEMY_HEIGHT;
    }
}
