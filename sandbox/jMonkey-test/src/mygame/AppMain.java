package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.material.Material;
import com.jme3.material.RenderState.BlendMode;
import com.jme3.math.FastMath;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.Control;
import com.jme3.texture.Texture2D;
import com.jme3.ui.Picture;

/**
 * This is the Main Class of your Game. You should only do initialization here.
 *
 * The code is adapted and refactored from Tutorial "Make a Neon Vector Shooter
 * in jMonkeyEngine"
 * https://gamedevelopment.tutsplus.com/series/cross-platform-vector-shooter-jmonkeyengine--gamedev-13757
 */
public class AppMain extends SimpleApplication {

    // Time between two bullets, in milliseconds
    private final long BULLET_COOLDOWN_MS = 200;
    // Time between two enemy spawns
    private final long ENEMY_SPAWN_COOLDOWN_MS = 20;
    // Time between two black holes
    private final long BLACK_HOLE_COOLDOWN_MS = 20;
    // Respawn timeout after players death
    private final long PLAYER_RESPAWN_TIMEOUT_MS = 2000;

    // Min distance between enemy and player, when a new enemy is spawned
    private final long MIN_ENEMY_PLAYER_DIST = 100;

    // Max number of enemies that we can simultaneously have
    private final int MAX_ENEMIES = 10;

    // Max number of black holes that we can simultaneously have
    private final int MAX_BLACK_HOLES = 2;

    // Number of lives for the player
    private final int PLAYER_LIVES = 3;

    // Odds that in next spawn cycle we create an enemy (1/100)
    private float enemySpawnChance = 100f;

    // Odds that in next spawn cycle we create a black hole (1/200)
    private float blackHoleSpawnChance = 20f;

    private long lastBulletReleaseTime = 0;
    private long lastEnemySpawnTime = 0;
    private long lastBlackHoleSpawnTime = 0;

    // The number of lives left until the game is over
    private int livesLeft = PLAYER_LIVES;

    private Spatial player;
    private Node bulletNode;
    private Node enemyNode;
    private Node blackHoleNode;

    private Sound sound;

