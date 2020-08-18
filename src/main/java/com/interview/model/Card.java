package com.interview.model;

public class Card {

    private final int damage;

    public Card(int damage) {
        this.damage = damage;
    }

    public int getDamage() {
        return damage;
    }

    @Override
    public String toString() {
        return "Card " +
            "damage {" + damage +"}";
    }
}
