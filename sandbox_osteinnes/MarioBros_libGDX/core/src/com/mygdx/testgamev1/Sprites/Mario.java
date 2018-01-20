package com.mygdx.testgamev1.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.testgamev1.MarioBros;

/**
 * Creates and define the player sprite "mario".
 *
 * @author Ole-martin Steinnes
 * @version 1
 */
public class Mario extends Sprite {
    private World world;
    private Body b2Body;

    private MarioBros game;

    public Mario(World world, MarioBros game) {
        this.world = world;
        this.game = game;
        defineMario();
    }

    public void defineMario(){
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(32 / game.getPixelsPerMeter(), 32 / game.getPixelsPerMeter());
        bodyDef.type = BodyDef.BodyType.DynamicBody;

        b2Body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(5 / game.getPixelsPerMeter());

        fixtureDef.shape = circleShape;

        b2Body.createFixture(fixtureDef);

    }

    public World getWorld() {
        return this.world;
    }

    public Body getB2Body() {
        return this.b2Body;
    }
}
