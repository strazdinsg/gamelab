package no.ntnu;

import com.badlogic.gdx.physics.box2d.Contact;

public abstract class CollisionHandler {
    abstract void beginContact(Contact contact);
    
    abstract void endContact(Contact contact);
}
