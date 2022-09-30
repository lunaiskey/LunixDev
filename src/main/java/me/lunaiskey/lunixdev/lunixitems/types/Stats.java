package me.lunaiskey.lunixdev.lunixitems.types;

import me.lunaiskey.lunixdev.lunixitems.StatType;

public class Stats {

    private int strength;
    private int defence;

    private Stats(int strength,int defence) {
        this.strength = strength;
        this.defence = defence;
    }

    public int getStrength() {
        return strength;
    }

    public int getDefence() {
        return defence;
    }

    public double getStat(StatType type) {
        return switch(type) {
            case STRENGTH -> getStrength();
            case DEFENCE -> getDefence();
            default -> 0.0;
        };
    }
}
