package com.mygdx.testgamev1.Sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;

public class Brick extends InteractiveTileObjects {
    public Brick(World world, TiledMap map, Rectangle bounds, float pixelsPerMeter) {
        super(world, map, bounds, pixelsPerMeter);
    }
}
