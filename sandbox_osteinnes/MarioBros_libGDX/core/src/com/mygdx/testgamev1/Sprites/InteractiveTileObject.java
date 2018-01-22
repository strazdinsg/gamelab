package com.mygdx.testgamev1.Sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.testgamev1.MarioBros;


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
    protected MarioBros game;

    protected boolean isActive;

    protected float pixelsPerMeter;

    public InteractiveTileObject(World world, TiledMap map, Rectangle bounds, MarioBros game) {

        this.world = world;
        this.map = map;
        this.bounds = bounds;
        this.game = game;
        pixelsPerMeter = game.getPixelsPerMeter();

        defineObjects();
    }

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
    public abstract TiledMapTileLayer.Cell getCell();
}
