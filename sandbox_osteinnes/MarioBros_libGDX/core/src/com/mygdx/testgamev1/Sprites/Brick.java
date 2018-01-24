package com.mygdx.testgamev1.Sprites;

/*
        This game was inspired by the libGDX-tutorial by Brent Aureli on YouTube.
        Link to the tutorial playlist: https://www.youtube.com/playlist?list=PLZm85UZQLd2SXQzsF-a0-pPF6IWDDdrXt
 */

import com.badlogic.gdx.Gdx;
import com.mygdx.testgamev1.Scenes.Hud;
import com.mygdx.testgamev1.Screens.PlayScreen;

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
     * @param playScreen Screen the world is attached to.
     */
    public Brick(PlayScreen playScreen) {
        super(playScreen);

    }

    @Override
    public void initObject() {
        fixture.setUserData(this);
        isActive = true;
        setCategoryFilter(playScreen.getGame().getBrickBit());
    }

    @Override
    public void onHeadHit() {
        Gdx.app.log("Brick", "Collision");
        setCategoryFilter(playScreen.getGame().getDestroyedBit());

        // Removes the brick texture.
        getCell().setTile(null);
        if (isActive) {
            Hud hud = playScreen.getHud();

            hud.addScore(200);

            isActive = false;
        }
    }
}
