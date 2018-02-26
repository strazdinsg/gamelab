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
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

/**
 *
 * @author Thomas
 */
public class PlayerControl extends AbstractControl implements Collidable {
    Vector2f speed = new Vector2f(0,0);
    
    float maxacceleration = 1000;
    float maxacceldist = 20;
    float maxspeed = 10000;

    @Override
    protected void controlUpdate(float tpf) {
        Vector2f targpos = new Vector2f(Mouse.getX(), Mouse.getY());
        Vector2f movVect = new Vector2f(
                targpos.x - spatial.getLocalTranslation().getX(),
                targpos.y - spatial.getLocalTranslation().getY()
        );
        float len = movVect.length();
        movVect = movVect.normalize();
        if (maxacceleration < len) {
            movVect = movVect.mult(maxacceleration);
        } else { 
            movVect = movVect.mult(len);
        }
        movVect = movVect.mult(Math.min((movVect.length()+100)/maxacceldist, 1));
        movVect.multLocal(tpf);
        speed.addLocal(movVect);
        speed.subtractLocal(speed.mult(speed.length()/maxspeed));
        
        movVect = speed.mult(tpf);
        spatial.move(movVect.x, movVect.y, 0);
        
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
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {}
    
    @Override
    public void onCollide(Vector2f deflectDirection, Vector2f collisionDisplacement, float deflectSpeed){
        spatial.move(collisionDisplacement.x, collisionDisplacement.y, 0);
        speed.addLocal(deflectDirection.mult(deflectSpeed*1));
        System.out.println(deflectDirection.x + " " + deflectDirection.y + " " + deflectSpeed);
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
