package org.example.soulofdarkness;

import javafx.animation.PauseTransition;
import javafx.animation.ScaleTransition;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GameMainMenuController extends Application {

    @FXML
    private Pane GifMainMenu;

    @FXML
    private Button StartButton;

    @FXML
    private Button ExitButton;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainMenu.fxml"));
        loader.setController(this);
        Parent root = loader.load();

        // Affichage du gif pour fond menu
        ImageView gifView = new ImageView(getClass().getResource("/gif/mainMenu.gif").toString());
        gifView.setFitHeight(730);
        gifView.setFitWidth(894);
        GifMainMenu.getChildren().add(gifView);

        // Musique de fond
        String ostPath = getClass().getResource("/sound/MainMenuOST.mp3").toString();
        Media media = new Media(ostPath);
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.setVolume(0.5);

        Scene scene = new Scene(root);
        primaryStage.setTitle("Souls of Darkness - Rogue-Like");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @FXML
    private void closeGame() {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(50), ExitButton);
        scaleTransition.setFromX(1);
        scaleTransition.setFromY(1);
        scaleTransition.setToX(0.8);
        scaleTransition.setToY(0.8);
        scaleTransition.setAutoReverse(true);
        scaleTransition.setCycleCount(2);
        PauseTransition pauseTransition = new PauseTransition(Duration.millis(100));
        pauseTransition.setOnFinished(event -> {
            System.exit(0);
        });
        pauseTransition.play();
        scaleTransition.play();

    }

    @FXML
    private void startGame() throws Exception {
        Stage stage = new Stage();
        stage.setTitle("Souls of Darkness - Rogue-Like");
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/fxml/GameView.fxml"))));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
