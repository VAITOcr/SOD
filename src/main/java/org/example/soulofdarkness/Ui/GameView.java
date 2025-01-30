package com.example.souls_of_darkness.ui;

import com.example.souls_of_darkness.game.MazeGenerator;
import com.example.souls_of_darkness.game.Enemy;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class GameView extends Canvas {
    private static final int TILE_SIZE = 30; // Taille d'une case
    private MazeGenerator mazeGenerator;
    private int[][] maze;
    private int playerX = 1, playerY = 1; // Position du joueur
    private List<Enemy> enemies = new ArrayList<>(); // Liste des ennemis

    public GameView(int width, int height) {
        super(width * TILE_SIZE, height * TILE_SIZE);
        generateNewMaze(width, height);
    }

    public void generateNewMaze(int width, int height) {
        mazeGenerator = new MazeGenerator(width, height);
        maze = mazeGenerator.getMaze();
        spawnEnemies(3); // Générer 3 ennemis
        drawMaze();
    }

    private void spawnEnemies(int count) {
        enemies.clear();
        for (int i = 0; i < count; i++) {
            enemies.add(new Enemy(maze));
        }
    }

    public void updatePlayerPosition(int x, int y) {
        this.playerX = x;
        this.playerY = y;
        checkEnemyCollision();
        drawMaze();
    }

    public void moveEnemies() {
        for (Enemy enemy : enemies) {
            enemy.moveRandomly(maze);
        }
        drawMaze();
    }

    private void checkEnemyCollision() {
        for (Enemy enemy : enemies) {
            if (enemy.checkCollision(playerX, playerY)) {
                System.out.println("💀 Combat engagé avec un ennemi !");
                // Ici, on pourra appeler un `CombatSystem`
            }
        }
    }

    public int[][] getMaze() {
        return maze;
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    private void drawMaze() {
        GraphicsContext gc = getGraphicsContext2D();
        gc.clearRect(0, 0, getWidth(), getHeight());

        for (int y = 0; y < maze.length; y++) {
            for (int x = 0; x < maze[0].length; x++) {
                gc.setFill(maze[y][x] == 0 ? Color.BLACK : Color.WHITE);
                gc.fillRect(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
            }
        }

        // Dessiner le joueur en bleu
        gc.setFill(Color.BLUE);
        gc.fillOval(playerX * TILE_SIZE + TILE_SIZE / 4, playerY * TILE_SIZE + TILE_SIZE / 4, TILE_SIZE / 2, TILE_SIZE / 2);

        // Dessiner les ennemis en rouge
        gc.setFill(Color.RED);
        for (Enemy enemy : enemies) {
            gc.fillOval(enemy.getX() * TILE_SIZE + TILE_SIZE / 4, enemy.getY() * TILE_SIZE + TILE_SIZE / 4, TILE_SIZE / 2, TILE_SIZE / 2);
        }
    }
}
