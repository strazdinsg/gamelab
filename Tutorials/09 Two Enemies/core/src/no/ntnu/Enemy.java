package no.ntnu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Represents one enemy in the game world: it's location, speed, texture, etc
 */
public class Enemy {

    // Texture for enemy
    private final Texture texture;
    private final Sprite sprite;

    /* We use the Entity-Component-System pattern 
     * https://en.wikipedia.org/wiki/Entity%E2%80%93component%E2%80%93system
     * rather than having subclasses for Enemy, we attach the movement 
     * behaviour as  a component for the enemy.
     */
    private MovementAI movementAI;

    /**
     * Initialize the enemy
     *
     * @param textureFilePath path to image file used as the texture
     * @param x initial x coordinate of the enemy
     * @param y initial y coordinate of the enemy
     */
    public Enemy(String textureFilePath, float x, float y) {
        texture = new Texture(textureFilePath);
        sprite = new Sprite(texture);
        sprite.setSize(texture.getWidth(), texture.getHeight());
        moveTo(x, y);
    }

    /**
     * Get the AI controlling enemy movement
     *
     * @return AI or null if none set
     */
    public MovementAI getMovementAI() {
        return movementAI;
    }

    /**
     * Set AI that will control the movement of the enemy
     *
     * @param ai
     */
    public void setMovementAI(MovementAI ai) {
        this.movementAI = ai;
    }

    /**
     * This method must be called in every rendering step of the game
     *
     * @param batch
     */
    public void updateAndRender(SpriteBatch batch) {
        // Allow the movementAI to decide the new position of the enemy
        if (movementAI != null) {
            float secondsSinceLastFrame = Gdx.graphics.getDeltaTime();
            Vector2 newPosition = movementAI.getNextPosition(
                    secondsSinceLastFrame);
            if (newPosition != null) {
                moveTo(newPosition.x, newPosition.y);
            }
        }

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
     * Release the resources reserved for the enemy
     */
    public void dispose() {
        texture.dispose();
    }
}
