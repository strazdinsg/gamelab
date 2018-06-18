/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jme3test.helloworld;

import com.jme3.math.Vector2f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;
import org.lwjgl.opengl.Display;

/**
 *
 * @author Thomas
 */
public class BallControl extends AbstractControl implements Collidable {
    Vector2f speed = new Vector2f(0,0);

    @Override
    protected void controlUpdate(float tpf) {
        //Add gravity
        speed.addLocal(new Vector2f(0, -(float)spatial.getUserData("gravity")).mult(tpf));
        //check if hitting walls/roof
        if (this.getPosition().x < this.getRadius()){
            spatial.move(this.getRadius() - this.getPosition().x,0,0);
            speed.x = Math.abs(speed.x);
        }
        if (this.getPosition().x + this.getRadius() > Display.getWidth()){
            spatial.move((Display.getWidth() - this.getRadius()) - this.getPosition().x,0,0);
            speed.x = -Math.abs(speed.x);
        }
        if (this.getPosition().y + this.getRadius() > Display.getHeight()){
            spatial.move(0, Display.getHeight() - (this.getPosition().y + this.getRadius()), 0);
            speed.y = -Math.abs(speed.y);
        }
        if (this.getPosition().y < -this.getRadius()){
            spatial.setLocalTranslation(Display.getWidth()/2, Display.getHeight() - (this.getRadius()+10), 0);
            speed = new Vector2f(0,0);
        }
        //Move based on speed
        spatial.move(speed.x*tpf, speed.y*tpf, 0);
        
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {}
    
    @Override
    public void onCollide(Vector2f deflectDirection, Vector2f collisionDisplacement, float deflectSpeed){
        spatial.move(collisionDisplacement.x, collisionDisplacement.y, 0);
        speed.addLocal(deflectDirection.mult(deflectSpeed));
    }

    @Override
    public float getRadius() {
        return spatial.getUserData("radius");
    }

    @Override
    public Vector2f getSpeed() {
        return speed;
    }

    @Override
    public Vector2f getPosition() {
        return new Vector2f(spatial.getLocalTranslation().x, spatial.getLocalTranslation().y);
    }

    @Override
    public float getMass() {
        return spatial.getUserData("mass");
    }

    @Override
    public float getBounce() {
        return spatial.getUserData("bounce");
    }
}
