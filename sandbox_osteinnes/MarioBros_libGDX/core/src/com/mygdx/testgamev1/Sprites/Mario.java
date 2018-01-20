package com.mygdx.testgamev1.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.testgamev1.MarioBros;
import com.mygdx.testgamev1.Screens.PlayScreen;

/**
 * Creates and define the player sprite "mario".
 *
 * @author Ole-martin Steinnes
 * @version 1
 */
public class Mario extends Sprite {
    private World world;
    private Body b2Body;

    private TextureRegion marioStandStill;

    private MarioBros game;

    public Mario(World world, MarioBros game, PlayScreen playScreen) {
        super(playScreen.getTextureAtlas().findRegion("little_mario"));
        this.world = world;
        this.game = game;
        defineMario();

        marioStandStill = new TextureRegion(getTexture(), 0, 0, 16, 16);
        setBounds(0, 0, 16 / game.getPixelsPerMeter(), 16 / game.getPixelsPerMeter());
        setRegion(marioStandStill);
    }

    public void update(float dt) {
        setPosition(b2Body.getPosition().x  - getWidth()/2, b2Body.getPosition().y - getHeight()/2);
    }

    public void defineMario(){
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(32 / game.getPixelsPerMeter(), 32 / game.getPixelsPerMeter());
        bodyDef.type = BodyDef.BodyType.DynamicBody;

        b2Body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(6 / game.getPixelsPerMeter());

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
