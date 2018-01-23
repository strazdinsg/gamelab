package com.mygdx.testgamev1.Sprites;

/*
        This game was inspired by the libGDX-tutorial by Brent Aureli on YouTube.
        Link to the tutorial playlist: https://www.youtube.com/playlist?list=PLZm85UZQLd2SXQzsF-a0-pPF6IWDDdrXt
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.mygdx.testgamev1.Scenes.Hud;
import com.mygdx.testgamev1.Screens.PlayScreen;

/**
 * The Coin-class extends InteractiveTileObject. This is because the Brick and Coin objects
 * will both behave similarly. Therefore their common traits can be written in a parent-class (r: super)
 *
 * @author Ole-martin Steinnes
 * @version 1
 */
public class Coin extends InteractiveTileObject {

    // The tile set we want to use after collision
    private static TiledMapTileSet tileSet;

    /*
    The unique tile ID in the tile set.
    Tiled starts counting from zero, while gdx tile sets starts
    counting on the 1 index. Which means the Blank coin texture had
    an ID of 27 in Tiled. However, in gdx we call it 28.
    */
    private final int BLANK_COIN = 28;
    /**
     * Constructor in the Coin-class. Takes in fields for creation of defined Box2D-boxes.
     * There will also be implemented responses to interactions with the player, in the future.
     *
     * @param playScreen Screen the world is attached to.
     */
    public Coin(PlayScreen playScreen) {
        super(playScreen);


    }

    @Override
    public void initObject() {
        tileSet = map.getTileSets().getTileSet("tileset_gutter");
        fixture.setUserData(this);

        isActive = true;

        setCategoryFilter(playScreen.getGame().getCoinBit());
    }

    @Override
    public void onHeadHit() {
        Gdx.app.log("Coin", "Collision");
        getCell().setTile(tileSet.getTile(BLANK_COIN));

        if (isActive) {
            Hud hud = playScreen.getHud();
            hud.addScore(100);
            isActive = false;
        }
    }
}
