package no.ntnu;

import com.badlogic.gdx.ApplicationAdapter;

/**
 * This applications shows how to store game state: score, etc.
 * Wiki-page: https://github.com/strazdinsg/gamelab/wiki/Save-and-load-game-state
 */
public class GameStateExample extends ApplicationAdapter {

    private EventTimer scoreTimer;
    private GameState gameState;
    
    @Override
    public void create() {
        gameState = GameState.getInstance();
        // Store the initial game state
        gameState.set("score", 0);
        gameState.set("level", 1);
        gameState.set("lives", 2);
        
        // Update game state every second
        scoreTimer = new EventTimer();
        scoreTimer.start(1000, true);
    }

    @Override
    public void render() {
        // Alternative #1: Use EventTimer class
        if (scoreTimer.mustFire()) {
            // Get current score, increment it and store it back
            Integer score = gameState.getInt("score");
            score += 100;
            // Pretend that new level is reached when score reaches a thousand
            if (score >= 1000) {
                System.out.println("New level reached!");
                Integer level = gameState.getInt("level");
                level++;
                gameState.set("level", level);
                // Start counting score from zero again
                score = 0;
            }
            gameState.set("score", score);
            System.out.println("Current game state: " + gameState.toJson());
        }
    }

    @Override
    public void dispose() {
    }
}
