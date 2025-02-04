package org.example.soulofdarkness.Utils.Input;

import org.example.soulofdarkness.Ui.GameView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class InputHandler {
    // Référence à l'instance de GameView pour gérer les mises à jour du joueur et du labyrinthe
    private final GameView gameView;

    // Constructeur qui initialise l'InputHandler avec l'instance de GameView
    public InputHandler(GameView gameView) {
        this.gameView = gameView;
    }

    // Méthode qui gère les événements de pression des touches
    public void handleKeyPress(KeyEvent event) {
        KeyCode key = event.getCode(); // Récupère la touche pressée

        // Récupère la position actuelle du joueur depuis GameView
        int newX = gameView.getPlayer().getX();
        int newY = gameView.getPlayer().getY();

        // Met à jour les coordonnées en fonction de la touche pressée
        switch (key) {
            case UP, Z -> newY--;
            case DOWN, S -> newY++;
            case LEFT, Q -> newX--;
            case RIGHT, D -> newX++;
        }

        // Vérifie si le mouvement est possible (pas de mur)
        if (canMove(newX, newY)) {
            gameView.updatePlayerPosition(newX, newY); // Met à jour la position du joueur dans GameView
        }
    }

    // Vérifie si le joueur peut se déplacer à la position (x, y)
    private boolean canMove(int x, int y) {
        int[][] maze = gameView.getMaze(); // Récupère le labyrinthe actuel
        return maze[y][x] == 1; // Le joueur peut se déplacer seulement sur les cases avec des chemins (valeur 1)
    }
}
