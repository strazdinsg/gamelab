package no.ntnu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
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
    int jumpsRemaining = 2;
    
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
        if (Gdx.input.isKeyPressed(Keys.A) && body.getLinearVelocity().x>-45){
            body.applyLinearImpulse(new Vector2(-45*delta,0), body.getWorldCenter(), true);
        }
        if (Gdx.input.isKeyPressed(Keys.D) && body.getLinearVelocity().x<45){
            body.applyLinearImpulse(new Vector2(45*delta,0), body.getWorldCenter(), true);
        }
        if (Gdx.input.isKeyJustPressed(Keys.W) && jumpsRemaining>0){
            body.applyLinearImpulse(new Vector2(0,30), body.getWorldCenter(), true);
            jumpsRemaining--;
        }
        
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
        body.setLinearDamping(0);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(SIZE/2, SIZE/2);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.restitution = 0.0f;
        fixtureDef.friction = 0.5f;
        Fixture bodyfix = body.createFixture(fixtureDef);
        bodyfix.setUserData(new CollisionHandler(){
            @Override
            void beginContact(Contact contact) {
                jumpsRemaining = 2;
            }
            @Override
            void endContact(Contact contact) {
                
            }
        });
        
    }
}
