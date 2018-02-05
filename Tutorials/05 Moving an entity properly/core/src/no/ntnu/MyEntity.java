package no.ntnu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MyEntity {

    private final Sprite entitySprite;

    public MyEntity(Texture texture) {
        //We set the texture of the sprite to entityTexture
        entitySprite = new Sprite(texture);
        //We set the sprite to be 150 pixels by 150 pixels big
        entitySprite.setSize(150, 150);
        //We set the sprite to be drawn 50 pixels up and right from the bottom left corner
        entitySprite.setPosition(50, 50);
        //We set the sprites origin to be dead center of the sprite
        entitySprite.setOriginCenter();
    }

    /**
     * This method must be called in every rendering step of the game
     *
     * @param batch, The sprite batch to be used for drawing the entity
     * @param dt, The frametime
     */
    public void render(SpriteBatch batch, float dt) {
        float speed = 100;
        entitySprite.translateX(speed * dt);
        float spriteRadius = entitySprite.getWidth() / 2;
        entitySprite.rotate(-(float) (speed * 360 / (2 * 3.14 * spriteRadius)) * dt);
        if (entitySprite.getX() > Gdx.graphics.getWidth()) {
            entitySprite.setX(0);
        }
        entitySprite.draw(batch);
    }
}
