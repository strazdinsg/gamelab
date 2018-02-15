package no.ntnu;

import com.badlogic.gdx.math.Vector2;

/**
 * An AI that moves an enemy straight following a given start speed vector. When
 * a min/max boundary is hit, the speed is reversed.
 */
public class StraightMovementAI extends MovementAI {

    // Speed of the enemy, pixels per second
    private final Vector2 speed;

    /**
     * @param currentX current X position of the enemy
     * @param currentY current Y position of the enemy
     * @param minX minimum allowed X position of the enemy
     * @param maxX maximum allowed X position of the enemy
     * @param minY minimum allowed Y position of the enemy
     * @param maxY maximum allowed Y position of the enemy
     * @param speedX the initial movement speed X component
     * @param speedY the initial movement speed Y component
     */
    public StraightMovementAI(float currentX, float currentY, float minX,
            float maxX, float minY, float maxY, float speedX, float speedY) {
        super(currentX, currentY, minX, maxX, minY, maxY);
        this.speed = new Vector2(speedX, speedY);
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
        // Scale the speed according to framerate        
        currentPos.x += speed.x * fdt;
        currentPos.y += speed.y * fdt;

        // When screen boundary hit, reverse the speed
        if ((currentPos.x < minX && speed.x < 0)
                || (currentPos.x > maxX && speed.x > 0)) {
            speed.x = -speed.x;
        }
        if ((currentPos.y < minY && speed.y < 0)
                || (currentPos.y > maxY && speed.y > 0)) {
            speed.y = -speed.y;
        }

        return currentPos;
    }

}
