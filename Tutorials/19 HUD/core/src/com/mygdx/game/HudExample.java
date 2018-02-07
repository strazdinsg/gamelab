package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

/**
 * An example with Head-Up-Display (HUD) showing some text over our game world
 */
public class HudExample extends Game {

    // HUD-object
    private Hud hud;

    // To avoid typing errors in game state keys, use constants!
    public static final String KSCORE = "score";
    public static final String KLEVEL = "level";
    public static final String KLIVES = "lives";

    // Default starting configuration
    private static final Integer SLIVES = 3;
    private static final Integer SLEVEL = 1;
    private static final Integer SSCORE = 0;

    // Explained in Tutorial #17.
    private EventTimer scoreTimer;
    private GameState gameState;

    @Override
    public void create() {
        // Initialises game state.
        gameState = GameState.getInstance();
        initGameState();

        // Create a new HUD-object.
        hud = new Hud(this);

        // Update game state every second
        scoreTimer = new EventTimer();
        scoreTimer.start(1000, true);
    }

    @Override
    public void render() {
        // Clear the screen.
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Render HUD-object
        hud.render();

        // For each second passed, update game state.
        if (scoreTimer.mustFire()) {
            updateGameState();
        }
    }

    @Override
    public void resize(int width, int height) {
        hud.resize(width, height);
    }

    @Override
    public void dispose() {
        hud.dispose();
    }

    /**
     * Initialize the game state with default start values
     */
    private void initGameState() {
        gameState.set(KLEVEL, SLEVEL);
        gameState.set(KSCORE, SSCORE);
        gameState.set(KLIVES, SLIVES);
    }

    /**
     * Update current game state: score, etc
     */
    private void updateGameState() {
        // Get current score, increment it and store it back
        Integer score = gameState.getInt(KSCORE);
        score += 100;
        // Pretend that new level is reached when score reaches a thousand
        if (score >= 1000) {
            System.out.println("New level reached!");
            Integer level = gameState.getInt(KLEVEL);
            level++;
            gameState.set(KLEVEL, level);
            // Start counting score from zero again
            score = 0;
        }
        gameState.set(KSCORE, score);
    }
}
