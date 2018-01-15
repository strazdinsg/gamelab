package mygame;

import com.jme3.math.FastMath;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
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

    /**
     * Return true if two spatials collide. Both Spatial objects must have "radius" property set
     * @param s1
     * @param s2
     * @return true if collide, false if don't collide. On error return false;
     */
    static boolean checkCollision(Spatial s1, Spatial s2) {
        if (s1 == null || s2 == null) {
            return false;
        }
        int radius1 = s1.getUserData("radius");
        int radius2 = s2.getUserData("radius");
        float distance = s1.getLocalTranslation().distance(s2.getLocalTranslation());
        float maxDistance =  radius1 + radius2;
        return distance <= maxDistance;
    }
}
