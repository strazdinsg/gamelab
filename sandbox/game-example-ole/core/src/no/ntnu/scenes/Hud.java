package no.ntnu.scenes;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import no.ntnu.ShooterGame;
import no.ntnu.screens.GameScreen;
import no.ntnu.tools.GameState;

public class Hud implements Disposable {

    // The components needed to draw the hud (and position it)
    private SpriteBatch batch;
    private Stage stage;
    private Viewport viewport;
    private Camera hudCamera;

    // HUD table.
    private Table table;

    // Game state fields for the hud.
    private int lives;
    private int score;
    private int level;

    // HUD integer labels
    private Label levelIntLevel;
    private Label livesIntLabel;
    private Label scoreIntLabel;

    // HUD string labels
    private Label levelLabel;
    private Label livesLabel;
    private Label scoreLabel;

    // GameState instance and game-object.
    private GameState gameState;
    private final GameScreen gameScreen;

    public Hud(GameScreen gameScreen) {

        // Initiate game-object
        this.gameScreen = gameScreen;

        // Initiate HUD-fields.
        initHudFields();

        // Create labels and table containing labels.
        createLabels();
        createTable();

        // Adding table to stage.
        stage.addActor(table);
    }

    /**
     * Renders the HUD.
     */
    public void render() {

        // Draws stage content on it's batch
        // with the view of it's camera.
        stage.draw();

        // Updates HUD-label fields.
        updateHudFields();
    }

    /**
     * Disposes of components when the HUD is
     * no longer needed.
     */
    @Override
    public void dispose() {
        batch.dispose();
        stage.dispose();
    }

    /**
     * See Tutorial #23.
     * Resize the viewport, when the game screen gets resized.
     *
     * @param width of the game screen
     * @param height of the game screen
     */
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    /**
     * Fetches game state fields and updates the HUD-labels.
     */
    private void updateHudFields() {
        // Retrieves the game state score and updates it.
        score = gameState.getInt(ShooterGame.KSCORE);
        scoreIntLabel.setText(String.format("%03d", score));

        // Retrieves the game state lives and updates it.
        lives = gameState.getInt(ShooterGame.KLIVES);
        livesIntLabel.setText(String.format("%02d", lives));

        // Retrieves the game state level and updates it.
        level = gameState.getInt(ShooterGame.KLEVEL);
        levelIntLevel.setText(String.format("%03d", level));
    }

    /**
     * Creates labels to be added to table. (Score, lives, and level)
     */
    private void createLabels() {

        // Sets the label-style. Font and color.
        Label.LabelStyle labelStyle
                = new Label.LabelStyle(new BitmapFont(), Color.WHITE);

        // Create string labels.
        scoreLabel = new Label("SCORE", labelStyle);
        livesLabel = new Label("LIVES", labelStyle);
        levelLabel = new Label("LEVEL", labelStyle);

        // Create int labels. Where "%03d" should be read as 3 digits.
        livesIntLabel = new Label(String.format("%03d", lives), labelStyle);
        scoreIntLabel = new Label(String.format("%03d", score), labelStyle);
        levelIntLevel = new Label(String.format("%02d", level), labelStyle);
    }

    /**
     * Creates table to be added to stage.
     */
    private void createTable() {

        // Sets the table at the top of the screen
        table.top();
        table.setFillParent(true);

        // Adds the first row to the table.
        table.add(scoreLabel).expandX().padTop(10);
        table.add(livesLabel).expandX().padTop(10);
        table.add(levelLabel).expandX().padTop(10);

        // New row
        table.row();

        // Adds the second row to the table.
        table.add(scoreIntLabel).expandX();
        table.add(livesIntLabel).expandX();
        table.add(levelIntLevel).expandX();
    }

    /**
     * Initiates fields the HUD needs.
     */
    private void initHudFields() {
        // Fetch game state instance.
        gameState = GameState.getInstance();

        // Initiate batch, table and 2D camera.
        batch = new SpriteBatch();
        table = new Table();
        hudCamera = new OrthographicCamera();

        // Initiate viewport (see Tutorial #23)
        viewport = new FitViewport(800, 600, hudCamera);
        viewport.apply();

        // Set up the stage
        stage = new Stage(viewport, batch);
    }
}