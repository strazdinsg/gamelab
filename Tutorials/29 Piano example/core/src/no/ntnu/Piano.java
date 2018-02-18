package no.ntnu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Piano {

    private PianoTab pianoTab1;
    private PianoTab pianoTab2;
    private PianoTab pianoTab3;
    private PianoTab pianoTab4;

    private Sound pianoCSharp;
    private Sound pianoC;
    private Sound pianoD;
    private Sound pianoE;

    private Texture unpressedTexture;
    private Texture pressedTexture;

    public Piano() {
        loadTextures();
        createSounds();

        pianoTab1 = new PianoTab(unpressedTexture , 150, 300, 25, 25);
        pianoTab2 = new PianoTab(unpressedTexture ,150, 300, 176, 25);
        pianoTab3 = new PianoTab(unpressedTexture ,150, 300, 327, 25);
        pianoTab4 = new PianoTab(unpressedTexture ,150, 300, 478, 25);

        Gdx.input.setInputProcessor(new PianoTabListener(this));
    }


    public void render(SpriteBatch batch) {
        pianoTab1.render(batch);
        pianoTab2.render(batch);
        pianoTab3.render(batch);
        pianoTab4.render(batch);
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
    }

    public void touchDown(int x, int y) {
        if ((25<x) && (x<175) && (150<y) && (y<450)) {
            pianoTab1.setTexture(pressedTexture);
            pianoCSharp.play();
        } else if ((176<x) && (x<326) && (25<y) && (y<325)) {
            pianoTab2.setTexture(pressedTexture);
            pianoC.play();
        } else if ((327<x) && (x<477) && (25<y) && (y<325)) {
            pianoTab3.setTexture(pressedTexture);
            pianoD.play();
        } else if ((478<x) && (x<628) && (25<y) && (y<325)) {
            pianoTab4.setTexture(pressedTexture);
            pianoE.play();
        }
    }

    public void touchUp(int x, int y) {
        if ((25<x) && (x<175) && (150<y) && (y<450)) {
            pianoTab1.setTexture(unpressedTexture);
        } else if ((176<x) && (x<326) && (25<y) && (y<325)) {
            pianoTab2.setTexture(unpressedTexture);
        } else if ((327<x) && (x<477) && (25<y) && (y<325)) {
            pianoTab3.setTexture(unpressedTexture);
        } else if ((478<x) && (x<628) && (25<y) && (y<325)) {
            pianoTab4.setTexture(unpressedTexture);
        }
    }

    private void loadTextures() {
        pressedTexture = new Texture("pianotab_2.png");
        unpressedTexture = new Texture("pianotab_1.png");
    }

    private void createSounds() {
        pianoCSharp = Gdx.audio.newSound(new FileHandle("sounds/piano-csharp.wav"));
        pianoC = Gdx.audio.newSound(new FileHandle("sounds/piano-c.wav"));
        pianoD = Gdx.audio.newSound(new FileHandle("sounds/piano-d.wav"));
        pianoE = Gdx.audio.newSound(new FileHandle("sounds/piano-e.wav"));
    }
}
