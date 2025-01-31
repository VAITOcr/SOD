package org.example.soulofdarkness;

import org.example.soulofdarkness.Ui.GameView;
import org.example.soulofdarkness.Utils.Input.InputHandler;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MainMenuController {



    @FXML
    private void initialize() {
        startButton.setOnAction(event -> startButton.getScene().getWindow().hide());
        exitButton.setOnAction(event -> System.exit(0));
    }

    private void Button() {
        GameView gameView = new GameView(10, 10);
        InputHandler inputHandler = new InputHandler(gameView);
        Scene scene = new Scene(new StackPane(gameView));
        scene.setOnKeyPressed(inputHandler::handleKeyPress);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

}
