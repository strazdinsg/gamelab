package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.material.Material;
import com.jme3.material.RenderState.BlendMode;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.texture.Texture2D;
import com.jme3.ui.Picture;

/**
 * This is the Main Class of your Game. You should only do initialization here.
 * Move your Logic into AppStates or Controls
 *
 * @author normenhansen
 */
public class AppMain extends SimpleApplication {

    // Time between two bullets, in milliseconds
    private final long BULLET_COOLDOWN_MS = 100;

    private long lastBulletReleaseTime = 0;

    private Spatial player;
    private Node bulletNode;

    public static void main(String[] args) {
        AppMain app = new AppMain();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        setUp2DCamera();
        turnOffStats();
        setUpPlayer();
        setUpControls();
        setUpBulletNode();
    }

    private Spatial getSpatial(String name) {
        Node node = new Node(name);
//        load picture
        Picture pic = new Picture(name);
        Texture2D tex = (Texture2D) assetManager.loadTexture("Textures/" + name + ".png");
        pic.setTexture(assetManager, tex, true);

//        adjust picture
        float width = tex.getImage().getWidth();
        float height = tex.getImage().getHeight();
        pic.setWidth(width);
        pic.setHeight(height);
        pic.move(-width / 2f, -height / 2f, 0);

//        add a material to the picture
        Material picMat = new Material(assetManager, "Common/MatDefs/Gui/Gui.j3md");
        picMat.getAdditionalRenderState().setBlendMode(BlendMode.AlphaAdditive);
        node.setMaterial(picMat);

//        set the radius of the spatial
//        (using width only as a simple approximation)
        node.setUserData("radius", (int) width / 2);

//        attach the picture to the node and return it
        node.attachChild(pic);
        return node;
    }

    @Override
    public void simpleUpdate(float tpf) {
        //TODO: add update code
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }

    /**
     * Setup camera for 2D game
     */
    private void setUp2DCamera() {
        cam.setParallelProjection(true);
        cam.setLocation(new Vector3f(0, 0, 0.5f));
        getFlyByCamera().setEnabled(false);
    }

    /**
     * Hide debug and framerate stats
     */
    private void turnOffStats() {
        setDisplayStatView(false);
        setDisplayFps(false);
    }

    /**
     * Set up the player spatial entity
     */
    private void setUpPlayer() {
        player = getSpatial("Player");
        player.setUserData("alive", true);
        player.move(settings.getWidth() / 2, settings.getHeight() / 2, 0);
        guiNode.attachChild(player);
    }

    /**
     * Register keyboard and mouse handlers
     */
    private void setUpControls() {
        // Keyboard handling
        PlayerControl controller = new PlayerControl(this, settings.getWidth(), settings.getHeight());
        player.addControl(controller);
        inputManager.addMapping("left", new KeyTrigger(KeyInput.KEY_LEFT));
        inputManager.addMapping("right", new KeyTrigger(KeyInput.KEY_RIGHT));
        inputManager.addMapping("up", new KeyTrigger(KeyInput.KEY_UP));
        inputManager.addMapping("down", new KeyTrigger(KeyInput.KEY_DOWN));
        inputManager.addMapping("return", new KeyTrigger(KeyInput.KEY_RETURN));
        inputManager.addListener(controller, "left");
        inputManager.addListener(controller, "right");
        inputManager.addListener(controller, "up");
        inputManager.addListener(controller, "down");
        inputManager.addListener(controller, "return");

        // Mouse Left-click handling
        inputManager.addMapping("mousePick", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        inputManager.addListener(controller, "mousePick");
    }

    private void setUpBulletNode() {
        bulletNode = new Node("bullets");
        guiNode.attachChild(bulletNode);
    }

    /**
     * Creates a new bullet
     */
    protected void shootBullet() {
        long t = System.currentTimeMillis();
        // Check if enough time passed since last bulled
        if (t - lastBulletReleaseTime > BULLET_COOLDOWN_MS) {
            lastBulletReleaseTime = System.currentTimeMillis();

            Vector3f aim = getAimDirection();
            Vector3f offset = new Vector3f(aim.y / 3, -aim.x / 3, 0);

            // Initialize bullets: take player position as a starting point,
            // add some offset in the direction of mouse pointer
//                    init bullet 1
            Spatial bullet = getSpatial("Bullet");
            Vector3f finalOffset1 = aim.add(offset).mult(30);
            Vector3f trans = player.getLocalTranslation().add(finalOffset1);
            bullet.setLocalTranslation(trans);
            bullet.addControl(new BulletControl(aim, settings.getWidth(), settings.getHeight()));
            bulletNode.attachChild(bullet);

//                    init bullet 2
            Spatial bullet2 = getSpatial("Bullet");
            Vector3f finalOffset2 = aim.add(offset.negate()).mult(30);
            trans = player.getLocalTranslation().add(finalOffset2);
            bullet2.setLocalTranslation(trans);
            bullet2.addControl(new BulletControl(aim, settings.getWidth(), settings.getHeight()));
            bulletNode.attachChild(bullet2);
        }
    }

    /**
     * Calculate the aiming direction based on player and mouse positions
     * @return 
     */
    private Vector3f getAimDirection() {
        Vector2f mouse = inputManager.getCursorPosition();
        Vector3f playerPos = player.getLocalTranslation();
        Vector3f dif = new Vector3f(mouse.x - playerPos.x, mouse.y - playerPos.y, 0);
        return dif.normalizeLocal();
    }

}
