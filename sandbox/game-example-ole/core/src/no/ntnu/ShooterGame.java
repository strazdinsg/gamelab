package no.ntnu;

import com.badlogic.gdx.Game;
import no.ntnu.screens.MenuScreen;
import no.ntnu.tools.EventTimer;
import no.ntnu.tools.GameState;

/**
 * An example showing how to spawn bullets in the direction of the mouse, and
 * how to dispose them when they go out of the screen.
 * Textures source: https://gamedevelopment.tutsplus.com/tutorials/make-a-neon-vector-shooter-in-jmonkeyengine-the-basics--gamedev-11616
 */
public class ShooterGame extends Game {

    // Screen size, in pixels
    public static final int SCREEN_WIDTH = 800;
    public static final int SCREEN_HEIGHT = 600;

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


    // Implement the singleton pattern
    private static final ShooterGame instance = new ShooterGame();

    // Private constructor - singleton needed
    private ShooterGame() {
    }

    public static ShooterGame getInstance() {
        return instance;
    }

    @Override
    public void create() {
        setScreen(new MenuScreen());

        // Initialises game state.
        gameState = GameState.getInstance();
        initGameState();

        // Update game state every second
        scoreTimer = new EventTimer();
    }

    @Override
    public void render() {
        super.render();

        // For each second passed, update game state.
        if (scoreTimer.mustFire()) {
            updateGameState();
        }
    }

    @Override
    public void dispose() {
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

    public void startEventTimer() {
        scoreTimer.start(1000, true);
    }

}
