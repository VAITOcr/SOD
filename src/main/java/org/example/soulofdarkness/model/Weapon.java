package org.example.soulofdarkness.model;

public class Weapon implements Item {
    private String name;
    private int damage;
    private String type;
    private String description;

    public Weapon(String name, int damage, String type) {
        this.name = name;
        this.damage = damage;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public int getDamage() {
        return damage;
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
}