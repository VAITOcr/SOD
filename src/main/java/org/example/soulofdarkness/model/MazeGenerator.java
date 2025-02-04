package org.example.soulofdarkness.model;

import java.util.*;

public class MazeGenerator {
    private final int width;  // Largeur du labyrinthe
    private final int height; // Hauteur du labyrinthe
    private final int[][] maze; // Représentation matricielle du labyrinthe
    private static final Random random = new Random(); // Générateur de nombres aléatoires pour mélanger les directions

    // Constructeur : initialise les dimensions du labyrinthe et génère le labyrinthe
    public MazeGenerator(int width, int height) {
        this.width = width;
        this.height = height;
        this.maze = new int[height][width]; // Initialisation de la matrice avec des zéros (murs)

        generateMaze(1, 1); // Commence la génération du labyrinthe à partir de la cellule (1, 1)
        addEntranceAndExit(); // Ajoute l'entrée et la sortie au labyrinthe
    }

    // Méthode récursive pour générer le labyrinthe avec l'algorithme de backtracking
    private void generateMaze(int x, int y) {
        maze[y][x] = 1; // Marque la cellule actuelle comme visitée (1 = chemin)

        // Liste des directions possibles (haut, bas, gauche, droite), en sautant une cellule pour créer des murs
        List<int[]> directions = Arrays.asList(
                new int[] { 0, -2 }, // Haut
                new int[] { 0, 2 },  // Bas
                new int[] { -2, 0 }, // Gauche
                new int[] { 2, 0 }   // Droite
        );

        Collections.shuffle(directions); // Mélange aléatoire des directions pour un labyrinthe unique à chaque génération

        // Parcourt chaque direction pour étendre le labyrinthe
        for (int[] dir : directions) {
            int nx = x + dir[0], ny = y + dir[1]; // Coordonnées de la cellule cible

            if (isValid(nx, ny)) {
                maze[ny][nx] = 1; // Creuse la cellule cible
                maze[y + dir[1] / 2][x + dir[0] / 2] = 1; // Creuse le passage entre la cellule actuelle et la cellule cible
                generateMaze(nx, ny); // Appel récursif pour continuer à partir de la nouvelle cellule
            }
        }
    }

    // Vérifie si une cellule peut être visitée (doit être dans les limites et non encore visitée)
    private boolean isValid(int x, int y) {
        return x > 0 && y > 0 && x < width - 1 && y < height - 1 && maze[y][x] == 0;
    }

    // Ajoute une entrée en haut et une sortie en bas du labyrinthe
    private void addEntranceAndExit() {
        maze[0][1] = 1; // Crée l'entrée en haut à la colonne 1
        for (int x = width - 2; x > 0; x--) { // Cherche un chemin pour la sortie en bas à partir de la droite
            if (maze[height - 3][x] == 1) { // Vérifie si la cellule à deux lignes de la fin est un chemin
                maze[height - 1][x] = 1; // Crée la sortie en bas
                break;
            }
        }
    }

    // Affiche le labyrinthe dans la console
    public void printMaze() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                System.out.print(maze[y][x] == 1 ? "  " : "██"); // Affiche un espace pour les chemins et "██" pour les murs
            }
            System.out.println();
        }
    }

    // Retourne la matrice du labyrinthe (utile pour d'autres traitements)
    public int[][] getMaze() {
        return maze;
    }

    // Méthode principale pour tester la génération du labyrinthe
    public static void main(String[] args) {
        MazeGenerator generator = new MazeGenerator(10, 10); // Crée un labyrinthe de 10x10
        generator.printMaze(); // Affiche le labyrinthe généré
    }
}

