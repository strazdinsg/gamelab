package mygame;

import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioData;
import com.jme3.audio.AudioNode;

/**
 * Our sound factory for playing music and short sounds
 */
public class Sound {

    private AudioNode music;
    private final AudioNode[] shots;
    private final AudioNode[] explosions;
    private final AudioNode[] spawns;

    private final AssetManager assetManager;

    public Sound(AssetManager assetManager) {
        this.assetManager = assetManager;
        shots = new AudioNode[4];
        explosions = new AudioNode[8];
        spawns = new AudioNode[8];

        loadSounds();
    }

    private void loadSounds() {
        music = new AudioNode(assetManager, "Sounds/Music.ogg", AudioData.DataType.Buffer);
        music.setPositional(false);
        music.setReverbEnabled(false);
        music.setLooping(true);

        for (int i = 0; i < shots.length; i++) {
            shots[i] = new AudioNode(assetManager, "Sounds/shoot-0" + (i + 1) + ".wav", 
                    AudioData.DataType.Buffer);
            shots[i].setPositional(false);
            shots[i].setReverbEnabled(false);
            shots[i].setLooping(false);
        }

        for (int i = 0; i < explosions.length; i++) {
            explosions[i] = new AudioNode(assetManager, "Sounds/explosion-0" + (i + 1) + ".wav", 
                    AudioData.DataType.Buffer);
            explosions[i].setPositional(false);
            explosions[i].setReverbEnabled(false);
            explosions[i].setLooping(false);
        }

        for (int i = 0; i < spawns.length; i++) {
            spawns[i] = new AudioNode(assetManager, "Sounds/spawn-0" + (i + 1) + ".wav", 
                    AudioData.DataType.Buffer);
            spawns[i].setPositional(false);
            spawns[i].setReverbEnabled(false);
            spawns[i].setLooping(false);
        }
    }

    public void startMusic() {
        music.play();
    }

    public void shoot() {
        shots[Helper.RANDOMIZER.nextInt(shots.length)].playInstance();
    }

    public void explosion() {
        explosions[Helper.RANDOMIZER.nextInt(explosions.length)].playInstance();
    }

    public void spawn() {
        spawns[Helper.RANDOMIZER.nextInt(spawns.length)].playInstance();
    }
}
