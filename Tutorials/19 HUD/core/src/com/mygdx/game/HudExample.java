package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.*;


public class HudExample extends Game {

        private Hud hud;

        @Override
        public void create() {
                hud = new Hud();
        }

        @Override
        public void render() {
                // Avoid flickering.
                Gdx.gl.glClearColor(0, 0, 0, 1);
                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

                hud.render();
        }

        @Override
        public void resize(int width, int height) {
                hud.resize(width, height);
        }

        @Override
        public void dispose() {
                hud.dispose();
        }
}
