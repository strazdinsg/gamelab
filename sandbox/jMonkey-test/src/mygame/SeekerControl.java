package mygame;

import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

/**
 * Contoller implementing behavior for Seeker enemy: follow the player
 */
public class SeekerControl extends EnemyControl {
    private static final float ANGLE_DEVIATION = 100f;
    
    private final Spatial player;

    /**
     * Create enemy controller
     *
     * @param player
     * @param spatialName name of the spatial, used to reference Picture object
     */
    public SeekerControl(Spatial player, String spatialName) {
        super(spatialName);
        this.player = player;
    }

    /**
     * Update the current velocity vector
     *
     * @param tpf time per frame
     */
    @Override
    protected void updateVelocity(float tpf) {
        // Rind seekers position relative to the player
        Vector3f playerDirection = player.getLocalTranslation().subtract(spatial.getLocalTranslation());
        playerDirection.normalizeLocal();
        playerDirection.multLocal(1000f);
        velocity = velocity.addLocal(playerDirection);
    }

    /**
     * Update the enemy after movement is already made
     *
     * @param tpf time per frame
     */
    @Override
    protected void updateAfterMovement(float tpf) {
        // Rotate the seeker
        if (velocity != Vector3f.ZERO) {
            spatial.rotateUpTo(velocity.normalize());
            // Rotate 90 degrees (seeker in PNG is pointed to the right, not upwards)
            spatial.rotate(0, 0, FastMath.PI / 2f);
        }
    }
}
