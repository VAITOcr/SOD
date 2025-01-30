package org.example.soulofdarkness.model;

public class Potion implements Item {

    private String name;
    private String description;
    private String type;
    private int healingAmount;

    public Potion(String name, String description, String type, int healingAmount) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.healingAmount = healingAmount;
    }

    public String getName() {
        return name;
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
