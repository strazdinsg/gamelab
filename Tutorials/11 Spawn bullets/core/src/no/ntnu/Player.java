package no.ntnu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Player implements Entity {

    // Time interval between shooting two bullets
    private static final long WEAPON_COOLDOWN = 100;

    private long lastBulletTime = 0;
    
    private final Sprite sprite;
    private Vector2 pos; // Current coordinates of the player
    private final Texture bulletTexture;

    /**
     * Create a player
     *
     * @param texture texture for the player
     * @param bulletTexture texture to be used for the bullets
     */
    public Player(Texture texture, Texture bulletTexture) {
        // Create sprite, set size and position, and center for rotation
        sprite = new Sprite(texture);
        pos = new Vector2(300, 200);
        sprite.setPosition(pos.x, pos.y);
        sprite.setOriginCenter();
        this.bulletTexture = bulletTexture;
    }

    /**
     * This method must be called in every rendering step of the game
     *
     * @param batch, The sprite batch to be used for drawing the entity
     */
    @Override
    public void render(SpriteBatch batch) {
        // Find out direction (angle) of the mouse pointer, rotate the 
        // player's ship accordingly, and shoot bullet in that direction if
        // mouse is pressed
        Vector2 aim = getAim();
        rotateSprite(aim.angleRad());
        if (mouseClicked()) {
            if (weaponCooledDown()) {
                spawnBullet(aim);
            }
        }
        sprite.draw(batch);
    }

    /**
     * Rotate the sprite so that it faces a desired direction
     *
     * @param angle in radians
     */
    private void rotateSprite(float angle) {
        sprite.setRotation(180 - angle / MathUtils.PI * 180);
    }

    /**
     * Return vector pointing from player to the mouse position
     *
     * @return
     */
    private Vector2 getAim() {
        // Warning - the calculations are incorrect if the screen is 
        // resized and world is scaled!
        int mouseX = Gdx.input.getX();
        int mouseY = Gdx.input.getY();
        // The sprite has some offset, we need to compensate for it
        float dx = mouseX - pos.x - sprite.getOriginX();
        float dy = mouseY - pos.y - 3 * sprite.getOriginY();
        return new Vector2(dx, dy);
    }

    /**
     * Return true, when mouse is clicked (screen touched)
     *
     * @return
     */
    private boolean mouseClicked() {
        return Gdx.input.isTouched();
    }

    /**
     * Spawn a bullet object in the direction of the mouse
     *
     * @param angle
     */
    private void spawnBullet(Vector2 aim) {
        // Scale the vector to the aim, to get our desired length
        Vector2 offset = aim.cpy();
        float dist = offset.len();
        float scale = 30.0f / dist;
        offset.scl(scale);

        // Reverse the y axis - screen goes top to bottom while world 
        // coordinates go bottom-to-top
        offset.y = -offset.y;

        // Bullet will be positioned relative to the player position
        Vector2 bpos = offset.cpy();
        bpos.add(pos);
        bpos.y += sprite.getOriginY();

        // Set speed in the same direction, just scale it up again
        Vector2 bspeed = offset.cpy();
        bspeed.scl(20);

        // Create the bullet and include it in the render loop
        Bullet bullet = new Bullet(bpos, bspeed, bulletTexture);
        BulletExample game = BulletExample.getInstance();
        game.registerEntity(bullet);
        lastBulletTime = System.currentTimeMillis();
    }

    @Override
    public boolean isAlive() {
        return true; // Player never dies through the whole app life cycle
    }

    /**
     * Return true if enough time passed since last spawned bullet
     * @return 
     */
    private boolean weaponCooledDown() {
        return (System.currentTimeMillis() - lastBulletTime) >= WEAPON_COOLDOWN;
    }
}
