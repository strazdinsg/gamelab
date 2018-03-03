package no.ntnu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Piano {

    private Sound pianoCSharp;
    private Sound pianoC;
    private Sound pianoD;
    private Sound pianoE;

    private Texture unpressedTexture;
    private Texture pressedTexture;

    private ImageButton button;
    private ImageButton button2;
    private ImageButton button3;
    private ImageButton button4;

    private Stage stage;
    private Table table;

    private Viewport viewport;

    public Piano() {
        loadTextures();
        createSounds();
        createStage();
        createTable();
        createButtonListeners();

        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);
    }


    public void render() {
        stage.act();
        stage.draw();
    }

    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    public void dispose() {
        if (pianoE != null) {
            pianoE.dispose();
        }
        if (pianoD != null) {
            pianoD.dispose();
        }
        if (pianoC != null) {
            pianoC.dispose();
        }
        if (pianoCSharp != null) {
            pianoCSharp.dispose();
        }

        unpressedTexture.dispose();
        pressedTexture.dispose();
        stage.dispose();

    }

    private void loadTextures() {
        pressedTexture = new Texture("pianotab_2.png");
        unpressedTexture = new Texture("pianotab_1.png");

        TextureRegion pressedTextureRegion = new TextureRegion(pressedTexture);
        TextureRegion unpressedTextureRegion = new TextureRegion(unpressedTexture);

        TextureRegionDrawable pressedDrawable = new TextureRegionDrawable(pressedTextureRegion);
        TextureRegionDrawable unpressedDrawable = new TextureRegionDrawable(unpressedTextureRegion);

        button = new ImageButton(pressedDrawable, unpressedDrawable);
        button2 = new ImageButton(pressedDrawable, unpressedDrawable);
        button3 = new ImageButton(pressedDrawable, unpressedDrawable);
        button4 = new ImageButton(pressedDrawable, unpressedDrawable);
    }

    private void createSounds() {
        pianoCSharp = Gdx.audio.newSound(new FileHandle("sounds/piano-csharp.wav"));
        pianoC = Gdx.audio.newSound(new FileHandle("sounds/piano-c.wav"));
        pianoD = Gdx.audio.newSound(new FileHandle("sounds/piano-d.wav"));
        pianoE = Gdx.audio.newSound(new FileHandle("sounds/piano-e.wav"));
    }

    private void createStage() {
        Camera camera = new OrthographicCamera();
        viewport = new FitViewport(800,600,camera);
        stage = new Stage(viewport);
    }

    private void createTable(){
        table = new Table();
        table.setSize(800, 600);

        table.add(button).padRight(4);
        table.add(button2).padRight(4);
        table.add(button3).padRight(4);
        table.add(button4).padRight(4);
    }

    private void createButtonListeners() {
        button.addListener(new ClickListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                pianoC.play();
                return true;
            }
        });
        button2.addListener(new ClickListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                pianoCSharp.play();
                return true;
            }
        });
        button3.addListener(new ClickListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                pianoD.play();
                return true;
            }
        });
        button4.addListener(new ClickListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                pianoE.play();
                return true;
            }
        });
    }
}
