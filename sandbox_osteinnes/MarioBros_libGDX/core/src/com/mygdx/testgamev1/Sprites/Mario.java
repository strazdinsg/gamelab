package com.mygdx.testgamev1.Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
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

    private enum State { FALLING, JUMPING, STANDING, RUNNING };
    private State currentState;
    private State previousState;

    private Animation marioRun;
    private Animation marioJump;

    private boolean runningRight;
    private float stateTimer;

    private TextureRegion marioStandStill;

    private MarioBros game;

    public Mario(World world, MarioBros game, PlayScreen playScreen) {
        super(playScreen.getTextureAtlas().findRegion("little_mario"));
        this.world = world;
        this.game = game;

        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        runningRight = true;

        Array<TextureRegion> frames = new Array<TextureRegion>();
        for (int i = 1; i < 4; i++){
            frames.add(new TextureRegion(getTexture(), i*16, 0, 16, 16));
        }

        marioRun = new Animation(0.1f, frames);
        frames.clear();

        for(int i = 4; i < 6; i++){
            frames.add(new TextureRegion(getTexture(), i*16, 0, 16, 16));
        }
        marioJump = new Animation(0.1f, frames);

        defineMario();

        marioStandStill = new TextureRegion(getTexture(), 0, 0, 16, 16);
        setBounds(0, 0, 16 / game.getPixelsPerMeter(), 16 / game.getPixelsPerMeter());
        setRegion(marioStandStill);
    }

    public void update(float dt) {
        setPosition(b2Body.getPosition().x  - getWidth()/2, b2Body.getPosition().y - getHeight()/2);
        setRegion(getFrame(dt));
    }

    ///////// GETTER METHODS /////////////////////////////////////////////////////////////

    public World getWorld() {
        return this.world;
    }

    public Body getB2Body() {
        return this.b2Body;
    }

    ///////// HELPER METHODS /////////////////////////////////////////////////////////////

    private TextureRegion getFrame(float dt) {
        currentState = getState();

        TextureRegion region;

        switch (currentState) {
            case JUMPING:

                region = (TextureRegion) marioJump.getKeyFrame(stateTimer);

                break;

            case RUNNING:

                region = (TextureRegion) marioRun.getKeyFrame(stateTimer, true);

                break;

            case FALLING:

                region  = marioStandStill;

                break;

            case STANDING:

                region = marioStandStill;

                break;

            default:

                region = marioStandStill;

                break;
        }

        if ((b2Body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()) {
            region.flip(true,false);
            runningRight = false;
        } else if ((b2Body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()) {
            region.flip(true, false);
            runningRight = true;
        }

        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        previousState = currentState;
        return region;
    }

    private State getState() {

        boolean isMovingUpward = (b2Body.getLinearVelocity().y > 0);
        boolean isMovingDownward = (b2Body.getLinearVelocity().y < 0);
        boolean isMovingHorizontally = (b2Body.getLinearVelocity().x != 0);
        boolean isMovingDownwardAfterJump = (b2Body.getLinearVelocity().y < 0 && previousState == State.JUMPING);

        if (isMovingUpward || isMovingDownwardAfterJump) {

            return State.JUMPING;

        } else if (isMovingDownward) {

            return State.FALLING;

        } else if (isMovingHorizontally) {

            return State.RUNNING;

        } else {

            return State.STANDING;

        }
    }

    private void defineMario(){
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
}
