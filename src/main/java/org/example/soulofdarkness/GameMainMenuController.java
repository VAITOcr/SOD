package org.example.soulofdarkness;


import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class GameMainMenuController extends Application {

    @FXML
    private Pane GifMainMenu;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainMenu.fxml"));
        loader.setController(this);
        Parent root = loader.load();
        ImageView gifView = new ImageView(getClass().getResource("/gif/mainMenu.gif").toString());
        gifView.setFitHeight(730);
        gifView.setFitWidth(894);
        GifMainMenu.getChildren().add(gifView);

        Scene scene = new Scene(root);
        primaryStage.setTitle("Souls of Darkness - Rogue-Like");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

// private static final int MAZE_WIDTH = 21;
// private static final int MAZE_HEIGHT = 21;

/**
 * @Override
 *           public void start(Stage primaryStage) {
 *           GameView gameView = new GameView(MAZE_WIDTH, MAZE_HEIGHT);
 *           InputHandler inputHandler = new InputHandler(gameView);
 * 
 *           Scene scene = new Scene(new StackPane(gameView));
 *           scene.setOnKeyPressed(inputHandler::handleKeyPress);
 * 
 *           primaryStage.setTitle("Souls of Darkness - Rogue-Like");
 *           primaryStage.setScene(scene);
 *           primaryStage.show();
 * 
 *           // Boucle pour faire bouger les ennemis toutes les 500ms
 *           new AnimationTimer() {
 *           private long lastUpdate = 0;
 * 
 * @Override
 *           public void handle(long now) {
 *           if (now - lastUpdate > 500_000_000) { // 500 ms
 *           gameView.moveEnemies();
 *           lastUpdate = now;
 *           }
 *           }
 *           }.start();
 *           }
 **/
