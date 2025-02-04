package org.example.soulofdarkness;

import org.example.soulofdarkness.Ui.GameView;
import org.example.soulofdarkness.Utils.Input.InputHandler;
import org.example.soulofdarkness.model.Player;

import javafx.animation.AnimationTimer;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GameController {

    @FXML
    private Canvas gameScreen;
    @FXML
    private Label hpLabel;
    @FXML
    private Label damageLabel;
    @FXML
    private Label armorLabel;
    @FXML
    private Label speedLabel;
    @FXML
    private Label expLabel;

    private Player player;
    private Stage primaryStage;
    private static final int MAZE_WIDTH = 33;
    private static final int MAZE_HEIGHT = 27;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    public void initialize() throws Exception {
        System.out.println("GameController initialized");
        player = new Player(1, 1, 100, 100, 0, 1, 10, 5, 5, 100,
                new Image(getClass().getResource("/assets/Player.png").toString()));
        updateUI();
    }

    public void updateUI() {
        hpLabel.setText("HP: " + player.getHealth() + "/" + player.getMaxHealth());
        damageLabel.setText("Damage: " + player.getAttack());
        armorLabel.setText("Armor: " + player.getDefense());
        speedLabel.setText("Speed: " + player.getSpeed());
        expLabel.setText("Exp: " + player.getExperience() + "/" + player.getMaxExperience());
    }

    public void startGame(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
    }

    public void startGame() throws Exception {
        GameView gameView = new GameView(MAZE_WIDTH, MAZE_HEIGHT, gameScreen);

        InputHandler inputHandler = new InputHandler(gameView);
        gameScreen.setOnKeyPressed(event -> inputHandler.handleKeyPress(event));
        gameScreen.setFocusTraversable(true);
        gameScreen.requestFocus();

    }

}
