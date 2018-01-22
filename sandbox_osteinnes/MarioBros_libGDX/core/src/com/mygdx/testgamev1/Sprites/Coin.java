package com.mygdx.testgamev1.Sprites;

/*
        This game was inspired by the libGDX-tutorial by Brent Aureli on YouTube.
        Link to the tutorial playlist: https://www.youtube.com/playlist?list=PLZm85UZQLd2SXQzsF-a0-pPF6IWDDdrXt
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.testgamev1.MarioBros;

/**
 * The Coin-class extends InteractiveTileObject. This is because the Brick and Coin objects
 * will both behave similarly. Therefore their common traits can be written in a parent-class (r: super)
 *
 * @author Ole-martin Steinnes
 * @version 1
 */
public class Coin extends InteractiveTileObject {

    /**
     * Constructor in the Coin-class. Takes in fields for creation of defined Box2D-boxes.
     * There will also be implemented responses to interactions with the player, in the future.
     *
     * @param world World of the box2d restraints
     * @param map Map of the game.
     * @param bounds Bounds of the map.
     * @param game The Game object
     */
    public Coin(World world, TiledMap map, Rectangle bounds, MarioBros game) {
        super(world, map, bounds, game);
        fixture.setUserData(this);
        isActive = true;
        setCategoryFilter(game.getCoinBit());
    }

    @Override
    public void onHeadHit() {
        Gdx.app.log("Coin", "Collision");
    }

    @Override
    public TiledMapTileLayer.Cell getCell() {
        return null;
    }
}
