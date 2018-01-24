package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

/**
 * Plays audio when the play-methods are called.
 * If a looping audio is set, one must dispose it when the process is done.
 */
public class PlayAudio {

    private Music music;
    private Sound sound;

    public PlayAudio() {

    }

    /**
     * Plays music
     * @param isLooping     Whether the music should be looping or played once.
     * @param filepath      The filepath of the audio file.
     */
    public void playMusic(boolean isLooping, String filepath) {
        music = Gdx.audio.newMusic(Gdx.files.internal(filepath));
        music.setLooping(isLooping);
        music.play();
    }

    /**
     * Disposes of the audio-loop when the method is called.
     */
    public void stopLoopingMusic() {
        if (music != null) {
            music.dispose();
            music = null;
        }
    }

    /**
     * Plays sound based on the given filepath.
     * @param filepath      The filepath of the audio file.
     */
    public void playSound(String filepath) {
        sound = Gdx.audio.newSound(Gdx.files.internal(filepath));
        sound.play();
    }
}
