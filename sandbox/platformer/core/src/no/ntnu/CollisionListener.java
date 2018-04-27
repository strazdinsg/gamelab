package no.ntnu;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

public class CollisionListener implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        if (contact.getFixtureA().getUserData() instanceof CollisionHandler){
            ((CollisionHandler)contact.getFixtureA().getUserData()).beginContact(contact);
        }
        if (contact.getFixtureB().getUserData() instanceof CollisionHandler){
            ((CollisionHandler)contact.getFixtureB().getUserData()).beginContact(contact);
        }
    }

    @Override
    public void endContact(Contact contact) {
        if (contact.getFixtureA().getUserData() instanceof CollisionHandler){
            ((CollisionHandler)contact.getFixtureA().getUserData()).endContact(contact);
        }
        if (contact.getFixtureB().getUserData() instanceof CollisionHandler){
            ((CollisionHandler)contact.getFixtureB().getUserData()).endContact(contact);
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {}

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {}
    
}
