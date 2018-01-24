package no.ntnu;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class EntityExample extends ApplicationAdapter {
	SpriteBatch batch;
	Texture entityTexture;
        MyEntity testEntity;
	
	@Override
	public void create () {
                //We load our textures
                loadTextures();
                //We create the SpriteBatch and our entity
		batch = new SpriteBatch();
                testEntity = new MyEntity(this);
	}

	@Override
	public void render () {
                //We clear the screen
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
                
                //We draw our entity
		batch.begin();
                testEntity.render(batch);
		batch.end();
	}
	
	@Override
	public void dispose () {
                //We clean up
		batch.dispose();
		entityTexture.dispose();
	}
        
        void loadTextures(){
            //We load our textures so they're ready to be drawn
            entityTexture = new Texture("TestTexture.png");
        }
}
