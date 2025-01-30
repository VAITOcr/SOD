package com.example.souls_of_darkness.game;

import java.util.Random;

public class Enemy {
    private int x, y;
    private static final Random random = new Random();

    public Enemy(int[][] maze) {
        spawnEnemy(maze);
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
        int[] dx = {0, 0, -1, 1};
        int[] dy = {-1, 1, 0, 0};
        int dir = random.nextInt(4);

        int newX = x + dx[dir];
        int newY = y + dy[dir];

        if (maze[newY][newX] == 1) { // L'ennemi ne peut se déplacer que sur les chemins
            x = newX;
            y = newY;
        }
    }

    public boolean checkCollision(int playerX, int playerY) {
        return this.x == playerX && this.y == playerY;
    }

    public int getX() { return x; }
    public int getY() { return y; }
}
