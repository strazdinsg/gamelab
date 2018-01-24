/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jme3test.helloworld;

import com.jme3.math.Vector2f;

/**
 *
 * @author Thomas
 */
public interface Collidable {
    public float getRadius();
    public Vector2f getSpeed();
    public Vector2f getPosition();
    public float getMass();
    public float getBounce();
    public void onCollide(Vector2f deflectDirection, Vector2f collisionDisplacement, float deflectSpeed);
}
