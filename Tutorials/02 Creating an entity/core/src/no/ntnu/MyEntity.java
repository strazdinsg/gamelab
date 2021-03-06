package no.ntnu;

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
    }

    /**
     * This method must be called in every rendering step of the game
     * @param batch 
     */
    public void render(SpriteBatch batch) {
        //We add the sprite to the spritebatch
        entitySprite.draw(batch);
    }
}
