/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package no.ntnu;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 *
 * @author Thomas
 */
public class MyEntity {
    Sprite entitySprite;
    
    MyEntity(EntityExample mainClass){
        //We set the texture of the sprite to entityTexture
        entitySprite = new Sprite(mainClass.entityTexture);
        //We set the sprite to be 150 pixels by 150 pixels big
        entitySprite.setSize(150, 150);
        //We set the sprite to be drawn 50 pixels up and right from the bottom left corner
        entitySprite.setPosition(50, 50);
    }
    
    void render(SpriteBatch batch){
        //We add the sprite to the spritebatch
        entitySprite.draw(batch);
    }
    
}
