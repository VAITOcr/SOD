package org.example.soulofdarkness.model;

import javafx.scene.image.Image;

public class Player {

    private int x, y;
    private int health = 100;
    private int maxHealth = 100;
    private int experience = 0;
    private int level = 1;
    private int attack = 10;
    private int defense = 5;
    private int speed = 5;
    private int maxExperience = 100;
    private Image imagePlayer;
    private Inventory inventory = new Inventory();

    public Player() {
    }

    public Player(int x, int y, int health, int maxHealth, int experience, int level, int attack, int defense,
            int speed, int maxExperience, Inventory inventory, Image imagePlayer) {
        this.x = x;
        this.y = y;
        this.health = health;
        this.maxHealth = maxHealth;
        this.experience = experience;
        this.level = level;
        this.attack = attack;
        this.defense = defense;
        this.speed = speed;
        this.maxExperience = maxExperience;
        this.inventory = inventory;
        this.imagePlayer = imagePlayer;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getHealth() {
        return health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getExperience() {
        return experience;
    }

    public int getLevel() {
        return level;
    }

    public int getAttack() {
        return attack;
    }

    public int getDefense() {
        return defense;
    }

    public int getSpeed() {
        return speed;
    }

    public int getMaxExperience() {
        return maxExperience;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setMaxExperience(int maxExperience) {
        this.maxExperience = maxExperience;
    }

    public void levelUp() {
        level += 1;
        maxHealth += 10;
        health = maxHealth;
        attack += 5;
        defense += 3;
        speed += 2;
        maxExperience += 100;
        experience = 0;
    }

    public void addExperience(int amount) {
        experience += amount;
        if (experience >= maxExperience) {
            levelUp();
        }
    }

    public void takeDamage(int damage) {
        health -= damage;
        if (health <= 0) {
            gameOver();
        }
    }

    public void gameOver() {
        System.out.println("Game Over!!!");
        System.exit(0);
    }

    public void movePlayer(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public Image getImagePlayer() {
        return imagePlayer;
    }

    public void setImagePlayer(Image imagePlayer) {
        this.imagePlayer = imagePlayer;
    }

    public void pickUpItems(Item item) {
        if (item instanceof Potion) {
            Potion potion = (Potion) item;
            health += potion.getHealingAmount();
            if (health > maxHealth) {
                health = maxHealth;
            }
        }

    }

}
