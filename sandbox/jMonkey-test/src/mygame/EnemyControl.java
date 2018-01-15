package mygame;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.scene.control.AbstractControl;
import com.jme3.ui.Picture;

/**
 * Contoller implementing behavior for an enemy
 * 
 * The code is adapted and refactored from Tutorial "Make a Neon Vector Shooter in jMonkeyEngine"
 * https://gamedevelopment.tutsplus.com/series/cross-platform-vector-shooter-jmonkeyengine--gamedev-13757
 */
public abstract class EnemyControl extends AbstractControl {

    // Time period when enemy is initializing itself (fading in) after spawn
    private static final float ACTIVATION_TIME = 1000f;

    // Enemy initial movement speed, relative to distance to the player
    private static final float SPEED = 0.1f;

    protected Vector3f velocity;
    private final long spawnTime;

    private final String spatialName;

    /**
     * Create enemy controller
     *
     * @param spatialName name of the spatial, used to reference Picture object
     */
    public EnemyControl(String spatialName) {
        velocity = new Vector3f(0, 0, 0);
        spawnTime = System.currentTimeMillis();
        this.spatialName = spatialName;
    }

    /**
     * Update the current velocity vector
     * @param tpf time per frame
     */
    protected abstract void updateVelocity(float tpf);

    /**
     * Do some updates after movement, if necessary
     * @param tpf time per frame
     */
    protected abstract void updateAfterMovement(float tpf);

    @Override
    protected void controlUpdate(float tpf) {
        if ((Boolean) spatial.getUserData("active")) {
            // Enemy is activated            
            updateVelocity(tpf);
            velocity.multLocal(0.8f); // Every time velocity is decreased by 20%
            spatial.move(velocity.mult(tpf * SPEED)); // Move the enemy
            updateAfterMovement(tpf);
        } else {
            // Fade in enemy, inactive for now
            float timeSinceSpawn = System.currentTimeMillis() - spawnTime;
            if (timeSinceSpawn >= ACTIVATION_TIME) {
                spatial.setUserData("active", true);
            }

            ColorRGBA color = new ColorRGBA(1, 1, 1, timeSinceSpawn / ACTIVATION_TIME);
            Node spatialNode = (Node) spatial;
            Picture pic = (Picture) spatialNode.getChild(spatialName);
            pic.getMaterial().setColor("Color", color);
        }
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
    }
}
