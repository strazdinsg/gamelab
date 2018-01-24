package mygame;

import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.ui.Picture;
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
     * Return true if two spatials collide. Both Spatial objects must have
     * "radius" property set
     *
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
        float maxDistance = radius1 + radius2;
        return distance <= maxDistance;
    }

    /**
     * Gradually fade in a spatial. Must be called inside update() method. One
     * call to this method only changes opacity of the spatial node Picture
     *
     * @param spatialNode parent node of the spatial
     * @param spatialName name of the spatial object
     * @param spawnTime time when the object was spawned
     * @param timeToFull time to full saturation (in milliseconds)
     * @return true if fading done, false if still in progress
     */
    static boolean fadeIn(Node spatialNode, String spatialName, long spawnTime, int timeToFull) {
        long timeSinceSpawn = System.currentTimeMillis() - spawnTime;
        if (timeSinceSpawn >= 1000f) {
            return true;
        }

        ColorRGBA color = new ColorRGBA(1, 1, 1, (float) timeSinceSpawn / (float) timeToFull);
        Picture pic = (Picture) spatialNode.getChild(spatialName);
        pic.getMaterial().setColor("Color", color);
        return false;
    }
}
