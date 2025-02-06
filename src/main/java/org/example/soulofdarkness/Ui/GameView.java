package org.example.soulofdarkness.Ui;

import org.example.soulofdarkness.model.Chest;
import org.example.soulofdarkness.model.Enemy;
import org.example.soulofdarkness.model.Item;
import org.example.soulofdarkness.model.MazeGenerator;
import org.example.soulofdarkness.model.Player;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameView extends Pane {
    private static final int TILE_SIZE = 30;
    private static final int OFFSET_X = 42;// Taille d'une case du labyrinthe
    private static final int OFFSET_Y = 75;
    private MazeGenerator mazeGenerator; // Générateur de labyrinthe
    private int[][] maze; // Représentation matricielle du labyrinthe
    private Player player; // Instance du joueur
    private List<Enemy> enemies; // Liste des ennemis présents dans le labyrinthe
    private List<Chest> chests; // Liste des coffres presents dans le labyrinthe
    private Canvas currentCanvas; // Canevas actuel pour le rendu
    private Canvas nextCanvas; // Canevas pour les transitions de labyrinthe

    private Image wallImage; // Image des murs
    private Image floorImage; // Image des sols

    private Image floorImage2; // Image des sols 2
    private Image wallImage2; // Image des murs 2

    private Image floorImage3; // Image des sols 3
    private Image wallImage3; // Image des murs 3

    private Random random = new Random();
    private int currentTypeMaze;

    // Constructeur : initialise la vue du jeu avec le labyrinthe et le joueur
    public GameView(int width, int height) {
        this.setPrefSize(width * TILE_SIZE, height * TILE_SIZE); // Définir la taille de la vue

        // Chargement des images des murs et sols
        this.wallImage = new Image(getClass().getResource("/assets/Wall1.png").toString());
        this.floorImage = new Image(getClass().getResource("/assets/Floor1.jpg").toString());

        this.wallImage2 = new Image(getClass().getResource("/assets/wall2.png").toString());
        this.floorImage2 = new Image(getClass().getResource("/assets/floor2.jpg").toString());

        this.wallImage3 = new Image(getClass().getResource("/assets/wall3.png").toString());
        this.floorImage3 = new Image(getClass().getResource("/assets/floor3.png").toString());

        // Initialisation du joueur à la position de départ (1,1)
        this.player = new Player(1, 1, 100, 100, 0, 1, 10, 5, 5, 100,
                new Image(getClass().getResource("/assets/Player.png").toString()));

        // Création du canevas pour le rendu du labyrinthe
        this.currentCanvas = new Canvas(width * TILE_SIZE, height * TILE_SIZE);
        this.currentCanvas.setTranslateX(OFFSET_X);
        this.currentCanvas.setTranslateY(OFFSET_Y);
        this.getChildren().add(currentCanvas);

        // Génération du labyrinthe initial
        generateNewMaze(width, height);
    }

    // Getter pour accéder à l'objet Player
    public Player getPlayer() {
        return player;
    }

    // Génère un nouveau labyrinthe et place les ennemis
    public void generateNewMaze(int width, int height) {
        mazeGenerator = new MazeGenerator(width, height);
        maze = mazeGenerator.getMaze();
        spawnEnemies(5); // Générer 5 ennemis
        spawnChests(2); // Générer 2 coffres par labyrinthe
        currentTypeMaze = random.nextInt(3);
        drawMaze(currentCanvas); // Afficher le labyrinthe
    }

    // Génère et place un nombre donné d'ennemis dans le labyrinthe
    private void spawnEnemies(int count) {
        enemies = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            enemies.add(new Enemy(maze));
        }
    }

    // Genere des coffres dans le labyrinthe
    private void spawnChests(int count) {
        chests = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            chests.add(new Chest(maze));
        }
    }

    // Met à jour la position du joueur et vérifie les collisions
    public void updatePlayerPosition(int playerX, int playerY) {
        player.movePlayer(playerX, playerY);
        checkEnemyCollision();
        checkChestCollision();
        moveEnemies();

        // Vérifie si le joueur a atteint la sortie pour générer un nouveau labyrinthe
        if (isExitReached(playerX, playerY)) {
            transitionToNewMaze();
        } else {
            drawMaze(currentCanvas);
        }
    }

    // Vérifie si le joueur a atteint la sortie en bas du labyrinthe
    private boolean isExitReached(int playerX, int playerY) {
        return playerY == maze.length - 1 && maze[playerY][playerX] == 1;
    }

    // Déplace les ennemis aléatoirement
    public void moveEnemies() {
        for (Enemy enemy : enemies) {
            enemy.moveRandomly(maze);
        }
    }

    // Vérifie les collisions entre le joueur et les ennemis
    private void checkEnemyCollision() {
        for (Enemy enemy : enemies) {
            if (enemy.checkCollision(player.getX(), player.getY())) {
                System.out.println("\uD83D\uDC80 Combat engagé avec un ennemi !");
            }
        }
    }

    private void checkChestCollision() {
        GraphicsContext gc = currentCanvas.getGraphicsContext2D();

        for (Chest chest : chests) {
            if (chest.checkCollision(player.getX(), player.getY())) {

                if (!chest.isOpen()) {
                    // pick up the item
                    Item foundItem = chest.getRandomItem();
                    player.pickUpItems(foundItem);
                    System.out.println("You found: " + foundItem.getName() + "!");

                    // open the chest
                    chest.openChest();
                }

                gc.drawImage(chest.getChestImage(), chest.getX() * TILE_SIZE, chest.getY() * TILE_SIZE);

            }
        }
    }

    // Transition animée vers un nouveau labyrinthe
    public void transitionToNewMaze() {
        int OFFSET_X = 42;
        int OFFSET_Y = 75;
        nextCanvas = new Canvas(currentCanvas.getWidth(), currentCanvas.getHeight());
        generateNewMaze(maze[0].length, maze.length);
        player.movePlayer(1, 1); // Réinitialiser la position du joueur à (1,1)
        nextCanvas.setTranslateX(OFFSET_X);
        nextCanvas.setTranslateY(OFFSET_Y);
        drawMaze(nextCanvas);

        // Au debut de la transition, le labyrinthe actuel est invisible
        nextCanvas.setOpacity(0);
        this.getChildren().add(nextCanvas);

        // Créer la transition du labyrinthe actuel vers le bas
        FadeTransition currentTransitionOut = new FadeTransition(Duration.seconds(2), currentCanvas);
        currentTransitionOut.setToValue(0);

        TranslateTransition moveOut = new TranslateTransition(Duration.seconds(1), currentCanvas);
        moveOut.setToY(-nextCanvas.getHeight());

        // Créer la transition du labyrinthe suivant vers le haut
        FadeTransition nextTransitionIn = new FadeTransition(Duration.seconds(2), nextCanvas);
        nextTransitionIn.setToValue(1);

        TranslateTransition moveIn = new TranslateTransition(Duration.seconds(1), nextCanvas);
        moveIn.setToY(75);

        // Exécuter les transitions
        ParallelTransition transitionOut = new ParallelTransition(currentTransitionOut, nextTransitionIn);

        transitionOut.setOnFinished(event -> {
            this.getChildren().remove(currentCanvas);
            currentCanvas = nextCanvas;

        });

        transitionOut.play();
    }

    // Dessine le labyrinthe, le joueur et les ennemis sur le canevas spécifié
    private void drawMaze(Canvas canvas) {

        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // Dessiner les murs et les sols
        for (int y = 0; y < maze.length; y++) {
            for (int x = 0; x < maze[0].length; x++) {
                if (maze[y][x] == 0 && currentTypeMaze == 0) {
                    gc.drawImage(floorImage, x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                } else if (maze[y][x] == 1 && currentTypeMaze == 0) {
                    gc.drawImage(wallImage, x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                } else if (maze[y][x] == 0 && currentTypeMaze == 1) {
                    gc.drawImage(floorImage2, x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                } else if (maze[y][x] == 1 && currentTypeMaze == 1) {
                    gc.drawImage(wallImage2, x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                } else if (maze[y][x] == 0 && currentTypeMaze == 2) {
                    gc.drawImage(floorImage3, x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                } else if (maze[y][x] == 1 && currentTypeMaze == 2) {
                    gc.drawImage(wallImage3, x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                }
            }
        }

        // Dessiner le joueur
        gc.drawImage(player.getImagePlayer(), player.getX() * TILE_SIZE, player.getY() * TILE_SIZE, TILE_SIZE,
                TILE_SIZE);

        // Dessiner les ennemis
        for (Enemy enemy : enemies) {
            gc.drawImage(enemy.getEnemyImage(), enemy.getX() * TILE_SIZE, enemy.getY() * TILE_SIZE, TILE_SIZE,
                    TILE_SIZE);
        }

        // Dessiner les coffres
        for (Chest chest : chests) {
            gc.drawImage(chest.getChestImage(), chest.getX() * TILE_SIZE, chest.getY() * TILE_SIZE, TILE_SIZE,
                    TILE_SIZE);

        }

    }

    // Getter pour récupérer la matrice du labyrinthe
    public int[][] getMaze() {
        return maze;
    }

    // Getter pour récupérer la liste des ennemis
    public List<Enemy> getEnemies() {
        return enemies;
    }
}
