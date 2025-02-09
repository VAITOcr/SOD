package org.example.soulofdarkness.model;

import javafx.scene.image.Image;

public class Weapon implements Item {
    private static int weaponCount = 0;
    private final int id;
    private String name;
    private int damage;
    private String type;
    private String description;
    private Image image;

    public Weapon(String name, int damage, String type, String description, Image image) {
        this.id = weaponCount++;
        this.name = name;
        this.damage = damage;
        this.type = type;
        this.description = description;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public Image getImage() {
        if (type == null) {
            System.err.println("Weapon type is null!");
            return null;
        }

        switch (type) {
            case "Sword":
                return new Image(getClass().getResource("/assets/Sword.png").toString());
            case "Excalibur":
                return new Image(getClass().getResource("/assets/Excalibur.png").toString());
            case "Dagger":
                return new Image(getClass().getResource("/assets/Dagger.png").toString());
            case "Axe":
                return new Image(getClass().getResource("/assets/Axe.png").toString());
            case "Staff":
                return new Image(getClass().getResource("/assets/Staff.png").toString());
            default:
                System.err.println("Unknown weapon type: " + type);
                return null;
        }
    }

    public String getName() {
        return name;
    }

    public int getDamage() {
        return this.damage;
    }

    public String getType() {
        return type;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void use() {
        if (type.equals("sword")) {
            System.out.println("You swing your sword and deal " + damage + " damage.");
        } else if (type.equals("dagger")) {
            System.out.println("You stab with your dagger and deal " + damage + " damage.");
        } else if (type.equals("axe")) {
            System.out.println("You swing your axe and deal " + damage + " damage.");
        } else if (type.equals("staff")) {
            System.out.println("You swing your staff and deal " + damage + " damage.");
        }
    }

    public int getAttack() {
        return this.damage;
    }
}