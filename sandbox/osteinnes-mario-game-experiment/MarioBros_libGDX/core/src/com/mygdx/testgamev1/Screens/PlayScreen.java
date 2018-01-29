package com.mygdx.testgamev1.Screens;

/*
        This game was inspired by the libGDX-tutorial by Brent Aureli on YouTube.
        Link to the tutorial playlist: https://www.youtube.com/playlist?list=PLZm85UZQLd2SXQzsF-a0-pPF6IWDDdrXt
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.testgamev1.MarioBros;
import com.mygdx.testgamev1.Scenes.Hud;
import com.mygdx.testgamev1.Sprites.Mario;
import com.mygdx.testgamev1.Tools.B2WorldCreator;
import com.mygdx.testgamev1.Tools.WorldContactListener;

/**
 * The play screen is where everything that is shown on the screen is set up.
 * The camera(s) gets set up, as well as objects get defined as physical-objects with friction
 * gravity and density (box2d).
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
    private OrthogonalTiledMapRenderer mapRenderer;

    private World world;
    private Box2DDebugRenderer box2DDebugRenderer;

    private TextureAtlas textureAtlas;

    private float pixelsPerMeter;

    private Texture background;



    /**
     * Constructs the play screen.
     * @param game The game-object that the PlayScreen is built on.
     */
    public PlayScreen(MarioBros game) {

        this.game = game;


        initiatePlayScreen();

        background = new Texture("sky1.png");

    }

    private void initiatePlayScreen() {

        loadTextures();
        setUpCamera();
        createHud();
        loadMap();
        setUpWorld();
        createB2World();
        addPlayer();
        setWorldContactListener();

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

        game.getBatch().begin();
        game.getBatch().draw(background, 0 , 0);
        game.getBatch().end();

        mapRenderer.render();

        box2DDebugRenderer.render(world, gameCamera.combined);

        game.getBatch().setProjectionMatrix(gameCamera.combined);
        game.getBatch().begin();
        player.draw(game.getBatch());
        game.getBatch().end();

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
        map.dispose();
        mapRenderer.dispose();
        world.dispose();
        box2DDebugRenderer.dispose();
        hud.dispose();
    }

    //////// GETTER METHODS /////////////////////////////////////////////////////////////

    /**
     * Returns the texture atlas.
     * The texture atlas contains every moving objects textures. In this case
     * it contains Mario and his enemies.
     *
     * @return the texture atlas.
     */
    public TextureAtlas getTextureAtlas() {
        return this.textureAtlas;
    }

    /**
     * Returns the hud of the play screen.
     * @return the hud of the play screen.
     */
    public Hud getHud() {
        return hud;
    }

    public MarioBros getGame() {
        return game;
    }

    public World getWorld() {
        return world;
    }

    public TiledMap getMap() {
        return map;
    }

    //////// HELPER METHODS /////////////////////////////////////////////////////////////

    /**
     * Loads the textures of Mario and his enemies from the assets-folder.
     */
    private void loadTextures() {
        textureAtlas = new TextureAtlas("Mario_and_Enemies.pack");
    }

    /**
     * Creates the HUD of the game.
     */
    private void createHud() {
        // Creates the HUD
        hud = new Hud(this);
    }

    /**
     * Sets up the camera, and it's posiition at the start of the game.
     */
    private void setUpCamera() {
        int gameWidth = game.getvWidth();
        int gameHeight = game.getvHeight();
        pixelsPerMeter = game.getPixelsPerMeter();

        // Sets up camera
        gameCamera = new OrthographicCamera();
        gamePort = new FitViewport(gameWidth / pixelsPerMeter
                ,gameHeight / pixelsPerMeter ,gameCamera);

        // Sets the camera position correctly at the start of the game
        gameCamera.position.set(gamePort.getWorldWidth()/2, gamePort.getWorldHeight()/2, 0);
    }

    /**
     * Loads the map, made in Tiled.
     */
    private void loadMap() {

        // Loads map, based on the camera.
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("level1.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map, 1 / pixelsPerMeter);

    }

    /**
     * Sets up the Box2D-world
     */
    private void setUpWorld() {
        // X and Y is graviy in this context.
        world = new World(new Vector2(0,-10), true);

        box2DDebugRenderer = new Box2DDebugRenderer();
    }

    /**
     * Creates the Box2D-world.
     */
    private void createB2World() {

        // Creates the solid objects in the world (ground, bricks, pipes and coins)
        new B2WorldCreator(this);

    }

    /**
     * Adds the player to the game (both the texture and box2d-component).
     */
    private void addPlayer() {
        // Defines the player sprite.
        player = new Mario(this);
    }

    private void setWorldContactListener() {
        WorldContactListener contactListener = new WorldContactListener();

        world.setContactListener(contactListener);
    }

    /**
     * Method to make sure that the camera gets updated, in case it needs to move.
     * @param dt Change in time.
     */
    private void update(float dt) {

        Body playerBody = player.getB2Body();

        // Helper method that handles input, before it does anything else.
        handleInput(dt);

        player.update(dt);
        hud.update(dt);

        world.step(1/60f, 6, 2 );

        // Only want the camera to follow the x-axis.
        gameCamera.position.x = playerBody.getPosition().x;

        gameCamera.update();

        // Tells mapRenderer to draw only what the camera see.
        mapRenderer.setView(gameCamera);
    }

    /**
     * Handles the input from the user.
     * @param dt Change of time
     */
    private void handleInput(float dt) {

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
