package org.example.soulofdarkness.model;

import org.example.soulofdarkness.GameController;
import org.example.soulofdarkness.Ui.GameView;

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
    private GameController gameController;

    public Player() {
        this.inventory = new Inventory();
    }

    public Player(int x, int y, int health, int maxHealth, int experience, int level, int attack, int defense,
            int speAed, int maxExperience, Inventory inventory, Image imagePlayer, GameController gameController) {
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
        this.gameController = gameController;
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
        int totalAttack = this.attack; // Valeur de base du joueur (10)
        System.out.println("Base attack: " + this.attack);

        if (this.inventory != null) {
            if (this.inventory.getRightHand() != null) {
                int bonusRight = this.inventory.getRightHand().getDamage();
                System.out.println("Right hand bonus: " + bonusRight);
                totalAttack += bonusRight;
            } else {
                System.out.println("No weapon equipped in right hand.");
            }
            if (this.inventory.getLeftHand() != null) {
                int bonusLeft = this.inventory.getLeftHand().getDamage();
                System.out.println("Left hand bonus: " + bonusLeft);
                totalAttack += bonusLeft;
            } else {
                System.out.println("No weapon equipped in left hand.");
            }
        }
        System.out.println("Total attack: " + totalAttack);
        return totalAttack;
    }

    public int getDefense() {
        int totalDefense = this.defense; // La valeur de base de la defense du joueur

        if (this.inventory != null) {
            if (this.inventory.getHelmet() != null) {
                totalDefense += this.inventory.getHelmet().getDefense();
            }
            if (this.inventory.getArmor() != null) {
                totalDefense += this.inventory.getArmor().getDefense();
            }
            if (this.inventory.getBoots() != null) {
                totalDefense += this.inventory.getBoots().getDefense();
            }
        }

        return totalDefense;
    }

    public int getSpeed() {
        int totalSpeed = this.speed; // La valeur de base de la vitesse du joueur

        if (this.inventory != null) {
            if (this.inventory.getBoots() != null) {
                totalSpeed += this.inventory.getBoots().getSpeed();
            } else {
                System.out.println("No boots equipped.");
            }
        }
        return totalSpeed;
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

    public void attack(Enemy enemy) {
        int totalAttack = getAttack();
        int enemyDefense = enemy.getDefense();
        int damage = totalAttack - enemyDefense;
        if (damage < 0) {
            damage = 0;
        }
        System.out.println("Player total attack: " + totalAttack);
        System.out.println("Enemy defense: " + enemyDefense);
        System.out.println("Damage: " + damage);
        enemy.takeDamage(damage);
        System.out.println("You hit the enemy for " + damage + " damage.");
        System.out.println("Enemy health: " + enemy.getHealth());

        if (enemy.getHealth() <= 0) {
            addExperience(10);
            destroyEnemy(enemy);
        }
    }

    public void destroyEnemy(Enemy enemy) {
        gameController.removeEnemyFromMaze(enemy);
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

    public void equipeBetterWeapon(Weapon weapon) {
        if (weapon.getDamage() > this.getInventory().getRightHand().getDamage()) {
            this.getInventory().setRightHand(weapon);
        }
    }

    public void equipeBetterArmor(Armor armor) {
        if (armor.getDefense() > this.getInventory().getArmor().getDefense()) {
            this.getInventory().setArmor(armor);
        }
    }

    public void equipeBetterBoots(Boots boots) {
        if (boots.getSpeed() > this.getInventory().getBoots().getSpeed()) {
            this.getInventory().setBoots(boots);
        }
    }

    public void equipeBetterHelmet(Helmet helmet) {
        if (helmet.getDefense() > this.getInventory().getHelmet().getDefense()) {
            this.getInventory().setHelmet(helmet);
        }
    }

}
