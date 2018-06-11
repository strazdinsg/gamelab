package no.ntnu;

public abstract class Entity {
    private boolean alive=true;
    
    abstract void render();
    
    abstract void update();
    
    boolean isAlive(){
        return alive;
    }
    
    void kill(){
        alive = false;
    }
    
    abstract void dispose();
}
