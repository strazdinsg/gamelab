package com.mygdx.testgamev1.Sprites;

/*
        This game was inspired by the libGDX-tutorial by Brent Aureli on YouTube.
        Link to the tutorial playlist: https://www.youtube.com/playlist?list=PLZm85UZQLd2SXQzsF-a0-pPF6IWDDdrXt
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;

/**
 * The Brick-class extends InteractiveTileObject. This is because the Brick and Coin objects
 * will both behave similarly. Therefore their common traits can be written in a parent-class (r: super)
 *
 * @author Ole-martin Steinnes
 * @version 1
 */
public class Brick extends InteractiveTileObject {

    /**
     * Constructor in the Brick-class. Takes in fields for creation of defined Box2D-boxes.
     * There will also be implemented responses to interactions with the player, in the future.
     *
     * @param world World of the box2d restraints
     * @param map Map of the game.
     * @param bounds Bounds of the map.
     * @param pixelsPerMeter Pixels Per Meter unit from the game settings.
     */
    public Brick(World world, TiledMap map, Rectangle bounds, float pixelsPerMeter) {
        super(world, map, bounds, pixelsPerMeter);
        fixture.setUserData(this);
    }

    @Override
    public void onHeadHit() {
        Gdx.app.log("Brick", "Collision");
    }
}
