package mygame;

import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;

/**
 * Contoller implementing behavior for Wanderer enemy: random movement
 */
public class WandererControl extends EnemyControl {

    private float directionAngle;
    private final int screenWidth;
    private final int screenHeight;

    /**
     * Create enemy controller
     *
     * @param spatialName name of the spatial, used to reference Picture object
     * @param screenWidth
     * @param screenHeight
     */
    public WandererControl(String spatialName, int screenWidth, int screenHeight) {
        super(spatialName);
        // Randomize initial rotation angle from 0 to 2 Pi
        directionAngle = Helper.RANDOMIZER.nextFloat() * 2f * FastMath.PI;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }

    /**
     * Update the current velocity vector
     *
     * @param tpf time per frame
     */
    @Override
    protected void updateVelocity(float tpf) {
        // change the directionAngle a bit
        directionAngle += (Helper.RANDOMIZER.nextFloat() * 20f - 10f) * tpf;
        Vector3f directionVector = Helper.getVectorFromAngle(directionAngle);
        directionVector.multLocal(1000f);
        velocity.addLocal(directionVector);
    }

    @Override
    protected void updateAfterMovement(float tpf) {
        // make the wanderer bounce off the screen borders
        Vector3f loc = spatial.getLocalTranslation();
        if (loc.x > screenWidth || loc.y > screenHeight || loc.x < 0 || loc.y < 0) {
            // New direction
            Vector3f newDir = new Vector3f(screenWidth / 2, screenHeight / 2, 0).subtract(loc);
            directionAngle = Helper.getAngleFromVector(newDir);
        }

        // rotate the wanderer
        spatial.rotate(0, 0, tpf * 2);
    }

}
