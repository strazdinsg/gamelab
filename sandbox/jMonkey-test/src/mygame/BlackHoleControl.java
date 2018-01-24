package mygame;

import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.scene.control.AbstractControl;

public class BlackHoleControl extends AbstractControl {
    private final long spawnTime;
    private int hitpoints;
    private final String spatialName;
 
    public BlackHoleControl(String spatialName) {
        spawnTime = System.currentTimeMillis();
        hitpoints = 10;
        this.spatialName = spatialName;
    }
 
    @Override
    protected void controlUpdate(float tpf) {
        if ((Boolean) spatial.getUserData("active")) {
//            we'll use this spot later...
        } else {
            // handle the "active"-status
            if (Helper.fadeIn((Node) spatial, spatialName, spawnTime, 1000)) {
                spatial.setUserData("active", true);                
            }
        }
 
    }
 
    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {}
 
    public void wasShot() {
        hitpoints--;
    }
 
    public boolean isDead() {
        return hitpoints <= 0;
    }
}