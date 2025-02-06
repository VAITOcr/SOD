package org.example.soulofdarkness.model;

import javafx.scene.image.Image;

public class Helmet implements Item {
    private String name;
    private String description;
    private String type;
    private int defense;
    private Image image;

    public Helmet(String name, int defense, Image image) {
        this.name = name;
        this.defense = defense;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public int getDefense() {
        return defense;
    }

    public  Image getImage() {
        return new Image(getClass().getResource("/assets/Helmet.png").toString());
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void use() {

        System.out.println("You put on your helmet and increase your defense by " + defense + ".");
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
