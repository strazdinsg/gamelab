package com.mygdx.testgamev1.Scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.testgamev1.MarioBros;

public class Hud implements Disposable{

    private MarioBros game;

    private Stage stage;
    private Viewport viewport;

    private Integer worldTimer;
    private float timeCount;
    private Integer score;

    Label countdownLabel;
    Label scoreLabel;
    Label timeLabel;
    Label levelLabel;
    Label worldLabel;
    Label marioLabel;

    public Hud(SpriteBatch sb , MarioBros game) {

        this.game = game;

        this.worldTimer = 300;
        this.timeCount = 0;
        this.score = 0;

        int gameWidth = game.getvWidth();
        int gameHeight = game.getvHeight();
        OrthographicCamera hudCamera = new OrthographicCamera();

        viewport = new FitViewport(gameWidth, gameHeight, hudCamera);
        stage = new Stage(viewport, sb);
        Table table = new Table();
        // Sets the table to the top of our stage
        table.top();
        // Sets the table to the size of our stage.
        table.setFillParent(true);

        /*
        Creates labels.
        "%03d" - makes the label 3 digits, based on the world timer.
        LabelStyle is the font and the font color.
         */
        countdownLabel = new Label(String.format("%03d", worldTimer), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scoreLabel = new Label(String.format("%06d", score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        timeLabel = new Label("TIME", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        levelLabel =new Label("1-1", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        worldLabel = new Label("WORLD", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        marioLabel = new Label("MARIO", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        // Adds the labels at the top of the screen (padded 10 pixels)
        table.add(marioLabel).expandX().padTop(10);
        table.add(worldLabel).expandX().padTop(10);
        table.add(timeLabel).expandX().padTop(10);

        // Creates a new row.
        table.row();

        // Adds labels to the new row.
        table.add(scoreLabel).expandX();
        table.add(levelLabel).expandX();
        table.add(countdownLabel).expandX();

        // Adds the table (as an actor) to the stage.
        stage.addActor(table);
    }

    public Stage getStage() {
        return this.stage;
    }

    @Override
    public void dispose() {

    }
}
