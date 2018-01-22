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
     * @param game The game object
     */
    public Brick(World world, TiledMap map, Rectangle bounds, MarioBros game) {
        super(world, map, bounds, game);
        fixture.setUserData(this);
        setCategoryFilter(game.getBrickBit());
    }

    @Override
    public void onHeadHit() {
        Gdx.app.log("Brick", "Collision");
        setCategoryFilter(game.getDestroyedBit());

        // Removes the brick texture.
        getCell().setTile(null);
    }

    @Override
    public TiledMapTileLayer.Cell getCell() {

        // Retrieves the correct layer(Brick) which is 1.
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(1);

        /*
        Since the Tiled-map was scaled down (for the Box2D physics) we now need to
        scale it back to it's original size. Since we now want to remove the brick
        texture at the cell Mario collides with. We need the map rescaled to it's
        original size, in order to find the correct cell he collided with. Then we
        divide it by the Tile-size to get the correct coordinates of the cell.
         */
        int xCoordinates = (int) (body.getPosition().x * game.getPixelsPerMeter() / 16);
        int yCoordinates = (int)( body.getPosition().y * game.getPixelsPerMeter() / 16);

        return layer.getCell(xCoordinates, yCoordinates);
    }
}
