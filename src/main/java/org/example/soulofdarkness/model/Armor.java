package org.example.soulofdarkness.model;

import javafx.scene.image.Image;

public class Armor implements Item {
    private static int armorCount = 0;
    private final int id;
    private String name;
    private int defense;
    private String type;
    private String description;
    private Image image;

    public Armor(String name, int defense, Image image) {
        this.id = armorCount++;
        this.name = name;
        this.defense = defense;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getDefense() {
        return defense;
    }

    public Image getImage() {
        return new Image(getClass().getResource("/assets/Armor.png").toString());
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    @Override
    public void use() {
        System.out.println("You put on your armor and increase your defense by " + defense + ".");
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getType() {
        return type;
    }

}
