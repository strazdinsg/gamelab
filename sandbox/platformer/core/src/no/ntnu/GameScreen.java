package no.ntnu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Thomas S. Mjåland
 */
public class GameScreen implements Screen  {

    private Viewport viewport;
    public final World world;
    private final MapLoader mapLoader;
    private final List<Body> staticCollidables = new ArrayList<Body>();
    
    private final Camera camera;
    private final SpriteBatch batch;
    private final Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();

    private final Player player;
    
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;

    public GameScreen(){
        camera = MainClass.camera;
        batch = MainClass.batch;
        world = new World(new Vector2(0, -9.81f), false);
        player = new Player(this);
        mapLoader = new MapLoader();
        world.setContactListener(new CollisionListener());
        loadLevel(1);
        createFitViewport();
    }
    
    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        player.update(delta);
        world.step(delta, 2, 4);
        
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        
        mapLoader.render();
        debugRenderer.render(world, camera.combined);
        
        batch.begin();
        player.render();
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
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

    private void createFitViewport() {
        viewport = new FitViewport(WIDTH, HEIGHT, camera);
        viewport.apply();
    }
    
    private void loadLevel(int level){
        mapLoader.loadMap("map_level_" + Integer.toString(level) + ".tmx");
        player.setPosition(new Vector2(32*1, 32*7));
        
        for (Body b:staticCollidables){
            world.destroyBody(b);
        }
        staticCollidables.clear();
        MapLayer collisionLayer = mapLoader.getMap().getLayers().get("collision_layer");
        MapObjects collisionObjects = collisionLayer.getObjects();
        
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        Body body;
        PolygonShape shape = new PolygonShape();
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        for (MapObject o:collisionObjects){
            MapProperties op = o.getProperties();
            float x = (Float)o.getProperties().get("x");
            float y = (Float)o.getProperties().get("y");
            float width = (Float)o.getProperties().get("width");
            float height = (Float)o.getProperties().get("height");
            
            body = world.createBody(bodyDef);
            body.setTransform(x+width/2, y+height/2, 0);
            shape.setAsBox(width/2, height/2);
            body.createFixture(fixtureDef);
            staticCollidables.add(body);
        }
    }

}
