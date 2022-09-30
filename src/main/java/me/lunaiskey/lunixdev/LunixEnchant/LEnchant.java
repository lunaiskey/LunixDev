package me.lunaiskey.lunixdev.LunixEnchant;

import org.bukkit.event.block.BlockBreakEvent;

import java.util.Objects;

public abstract class LEnchant {

    private String enchantID;
    private String enchantName;
    private int maxLevel;

    public LEnchant(String enchantID, String enchantName, int maxLevel) {
        this.enchantID = enchantID.toUpperCase();
        this.enchantName = Objects.requireNonNullElse(enchantName, enchantID);
        this.maxLevel = maxLevel;
    }

    public abstract BlockBreakEvent onBreak(BlockBreakEvent e, int level);
    public String getEnchantID() {
        return enchantID;
    }

    public String getEnchantName() {
        return enchantName;
    }

    public int getMaxLevel() {
        return maxLevel;
    }
}
