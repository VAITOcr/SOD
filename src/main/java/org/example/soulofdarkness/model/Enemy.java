package org.example.soulofdarkness.model;

import java.util.Random;

import javafx.scene.image.Image;

public class Enemy {
    private int x, y;
    private static final Random random = new Random();
    private Image enemyImage = new Image(getClass().getResource("/assets/enemy1.png").toString());
    private Image enemyImage2 = new Image(getClass().getResource("/assets/enemy2.png").toString());
    private Image enemyImage3 = new Image(getClass().getResource("/assets/wizard.png").toString());

    public Enemy(int[][] maze) {
        spawnEnemy(maze);
        assignRandomImage();
    }

    private void assignRandomImage() {
        Random random = new Random();
        switch (random.nextInt(3)) {
            case 0:
                this.enemyImage = enemyImage;
                break;
            case 1:
                this.enemyImage = enemyImage2;
                break;
            case 2:
                this.enemyImage = enemyImage3;
                break;
        }
    }

    private void spawnEnemy(int[][] maze) {
        int mazeHeight = maze.length;
        int mazeWidth = maze[0].length;

        do {
            x = random.nextInt(mazeWidth);
            y = random.nextInt(mazeHeight);
        } while (maze[y][x] != 1); // S'assurer que l'ennemi spawn sur un chemin
    }

    public void moveRandomly(int[][] maze) {
        int[] dx = { 0, 0, -1, 1 };
        int[] dy = { -1, 1, 0, 0 };
        int dir = random.nextInt(4);

        int newX = x + dx[dir];
        int newY = y + dy[dir];

        if (maze[newY][newX] == 1) { // L'ennemi ne peut se déplacer que sur les chemins
            x = newX;
            y = newY;
        }
    }

    public Image getEnemyImage() {
        return enemyImage;
    }

    public boolean checkCollision(int playerX, int playerY) {
        return this.x == playerX && this.y == playerY;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
