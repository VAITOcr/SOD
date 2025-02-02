package org.example.soulofdarkness;

import org.example.soulofdarkness.Ui.GameView;
import org.example.soulofdarkness.Utils.Input.InputHandler;

import javafx.animation.AnimationTimer;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GameController {

    @FXML
    private Canvas gameScreen;

    private Stage primaryStage;
    private static final int MAZE_WIDTH = 21;
    private static final int MAZE_HEIGHT = 21;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    public void initialize() throws Exception {
        System.out.println("GameController initialized");
    }

    public void startGame(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
    }

    public void startGame() throws Exception {
        GameView gameView = new GameView(MAZE_WIDTH, MAZE_HEIGHT, gameScreen);
        gameView.generateNewMaze(MAZE_WIDTH, MAZE_HEIGHT);

        InputHandler inputHandler = new InputHandler(gameView);
        gameScreen.setOnKeyPressed(event -> inputHandler.handleKeyPress(event));
        gameScreen.setFocusTraversable(true);
        gameScreen.requestFocus();

    }

}
