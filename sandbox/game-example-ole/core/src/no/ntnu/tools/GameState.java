package no.ntnu.tools;

import com.badlogic.gdx.utils.Json;

import java.util.HashMap;
import java.util.Map;

/**
 * A class for storing any game-state related values
 */
public class GameState {

    // We store the value here
    private final Map<String, Object> values = new HashMap<String, Object>();

    // Used to save/load the state to/from JSON
    private final Json json = new Json();

    // The singleton instance of GameState
    private final static GameState INSTANCE = new GameState();

    /**
     * Not allowed to create GameState directly, it is a singleton
     */
    private GameState() {
    }

    /**
     * Use this method to get a Singleton instance of GameState
     *
     * @return
     */
    public static GameState getInstance() {
        return INSTANCE;
    }

    /**
     * Store a value for a given key
     *
     * @param key
     * @param value
     */
    public void set(String key, Object value) {
        values.put(key, value);
    }

    /**
     * General method to get a value stored for given key
     *
     * @param key
     * @return value, or null if no value stored
     */
    public Object get(String key) {
        return values.get(key);
    }

    /**
     * Get a value stored for given key, interpret it as a string
     * Warning: the value must be stored as a String before, otherwise it will
     * return null!
     *
     * @param key
     * @return the stored value or null if none found or it is not a String
     */
    public String getString(String key) {
        Object value = values.get(key);
        if (value instanceof String) {
            return (String) value;
        } else {
            return null;
        }
    }

    /**
     * Get a value stored for given key, interpret it as an integer
     * Warning: the value must be stored as an Integer before, otherwise it will
     * return null!
     *
     * @param key
     * @return the stored value or null if none found or it is not an Integer
     */
    public Integer getInt(String key) {
        Object value = values.get(key);
        if (value instanceof Integer) {
            return (Integer) value;
        } else {
            return null;
        }
    }

    /**
     * Get a value stored for given key, interpret it as a boolean
     * Warning: the value must be stored as a Boolean before, otherwise it will
     * return null!
     *
     * @param key
     * @return the stored value or null if none found or it is not of correct
     * type
     */
    public Boolean getBool(String key) {
        Object value = values.get(key);
        if (value instanceof Boolean) {
            return (Boolean) value;
        } else {
            return null;
        }
    }

    // You can add other convenience methods here
    /**
     * Convert the game state to a JSON string
     *
     * @return
     */
    public String toJson() {
        return json.toJson(values);
    }

}