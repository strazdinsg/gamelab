package no.ntnu;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class PhysicsExample extends ApplicationAdapter {

    private SpriteBatch batch;
    private Texture boxTexture;
    private Texture floorTexture;
    private Body box1, box2;
    private Body floor;
    private Sprite box1Sprite, box2Sprite;
    private Sprite floorSprite;
    private World world;

    @Override
    public void create() {
        batch = new SpriteBatch();
        boxTexture = new Texture("box.png");
        floorTexture = new Texture("floor.png");
        world = new World(new Vector2(0, -98.1f), false);
        BodyDef boxBodyDef = new BodyDef();
        BodyDef floorBodyDef = new BodyDef();

        boxBodyDef.type = BodyType.DynamicBody;
        floorBodyDef.type = BodyType.StaticBody;

        PolygonShape boxShape = new PolygonShape();
        PolygonShape floorShape = new PolygonShape();
        boxShape.setAsBox(boxTexture.getWidth() / 2, boxTexture.getHeight() / 2);
        floorShape.setAsBox(floorTexture.getWidth() / 2, floorTexture.getHeight() / 2);

        box1Sprite = new Sprite(boxTexture);
        box2Sprite = new Sprite(boxTexture);
        floorSprite = new Sprite(floorTexture);

        box1 = world.createBody(boxBodyDef);
        box2 = world.createBody(boxBodyDef);
        floor = world.createBody(floorBodyDef);

        box1.setTransform(new Vector2(100, 200), 0);
        box2.setTransform(new Vector2(150, 400), 0);
        floor.setTransform(new Vector2(0, 0), 0);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = 1.0f;
        fixtureDef.shape = boxShape;
        fixtureDef.restitution = 0.5f;
        fixtureDef.friction = 1f;

        box1.createFixture(fixtureDef);
        box2.createFixture(fixtureDef);

        fixtureDef.shape = floorShape;
        floor.createFixture(fixtureDef);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        world.step(Gdx.graphics.getDeltaTime(), 6, 2);
        box1Sprite.setPosition(box1.getPosition().x - box1Sprite.getWidth() / 2, box1.getPosition().y - box1Sprite.getHeight() / 2);
        box2Sprite.setPosition(box2.getPosition().x - box2Sprite.getWidth() / 2, box2.getPosition().y - box2Sprite.getHeight() / 2);
        floorSprite.setPosition(floor.getPosition().x - floorSprite.getWidth() / 2, floor.getPosition().y - floorSprite.getHeight() / 2);
        box1Sprite.setRotation(box1.getAngle() * 180f / 3.14f);
        box2Sprite.setRotation(box2.getAngle() * 180f / 3.14f);
        floorSprite.setRotation(floor.getAngle() * 180f / 3.14f);

        batch.begin();
        box1Sprite.draw(batch);
        box2Sprite.draw(batch);
        floorSprite.draw(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        boxTexture.dispose();
        world.dispose();
    }
}
