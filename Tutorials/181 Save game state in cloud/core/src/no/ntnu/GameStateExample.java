package no.ntnu;

import com.badlogic.gdx.ApplicationAdapter;

/**
 * This applications shows how to store game state (score, etc) to a file, and
 * load it from a file.
 *
 * When you launch the application for the first time, it will start with a
 * default state. It will update and save the game state periodically. Next time
 * when you load it, the old game state should be restored.
 */
public class GameStateExample extends ApplicationAdapter {

    // To avoid typing errors in game state keys, use constants!
    private static final String KSCORE = "score";
    private static final String KLEVEL = "level";
    private static final String KLIVES = "lives";

    // Deffault starting configuration
    private static final Integer SLIVES = 3;
    private static final Integer SLEVEL = 1;
    private static final Integer SSCORE = 0;

    private GameStateCloudStorage cloudStorage;

    private EventTimer scoreTimer;
    private GameState gameState;

    @Override
    public void create() {
        gameState = GameState.getInstance();
        cloudStorage = new GameStateCloudStorage();
        // Try to load game state from the cloud. if it fails, fall back 
        // to default startup state
        if (!cloudStorage.loadGameState(gameState, KLEVEL)) {
            initGameState();
        }

        System.out.println("Starting with the following game state: "
                + gameState.toJson());

        // Update game state every second
        scoreTimer = new EventTimer();
        scoreTimer.start(1000, true);
    }

    /**
     * This method is called in every frame
     */
    @Override
    public void render() {
        if (scoreTimer.mustFire()) {
            updateGameState();
            System.out.println("Current game state: " + gameState.toJson());
        }
    }

    /**
     * This method is called, when the game is closed
     */
    @Override
    public void dispose() {
        // We save the game state to the cloud before closing
        cloudStorage.saveGameState(gameState);
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
