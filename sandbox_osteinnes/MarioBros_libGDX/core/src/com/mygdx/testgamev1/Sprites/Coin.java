package com.mygdx.testgamev1.Sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;

public class Coin extends InteractiveTileObjects {



    public Coin(World world, TiledMap map, Rectangle bounds, float pixelsPerMeter) {
        super(world, map, bounds, pixelsPerMeter);
    }
}
