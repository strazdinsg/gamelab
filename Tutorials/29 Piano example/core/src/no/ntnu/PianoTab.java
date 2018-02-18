package no.ntnu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PianoTab {

    private final Sprite entitySprite;
    private Texture texture;

    public PianoTab(int width, int height, int posX, int posY) {
        loadTexture();
        //We set the texture of the sprite to entityTexture
        entitySprite = new Sprite(texture);
        //We set the sprite to be 150 pixels by 150 pixels big
        entitySprite.setSize(width, height);
        //We set the sprite to be drawn 50 pixels up and right from the bottom left corner
        entitySprite.setPosition(posX, posY);
    }

    /**
     * This method must be called in every rendering step of the game
     * @param batch 
     */
    public void render(SpriteBatch batch) {
        //We add the sprite to the spritebatch
        entitySprite.draw(batch);
    }

    private void loadTexture() {
        texture = new Texture("pianotab_1.png");
    }

    public void setTexture(Texture tex) {
        entitySprite.setTexture(tex);
    }
}
