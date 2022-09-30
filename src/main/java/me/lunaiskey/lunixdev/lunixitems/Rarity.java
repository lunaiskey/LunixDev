package me.lunaiskey.lunixdev.lunixitems;

import org.bukkit.ChatColor;

public enum Rarity {
    NO_RARITY('r'),
    JUNK('7'),
    COMMON('f'),
    UNCOMMON('a'),
    RARE('9'),
    EPIC('5'),
    LEGENDARY('6'),
    MYTHIC('d'),
    ;

    private final ChatColor color;
    Rarity(char color) {
        this.color = ChatColor.getByChar(color);
    }

    public String color() {
        return String.valueOf(color);
    }
}
