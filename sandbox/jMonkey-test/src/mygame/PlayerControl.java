package mygame;

import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;

/**
 * Movement controller for the player
 */
public class PlayerControl extends AbstractControl implements ActionListener, AnalogListener {
    // Some distance from the sides of the screen that playe is not allowed to move into
    private final static int SAFETY_BOUNDARY = 20;
    
    // Speed of the player
    private final float SPEED = 500f;
    private final AppMain app;
    
    private final int screenWidth;
    private final int screenHeight;

    private boolean up;
    private boolean down;
    private boolean left;
    private boolean right;

    private int radius = -1;
    private int maxX = -1;
    private int minX = -1;
    private int maxY = -1;
    private int minY = -1;
    
    

    private float currentRotation = FastMath.PI;

    public PlayerControl(AppMain app, int screenWidth, int screenHeight) {
        this.app = app;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }

    /**
     * Called in each frame
     *
     * @param tpf time per frame
     */
    @Override
    protected void controlUpdate(float tpf) {
        if (radius < 0) {
            recalcBoundingBox();
        }
        // Get current position
        Vector3f currentPos = spatial.getLocalTranslation();
        int currentX = (int) currentPos.x;
        int currentY = (int) currentPos.y;

        if (up) {
            // -- in vertical direction, rotate 90 degrees (pi / 2)
            if (currentY < maxY) {
                spatial.move(0, tpf * SPEED, 0);
            }
            rotate(FastMath.PI / 2.0f);
        }
        if (down) {
            // ++ in vertical direction, rotate 270 degrees (1.5 pi)
            if (currentY > minY) {
                spatial.move(0, tpf * -SPEED, 0);
            }
            rotate(1.5f * FastMath.PI);
        }
        if (left) {
            // -- in horizontal direction, rotate 180 degrees (pi)
            if (currentX > minX) {
                spatial.move(tpf * -SPEED, 0, 0);
            }
            rotate(FastMath.PI);

        }
        if (right) {
            // ++ in horizontal direction, rotate 0 degrees
            if (currentX < maxX) {
                spatial.move(tpf * SPEED, 0, 0);
            }
            rotate(0);
        }
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
    }

    /**
     * Recalculate the bounding box - min and max (x, y) coordinates of the
     * spatial
     */
    private void recalcBoundingBox() {
        if (spatial == null) {
            return;
        }
        if (radius < 0) {
            radius = spatial.getUserData("radius");
        }
        minX = radius + SAFETY_BOUNDARY;
        minY = radius + SAFETY_BOUNDARY;
        maxX = screenWidth - radius - SAFETY_BOUNDARY;
        maxY = screenHeight - radius - SAFETY_BOUNDARY;
    }

    /**
     * Rotate picture of the spatial object by given angle (in z axis)
     *
     * @param angle in radians
     */
    private void rotate(float angle) {
        // We have to calculate difference between the current angle and desired angle
        float angleDiff = angle - currentRotation;
        spatial.rotate(0, 0, angleDiff);
        currentRotation = angle;
    }

    /**
     * This method is called when a key-press event arrives
     *
     * @param name
     * @param isPressed
     * @param tpf
     */
    @Override
    public void onAction(String name, boolean isPressed, float tpf) {
        if (app.isPlayerAlive()) {
            // Check pressed key
            if (name.equals("up")) {
                this.up = isPressed;
            } else if (name.equals("down")) {
                this.down = isPressed;
            } else if (name.equals("left")) {
                this.left = isPressed;
            } else if (name.equals("right")) {
                this.right = isPressed;
            }
        }
    }

    /**
     * This method is called when a mouse-click event arrives
     *
     * @param name
     * @param value
     * @param tpf
     */
    @Override
    public void onAnalog(String name, float value, float tpf) {
        if (app.isPlayerAlive()) {
            if (name.equals("mousePick")) {
                app.shootBullet();
            }
        }
    }

    public void reset() {
        up = down = left = right = false;
    }
}
