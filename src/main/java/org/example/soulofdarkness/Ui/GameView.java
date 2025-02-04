package org.example.soulofdarkness.Ui;

import org.example.soulofdarkness.model.Enemy;
import org.example.soulofdarkness.model.MazeGenerator;
import org.example.soulofdarkness.model.Player;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameView extends Canvas {
    private static final int TILE_SIZE = 30; // Taille d'une case
    private MazeGenerator mazeGenerator;
    private int[][] maze;
    private Player player = new Player(1, 1, 100, 100, 0, 1, 10, 5, 5, 100,
            new Image(getClass().getResource("/assets/Player.png").toString()));

    // Images for assets
    private Image wallImage = new Image(getClass().getResource("/assets/Wall1.png").toString());
    private Image floorImage = new Image(getClass().getResource("/assets/Floor1.jpg").toString());
    

    // private int playerX = 1, playerY = 1; // Position du joueur
    private List<Enemy> enemies = new ArrayList<>(); // Liste des ennemis
    private Canvas canvas;

    public GameView(int width, int height, Canvas canvas) {
        this.canvas = canvas;
        this.canvas.setWidth(width * TILE_SIZE);
        this.canvas.setHeight(height * TILE_SIZE);
        generateNewMaze(width, height);
        moveEnemies();
    }

    public void generateNewMaze(int width, int height) {
        mazeGenerator = new MazeGenerator(width, height);
        maze = mazeGenerator.getMaze();
        spawnEnemies(5); // Générer 3 ennemis
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
        moveEnemies();
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
                if (maze[y][x] == 0) {
                    gc.drawImage(floorImage, x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                } else if (maze[y][x] == 1) {
                    gc.drawImage(wallImage, x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                }
            }
        }

        // Dessiner le joueur en bleu

        gc.drawImage(player.getImagePlayer(),
                player.getX() * TILE_SIZE,
                player.getY() * TILE_SIZE,
                TILE_SIZE, TILE_SIZE);

        // Dessiner les ennemis en rouge
        Random random = new Random();
        for (Enemy enemy : enemies) {
            gc.drawImage(enemy.getEnemyImage(),
                    enemy.getX() * TILE_SIZE,
                    enemy.getY() * TILE_SIZE,
                    TILE_SIZE, TILE_SIZE);
        }
    }
}
