package no.ntnu.entities;

        import com.badlogic.gdx.Gdx;
        import com.badlogic.gdx.graphics.Texture;
        import com.badlogic.gdx.graphics.g2d.Sprite;
        import com.badlogic.gdx.graphics.g2d.SpriteBatch;
        import no.ntnu.ShooterGame;

/**
 * Represents one enemy in the game world: it's location, speed, texture, etc
 */
public class Enemy {

    // How many pixels from the sides the enemy is not allowed to step into
    private static final float SAFE_BOUNDARY = 100;
    private static final float MIN_X = SAFE_BOUNDARY;
    private static final float MIN_Y = SAFE_BOUNDARY;
    private static final float MAX_X = ShooterGame.SCREEN_WIDTH - SAFE_BOUNDARY;
    private static final float MAX_Y = ShooterGame.SCREEN_HEIGHT - SAFE_BOUNDARY;

    // Texture for enemy
    private final Texture texture;
    private final Sprite sprite;

    // Speed of the enemy, pixels per second
    private float speedX;
    private float speedY;

    /**
     * Initialize the enemy
     *
     * @param textureFilePath path to image file used as the texture
     * @param x initial x coordinate of the enemy
     * @param y initial y coordinate of the enemy
     * @param speedX initial horizontal speed of the enemy, pixels per second.
     * Positive value goes to the right, negative: to the left
     * @param speedY initial vertical speed of the enemy, pixels per second.
     * Positive value goes up, negative: down
     */
    public Enemy(String textureFilePath, float x, float y, float speedX, float speedY) {
        texture = new Texture(textureFilePath);
        sprite = new Sprite(texture);
        sprite.setSize(texture.getWidth(), texture.getHeight());
        moveTo(x, y);
        this.speedX = speedX;
        this.speedY = speedY;
    }

    /**
     * This method must be called in every rendering step of the game
     *
     * @param batch
     */
    public void updateAndRender(SpriteBatch batch) {
        doMovement();
        // We add the sprite to the spritebatch
        sprite.draw(batch);
    }

    /**
     * Move the enemy to position x, y
     *
     * @param x
     * @param y
     */
    private void moveTo(float x, float y) {
        sprite.setPosition(x, y);
    }

    /**
     * Moves the enemy according to its AI algorithms
     */
    private void doMovement() {
        // Move according to the speed
        float x = sprite.getX();
        float y = sprite.getY();

        // Scale the speed according to framerate
        float secondsSinceLastFrame = Gdx.graphics.getDeltaTime();
        x += speedX * secondsSinceLastFrame;
        y += speedY * secondsSinceLastFrame;

        moveTo(x, y);

        // When screen boundary hit, reverse the speed
        if ((x < 25 && speedX < 0)
                || (x > ShooterGame.SCREEN_WIDTH - 150 - sprite.getWidth() && speedX > 0)) {
            speedX = -speedX;
        }
        if ((y < 25 && speedY < 0)
                || (y > MAX_Y - sprite.getHeight() && speedY > 0)) {
            speedY = -speedY;
        }
    }

    /**
     * Release the resources reserved for the enemy
     */
    public void dispose() {
        texture.dispose();
    }
}