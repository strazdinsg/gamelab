package com.mygdx.testgamev1.Tools;

/*
        This game was inspired by the libGDX-tutorial by Brent Aureli on YouTube.
        Link to the tutorial playlist: https://www.youtube.com/playlist?list=PLZm85UZQLd2SXQzsF-a0-pPF6IWDDdrXt
 */

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.mygdx.testgamev1.MarioBros;
import com.mygdx.testgamev1.Sprites.Brick;
import com.mygdx.testgamev1.Sprites.Coin;

/**
 * Defines and creates the solid objects in the game world, with the use of Box2D.
 * Such as the ground, bricks, pipes and coins.
 *
 * @author Ole-martin Steinnes
 * @version 1
 */
public class B2WorldCreator {

    // Arrays for holding tilemap-objects.
    private Array<RectangleMapObject> groundObjects;
    private Array<RectangleMapObject> pipeObjects;

    private Body body;
    private PolygonShape polygonShape;
    private FixtureDef fixtureDef;
    private BodyDef bodyDef;

    private World world;
    private TiledMap map;
    private  MarioBros game;

    private float pixelsPerMeter;

    /**
     * Constructor for the B2WorldCreator-class. Takes in the world-, map- and game-field.
     * Creates the Box2D world.
     *
     * @param world The world of your game
     * @param map Map of your game
     * @param game The MarioBros object.
     */
    public B2WorldCreator(World world, TiledMap map, MarioBros game){

        this.world = world;
        this.map = map;
        this.game = game;

       createWorld();
    }

    /**
     * Creates the Box2D world. Sets values to fields and make use of helper methods to
     * create the world.
     */
    private void createWorld() {

        pixelsPerMeter = game.getPixelsPerMeter();

        bodyDef = new BodyDef();
        polygonShape = new PolygonShape();
        fixtureDef = new FixtureDef();

        fetchObjects();

        // Defining solid objects in the world map.
        defineObjects(groundObjects);
        defineObjects(pipeObjects);
        defineCoins();
        defineBricks();
    }

    /**
     * Fetches the Array<RectangleMapObject>- for each object that is to be defined.
     */
    private void fetchObjects() {

        // Fetch objects from the tile-map.
        // ".get(x)" is from the "Tiled"-softwares layer number of the objects.
        groundObjects = map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class);
        pipeObjects = map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class);

    }

    /**
     * Defines the objects that should be solid, with the help of box2d. (Attaches a box to the texture)
     * @param objects An array with RectangleMapObjects that needs to be solid.
     */
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

    /**
     * Defines a box for the Coin objects.
     */
    private void defineCoins() {

        Array<RectangleMapObject> coinObjects;

        coinObjects = map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class);

        for (MapObject object : coinObjects) {
            Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
            new Coin(world, map, rectangle, game);
        }
    }

    /**
     * Defines a bix fir the Brick objects.
     */
    private void defineBricks() {

        Array<RectangleMapObject> brickObjects;

        brickObjects = map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class);

        for (MapObject object : brickObjects) {
            Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
            new Brick(world, map, rectangle, game);
        }
    }
}
