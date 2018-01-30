package com.mygdx.game;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Hud {

        private SpriteBatch batch;
        private Stage stage;
        private Viewport viewport;
        private Camera hudCamera;

        private Table table;

        private int time;
        private int score;

        private Label countdownLabel;
        private Label scoreLabel;
        private Label timeLabel;
        private Label pointLabel;

        private Label.LabelStyle labelStyle;

        public Hud() {
                batch = new SpriteBatch();
                table = new Table();
                hudCamera = new OrthographicCamera();

                time = 100;
                score = 555;

                viewport = new FitViewport(800,600, hudCamera);
                viewport.apply();

                stage = new Stage(viewport, batch);

                createLabels();
                createTable();

                stage.addActor(table);

                viewport.apply();
        }

        public void render() {
                batch.setProjectionMatrix(stage.getCamera().combined);
                stage.draw();


                time += 1;
                countdownLabel.setText(String.format("%03d", time));
        }

        public void dispose() {
                batch.dispose();
                stage.dispose();
        }

        public void resize(int width, int height) {
                viewport.update(width, height);
        }

        private void createLabels() {
                labelStyle = new Label.LabelStyle(new BitmapFont(), Color.WHITE);

                countdownLabel = new Label(String.format("%03d",time ), labelStyle);
                scoreLabel = new Label(String.format("%06d",score), labelStyle);

                pointLabel = new Label("POINTS", labelStyle);
                timeLabel = new Label("TIME", labelStyle);
        }

        private void createTable() {
                table.top();

                table.setFillParent(true);

                table.add(pointLabel).expandX().padTop(10);
                table.add(timeLabel).expandX().padTop(10);

                table.row();

                table.add(scoreLabel).expandX();
                table.add(countdownLabel).expandX();
        }
}
