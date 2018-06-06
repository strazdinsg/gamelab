
package no.ntnu;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class Coin extends Entity {
    private static final Texture texture = new Texture("Textures/coin.png");
    private final Sprite sprite;
    
    public Coin(Vector2 position){
        sprite = new Sprite(texture);
    }
    
    @Override
    void render() {
        sprite.draw(MainClass.batch);
    }

    @Override
    void update() {
        
    }
    
}
