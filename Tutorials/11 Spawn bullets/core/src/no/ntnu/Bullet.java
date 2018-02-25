package no.ntnu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Represents a bullet. Renders sprite and moves until it reaches the screen
 * boundaries. Then it disposes itself.
 */
public class Bullet implements Entity {

    private final Sprite sprite;
    private final Vector2 pos; // Current position
    private final Vector2 speed; // Movement vector
    private boolean alive;

    /**
     * Create a bullet
     *
     * @param pos position
     * @param speed speed vector - will move this many pixels per second
     * @param texture texture to be used for the sprite
     */
    public Bullet(Vector2 pos, Vector2 speed, Texture texture) {
        sprite = new Sprite(texture);
        this.pos = pos;
        sprite.setPosition(pos.x, pos.y);
        sprite.rotate(speed.angle());
        this.alive = true;
        this.speed = speed;
    }

    @Override
    public void render(SpriteBatch batch) {
        // Calculate movement since last frame
        float dt = Gdx.graphics.getDeltaTime();
        Vector2 move = speed.cpy();
        move.scl(dt);

        // Move the bullet
        pos.add(move);
        sprite.setPosition(pos.x, pos.y);

        if (insideScreen()) {
            sprite.draw(batch);
        } else {
            // When the bullet moves outside screen, mark it as dead, the game
            // object will remove it from renderable object list
            alive = false;
        }
    }

    @Override
    public boolean isAlive() {
        return alive;
    }

    /**
     * Check if the bullet is inside screen (visible)
     * @return 
     */
    private boolean insideScreen() {
        if (pos.x < 0 || pos.x > BulletExample.SCREEN_WIDTH) {
            return false;
        }
        if (pos.y < 0 || pos.y > BulletExample.SCREEN_HEIGHT) {
            return false;
        }
        return true;
    }
}
