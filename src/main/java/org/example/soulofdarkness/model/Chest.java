package org.example.soulofdarkness.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Collections;

import javafx.scene.image.Image;

public class Chest {
    private int x;
    private int y;
    private static final Random random = new Random();
    private List<Item> items;
    private boolean isOpen = false;
    private Image chestImage;

    public Chest(int[][] maze) {
        spawnChest(maze);
        initializeItems();
        this.chestImage = new Image(getClass().getResource("/assets/ChestClosed.png").toString());
    }

    private void spawnChest(int[][] maze) {
        int mazeHeight = maze.length;
        int mazeWidth = maze[0].length;

        do {
            x = random.nextInt(mazeWidth);
            y = random.nextInt(mazeHeight);
        } while (maze[y][x] != 1); // the chest will spawn on a path
    }

    private void initializeItems() {
        // Create a list of items
        items = new ArrayList<>(Arrays.asList(
                new Potion("Health Potion", "A potion that restores 10 health.", "Potion", 10,
                        new Image(getClass().getResource("/assets/Potion.png").toString())),
                new Potion("Health Potion", "A potion that restores 5 health.", "Potion", 5,
                        new Image(getClass().getResource("/assets/Potion.png").toString())),
                new Weapon("Sword", 10, "Sword", "A sword that deals 10 damage.",
                        new Image(getClass().getResource("/assets/Sword.png").toString())),
                new Weapon("Dagger", 5, "Dagger", "A dagger that deals 5 damage.",
                        new Image(getClass().getResource("/assets/Dagger.png").toString())),
                new Weapon("Axe", 15, "Axe", "An axe that deals 15 damage.",
                        new Image(getClass().getResource("/assets/Axe.png").toString())),
                new Gold("Gold", 10, "Gold", "Coins that can be used to buy items and upgrade your character."),
                new Gold("Gold", 5, "Gold", "Coins that can be used to buy items and upgrade your character.")));
        new Helmet("Helmet", 5, new Image(getClass().getResource("/assets/Helmet.png").toString()));
        new Helmet("Helmet of Protection", 20, new Image(getClass().getResource("/assets/Helmet.png").toString()));
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    // it will return a random item
    public Item getRandomItem() {
        return items.get(random.nextInt(items.size()));
    }

    // it will return a list of random items
    public List<Item> getMultipleRandomItems(int count) {
        Collections.shuffle(items);
        return new ArrayList<>(items.subList(0, Math.min(count, items.size())));
    }

    public Image getChestImage() {
        return chestImage;
    }

    public void openChest() {
        if (!isOpen) {
            isOpen = true;
            chestImage = new Image(getClass().getResource("/assets/ChestOpened.png").toString());
        }
    }

    private Image loadImage(String path) {
        try {
            return new Image(getClass().getResourceAsStream(path));
        } catch (Exception e) {
            System.err.println("Error loading image: " + path);
            return null;
        }
    }

    public void pickUpItems(Player player) {
        if (!isOpen) {
            openChest();
        }
        Item item = getRandomItem();
        player.getInventory().equipItem(item);
    }

    public boolean checkCollision(int playerX, int playerY) {
        return this.x == playerX && this.y == playerY;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpened(boolean opened) {
        isOpen = opened;
    }

}
