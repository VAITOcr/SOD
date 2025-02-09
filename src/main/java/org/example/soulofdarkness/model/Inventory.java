package org.example.soulofdarkness.model;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Inventory {

    private Gold gold;
    private Potion potion;
    private Weapon leftHand;
    private Weapon rightHand;
    private Helmet helmet;
    private Armor armor;
    private Boots boots;

    public Inventory() {
        this.gold = new Gold();
        this.potion = new Potion(null, null, null, 0, null);
        this.leftHand = null;
        this.rightHand = null;
    }

    public Gold getGold() {
        return gold;
    }

    public Potion getPotion() {
        return potion;
    }

    public Weapon getLeftHand() {
        return leftHand;
    }

    public Weapon getRightHand() {
        return rightHand;
    }

    public Helmet getHelmet() {
        return helmet;
    }

    public Armor getArmor() {
        return armor;
    }

    public Boots getBoots() {
        return boots;
    }

    public void setGold(Gold gold) {
        this.gold = gold;
    }

    public void setPotion(Potion potion) {
        this.potion = potion;
    }

    public void setLeftHand(Weapon leftHand) {
        this.leftHand = leftHand;
    }

    public void setRightHand(Weapon rightHand) {
        this.rightHand = rightHand;
    }

    public void setHelmet(Helmet helmet) {
        this.helmet = helmet;
    }

    public void setArmor(Armor armor) {
        this.armor = armor;
    }

    public void setBoots(Boots boots) {
        this.boots = boots;
    }

    public void equipItem(Item item) {
        if (item instanceof Gold) {
            gold = (Gold) item;
        } else if (item instanceof Potion) {
            potion = (Potion) item;
        } else if (item instanceof Weapon) {
            Weapon newWeapon = (Weapon) item;
            if (rightHand == null) {
                rightHand = newWeapon;
            } else if (rightHand.getAttack() < newWeapon.getAttack()) {
                rightHand = newWeapon;
            }
        } else if (item instanceof Helmet) {
            Helmet newHelmet = (Helmet) item;
            if (newHelmet.getDefense() > helmet.getDefense()) {
                this.helmet = newHelmet;
            }
        } else if (item instanceof Armor) {
            Armor newArmor = (Armor) item;
            if (newArmor.getDefense() > armor.getDefense()) {
                this.armor = newArmor;
            }
        } else if (item instanceof Boots) {
            Boots newBoots = (Boots) item;
            if (newBoots.getSpeed() > boots.getSpeed()) {
                this.boots = newBoots;
            }
        }
    }

    public ArrayList<Item> getItems() {
        ArrayList<Item> items = new ArrayList<>();
        items.add(gold);
        items.add(potion);
        items.add(rightHand);
        items.add(leftHand);
        return items;
    }

}
