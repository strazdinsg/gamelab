package com.mygdx.testgamev1.Sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.testgamev1.Screens.PlayScreen;


/**
 * @author Ole-martin Steinnes
 */
public abstract class InteractiveTileObject {

    protected World world;
    protected TiledMap map;
    protected TiledMapTile tile;
    protected Rectangle bounds;

    protected Body body;
    protected PolygonShape polygonShape;
    protected FixtureDef fixtureDef;
    protected BodyDef bodyDef;
    protected Fixture fixture;
    protected PlayScreen playScreen;

    protected boolean isActive;

    protected float pixelsPerMeter;

    public InteractiveTileObject(PlayScreen playScreen) {

        this.playScreen = playScreen;
        this.world = playScreen.getWorld();
        this.map = playScreen.getMap();
        pixelsPerMeter = playScreen.getGame().getPixelsPerMeter();

    }

    public void attachToWorld(Rectangle bounds) {
        this.bounds = bounds;

        defineObjects();
        initObject();
    }

    public abstract void initObject();

    private void defineObjects() {

        this.bodyDef = new BodyDef();
        this.polygonShape = new PolygonShape();
        this.fixtureDef = new FixtureDef();

        // Positions the body, where we center it.
        bodyDef.position.set((bounds.getX() + bounds.getWidth()/2) / pixelsPerMeter
                , (bounds.getY() + bounds.getHeight()/2) / pixelsPerMeter);

        body = world.createBody(bodyDef);

        polygonShape.setAsBox((bounds.getWidth() / 2) / pixelsPerMeter
                , (bounds.getHeight()/2) / pixelsPerMeter);

        fixtureDef.shape = polygonShape;

        fixture = body.createFixture(fixtureDef);
    }

    public abstract void onHeadHit();

    public void setCategoryFilter(short filterBit) {
        Filter filter = new Filter();
        filter.categoryBits = filterBit;
        fixture.setFilterData(filter);
    }

    /**
     * Returns the cell Mario collides with.
     * Each sub-class determines what should happen to that cell.
     * @return the cell Mario collides with.
     */
    public TiledMapTileLayer.Cell getCell() {
        // Retrieves the correct laye, which is 1, for both bticks and coins.
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(1);

        /*
        Since the Tiled-map was scaled down (for the Box2D physics) we now need to
        scale it back to it's original size. Since we now want to remove the brick
        texture at the cell Mario collides with. We need the map rescaled to it's
        original size, in order to find the correct cell he collided with. Then we
        divide it by the Tile-size to get the correct coordinates of the cell.
         */
        int xCoordinates = (int) (body.getPosition().x * pixelsPerMeter / 16);
        int yCoordinates = (int)( body.getPosition().y * pixelsPerMeter / 16);

        return layer.getCell(xCoordinates, yCoordinates);
    }
}
