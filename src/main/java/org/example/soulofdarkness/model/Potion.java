package org.example.soulofdarkness.model;

import javafx.scene.image.Image;

public class Potion implements Item {

    private String name;
    private String description;
    private String type;
    private int healingAmount;
    private Image image;

    public Potion(String name, String description, String type, int healingAmount, Image image) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.healingAmount = healingAmount;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public  Image getImage() {
        if (type.equals("health")) {
            return new Image(getClass().getResource("/assets/Potion.png").toString());
        } else if (type.equals("mana")) {
            return new Image(getClass().getResource("/assets/Potion.png").toString());
        }
        return null;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }

    public int getHealingAmount() {
        return healingAmount;
    }

    public void use() {
        System.out.println("You drink the potion and recover " + healingAmount + " health.");
    }

}
