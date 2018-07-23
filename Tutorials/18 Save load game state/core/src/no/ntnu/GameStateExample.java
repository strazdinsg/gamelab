package no.ntnu;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

/**
 * This applications shows how to store game state (score, etc) to a file, and
 * load it from a file.
 *
 * When you launch the application for the first time, it will start with a
 * default state. It will update and save the game state periodically. Next time
 * when you load it, the old game state should be restored.
 * 
 * Wiki-page: https://github.com/strazdinsg/gamelab/wiki/Save-and-load-game-state
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

    // Path to the config file (by default, /core/assets is the working dir)
    private static final String CONFIG_FILE = "game-state.txt";

    private EventTimer scoreTimer;
    private GameState gameState;

    @Override
    public void create() {
        gameState = GameState.getInstance();
        loadGameState();

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
            saveGameState();
            System.out.println("Current game state: " + gameState.toJson());
        }
    }

    @Override
    public void dispose() {
        // We could also save the game state only when we dispose the game...
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
     * Try to restore gameState from a previously saved file
     */
    private void loadGameState() {
        FileHandle stateFile = Gdx.files.local(CONFIG_FILE);
        if (stateFile.exists()) {
            System.out.println("Previous game-save found!");
            String savedState = stateFile.readString();
            if (gameState.fromJson(savedState)) {
                System.out.println("Game state loaded");
            }
        }

        // If we don't have correct config, init defaults
        if (gameState.getInt(KLEVEL) == null) {
            initGameState();
        }
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

    /**
     * Save game state to a file
     *
     * @return true on success, false otherwise
     */
    private boolean saveGameState() {
        FileHandle stateFile = Gdx.files.local(CONFIG_FILE);
        String stateString = gameState.toJson();
        try {
            stateFile.writeString(stateString, false);
            return true;
        } catch (Exception ex) {
            System.out.println("Error while writing game state to file: "
                    + ex.getMessage());
            return false;
        }
    }
}
