
package no.ntnu;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Coin extends Entity {
    private static final Texture texture = new Texture("Textures\\Coin.png");
    private final Sprite sprite;
    private final Body body;
    private final static float width = 32;
    private final static float height = 32;
    
    public Coin(Vector2 position, World world){
        sprite = new Sprite(texture);
        
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        PolygonShape shape = new PolygonShape();
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        body = world.createBody(bodyDef);
        body.setTransform((position.x+width/2)/GameScreen.PIXELSPERUNIT, (position.y+height/2)/GameScreen.PIXELSPERUNIT, 0);
        shape.setAsBox((width/2)/GameScreen.PIXELSPERUNIT, (height/2)/GameScreen.PIXELSPERUNIT);
        fixtureDef.isSensor = true;
        Fixture fixture = body.createFixture(fixtureDef);
        final Coin selfReference = this;
        fixture.setUserData(new CollisionHandler(){
            @Override
            void beginContact(Contact contact) {
                if (
                        contact.getFixtureA().getBody() == Player.body || 
                        contact.getFixtureB().getBody() == Player.body){
                    selfReference.kill();
                }
            }
            @Override
            void endContact(Contact contact) {}
        });
    }
    
    @Override
    void render() {
        sprite.setCenter(body.getPosition().x*GameScreen.PIXELSPERUNIT, body.getPosition().y*GameScreen.PIXELSPERUNIT);
        sprite.draw(MainClass.batch);
    }

    @Override
    void update() {
        
    }
    
    @Override
    void dispose(){
        body.getWorld().destroyBody(body);
    }
}