    public static void main(String[] args) {
        AppMain app = new AppMain();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        setUpMusic();
        setUp2DCamera();
        turnOffStats();
        setUpPlayer();
        setUpControls();
        setUpBullets();
        setUpEnemies();
        setUpBlackHoles();
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

    /**
     * This method is called on each game cycle
     *
     * @param tpf
     */
    @Override
    public void simpleUpdate(float tpf) {
        if (isPlayerAlive()) {
            spawnEnemies();
            spawnBlackHoles();
            checkCollisions();
        } else {
            // If time to respawn
            long currentTime = System.currentTimeMillis();
            long dieTime = player.getUserData("dieTime");
            if (livesLeft > 0 && currentTime - dieTime >= PLAYER_RESPAWN_TIMEOUT_MS) {
                // spawn player
                int centerX = settings.getWidth() / 2;
                int centerY = settings.getHeight() / 2;
                player.setLocalTranslation(centerX, centerY, 0);
                guiNode.attachChild(player);
                player.setUserData("alive", true);
            }
        }
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
        sound.spawn();
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
        inputManager.addMapping("left", new KeyTrigger(KeyInput.KEY_A));
        inputManager.addMapping("right", new KeyTrigger(KeyInput.KEY_D));
        inputManager.addMapping("up", new KeyTrigger(KeyInput.KEY_W));
        inputManager.addMapping("down", new KeyTrigger(KeyInput.KEY_S));
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

    private void setUpBullets() {
        bulletNode = new Node("bullets");
        guiNode.attachChild(bulletNode);
    }

    private void setUpEnemies() {
        enemyNode = new Node("enemies");
        guiNode.attachChild(enemyNode);
    }

    private void setUpBlackHoles() {
        blackHoleNode = new Node("blackHoles");
        guiNode.attachChild(blackHoleNode);
    }
    
    private void setUpMusic() {
        sound = new Sound(assetManager);
        sound.startMusic();
    }

    /**
     * Returns true if the player is initalized and alive
     *
     * @return
     */
    public boolean isPlayerAlive() {
        if (player == null) {
            return false;
        }
        return (Boolean) player.getUserData("alive");
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

            sound.shoot();
        }
    }

    /**
     * Spawn next enemies if we need to
     */
    private void spawnEnemies() {
        long t = System.currentTimeMillis();
        // Check if enough time elapsed since previous enemy spawn
        if (t - lastEnemySpawnTime >= ENEMY_SPAWN_COOLDOWN_MS) {
            lastEnemySpawnTime = t;

            if (enemyNode.getQuantity() < MAX_ENEMIES) {
                // Probabilistic enemy spawning
                int r = Helper.RANDOMIZER.nextInt((int) enemySpawnChance);
                if (r == 0) {
                    createSeeker();
                }
                r = Helper.RANDOMIZER.nextInt((int) enemySpawnChance);
                if (r == 0) {
                    createWanderer();
                }
            }
            // Increase the chance of spawning enemies: decrease time between two spawns 
            // as the game progresses
            if (enemySpawnChance >= 1.1f) {
                enemySpawnChance -= 0.005f;
            }
        }
    }

    /**
     * Spawn next black hole if we need to
     */
    private void spawnBlackHoles() {
        long t = System.currentTimeMillis();
        // Check if enough time elapsed since previous black hole spawn
        if (t - lastBlackHoleSpawnTime >= BLACK_HOLE_COOLDOWN_MS) {
            lastBlackHoleSpawnTime = t;

            if (blackHoleNode.getQuantity() < MAX_BLACK_HOLES) {
                // Probabilistic black hole spawning
                int r = Helper.RANDOMIZER.nextInt((int) blackHoleSpawnChance);
                if (r == 0) {
                    createBlackHole();
                }
            }
            // Increase the chance of spawning black hole: decrease time between two spawns 
            // as the game progresses
            if (blackHoleSpawnChance >= 1.1f) {
                blackHoleSpawnChance -= 0.005f;
            }
        }
    }

    /**
     * Calculate the aiming direction based on player and mouse positions
     *
     * @return
     */
    private Vector3f getAimDirection() {
        Vector2f mouse = inputManager.getCursorPosition();
        Vector3f playerPos = player.getLocalTranslation();
        Vector3f dif = new Vector3f(mouse.x - playerPos.x, mouse.y - playerPos.y, 0);
        return dif.normalizeLocal();
    }

    /**
     * Create enemy of type "Seeker"
     */
    private void createSeeker() {
        createEnemy("Seeker", new SeekerControl(player, "Seeker"));
    }

    /**
     * Create enemy of type "Wanderer"
     */
    private void createWanderer() {
        createEnemy("Wanderer", new WandererControl("Wanderer", settings.getWidth(), settings.getHeight()));
    }

    /**
     * Create a single enemy with given Spatial asset name and controller
     *
     * @param spatialAssetName
     * @param enemyControl
     */
    private void createEnemy(String spatialAssetName, Control enemyControl) {
        Spatial enemy = getSpatial(spatialAssetName);
        enemy.setLocalTranslation(getSpawnPosition());
        enemy.addControl(enemyControl);
        enemy.setUserData("active", false);
        enemyNode.attachChild(enemy);
        sound.spawn();
    }

    private Vector3f getSpawnPosition() {
        Vector3f pos;
        do {
            // Try random positions until we get one far enough from the player
            int x = Helper.RANDOMIZER.nextInt(settings.getWidth());
            int y = Helper.RANDOMIZER.nextInt(settings.getHeight());
            pos = new Vector3f(x, y, 0);
        } while (FastMath.abs(pos.distance(player.getLocalTranslation())) < MIN_ENEMY_PLAYER_DIST);
        return pos;
    }

    /**
     * Check if the player has collided with any of the enemies and damage/kill
     * player if necessary
     */
    private void checkCollisions() {
        // Iterate over all enemies
        for (int enemyIndex = 0; enemyIndex < enemyNode.getQuantity(); ++enemyIndex) {
            Spatial enemy = enemyNode.getChild(enemyIndex);
            if (enemy.getUserData("active")) {
                // If enemy collided with player, player has died
                if (Helper.checkCollision(player, enemy)) {
                    killPlayer();
                }
                // If enemy collided with a bullet, both bullet and enemy are destroyed
                for (int bulletIndex = 0; bulletIndex < bulletNode.getQuantity(); ++bulletIndex) {
                    Spatial bullet = bulletNode.getChild(bulletIndex);
                    if (Helper.checkCollision(enemy, bullet)) {
                        sound.explosion();
                        enemyNode.detachChild(enemy);
                        bulletNode.detachChild(bullet);
                        // Deleting the items change indexing as well
                        enemyIndex--;
                        break; // Skip going over other bullets - this enemy is done
                    }
                }
            }
        }
    }

    /**
     * Kill the player
     */
    private void killPlayer() {
        sound.explosion();
        player.removeFromParent();
        player.getControl(PlayerControl.class).reset();
        player.setUserData("alive", false);
        player.setUserData("dieTime", System.currentTimeMillis());
        enemyNode.detachAllChildren();
        livesLeft--;
    }

    private void createBlackHole() {
        Spatial blackHole = getSpatial("Black Hole");
        blackHole.setLocalTranslation(getSpawnPosition());
        blackHole.addControl(new BlackHoleControl("Black Hole"));
        blackHole.setUserData("active", false);
        blackHoleNode.attachChild(blackHole);
    }

}
