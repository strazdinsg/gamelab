package no.ntnu;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class properMovingEntityExample extends ApplicationAdapter {

    private SpriteBatch batch;
    private Texture entityTexture;
    private MyEntity testEntity;

    @Override
    public void create() {
        loadTextures();
        batch = new SpriteBatch();
        testEntity = new MyEntity(entityTexture);
    }

    @Override
    public void render() {
        float frametime = Gdx.graphics.getDeltaTime();
        
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        testEntity.render(batch, frametime);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        entityTexture.dispose();
    }

    void loadTextures() {
        entityTexture = new Texture("TestTexture.png");
    }
}
