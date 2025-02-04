package org.example.soulofdarkness;

import org.example.soulofdarkness.Ui.GameView;
import org.example.soulofdarkness.Utils.Input.InputHandler;
import org.example.soulofdarkness.model.Player;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
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

    private Player player; // Instance du joueur
    private Stage primaryStage; // Fenêtre principale du jeu
    private static final int MAZE_WIDTH = 33; // Largeur du labyrinthe
    private static final int MAZE_HEIGHT = 27; // Hauteur du labyrinthe
    private GameView gameView; // Vue principale du jeu qui gère le labyrinthe et les personnages

    // Méthode pour définir la fenêtre principale depuis l'extérieur
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    // Méthode appelée automatiquement à l'initialisation du contrôleur
    @FXML
    public void initialize() throws Exception {
        System.out.println("GameController initialized");

        // Initialisation du joueur avec ses attributs de départ
        player = new Player(1, 1, 100, 100, 0, 1, 10, 5, 5, 100,
                new javafx.scene.image.Image(getClass().getResource("/assets/Player.png").toString()));

        updateUI(); // Mise à jour de l'interface utilisateur avec les informations du joueur
    }

    // Met à jour les labels de l'interface avec les statistiques du joueur
    public void updateUI() {
        hpLabel.setText("HP: " + player.getHealth() + "/" + player.getMaxHealth());
        damageLabel.setText("Damage: " + player.getAttack());
        armorLabel.setText("Armor: " + player.getDefense());
        speedLabel.setText("Speed: " + player.getSpeed());
        expLabel.setText("Level: " + player.getLevel() + " Exp: " + player.getExperience() + "/" + player.getMaxExperience());
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
