package com.mygdx.testgamev1.Scenes;

/*
        This game was inspired by the libGDX-tutorial by Brent Aureli on YouTube.
        Link to the tutorial playlist: https://www.youtube.com/playlist?list=PLZm85UZQLd2SXQzsF-a0-pPF6IWDDdrXt
 */


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

/**
 * Creates the HUD. With information about time, score and which level the player is on.
 */
public class Hud implements Disposable{

    private MarioBros game;

    private Stage stage;
    private SpriteBatch spriteBatch;

    private Integer worldTimer;
    private Integer score;

    // Time counting has yet to be implemented.
    private float timeCount;


    private Table table;
    private Label countdownLabel;
    private Label scoreLabel;
    private Label timeLabel;
    private Label levelLabel;
    private Label worldLabel;
    private Label marioLabel;

    /**
     * Takes in the MarioBros and SpriteBatch fields. Creates the HUD.
     * @param sb The SpriteBatch object to base the stage on.
     * @param game The MarioBros object the HUD is for.
     */
    public Hud(SpriteBatch sb , MarioBros game) {

        this.spriteBatch = sb;
        this.game = game;

        createHud();

    }

    /**
     * Creates the HUD by calling on helper methods.
     */
    private void createHud() {
        setFields();
        setUpStage();
        setUpTable();
        createLabels();
        addToTable();
        attachToStage();
    }

    @Override
    public void dispose() {

    }


    //////////// GETTER METHODS /////////////////////////////////////////

    /**
     * Returns the stage the HUD is on.
     * @return the stage the HUD is on.
     */
    public Stage getStage() {
        return this.stage;
    }

    //////////// HELPER METHODS //////////////////////////////////////////

    /**
     * Sets fields for the creation of the HUD.
     */
    private void setFields() {
        worldTimer = 300;
        timeCount = 0;
        score = 0;
    }

    /**
     * Sets up the stage that we attach our table to.
     */
    private void setUpStage() {

        int gameWidth = game.getvWidth();
        int gameHeight = game.getvHeight();

        OrthographicCamera hudCamera = new OrthographicCamera();

        Viewport viewport = new FitViewport(gameWidth, gameHeight, hudCamera);
        stage = new Stage(viewport, spriteBatch);

    }

    /**
     * Sets up the table that we attach to our stage.
     */
    private void setUpTable() {

        table = new Table();
        // Sets the table to the top of our stage
        table.top();
        // Sets the table to the size of our stage.
        table.setFillParent(true);

    }



    /**
     * Creates labels.
     * "%03d" - makes the label 3 digits, based on the world timer.
     * LabelStyle is the font and the font color.
     */
    private void createLabels() {

        countdownLabel = new Label(String.format("%03d", worldTimer), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scoreLabel = new Label(String.format("%06d", score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        timeLabel = new Label("TIME", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        levelLabel =new Label("1-1", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        worldLabel = new Label("WORLD", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        marioLabel = new Label("MARIO", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

    }

    /**
     * Adds the created labels to the table-field.
     */
    private void addToTable() {

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

    /**
     * Add's the created table to the stage.
     */
    private void attachToStage() {
        stage.addActor(table);
    }
}
