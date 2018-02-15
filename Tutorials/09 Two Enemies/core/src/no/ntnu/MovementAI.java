package no.ntnu;

import com.badlogic.gdx.math.Vector2;

/**
 * Represents Artificial Intelligence (AI) controlling enemy movement
 */
public abstract class MovementAI {

    // Allowed boundaries for enemy position
    protected final float minX;
    protected final float maxX;
    protected final float minY;
    protected final float maxY;

    // Current location of the enemy
    protected final Vector2 currentPos;

    /**
     * Initializes the AI
     *
     * @param currentX current X position of the enemy
     * @param currentY current Y position of the enemy
     * @param minX minimum X position of the enemy
     * @param maxX maximum X position of the enemy
     * @param minY minimum Y position of the enemy
     * @param maxY maximum Y position of the enemy
     */
    public MovementAI(float currentX, float currentY, float minX, float maxX,
            float minY, float maxY) {
        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY = maxY;
        this.currentPos = new Vector2(currentX, currentY);
    }

    /**
     * One movement step for the enemy.
     * This method will be called once on every frame.
     *
     * @param fdt Frame delta time - time since the last rendered frame, seconds
     * @return the desired new position of the enemy
     */
    public abstract Vector2 getNextPosition(float fdt);

}
