package no.ntnu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Piano {

    private PianoTab pianoTab1;
    private PianoTab pianoTab2;
    private PianoTab pianoTab3;
    private PianoTab pianoTab4;

    private Texture texture;

    public Piano() {
        pianoTab1 = new PianoTab(150, 300, 25, 25);
        pianoTab2 = new PianoTab(150, 300, 176, 25);
        pianoTab3 = new PianoTab(150, 300, 327, 25);
        pianoTab4 = new PianoTab(150, 300, 478, 25);

        Gdx.input.setInputProcessor(new PianoTabListener(this));

        texture = new Texture("pianotab_2.png");
    }


    public void render(SpriteBatch batch) {
        pianoTab1.render(batch);
        pianoTab2.render(batch);
        pianoTab3.render(batch);
        pianoTab4.render(batch);
    }

    public void onMouseClick(int x, int y) {
        if ((25<x) && (x<175) && (25<y) && (y<325)) {
            pianoTab1.setTexture(texture);
        } else if ((176<x) && (x<326) && (25<y) && (y<325)) {
            pianoTab2.setTexture(texture);
        } else if ((327<x) && (x<477) && (25<y) && (y<325)) {
            pianoTab3.setTexture(texture);
        } else if ((478<x) && (x<628) && (25<y) && (y<325)) {
            pianoTab4.setTexture(texture);
        }
    }
}
