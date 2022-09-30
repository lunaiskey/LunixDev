package me.lunaiskey.lunixdev.utils;

import org.bukkit.ChatColor;

import java.util.List;

public class ColorUtil {

    public static String color(String string) {
        return ChatColor.translateAlternateColorCodes('&',string);
    }

    public static String[] color(String... strings) {
        for (int i = 0;i<strings.length;i++) {
            strings[i] = color(strings[i]);
        }
        return strings;
    }
}
