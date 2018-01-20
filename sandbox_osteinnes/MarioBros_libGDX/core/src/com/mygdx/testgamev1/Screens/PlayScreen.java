package com.mygdx.testgamev1.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.testgamev1.MarioBros;
import com.mygdx.testgamev1.Scenes.Hud;
import com.mygdx.testgamev1.Sprites.Mario;

import java.util.ArrayList;

/**
 * The play screen used in the game. Implements the gdx.Screen class.
 *
 * @author Ole-martin Steinnes
 * @version 1
 */
public class PlayScreen implements Screen {

    // Classes that are used.
    private MarioBros game;
    private Hud hud;
    private Mario player;

    // Camere for this screen class.
    private OrthographicCamera gameCamera;
    private Viewport gamePort;

    // Variables for the map. Map is created in tiled. (mapeditor.org)
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    private World world;
    private Box2DDebugRenderer box2DDebugRenderer;

    // Arrays for holding tilemap-objects.
    private Array<RectangleMapObject> groundObjects;
    private Array<RectangleMapObject> pipeObjects;
    private Array<RectangleMapObject> brickObjects;
    private Array<RectangleMapObject> coinObjects;


    public PlayScreen(MarioBros game) {
        this.game = game;

        int gameWidth = game.getvWidth();
        int gameHeight = game.getvHeight();
        float pixelsPerMeter = game.getPixelsPerMeter();

        // Sets up camera
        gameCamera = new OrthographicCamera();
        gamePort = new FitViewport(gameWidth / pixelsPerMeter
                ,gameHeight / pixelsPerMeter ,gameCamera);

        // Creates the HUD
        hud = new Hud(game.getBatch(), this.game);



        // Loads map, based on the camera.
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("level1.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / pixelsPerMeter);

        // Sets the camera position correctly at the start of the game
        gameCamera.position.set(gamePort.getWorldWidth()/2, gamePort.getWorldHeight()/2, 0);

        // X and Y is graviy in this context.
        world = new World(new Vector2(0,-10), true);

        // Defines the player sprite.
        player = new Mario(this.world, this.game);

        box2DDebugRenderer = new Box2DDebugRenderer();

        BodyDef bodyDef = new BodyDef();
        PolygonShape polygonShape = new PolygonShape();
        FixtureDef fixtureDef = new FixtureDef();
        Body body;

       // Fetch objects from the tile-map.
        groundObjects = map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class);
        pipeObjects = map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class);
        brickObjects = map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class);
        coinObjects = map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class);

        // create ground bodies/fixtures
        for (MapObject object : groundObjects) {

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

        // create pipe bodies/fixtures
        for (MapObject object : pipeObjects) {

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

        // create brick bodies/fixtures

        for (MapObject object : brickObjects) {

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

        // create coin bodies/fixtures

        for (MapObject object : coinObjects) {

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

    @Override
    public void show() {

    }



    @Override
    public void render(float delta) {

        Stage hudStage = hud.getStage();

        update(delta);

        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();

        box2DDebugRenderer.render(world, gameCamera.combined);

        game.getBatch().setProjectionMatrix(hudStage.getCamera().combined);

        hudStage.draw();
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    //////// HELPER METHODS /////////////////////////////////////////////////////////////

    /**
     * Method to make sure that the camera gets updated, in case it needs to move.
     * @param dt Change in time.
     */
    public void update(float dt) {

        Body playerBody = player.getB2Body();

        // Helper method that handels input, before it does anything else.
        handleInput(dt);

        world.step(1/60f, 6, 2 );

        // Only want the camera to follow the x-axis.
        gameCamera.position.x = playerBody.getPosition().x;

        gameCamera.update();

        // Tells renderer to draw only what the camera see.
        renderer.setView(gameCamera);
    }

    /**
     * Handles the input from the user.
     * @param dt Change of time
     */
    public void handleInput(float dt) {

        Body playerBody = player.getB2Body();

        // Boolean variables that on whether keys are pressed or not.
        boolean isUpKeyPressed = Gdx.input.isKeyJustPressed(Input.Keys.UP);
        boolean isRightKeyPressed = Gdx.input.isKeyPressed(Input.Keys.RIGHT);
        boolean isLeftKeyPressed = Gdx.input.isKeyPressed(Input.Keys.LEFT);

        // Boolean variables on whether the player speed are low enough to give an impulse.
        boolean checkVelocityRight = (player.getB2Body().getLinearVelocity().x <= 2);
        boolean checkVelocityLeft = (player.getB2Body().getLinearVelocity().x >= -2);

        // Vector2 variables holding the impulse values. (Change how fast the player is moving
        // aswell as how high he can jump.)
        Vector2 upImpulseVector = new Vector2(0, 4f);
        Vector2 rightImpulseVector = new Vector2(0.1f, 0);
        Vector2 leftImpulseVector = new Vector2(-0.1f, 0 );

        Vector2 worldCenter = playerBody.getWorldCenter();

        if (isUpKeyPressed){

            playerBody.applyLinearImpulse( upImpulseVector, worldCenter, true);

        } else if (isRightKeyPressed && checkVelocityRight) {

           playerBody.applyLinearImpulse( rightImpulseVector, worldCenter, true);

        } else if (isLeftKeyPressed && checkVelocityLeft) {

            playerBody.applyLinearImpulse( leftImpulseVector, worldCenter, true);

        }
    }
}
