package com.mygdx.testgamev1.Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.mygdx.testgamev1.MarioBros;

/**
 * Defines and creates the solid objects in the game world.
 * Such as the ground, bricks, pipes and coins.
 *
 * @author Ole-martin Steinnes
 * @version 1
 */
public class B2WorldCreator {

    // Arrays for holding tilemap-objects.
    private Array<RectangleMapObject> groundObjects;
    private Array<RectangleMapObject> pipeObjects;
    private Array<RectangleMapObject> brickObjects;
    private Array<RectangleMapObject> coinObjects;

    private Body body;
    private PolygonShape polygonShape;
    private FixtureDef fixtureDef;
    private BodyDef bodyDef;

    private World world;
    private TiledMap map;
    private  MarioBros game;

    private float pixelsPerMeter;

    public B2WorldCreator(World world, TiledMap map, MarioBros game){

        this.world = world;
        this.map = map;
        this.game = game;

        this.pixelsPerMeter = game.getPixelsPerMeter();

        this.bodyDef = new BodyDef();
        this.polygonShape = new PolygonShape();
        this.fixtureDef = new FixtureDef();


        // Fetch objects from the tile-map.
        // ".get(x)" is from the "Tiled"-softwares layer number of the objects.
        groundObjects = map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class);
        pipeObjects = map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class);
        brickObjects = map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class);
        coinObjects = map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class);

        // Defining solid objects in the world map.
        defineObjects(groundObjects);
        defineObjects(pipeObjects);
        defineObjects(brickObjects);
        defineObjects(coinObjects);
    }

    private void defineObjects(Array<RectangleMapObject> objects) {

        for (MapObject object : objects) {

            Rectangle rectangle = ((RectangleMapObject) object).getRectangle();

            // Defines the body type as static. (One can choose kinetic and dynamic aswell)
            bodyDef.type = BodyDef.BodyType.StaticBody;
            // Positions the body, where we center it.
            bodyDef.position.set((rectangle.getX() + rectangle.getWidth()/2) / pixelsPerMeter
                    , (rectangle.getY() + rectangle.getHeight()/2) / pixelsPerMeter);

            body = world.createBody(bodyDef);

            polygonShape.setAsBox((rectangle.getWidth() / 2) / pixelsPerMeter
                    , (rectangle.getHeight()/2) / pixelsPerMeter);

            fixtureDef.shape = polygonShape;

            body.createFixture(fixtureDef);

        }
    }
}
