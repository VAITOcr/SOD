package org.example.soulofdarkness;

import org.example.soulofdarkness.Ui.GameView;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PauseTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class GameMainMenuController extends Application {

    @FXML
    private Pane GifMainMenu;

    @FXML
    private Button StartButton;

    @FXML
    private Button ExitButton;

    private Stage primaryStage;
    private MediaPlayer mediaPlayer;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainMenu.fxml"));
        loader.setController(this);
        Parent root = loader.load();

        // Affichage du gif pour fond menu
        ImageView gifView = new ImageView(getClass().getResource("/gif/mainMenu.gif").toString());
        gifView.setFitWidth(1279);
        gifView.setFitHeight(1043);
        GifMainMenu.getChildren().add(gifView);

        // Musique de fond
        String ostPath = getClass().getResource("/sound/MainMenuOST.mp3").toString();
        Media media = new Media(ostPath);
        this.mediaPlayer = new MediaPlayer(media);
        this.mediaPlayer.play();
        this.mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        this.mediaPlayer.setVolume(0.1);

        Scene scene = new Scene(root);
        primaryStage.initStyle(StageStyle.UTILITY);
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
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(50), StartButton);
        scaleTransition.setFromX(1);
        scaleTransition.setFromY(1);
        scaleTransition.setToX(0.8);
        scaleTransition.setToY(0.8);
        scaleTransition.setAutoReverse(true);
        scaleTransition.setCycleCount(2);
        scaleTransition.play();
        Timeline fadeOut = new Timeline(
                new KeyFrame(Duration.seconds(2),
                        new KeyValue(mediaPlayer.volumeProperty(), 0)));
        fadeOut.setOnFinished(e -> mediaPlayer.stop());
        fadeOut.play();

        PauseTransition pauseTransition = new PauseTransition(Duration.millis(1000));
        pauseTransition.setOnFinished(event -> {
            try {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/GameFXML.fxml"));
                Parent gameView = loader.load();
                mediaPlayer.stop();

                Scene scene = new Scene(gameView);
                Stage currentStage = (Stage) StartButton.getScene().getWindow();

                GameController gameController = loader.getController();

                gameController.setPrimaryStage((Stage) StartButton.getScene().getWindow());
                currentStage.setScene(scene);
                gameController.startGame();

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        pauseTransition.play();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
