package org.example.soulofdarkness.model;

import java.util.*;

public class MazeGenerator {
    private final int width;
    private final int height;
    private final int[][] maze;
    private static final Random random = new Random();

    public MazeGenerator(int width, int height) {
        this.width = width;
        this.height = height;
        this.maze = new int[height][width];

        generateMaze(1, 1); // Commencer à l'intérieur pour éviter les bords
        addEntranceAndExit();
    }

    private void generateMaze(int x, int y) {
        maze[y][x] = 1; // Marquer la cellule comme visitée

        List<int[]> directions = Arrays.asList(
                new int[] { 0, -2 }, // Haut
                new int[] { 0, 2 }, // Bas
                new int[] { -2, 0 }, // Gauche
                new int[] { 2, 0 } // Droite
        );

        Collections.shuffle(directions); // Mélange aléatoire des directions

        for (int[] dir : directions) {
            int nx = x + dir[0], ny = y + dir[1];

            if (isValid(nx, ny)) {
                maze[ny][nx] = 1; // Creuser la cellule cible
                maze[y + dir[1] / 2][x + dir[0] / 2] = 1; // Creuser le passage intermédiaire
                generateMaze(nx, ny);
            }
        }
    }

    private boolean isValid(int x, int y) {
        return x > 0 && y > 0 && x < width - 1 && y < height - 1 && maze[y][x] == 0;
    }

    private void addEntranceAndExit() {
        maze[0][1] = 1; // Entrée en haut
        maze[height - 1][width - 2] = 1; // Sortie en bas
    }

    public void printMaze() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                System.out.print(maze[y][x] == 1 ? "  " : "██");
            }
            System.out.println();
        }
    }

    public int[][] getMaze() {
        return maze;
    }

    public static void main(String[] args) {
        MazeGenerator generator = new MazeGenerator(21, 21);
        generator.printMaze();
    }
}
