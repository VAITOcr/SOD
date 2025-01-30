package org.example.soulofdarkness;

import org.example.soulofdarkness.Ui.GameView;
import org.example.soulofdarkness.Utils.Input.InputHandler;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class GameMain extends Application {
    private static final int MAZE_WIDTH = 21;
    private static final int MAZE_HEIGHT = 21;

    @Override
    public void start(Stage primaryStage) {
        GameView gameView = new GameView(MAZE_WIDTH, MAZE_HEIGHT);
        InputHandler inputHandler = new InputHandler(gameView);

        Scene scene = new Scene(new StackPane(gameView));
        scene.setOnKeyPressed(inputHandler::handleKeyPress);

        primaryStage.setTitle("Souls of Darkness - Rogue-Like");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Boucle pour faire bouger les ennemis toutes les 500ms
        new AnimationTimer() {
            private long lastUpdate = 0;

            @Override
            public void handle(long now) {
                if (now - lastUpdate > 500_000_000) { // 500 ms
                    gameView.moveEnemies();
                    lastUpdate = now;
                }
            }
        }.start();
    }

    public static void main(String[] args) {
        launch(args);
    }

}