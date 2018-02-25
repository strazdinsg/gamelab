package no.ntnu;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Represents an entity that will be synchronized with the game life cycle:
 * create(), render() and dispose() methods will be called by the game loop
 */
public interface Entity {
    /**
     * Called when the entity should render itself.
     *
     * @param batch should be used to render sprites on
     */
    public void render(SpriteBatch batch);

    /**
     * This method should return true while the entity is alive. When it returns
     * false, the game will dispose the entity and remove it from the game loop.
     *
     * @return
     */
    public boolean isAlive();

}
