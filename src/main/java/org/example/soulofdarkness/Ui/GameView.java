package org.example.soulofdarkness.Ui;

import org.example.soulofdarkness.model.Enemy;
import org.example.soulofdarkness.model.MazeGenerator;
import org.example.soulofdarkness.model.Player;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class GameView extends Canvas {
    private static final int TILE_SIZE = 30; // Taille d'une case
    private MazeGenerator mazeGenerator;
    private int[][] maze;
    private Player player = new Player(1, 1, 100, 100, 0, 1, 10, 5, 5, 100);
    // private int playerX = 1, playerY = 1; // Position du joueur
    private List<Enemy> enemies = new ArrayList<>(); // Liste des ennemis
    private Canvas canvas;

    public GameView(int width, int height, Canvas canvas) {
        this.canvas = canvas;
        this.canvas.setWidth(width * TILE_SIZE);
        this.canvas.setHeight(height * TILE_SIZE);
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

    public void updatePlayerPosition(int playerX, int playerY) {
        player.movePlayer(playerX, playerY);
        System.out.println("Position du joueur : (" + player.getX() + ", " + player.getY() + ")");
        // Mettre à jour la position du joueur et des ennemis
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
            if (enemy.checkCollision(player.getX(), player.getY())) {
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

    public void drawMaze() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        for (int y = 0; y < maze.length; y++) {
            for (int x = 0; x < maze[0].length; x++) {
                gc.setFill(maze[y][x] == 0 ? Color.BLACK : Color.WHITE);
                gc.fillRect(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
            }
        }

        // Dessiner le joueur en bleu
        gc.setFill(Color.BLUE);
        gc.fillOval(player.getX() * TILE_SIZE + TILE_SIZE / 4, player.getY() * TILE_SIZE + TILE_SIZE / 4, TILE_SIZE / 2,
                TILE_SIZE / 2);

        // Dessiner les ennemis en rouge
        gc.setFill(Color.RED);
        for (Enemy enemy : enemies) {
            gc.fillOval(enemy.getX() * TILE_SIZE + TILE_SIZE / 4, enemy.getY() * TILE_SIZE + TILE_SIZE / 4,
                    TILE_SIZE / 2, TILE_SIZE / 2);
        }
    }
}
