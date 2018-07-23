package no.ntnu;

import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

/**
 * Stores and loads game state to/from a cloud storage. The following cloud
 * storage is expected:
 * https://github.com/strazdinsg/datakomm-tools/tree/master/online-storage
 */
public class GameStateCloudStorage {

    // You need to set some kind of unique cloud-storage key for your game 
    // otherwise other games will overwrite your state!
    // If you will handle several players, make sure you generate a unique
    // key for every player!
    private static final String CLOUD_KEY = "myCoolGame.user-Chuck";

    // URL to cloud storage where the game state can be saved and loaded
    // We assume that this storage service is running there, allowing to 
    // save a single (key, value) pair and later load it
    // If you want to deploy your own PHP+MySQL-based cloud key-value storage,
    // see here: https://github.com/strazdinsg/datakomm-tools/tree/master/online-storage
    private static final String CLOUD_STORE_URL
            = "http://your.url.here/storage/set.php?key=" + CLOUD_KEY + "&value=";
    private static final String CLOUD_LOAD_URL
            = "http://your.url.here/storage/get.php?key=" + CLOUD_KEY;

    /**
     * Try to restore gameState from the cloud. Clears the values if the state
     * is not loaded correctly
     *
     * @param gameState the state will be stored here
     * @param requiredKey one key that must be present in the loaded state. If
     * it is not present, the state is not considered to be valid
     *
     * @return true on success, false otherwise
     */
    public boolean loadGameState(GameState gameState, String requiredKey) {
        FetchWebString fetcher = new FetchWebString();
        String response = fetcher.httpGet(CLOUD_LOAD_URL, null);
        gameState.clear();

        if (response == null) {
            System.out.println("Previous game-save found in cloud storage: "
                    + response);
            return false;
        }

        // The expected response is a JSON in format { success: true/false, value: gameState }
        try {
            JsonValue json = new JsonReader().parse(response);
            String success = json.getString("success");
            if ("true".equals(success)) {
                // This should be the string representing our game state
                String savedGameState = json.getString("value");
                if (savedGameState != null) {
                    if (gameState.fromJson(savedGameState)) {
                        System.out.println("Game state loaded");
                    } else {
                        System.out.println("Incorrect game state string, ignored: "
                                + savedGameState);
                    }
                }
            }
        } catch (Exception ex) {
            System.out.println("Wrong server's response format: " + response);
        }

        // Check if config is valid
        return gameState.get(requiredKey) != null;
    }

    /**
     * Save game state to a file
     *
     * @param gameState
     * @return true on success, false otherwise
     */
    public boolean saveGameState(GameState gameState) {
        System.out.println("Saving game state to the cloud...");
        FetchWebString fetcher = new FetchWebString();
        String stateString = gameState.toJson();
        String response = fetcher.httpGet(CLOUD_STORE_URL + stateString, null);

        try {
            JsonValue json = new JsonReader().parse(response);
            String success = json.getString("success");
            return "true".equals(success);
        } catch (Exception ex) {
            System.out.println("Wrong server's response format: "
                    + response);
            return false;
        }
    }
}
