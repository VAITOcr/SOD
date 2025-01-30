package com.example.souls_of_darkness.input;

import com.example.souls_of_darkness.ui.GameView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class InputHandler {
    private final GameView gameView;
    private int playerX = 1; // Position de départ du joueur
    private int playerY = 1;

    public InputHandler(GameView gameView) {
        this.gameView = gameView;
    }

    public void handleKeyPress(KeyEvent event) {
        KeyCode key = event.getCode();
        int newX = playerX;
        int newY = playerY;

        switch (key) {
            case UP, Z -> newY--;
            case DOWN, S -> newY++;
            case LEFT, Q -> newX--;
            case RIGHT, D -> newX++;
        }

        if (canMove(newX, newY)) {
            playerX = newX;
            playerY = newY;
            gameView.updatePlayerPosition(playerX, playerY); // Mise à jour du joueur
        }
    }

    private boolean canMove(int x, int y) {
        int[][] maze = gameView.getMaze();
        return maze[y][x] == 1; // Le joueur peut se déplacer seulement sur les chemins
    }
}



