package org.example.soulofdarkness;

import java.util.List;

import org.example.soulofdarkness.Ui.GameView;
import org.example.soulofdarkness.Utils.Input.InputHandler;
import org.example.soulofdarkness.model.Armor;
import org.example.soulofdarkness.model.Chest;
import org.example.soulofdarkness.model.Helmet;
import org.example.soulofdarkness.model.Inventory;
import org.example.soulofdarkness.model.Item;
import org.example.soulofdarkness.model.Player;
import org.example.soulofdarkness.model.Potion;
import org.example.soulofdarkness.model.Weapon;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class GameController {

    // Références aux éléments de l'interface utilisateur (UI)
    @FXML
    private Canvas gameScreen; // Canvas où le jeu est initialement dessiné
    @FXML
    private Label hpLabel; // Label pour afficher les points de vie (HP)
    @FXML
    private Label damageLabel; // Label pour afficher les dégâts
    @FXML
    private Label armorLabel; // Label pour afficher l'armure
    @FXML
    private Label speedLabel; // Label pour afficher la vitesse
    @FXML
    private Label expLabel; // Label pour afficher l'expérience et le niveau
    @FXML
    private ImageView helmetID;
    @FXML
    private ImageView rightHandID;
    @FXML
    private ImageView leftHandID;
    @FXML
    private ImageView chestID;
    @FXML
    private ImageView bootsID;
    @FXML
    private Label gameNotificationID;

    private Player player; // Instance du joueur
    private Inventory inventory = new Inventory();
    private List<Chest> chests;
    private Stage primaryStage; // Fenêtre principale du jeu
    private static final int MAZE_WIDTH = 23; // Largeur du labyrinthe
    private static final int MAZE_HEIGHT = 19; // Hauteur du labyrinthe
    private GameView gameView; // Vue principale du jeu qui gère le labyrinthe et les personnages

    // Méthode pour définir la fenêtre principale depuis l'extérieur
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    // Méthode appelée automatiquement à l'initialisation du contrôleur
    @FXML
    public void initialize() throws Exception {
        System.out.println("GameController initialized");

        // Creaation de la premiere weapon
        Weapon basicSword = new Weapon("Basic Sword", 5, "Sword", "A basic sword",
                new javafx.scene.image.Image(getClass().getResource("/assets/Sword.png").toString()));

        // Creation des premiers armors
        Helmet basicHelmet = new Helmet("Basic Helmet", 5,
                new javafx.scene.image.Image(getClass().getResource("/assets/Helmet.png").toString()));
        Armor basicArmor = new Armor("Basic Armor", 5,
                new javafx.scene.image.Image(getClass().getResource("/assets/Armor.png").toString()));

        // Initialisation du joueur avec ses attributs de départ
        player = new Player(1, 1, 100, 100, 0, 1, 10, 5, 5, 100, inventory,
                new javafx.scene.image.Image(getClass().getResource("/assets/Player.png").toString()));
        player.getInventory().setWeapon(basicSword);
        player.getInventory().setHelmet(basicHelmet);
        player.getInventory().setArmor(basicArmor);

        updateUI(); // Mise à jour de l'interface utilisateur avec les informations du joueur
    }

    public void updateGameNotification(String message) {
        gameNotificationID.setVisible(true);
        gameNotificationID.setTextFill(javafx.scene.paint.Color.WHITE);
        gameNotificationID.setText(message);
    }

    // Met à jour les labels de l'interface avec les statistiques du joueur
    public void updateUI() {
        hpLabel.setText("HP: " + player.getHealth() + "/" + player.getMaxHealth());
        damageLabel.setText("Damage: " + player.getAttack());
        armorLabel.setText("Armor: " + player.getDefense());
        speedLabel.setText("Speed: " + player.getSpeed());
        expLabel.setText(
                "Level: " + player.getLevel() + " Exp: " + player.getExperience() + "/" + player.getMaxExperience());

        Weapon rightHand = player.getInventory().getRightHand().getWeapon();
        if (rightHand != null) {
            rightHandID.setImage(rightHand.getImage());
        } else {
            rightHandID.setImage(null);
        }

        Weapon leftHand = player.getInventory().getLeftHand().getWeapon();
        if (leftHand != null) {
            leftHandID.setImage(leftHand.getImage());
        } else {
            leftHandID.setImage(null);
        }

        Helmet helmet = player.getInventory().getHelmet();
        if (helmet != null) {
            helmetID.setImage(helmet.getImage());
        } else {
            helmetID.setImage(null);
        }

        Armor armor = player.getInventory().getArmor();
        if (armor != null) {
            chestID.setImage(armor.getImage());
        } else {
            chestID.setImage(null);
        }
    }

    public void pickUpItemFromChest(Chest chest) {
        if (chest != null) {
            Item item = chest.getRandomItem();
            if (item instanceof Weapon) {
                Weapon weapon = (Weapon) item;
                player.equipeBetterWeapon(weapon);
            } else if (item instanceof Potion) {
                Potion potion = (Potion) item;
                int heal = potion.getHealingAmount();
                player.setHealth(player.getHealth() + heal);
                if (player.getHealth() > player.getMaxHealth()) {
                    player.setHealth(player.getMaxHealth());

                } else {
                    gameNotificationID.setText("Health of the player at max");
                }
            } else {
                chest.pickUpItems(player);
                System.out.println("Item picked up: " + item.getName());
            }
            updateUI();
        }
    }

    // Lance le jeu avec une fenêtre spécifiée
    public void startGame(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        startGame();
    }

    // Initialise la vue du jeu et configure les contrôles
    public void startGame() throws Exception {

        // Crée une nouvelle instance de GameView avec les dimensions du labyrinthe
        gameView = new GameView(MAZE_WIDTH, MAZE_HEIGHT);
        gameView.setGameController(this);

        // Remplace le canvas initial par la GameView dans l'interface utilisateur
        Pane rootPane = (Pane) gameScreen.getParent();
        rootPane.getChildren().remove(gameScreen);
        rootPane.getChildren().add(gameView);

        // Initialisation du gestionnaire des entrées clavier
        InputHandler inputHandler = new InputHandler(gameView);

        // Configuration des événements clavier pour déplacer le joueur
        gameView.setOnKeyPressed(event -> inputHandler.handleKeyPress(event));
        gameView.setFocusTraversable(true); // Permet à la GameView de recevoir les événements clavier
        gameView.requestFocus(); // Met le focus sur la GameView pour démarrer les interactions
    }
}
