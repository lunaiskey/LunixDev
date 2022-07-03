package me.lunaiskey.lunixdev.lunixitems;

import org.bukkit.ChatColor;

public enum Rarity {
    JUNK('7'),
    COMMON('f'),
    UNCOMMON('a'),
    RARE('9'),
    EPIC('5'),
    LEGENDARY('6'),
    MYTHIC('d'),
    ;

    private ChatColor color;
    Rarity(char color) {
        this.color = ChatColor.getByChar(color);
    }

    public String color() {
        return String.valueOf(color);
    }
}
