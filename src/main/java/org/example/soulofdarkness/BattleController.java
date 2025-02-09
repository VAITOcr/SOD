package org.example.soulofdarkness;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.Delayed;

import org.example.soulofdarkness.Ui.GameView;
import org.example.soulofdarkness.model.Enemy;
import org.example.soulofdarkness.model.Player;

import javafx.animation.PauseTransition;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

public class BattleController {

    @FXML
    private Pane playerField;
    @FXML
    private ImageView playerImage;
    @FXML
    private Pane enemyField;
    @FXML
    private ImageView enemyImage;
    @FXML
    private ImageView background;
    @FXML
    private ImageView frameButtons;
    @FXML
    private ImageView attackButton;
    @FXML
    private ImageView defenseButton;
    @FXML
    private ImageView fleeButton;
    @FXML
    private Label battleHPPlayer;
    @FXML
    private Label enemyHPBattle;
    @FXML
    private Label notificationBattle;

    private Stage mainStage;
    private MediaPlayer mediaPlayerWin;
    private MediaPlayer mediaPlayer;

    public void Chargement(Player player, Enemy enemy) {

    }

    public void enemyBattle(Player player, Enemy enemy) throws IOException {

        // Musique de combat
        Media media = new Media(getClass().getResource("/sound/Battle.mp3").toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.setVolume(0.02);

        // Chargement des images des ennemis
        int enemyType = enemy.getId();

        playerImage.setImage(new javafx.scene.image.Image(getClass().getResource("/assets/Player.png").toString()));
        if (enemyType == 0) {
            enemyImage.setImage(new Image(getClass().getResource("/assets/enemy1.png").toString()));
        } else if (enemyType == 1) {
            enemyImage.setImage(new Image(getClass().getResource("/assets/enemy2.png").toString()));
        } else if (enemyType == 2) {
            enemyImage.setImage(new Image(getClass().getResource("/assets/wizard.png").toString()));
        }

        battleHPPlayer.setText(String.valueOf(player.getHealth()));
        battleHPPlayer.setTextFill(javafx.scene.paint.Color.RED);
        enemyHPBattle.setText(String.valueOf(enemy.getHealth()));
        enemyHPBattle.setTextFill(javafx.scene.paint.Color.RED);

        buttonActions(player, enemy);
    }

    // Defense du player contre l'ennemi
    public void defend(Player player, Enemy enemy) {
        int defense = player.getDefense();
        int enemyAttack = enemy.getAttack();
        int damage = enemyAttack - defense;
        if (damage < 0) {
            damage = 0;
        }
        player.takeDamage(damage - 2);
        notificationBattle.setText("You took " + damage + " damage from the enemy's attack.");
        attackButton.setDisable(false);
        defenseButton.setDisable(false);
    }

    // Escape du player contre l'ennemi
    public void flee(Player player, Enemy enemy) {
        int speed = player.getSpeed();
        int enemySpeed = enemy.getSpeed();

        if (speed > enemySpeed) {
            Stage stage = (Stage) fleeButton.getScene().getWindow();
            stage.close();
        } else {
            player.takeDamage(enemy.getAttack());
        }
    }

    public void winNotification() {

        notificationBattle.setText("You won the battle!");
        notificationBattle.setTextFill(javafx.scene.paint.Color.RED);

        notificationBattle.setText("You got 10 experience points!");
        PauseTransition pauseTransition = new PauseTransition(Duration.seconds(4));

        pauseTransition.setOnFinished(event -> {
            Stage stage = (Stage) attackButton.getScene().getWindow();
            stage.close();
            mediaPlayerWin.stop();
        });
        pauseTransition.play();
    }

    public void buttonActions(Player player, Enemy enemy) throws IOException {
        attackButton.setOnMouseClicked(event -> {
            int damage = player.getAttack() - enemy.getDefense();
            attackButton.setDisable(true);
            ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(50), attackButton);
            scaleTransition.setFromX(1);
            scaleTransition.setFromY(1);
            scaleTransition.setToX(0.8);
            scaleTransition.setToY(0.8);
            scaleTransition.setAutoReverse(true);
            scaleTransition.setCycleCount(2);
            scaleTransition.play();
            player.attack(enemy);
            notificationBattle.setText("You hit the enemy for " + damage + " damage.");
            enemyHPBattle.setText(String.valueOf(enemy.getHealth()));

            PauseTransition pauseTransition = new PauseTransition(Duration.seconds(1));
            pauseTransition.setOnFinished(event1 -> {
                if (player.getHealth() <= 0 || enemy.getHealth() <= 0) {
                    try {
                        mediaPlayer.stop();
                        winNotification();
                        mediaPlayerWin = new MediaPlayer(
                                new Media(getClass().getResource("/sound/WinVictoryOST.mp3").toString()));
                        mediaPlayerWin.play();
                        mediaPlayerWin.setVolume(0.02);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    int enemyDamage = enemy.getAttack() - player.getDefense();
                    enemy.attack(player);
                    notificationBattle.setText("The enemy hit you for " + enemyDamage + " damage.");
                    battleHPPlayer.setText(String.valueOf(player.getHealth()));
                    attackButton.setDisable(false);

                }
            });
            pauseTransition.play();

        });

        defenseButton.setOnMouseClicked(event -> {
            attackButton.setDisable(true);
            defenseButton.setDisable(true);

            ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(50), defenseButton);
            scaleTransition.setFromX(1);
            scaleTransition.setFromY(1);
            scaleTransition.setToX(0.8);
            scaleTransition.setToY(0.8);
            scaleTransition.setAutoReverse(true);
            scaleTransition.setCycleCount(2);
            scaleTransition.play();
            defend(player, enemy);
            battleHPPlayer.setText(String.valueOf(player.getHealth()));
            enemyHPBattle.setText(String.valueOf(enemy.getHealth()));
        });

        fleeButton.setOnMouseClicked(event -> {
            ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(50), fleeButton);
            scaleTransition.setFromX(1);
            scaleTransition.setFromY(1);
            scaleTransition.setToX(0.8);
            scaleTransition.setToY(0.8);
            scaleTransition.setAutoReverse(true);
            scaleTransition.setCycleCount(2);
            scaleTransition.play();
            flee(player, enemy);
            battleHPPlayer.setText(String.valueOf(player.getHealth()));
            enemyHPBattle.setText(String.valueOf(enemy.getHealth()));
        });
    }
}
