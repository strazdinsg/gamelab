package com.mygdx.testgamev1.Sprites;

/*
        This game was inspired by the libGDX-tutorial by Brent Aureli on YouTube.
        Link to the tutorial playlist: https://www.youtube.com/playlist?list=PLZm85UZQLd2SXQzsF-a0-pPF6IWDDdrXt
 */


import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;

public class Coin extends InteractiveTileObjects {



    public Coin(World world, TiledMap map, Rectangle bounds, float pixelsPerMeter) {
        super(world, map, bounds, pixelsPerMeter);
    }
}
