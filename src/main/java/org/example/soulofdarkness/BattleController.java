package org.example.soulofdarkness;

import java.io.IOException;

import org.example.soulofdarkness.Ui.GameView;
import org.example.soulofdarkness.model.Enemy;
import org.example.soulofdarkness.model.Player;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

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

    private Stage mainStage;

    public void Chargement(Player player, Enemy enemy) {

    }

    public void enemyBattle(Player player, Enemy enemy) throws IOException {
        System.out.println("BattleController player instance: " + player.hashCode());

        int enemyType = enemy.getId();

        playerImage.setImage(new javafx.scene.image.Image(getClass().getResource("/assets/Player.png").toString()));
        if (enemyType == 0) {
            enemyImage.setImage(new Image(getClass().getResource("/assets/enemy1.png").toString()));
        } else if (enemyType == 1) {
            enemyImage.setImage(new Image(getClass().getResource("/assets/enemy2.png").toString()));
        } else if (enemyType == 2) {
            enemyImage.setImage(new Image(getClass().getResource("/assets/wizard.png").toString()));
        }

        buttonActions(player, enemy);
    }

    public void buttonActions(Player player, Enemy enemy) throws IOException {
        attackButton.setOnMouseClicked(event -> {
            player.attack(enemy);
            if (enemy.getHealth() <= 0) {
                player.destroyEnemy(enemy);
                Stage stage = (Stage) attackButton.getScene().getWindow();
                stage.close();
            }
            enemy.attack(player);

            if (player.getHealth() <= 0) {
                player.gameOver();
            }
        });
    }
}
