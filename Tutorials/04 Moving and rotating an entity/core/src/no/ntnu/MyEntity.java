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
     */
    public void render(SpriteBatch batch) {
        // Move the entity one pixel to the right and rotate it by 360 degrees 
        // divided by the circumferal length of the ball (2pi*r)
        // Effectively - rotate one pixel and move one pixel to the left
        entitySprite.translateX(1);
        float spriteRadius = entitySprite.getWidth() / 2;
        entitySprite.rotate(-(float) (360 / (2 * 3.14 * spriteRadius)));
        // When the entity moves out of the window, place it back again
        if (entitySprite.getX() > Gdx.graphics.getWidth()) {
            entitySprite.setX(0);
        }
        entitySprite.draw(batch);
    }
}
