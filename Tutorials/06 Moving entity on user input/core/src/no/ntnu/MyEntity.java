package no.ntnu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

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
        //We update the position based on what keys are being pressed
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            entitySprite.translateY(100 * dt);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            entitySprite.translateY(-100 * dt);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            entitySprite.translateX(100 * dt);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            entitySprite.translateX(-100 * dt);
        }

        //We get vectors pointing to the entity and the mouse
        Vector2 entityPosition = new Vector2();
        entityPosition.x = entitySprite.getX();
        entityPosition.y = entitySprite.getY();

        Vector2 mousePosition = new Vector2();
        mousePosition.x = Gdx.input.getX();
        mousePosition.y = Gdx.input.getY();

        //We change the origo of the mouse position to be lower left corner
        mousePosition.y = Gdx.graphics.getHeight() - mousePosition.y;

        //We change the entity vector so that it points to the middle of the entity
        entityPosition.x += entitySprite.getWidth() / 2;
        entityPosition.y += entitySprite.getHeight() / 2;

        //We get the vector from the entity to the mouse, and get the angle of it
        Vector2 viewVector = mousePosition.sub(entityPosition);
        float viewAngle = viewVector.angle();

        //We set the rotation of the entity equal the angle we found earlier
        entitySprite.setRotation(viewAngle);

        //We draw our entity
        entitySprite.draw(batch);
    }
}
