package mygame;

import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;

/**
 * Controls bullet behaviour (movement)
 */
public class BulletControl extends AbstractControl {

    private int screenWidth, screenHeight;

    private final static float SPEED = 1100f;
    public Vector3f direction;
    public float desiredAngle;
    private float actualAngle;

    public BulletControl(Vector3f direction, int screenWidth, int screenHeight) {
        this.direction = direction;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.desiredAngle = Helper.getAngleFromVector(direction);
    }

    @Override
    protected void controlUpdate(float tpf) {
//        movement
        spatial.move(direction.mult(SPEED * tpf));

//        rotation
        if (desiredAngle != actualAngle) {
            spatial.rotate(0, 0, desiredAngle - actualAngle);
            actualAngle = desiredAngle;
        }

//        check boundaries
        Vector3f loc = spatial.getLocalTranslation();
        if (loc.x > screenWidth
                || loc.y > screenHeight
                || loc.x < 0
                || loc.y < 0) {
            spatial.removeFromParent();
        }
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
    }
}
