package org.example.soulofdarkness.model;

import javafx.scene.image.Image;

public class Boots implements Item {

    private static int bootsCount = 0;
    private final int id;
    private String name;
    private int speed;
    private String type;
    private String description;
    private Image image;

    public Boots(String name, int speed, Image image) {
        this.id = bootsCount++;
        this.name = name;
        this.speed = speed;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getSpeed() {
        return speed;
    }

    public String getType() {
        return type;
    }

    public int getDefense() {
        return 0;
    }

    public String getDescription() {
        return description;
    }

    public Image getImage() {
        return new Image(getClass().getResource("/assets/BootsArmor.png").toString());
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void use() {
        System.out.println("Boots used");
    }

}
