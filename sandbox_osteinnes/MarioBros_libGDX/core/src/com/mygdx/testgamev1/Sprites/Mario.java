package com.mygdx.testgamev1.Sprites;

/*
        This game was inspired by the libGDX-tutorial by Brent Aureli on YouTube.
        Link to the tutorial playlist: https://www.youtube.com/playlist?list=PLZm85UZQLd2SXQzsF-a0-pPF6IWDDdrXt
 */

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
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
    private PlayScreen playScreen;

    private enum State { FALLING, JUMPING, STANDING, RUNNING };
    private State currentState;
    private State previousState;

    private Animation marioRun;
    private Animation marioJump;
    private TextureRegion marioStand;
    private TextureRegion bigMarioJump;
    private TextureRegion bigMarioStand;
    private Animation bigMarioRun;
    private Animation growMario;


    private boolean runningRight;
    private float stateTimer;

    private MarioBros game;

    /**
     * Takes the world, game and playScreen fields as input. Assign these to private fields.
     * Creates the Mario character with animations for jumping, running, falling and standing still.
     *
     * @param playScreen The PlayScreen of the game.
     */
    public Mario(PlayScreen playScreen) {

        super(playScreen.getTextureAtlas().findRegion("little_mario"));


        this.playScreen = playScreen;
        this.world = playScreen.getWorld();
        this.game = playScreen.getGame();

        createMario();
    }

    /**
     * Creates the Mario character.
     */
    private void createMario() {
        setStates();
        addRunAnimation();
        addJumpAnimation();
        addStandingAnimation();
        defineMario();
        addCollisionSensor();
        startAnimations();
    }

    /**
     * Updates position and region when there is change in time.
     * @param dt Change in time.
     */
    public void update(float dt) {
        setPosition(b2Body.getPosition().x  - getWidth()/2, b2Body.getPosition().y - getHeight()/2);
        setRegion(getFrame(dt));
    }

    ///////// GETTER METHODS /////////////////////////////////////////////////////////////

    /**
     * Returns the Box2D world.
     *
     * @return the Bux2D world.
     */
    public World getWorld() {
        return this.world;
    }

    /**
     * Returns the Box2D-box body.
     * @return the Box2D-box body.
     */
    public Body getB2Body() {
        return this.b2Body;
    }

    /**
     * Takes in change in time as a field, and returns the frame.
     * @param dt Change in time.
     * @return the current frame.
     */
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

                region  = marioStand;

                break;

            case STANDING:

                region = marioStand;

                break;

            default:

                region = marioStand;

                break;
        }

        boolean isRunningLeft = (b2Body.getLinearVelocity().x < 0 || !runningRight);
        boolean isRunningRight = (b2Body.getLinearVelocity().x > 0 || runningRight);

        if (isRunningLeft && !region.isFlipX()) {
            region.flip(true,false);
            runningRight = false;
        } else if (isRunningRight && region.isFlipX()) {
            region.flip(true, false);
            runningRight = true;
        }

        stateTimer = currentState == previousState ? stateTimer + dt : 0;

        previousState = currentState;

        return region;
    }

    /**
     * Returns the current state.
     * @return the current state.
     */
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

    ///////// HELPER METHODS /////////////////////////////////////////////////////////////

    /**
     * Sets the initial states.
     */
    private void setStates() {
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        runningRight = true;
    }

    /**
     * Adds the run textures as an animation.
     */
    private void addRunAnimation() {
        Array<TextureRegion> frames = new Array<TextureRegion>();
        for (int i = 1; i < 4; i++){
            frames.add(new TextureRegion(getTexture(), i*16, 0, 16, 16));
        }

        marioRun = new Animation<TextureRegion>(0.1f, frames);
        frames.clear();
    }

    /**
     * Add the jump textures as an animation.
     */
    private void addJumpAnimation() {
        Array<TextureRegion> frames = new Array<TextureRegion>();

        for(int i = 4; i < 6; i++){
            frames.add(new TextureRegion(getTexture(), i*16, 0, 16, 16));
        }
        marioJump = new Animation<TextureRegion>(0.1f, frames);
        frames.clear();
    }

    /**
     * Add the standing fixture as a texture region.
     */
    private void addStandingAnimation() {
        marioStand = new TextureRegion(getTexture(), 0, 0, 16, 16);
    }

    /**
     * Defining Box2D-box for Mario-character.
     */
    private void defineMario(){
        BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();
        CircleShape circleShape = new CircleShape();

        bodyDef.position.set(32 / game.getPixelsPerMeter(), 32 / game.getPixelsPerMeter());
        bodyDef.type = BodyDef.BodyType.DynamicBody;

        b2Body = world.createBody(bodyDef);

        circleShape.setRadius(6 / game.getPixelsPerMeter());

        setMarioFilter(fixtureDef);

        fixtureDef.shape = circleShape;

        b2Body.createFixture(fixtureDef);

    }

    /**
     * Initiate animation.
     */
    private void startAnimations() {
        setBounds(0, 0, 16 / game.getPixelsPerMeter(), 16 / game.getPixelsPerMeter());
        setRegion(marioStand);
    }

    /**
     * Creates a collision sensor for Marios head.
     */
    private void addCollisionSensor() {
        // creates sensor for collision detection for marios head

        FixtureDef fixtureDef = new FixtureDef();



        EdgeShape head = new EdgeShape();
        head.set(new Vector2(-2 / game.getPixelsPerMeter(), 6 / game.getPixelsPerMeter())
                , new Vector2(2 / game.getPixelsPerMeter(), 6 / game.getPixelsPerMeter()));

        fixtureDef.shape = head;
        fixtureDef.isSensor = true;

        b2Body.createFixture(fixtureDef).setUserData("head");

    }

    /**
     * Sets the fixture definition filter for the Mario character.
     * Sets the category bit: the fixture itself
     * Sets the mask bits: the fixtures it can collide with.
     * @param fixtureDef fixture definiton of mario.
     */
    private void setMarioFilter(FixtureDef fixtureDef) {
        // Creating category and what he can collide with
        // The category is: "what is this fixture"
        fixtureDef.filter.categoryBits = game.getMarioBit();

        // What mario can collide with.
        fixtureDef.filter.maskBits = (short) (game.getDefaultBit() | game.getBrickBit() | game.getCoinBit());
    }
}
