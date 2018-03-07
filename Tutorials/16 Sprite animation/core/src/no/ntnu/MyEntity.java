package no.ntnu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class MyEntity{

    // Constant rows and columns of the sprite sheet
    private static final int FRAME_COLS = 6, FRAME_ROWS = 5;

    private final Sprite entitySprite;

    private enum State {STANDING, RUNNING_RIGHT, RUNNING_LEFT};
    private State currentState;
    private State previousState;

    private Animation entityRun;
    private TextureRegion entityStanding;

    private boolean isRunningLeft, isRunningRight;

    private Animation<TextureRegion> walkAnimation;
    private Texture walkSheet;

    // A variable for tracking elapsed time for the animation
    float stateTime;

    public MyEntity() {
        //We set the texture of the sprite to entityTexture
        entitySprite = new Sprite();
        //We set the sprite to be 150 pixels by 150 pixels big
        entitySprite.setSize(150, 150);
        //We set the sprite to be drawn 50 pixels up and right from the bottom left corner
        entitySprite.setPosition(100, 100);

        // Load the sprite sheet as a Texture
        walkSheet = new Texture(Gdx.files.internal("sprite-animation4.png"));

        // Use the split utility method to create a 2D array of TextureRegions. This is
        // possible because this sprite sheet contains frames of equal size and they are
        // all aligned.
        TextureRegion[][] tmp = TextureRegion.split(walkSheet,
                walkSheet.getWidth() / FRAME_COLS,
                walkSheet.getHeight() / FRAME_ROWS);

        // Place the regions into a 1D array in the correct order, starting from the top
        // left, going across first. The Animation constructor requires a 1D array.
        TextureRegion[] walkFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                walkFrames[index++] = tmp[i][j];
            }
        }

        // Initialize the Animation with the frame interval and array of frames
        walkAnimation = new Animation<TextureRegion>(0.025f, walkFrames);

        entityStanding = walkFrames[1];

        // Reset the elapsed animation
        // time to 0
        stateTime = 0f;
    }

    /**
     * This method must be called in every rendering step of the game
     * @param batch 
     */
    public void render(SpriteBatch batch) {

        float dt = Gdx.graphics.getDeltaTime();
        stateTime += dt; // Accumulate elapsed animation time


        entitySprite.setRegion(getFrame(dt));


        //We add the sprite to the spritebatch
        entitySprite.draw(batch);
    }

    public void dispose() {
        walkSheet.dispose();
    }

    private TextureRegion getFrame(float dt) {
        currentState = getState();
        TextureRegion region;

        switch (currentState) {
            case STANDING:
                region = entityStanding;

                break;
            case RUNNING_LEFT:
                region = walkAnimation.getKeyFrame(stateTime, true);
                break;

            case RUNNING_RIGHT:
                region = walkAnimation.getKeyFrame(stateTime, true);
                break;

            default:
                region = entityStanding;
                break;

        }

        if (isRunningLeft && !region.isFlipX()) {
            region.flip(true,false);
        } else if (isRunningRight && region.isFlipX()) {
            region.flip(true, false);
        }

        if (currentState == previousState){
            stateTime = stateTime + dt;
        } else {
            stateTime = 0;
        }

        previousState = currentState;

        return region;
    }

    private State getState() {

        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            isRunningRight = true;
            isRunningLeft = false;
            return State.RUNNING_RIGHT;

        } else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            isRunningRight = false;
            isRunningLeft = true;
            return State.RUNNING_LEFT;

        } else {
            isRunningRight = false;
            isRunningLeft = false;
            return State.STANDING;
        }
    }
}
