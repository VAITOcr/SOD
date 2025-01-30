package org.example.soulofdarkness.model;

public class Gold implements Item {
    private String name = "Gold";
    private int mount = 0;
    private String type = "Gold";
    private String description = "Coins that can be used to buy items and upgrade your character.";

    public Gold() {
    }

    public Gold(String name, int mount, String type, String description) {
        this.name = name;
        this.mount = mount;
        this.type = type;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public int getMount() {
        return mount;
    }

    public void setMount(int mount) {
        this.mount = mount;
    }

    public void use() {
        System.out.println("You pick up: " + mount + " gold.");
    }

}
