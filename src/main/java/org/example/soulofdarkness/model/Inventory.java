package org.example.soulofdarkness.model;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Inventory {

    private Gold gold;
    private Potion potion;
    private Weapon weapon;
    private Helmet helmet;
    private Armor armor;

    public Inventory() {
        this.gold = new Gold();
        this.potion = new Potion(null, null, null, 0, null);
        this.weapon = new Weapon(null, 0, null, null, null);
    }

    public Inventory(Gold gold, Potion potion, Weapon weapon) {
        this.gold = gold;
        this.potion = potion;
        this.weapon = weapon;
    }

    public Gold getGold() {
        return gold;
    }

    public Potion getPotion() {
        return potion;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public Helmet getHelmet() {
        return helmet;
    }

    public Armor getArmor() {
        return armor;
    }

    public void setGold(Gold gold) {
        this.gold = gold;
    }

    public void setPotion(Potion potion) {
        this.potion = potion;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public void setHelmet(Helmet helmet) {
        this.helmet = helmet;
    }

    public void setArmor(Armor armor) {
        this.armor = armor;
    }

    public void equipItem(Item item) {
        if (item instanceof Gold) {
            gold = (Gold) item;
        } else if (item instanceof Potion) {
            potion = (Potion) item;
        } else if (item instanceof Weapon) {
            Weapon newWeapon = (Weapon) item;
            if (newWeapon.getDamage() > weapon.getDamage()) {
                this.weapon = newWeapon;
            }
        }
    }

    public ArrayList<Item> getItems() {
        ArrayList<Item> items = new ArrayList<>();
        items.add(gold);
        items.add(potion);
        items.add(weapon);
        return items;
    }

    // Exemple d'utilisation des mains droite et gauche
    public Inventory getRightHand() {
        return new Inventory(null, null, weapon);
    }

    public Inventory getLeftHand() {
        return new Inventory(null, potion, null);
    }

}
