package mygame;

import com.jme3.math.FastMath;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import java.util.Random;

/**
 * Some helper functions
 */
public class Helper {

    public static final Random RANDOMIZER = new Random();

    public static float getAngleFromVector(Vector3f vec) {
        Vector2f vec2 = new Vector2f(vec.x, vec.y);
        return vec2.getAngle();
    }

    public static Vector3f getVectorFromAngle(float angle) {
        return new Vector3f(FastMath.cos(angle), FastMath.sin(angle), 0);
    }
}
