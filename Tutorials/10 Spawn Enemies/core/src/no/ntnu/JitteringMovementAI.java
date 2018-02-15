package no.ntnu;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

/**
 * An AI that jitters the enemy in random directions
 */
public class JitteringMovementAI extends MovementAI {

    // Speed of the enemy, pixels per second
    private final Vector2 MAX_SPEED;

    /**
     * @param currentX current X position of the enemy
     * @param currentY current Y position of the enemy
     * @param minX minimum allowed X position of the enemy
     * @param maxX maximum allowed X position of the enemy
     * @param minY minimum allowed Y position of the enemy
     * @param maxY maximum allowed Y position of the enemy
     * @param maxSpeedX maximum movement speed X component
     * @param maxSpeedY maximum movement speed Y component
     */
    public JitteringMovementAI(float currentX, float currentY, float minX,
            float maxX, float minY, float maxY, float maxSpeedX, float maxSpeedY) {
        super(currentX, currentY, minX, maxX, minY, maxY);
        this.MAX_SPEED = new Vector2(maxSpeedX, maxSpeedY);
    }

    /**
     * One movement step for the enemy.
     * This method will be called once on every frame.
     *
     * @param fdt Frame delta time - time since the last rendered frame, seconds
     * @return the desired new position of the enemy
     */
    @Override
    public Vector2 getNextPosition(float fdt) {
        // Select this movement step
        Vector2 delta = new Vector2(MathUtils.random(-MAX_SPEED.x, MAX_SPEED.x),
                MathUtils.random(-MAX_SPEED.y, MAX_SPEED.y));
        // Scale the speed according to framerate 
        currentPos.mulAdd(delta, fdt);

        // When screen boundary hit, reverse the speed
        if (currentPos.x < minX) {
            currentPos.x = minX;
        }
        if (currentPos.x > maxX) {
            currentPos.x = maxX;
        }
        if (currentPos.y < minY) {
            currentPos.y = minY;
        }
        if (currentPos.y > maxY) {
            currentPos.y = maxY;
        }

        return currentPos;
    }

}
