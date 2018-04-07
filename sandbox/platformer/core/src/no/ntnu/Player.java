package no.ntnu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

/**
 *
 * @author Thomas S. Mjåland
 */
public class Player {
    
    Texture playerTexture;
    Sprite sprite;
    Body body;
    GameScreen game;
    int jumpsRemaining = 1000;
    
    private final int SIZE = 31;
    
    public Player(GameScreen game){
        this.game = game;
        createBody();
        playerTexture = new Texture("Textures/player.png");
        sprite = new Sprite(playerTexture);
        sprite.setSize(SIZE, SIZE);
        createBody();
    }
    
    public void setPosition(Vector2 pos){
        pos.x += SIZE/2;
        pos.y += SIZE/2;
        body.setTransform(pos, 0);
    }
    
    public void update(float delta){
        float xforce = 0;
        float yforce = 0;
        if (Gdx.input.isKeyPressed(Keys.A)){
            xforce += -10*32*delta;
        }
        if (Gdx.input.isKeyPressed(Keys.D)){
            xforce += 10*32*delta;
        }
        if (Gdx.input.isKeyJustPressed(Keys.W) && jumpsRemaining>0){
            yforce += 10*32;
            jumpsRemaining--;
        }
        body.applyLinearImpulse(new Vector2(xforce, yforce), body.getWorldCenter(), true);
    }
    
    public void render(){
        sprite.setPosition(body.getPosition().x - SIZE/2, body.getPosition().y-SIZE/2);
        sprite.draw(MainClass.batch);
    }
    
    private void createBody(){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DynamicBody;
        bodyDef.fixedRotation = true;
        body = game.world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(SIZE/2, SIZE/2);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.restitution = 0.1f;
        fixtureDef.friction = 0.9f;
        body.createFixture(fixtureDef);
    }
}
