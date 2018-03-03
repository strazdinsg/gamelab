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

    // Piano sounds
    private Sound pianoCSharp;
    private Sound pianoC;
    private Sound pianoD;
    private Sound pianoE;

    // Piano tab textures
    private Texture unpressedTexture;
    private Texture pressedTexture;

    // Piano tabs
    private ImageButton pianoTab1;
    private ImageButton pianoTab2;
    private ImageButton pianoTab3;
    private ImageButton pianoTab4;

    // Stage the piano is attached to.
    private Stage stage;

    // Viewport of the stage.
    private Viewport viewport;

    /**
     * Creates a Piano with 4 tabs.
     */
    public Piano() {

        // Loading textures for pressed and unpressed.
        loadTextures();
        // Creates sounds for each piano tab.
        createSounds();
        // Creates stage to attach table to.
        createStage();
        // Creates Table we attach piano tabs to.
        createTable();
        // Creates listeners that handle piano tab input.
        createButtonListeners();

        // Sets stage as the input processor.
        Gdx.input.setInputProcessor(stage);
    }


    public void render() {
        // Draw stage.
        stage.act();
        stage.draw();
    }

    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    public void dispose() {

        // Disposes of piano sounds
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

        // Dispose textures and stage.
        unpressedTexture.dispose();
        pressedTexture.dispose();
        stage.dispose();

    }

    private void loadTextures() {

        // Piano tab textures
        pressedTexture = new Texture("pianotab_2.png");
        unpressedTexture = new Texture("pianotab_1.png");

        // Add textures to a texture region
        TextureRegion pressedTextureRegion
                = new TextureRegion(pressedTexture);
        TextureRegion unpressedTextureRegion
                = new TextureRegion(unpressedTexture);

        // Add TextureRegion to a drawable texture region.
        TextureRegionDrawable pressedDrawable
                = new TextureRegionDrawable(pressedTextureRegion);
        TextureRegionDrawable unpressedDrawable
                = new TextureRegionDrawable(unpressedTextureRegion);

        // Creates the piano tabs.
        pianoTab1 = new ImageButton(pressedDrawable, unpressedDrawable);
        pianoTab2 = new ImageButton(pressedDrawable, unpressedDrawable);
        pianoTab3 = new ImageButton(pressedDrawable, unpressedDrawable);
        pianoTab4 = new ImageButton(pressedDrawable, unpressedDrawable);
    }

    /**
     * Creates the sounds that are used by the piano.
     */
    private void createSounds() {
        pianoCSharp = Gdx.audio.newSound(
                new FileHandle("sounds/piano-csharp.wav"));
        pianoC = Gdx.audio.newSound(
                new FileHandle("sounds/piano-c.wav"));
        pianoD = Gdx.audio.newSound(
                new FileHandle("sounds/piano-d.wav"));
        pianoE = Gdx.audio.newSound(
                new FileHandle("sounds/piano-e.wav"));
    }

    /**
     * Creates Stage that we add table to.
     */
    private void createStage() {
        Camera camera = new OrthographicCamera();
        viewport = new FitViewport(800,600,camera);
        stage = new Stage(viewport);
    }

    /**
     * Creates Table that we add piano tabs to.
     */
    private void createTable(){
        // Create and sets size of table.
        Table table = new Table();
        table.setSize(800, 600);

        // Add piano tabs to table.
        table.add(pianoTab1).padRight(4);
        table.add(pianoTab2).padRight(4);
        table.add(pianoTab3).padRight(4);
        table.add(pianoTab4).padRight(4);

        // Add table to stage.
        stage.addActor(table);
    }

    /**
     * Creates click listeners for each piano tab.
     */
    private void createButtonListeners() {

        // If tab1 is pressed, play sound.
        pianoTab1.addListener(new ClickListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                pianoC.play();
                return true;
            }
        });

        // If tab2 is pressed, play sound.
        pianoTab2.addListener(new ClickListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                pianoCSharp.play();
                return true;
            }
        });

        // If tab3 is pressed, play sound.
        pianoTab3.addListener(new ClickListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                pianoD.play();
                return true;
            }
        });

        // If tab4 is pressed, play sound.
        pianoTab4.addListener(new ClickListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                pianoE.play();
                return true;
            }
        });
    }
}
